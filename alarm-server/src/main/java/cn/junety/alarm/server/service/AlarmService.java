package cn.junety.alarm.server.service;

import cn.junety.alarm.base.dao.*;
import cn.junety.alarm.base.entity.*;
import cn.junety.alarm.base.exception.AlarmNotFoundException;
import cn.junety.alarm.base.util.DateUtil;
import cn.junety.alarm.server.channel.Channel;
import cn.junety.alarm.server.common.Configuration;
import cn.junety.alarm.server.common.HttpHelper;
import cn.junety.alarm.server.common.IdGenerator;
import cn.junety.alarm.server.vo.AlarmForm;
import cn.junety.alarm.server.vo.AlarmMessage;
import cn.junety.alarm.server.vo.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by caijt on 2017/1/28.
 */
@Service
public class AlarmService {

    private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);

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
    private Channel wechatChannel;
    @Autowired
    private Channel smsChannel;
    @Autowired
    private Channel mailChannel;
    @Autowired
    private Channel qqChannel;

    public ResponseEntity<?> handle(AlarmForm alarmForm) {
        try {
            // 已发送的接收人id
            Set<Integer> sent = new HashSet<>();
            // 根据表单配置获取告警列表
            List<Alarm> alarms = getAlarms(alarmForm);
            // 一个告警信息共用一个上报id
            long reportId = IdGenerator.generate(Configuration.ALARM_REPORT_ID_POOL);
            // 遍历告警列表
            for (Alarm alarm : alarms) {
                // 获取该告警接收人
                List<Receiver> receivers = receiverDao.getByGroupId(alarm.getGroupId());
                // 接收人去重,防止一条告警信息多次发送给同一个接收人
                receivers = removeDuplicatedReceiver(receivers, sent);
                if(receivers.size() != 0) {
                    AlarmMessage alarmMessage = buildAlarmMessage(alarm, alarmForm, receivers, reportId);
                    saveAlarmLog(alarmMessage);
                    triggerAlarm(alarmMessage);
                }
            }
            // 告警信息无接收人, 发送告警信息给管理员
            if(sent.size() == 0) {
                // TODO 无接收人的告警
            }
            return HttpHelper.buildResponse(200, 2000, "success");
        } catch (AlarmNotFoundException e) {
            logger.error("alarm not found by code:{}", alarmForm.getCode());
            return HttpHelper.buildResponse(400, 4000, "alarm not found.");
        }
    }

    private List<Alarm> getAlarms(AlarmForm alarmForm) {
        Integer code = alarmForm.getCode();
        String routeKey = alarmForm.getRouteKey() == null ? "" : alarmForm.getRouteKey().trim();
        List<Alarm> alarms;
        if(routeKey.length() == 0) {
            alarms = alarmDao.getByCode(code);
        } else {
            alarms = alarmDao.getByCodeWithRouteKey(code, routeKey);
        }
        if(alarms.size() == 0) {
            throw new AlarmNotFoundException();
        }
        return alarms;
    }

    private List<Receiver> removeDuplicatedReceiver(List<Receiver> receivers, Set<Integer> sent) {
        List<Receiver> uniqueReceivers = new ArrayList<>();
        for(Receiver receiver : receivers) {
            if(sent.add(receiver.getId())) {
                uniqueReceivers.add(receiver);
            }
        }
        return uniqueReceivers;
    }

    private void triggerAlarm(AlarmMessage alarmMessage) {
        JsonConfig config = new JsonConfig(alarmMessage.getConfig());
        if(config.getConfig("is_send_sms", false)) {
            smsChannel.send(alarmMessage);
        }
        if(config.getConfig("is_send_mail", false)) {
            mailChannel.send(alarmMessage);
        }
        if(config.getConfig("is_send_wechat", false)) {
            wechatChannel.send(alarmMessage);
        }
        if(config.getConfig("is_send_qq", false)) {
            qqChannel.send(alarmMessage);
        }
    }

    private void saveAlarmLog(AlarmMessage alarmMessage) {
        AlarmLog alarmLog = new AlarmLog();
        alarmLog.setReportId(alarmMessage.getReportId());
        alarmLog.setCode(alarmMessage.getCode());
        alarmLog.setAlarmName(alarmMessage.getAlarmName());
        alarmLog.setProjectName(alarmMessage.getProjectName());
        alarmLog.setModuleName(alarmMessage.getModuleName());
        alarmLog.setLevel(alarmMessage.getLevel());
        alarmLog.setReceivers(getReceiversName(alarmMessage.getReceivers()));
        alarmLog.setContent(alarmMessage.getContent(512));
        alarmLog.setIp(alarmMessage.getIp());
        alarmLog.setStatus(AlarmStatus.CREATE);
        alarmLog.setDeliveryStatus("");
        alarmLog.setCreateTime(alarmMessage.getCreateTime());

        alarmLogDao.save(alarmLog);

        alarmMessage.addLogId(alarmLog.getId());
    }

    private AlarmMessage buildAlarmMessage(Alarm alarm, AlarmForm alarmForm, List<Receiver> receivers, long reportId) {
        Module module = moduleDao.getById(alarm.getModuleId());
        Project project = projectDao.getById(module.getProjectId());

        AlarmMessage alarmMessage = new AlarmMessage();
        alarmMessage.addAlarm(alarm)
                .addAlarmForm(alarmForm)
                .addProject(project)
                .addModule(module)
                .addReceivers(receivers)
                .addReportId(reportId);

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
