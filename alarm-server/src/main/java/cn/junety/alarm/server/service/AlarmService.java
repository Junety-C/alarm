package cn.junety.alarm.server.service;

import cn.junety.alarm.api.AlarmClient;
import cn.junety.alarm.base.common.ConfigKey;
import cn.junety.alarm.server.dao.*;
import cn.junety.alarm.base.entity.*;
import cn.junety.alarm.base.exception.AlarmNotFoundException;
import cn.junety.alarm.base.redis.JedisFactory;
import cn.junety.alarm.server.channel.Channel;
import cn.junety.alarm.server.common.ResponseHelper;
import cn.junety.alarm.server.common.IdGenerator;
import cn.junety.alarm.server.vo.AlarmForm;
import cn.junety.alarm.server.vo.AlarmMessage;
import cn.junety.alarm.server.vo.JsonConfig;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by caijt on 2017/1/28.
 */
@Service
public class AlarmService {

    private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);

    // 限频参数
    private static final int DEFAULT_TIME_INTERVAL_LIMIT = 5;   //分钟
    private static final int DEFAULT_SEND_TIMES_LIMIT = 10;     //次数

    @Autowired
    private AlarmDao alarmDao;
    @Autowired
    private ReceiverDao receiverDao;
    @Autowired
    private AlarmLogDao alarmLogDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private GroupDao groupDao;


    @Autowired
    private Channel wechatChannel;
    @Autowired
    private Channel smsChannel;
    @Autowired
    private Channel mailChannel;
    @Autowired
    private Channel qqChannel;

    /**
     * 处理告警上报信息
     * @param alarmForm 告警表单
     * @return 处理结果
     */
    public String handle(AlarmForm alarmForm) {
        try {
            // 已发送的接收人id
            Set<Integer> sent = new HashSet<>();
            // 根据表单配置获取告警列表
            List<Alarm> alarmList = getAlarmList(alarmForm);
            // 一个告警信息共用一个上报id
            long reportId = IdGenerator.generate(ConfigKey.ALARM_REPORT_ID_POOL.value());
            for (Alarm alarm : alarmList) {
                // 获取该告警接收人
                List<Receiver> receiverList = receiverDao.getReceiverByGroupId(alarm.getGroupId());
                // 接收人去重,防止一条告警信息多次发送给同一个接收人
                receiverList = removeDuplicatedReceiver(receiverList, sent);
                if(receiverList.size() != 0) {
                    Group group = groupDao.getGroupById(alarm.getGroupId());
                    AlarmMessage alarmMessage = buildAlarmMessage(alarm, alarmForm, receiverList, reportId, group);
                    if (alarmForm.isTest()) {
                        logger.debug("ignore test alarm, message:{}", alarmMessage);
                        saveAlarmLog(alarmMessage, AlarmStatusEnum.TEST);
                    } else if(isFrequencyLimited(alarmMessage)) {
                        logger.debug("alarm frequency limit, message:{}", alarmMessage);
                        saveAlarmLog(alarmMessage, AlarmStatusEnum.LIMIT);
                    } else {
                        saveAlarmLog(alarmMessage, AlarmStatusEnum.CREATE);
                        triggerAlarm(alarmMessage);
                    }
                }
            }
            // 告警信息无接收人, 发送告警信息给管理员
            if(sent.size() == 0) {
                AlarmClient.info(1, "alarm.server.receiver", String.format("接收到无接收人的告警,reportId:%s", reportId));
                return ResponseHelper.buildResponse(4004, "reason", "receiver is empty");
            }
            return ResponseHelper.buildResponse(2000);
        } catch (AlarmNotFoundException e) {
            logger.error("alarm not found by code:{}", alarmForm.getCode());
            return ResponseHelper.buildResponse(4004, "reason", "alarm not found.");
        }
    }

    /**
     * 获取告警列表，根据code和routeKey过滤告警
     * @param alarmForm 告警表单
     * @return 告警列表
     */
    private List<Alarm> getAlarmList(AlarmForm alarmForm) {
        Integer code = alarmForm.getCode();
        List<Alarm> alarms = alarmDao.getAllByCode(code);

        String routeKey = alarmForm.getRouteKey();
        List<Alarm> legalAlarm = new ArrayList<>();
        if(Strings.isNullOrEmpty(alarmForm.getRouteKey())) {
            for (Alarm alarm : alarms) {
                if (Strings.isNullOrEmpty(alarm.getRouteKey())) {
                    legalAlarm.add(alarm);
                }
            }
        } else {
            for (Alarm alarm : alarms) {
                if (isMatch(routeKey, alarm.getRouteKey())) {
                    legalAlarm.add(alarm);
                }
            }
        }
        if (legalAlarm.size() == 0) {
            throw new AlarmNotFoundException();
        }
        return legalAlarm;
    }

    /**
     * route key匹配
     * @param userRouteKey 用户发送的route key
     * @param alarmRouteKey 告警配置的route key
     * @return 匹配结果
     */
    private boolean isMatch(String userRouteKey, String alarmRouteKey) {
        if(Strings.isNullOrEmpty(alarmRouteKey)) {
            return false;
        }
        List<String> userPart = Splitter.on(".").splitToList(userRouteKey);
        List<String> alarmPart = Splitter.on(".").splitToList(alarmRouteKey);

        if(userPart.size() != alarmPart.size()) return false;

        for(int i = 0; i < userPart.size(); i++) {
            if (!userPart.get(i).equals(alarmPart.get(i)) && !"*".equals(alarmPart.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 接收人去重
     * @param receiverList 接收人列表
     * @param sent 已发送的接收人
     * @return 去重后的接收人列表
     */
    private List<Receiver> removeDuplicatedReceiver(List<Receiver> receiverList, Set<Integer> sent) {
        List<Receiver> uniqueReceiverList = new ArrayList<>();
        for(Receiver receiver : receiverList) {
            if(sent.add(receiver.getId())) {
                uniqueReceiverList.add(receiver);
            }
        }
        return uniqueReceiverList;
    }

    /**
     * 根据用户配置，转发告警信息到各个渠道
     * @param alarmMessage 告警相关信息
     */
    private void triggerAlarm(AlarmMessage alarmMessage) {
        JsonConfig config = new JsonConfig(alarmMessage.getConfig());

        if(config.getConfig("sms", false)) {
            smsChannel.send(alarmMessage);
        }
        if(config.getConfig("mail", false)) {
            mailChannel.send(alarmMessage);
        }
        if(config.getConfig("wechat", false)) {
            wechatChannel.send(alarmMessage);
        }
        if(config.getConfig("qq", false)) {
            qqChannel.send(alarmMessage);
        }
    }

    /**
     * 告警限频处理
     * @param alarmMessage 告警相关信息
     * @return 是否限频
     */
    private boolean isFrequencyLimited(AlarmMessage alarmMessage) {
        JsonConfig config = new JsonConfig(alarmMessage.getConfig());
        if (!config.getConfig("freq_limit", true)) {
            return false;
        }
        switch (alarmMessage.getLevel()) {
            case DEBUG:
                Integer debugTimes = config.getConfig("debug_times", DEFAULT_SEND_TIMES_LIMIT);
                Integer debugInterval = config.getConfig("debug_interval", DEFAULT_TIME_INTERVAL_LIMIT);
                return checkFrequencyLimited(alarmMessage, debugTimes, debugInterval);
            case INFO:
                Integer infoTimes = config.getConfig("info_times", DEFAULT_SEND_TIMES_LIMIT);
                Integer infoInterval = config.getConfig("info_interval", DEFAULT_TIME_INTERVAL_LIMIT);
                return checkFrequencyLimited(alarmMessage, infoTimes, infoInterval);
            case ERROR:
                Integer errorTimes = config.getConfig("error_times", DEFAULT_SEND_TIMES_LIMIT);
                Integer errorInterval = config.getConfig("error_interval", DEFAULT_TIME_INTERVAL_LIMIT);
                return checkFrequencyLimited(alarmMessage, errorTimes, errorInterval);
            default:
                logger.info("unknow alarm level, throw away, content:{}", JSON.toJSONString(alarmMessage));
                return true;
        }
    }

    /**
     * 判断是否限频
     * @param alarmMessage 告警相关信息
     * @param times 限频次数
     * @param interval 限频间隔
     * @return
     */
    private boolean checkFrequencyLimited(AlarmMessage alarmMessage, int times, int interval) {
        String key = alarmMessage.getCode() + "." + alarmMessage.getRouteKey();
        try (Jedis jedis = JedisFactory.getJedisInstance()) {
            long currentTimes = jedis.incr(key);
            if (currentTimes == 1) {
                jedis.expire(key, interval * 60);
            }
            return currentTimes > times;
        } catch (Exception e) {
            logger.error("check frequency limited error, caused by", e);
            return false;
        }
    }

    /**
     * 告警日志落地
     * @param alarmMessage 告警相关信息
     * @param alarmStatusEnum 告警处理状态
     */
    private void saveAlarmLog(AlarmMessage alarmMessage, AlarmStatusEnum alarmStatusEnum) {
        AlarmLog alarmLog = new AlarmLog();
        alarmLog.setReportId(alarmMessage.getReportId());
        alarmLog.setCode(alarmMessage.getCode());
        alarmLog.setAlarmName(alarmMessage.getAlarmName());
        alarmLog.setProjectName(alarmMessage.getProjectName());
        alarmLog.setModuleName(alarmMessage.getModuleName());
        alarmLog.setLevel(alarmMessage.getLevel());
        alarmLog.setGroupName(alarmMessage.getGroup());
        alarmLog.setReceivers(getReceiversName(alarmMessage.getReceivers()));
        alarmLog.setContent(alarmMessage.getContent(512));
        alarmLog.setIp(alarmMessage.getIp());
        alarmLog.setStatus(alarmStatusEnum.value());
        alarmLog.setDeliveryStatus("");
        alarmLog.setCreateTime(alarmMessage.getCreateTime());

        alarmLogDao.save(alarmLog);

        alarmMessage.addLogId(alarmLog.getId());
    }

    private AlarmMessage buildAlarmMessage(Alarm alarm, AlarmForm alarmForm, List<Receiver> receiverList, long reportId, Group group) {
        Module module = moduleDao.getModuleById(alarm.getModuleId());
        Project project = projectDao.getProjectById(module.getProjectId());

        AlarmMessage alarmMessage = new AlarmMessage();
        alarmMessage.addAlarm(alarm)
                .addAlarmForm(alarmForm)
                .addProject(project)
                .addModule(module)
                .addReceivers(receiverList)
                .addReportId(reportId)
                .addGroup(group);

        return alarmMessage;
    }

    private String getReceiversName(List<Receiver> receivers) {
        if(receivers.size() == 0) {
            return "";
        } else if(receivers.size() == 1) {
            return receivers.get(0).getName();
        } else {
            String receiverNames = receivers.get(0).getName();
            for(int i = 1; i < receivers.size(); i++) {
                receiverNames += "," + receivers.get(i).getName();
            }
            return receiverNames;
        }
    }
}
