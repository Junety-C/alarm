package cn.junety.alarm.sender.client.impl;

import cn.junety.alarm.base.common.ConfigKey;
import cn.junety.alarm.base.entity.QueueMessage;
import cn.junety.alarm.base.util.HttpUtils;
import cn.junety.alarm.sender.client.Client;
import cn.junety.alarm.sender.configuration.Configuration;
import cn.junety.alarm.sender.sms.SmsFlowWkx;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caijt on 2017/3/4.
 */
public class SmsClient extends Client {

    public SmsClient(String name, String queueName) {
        super(name, queueName, "sms");
    }

    @Override
    protected int send(String message) {
        try {
            logger.debug("start send mail alarm, body:{}", message);
            QueueMessage queueMessage = JSON.parseObject(message, QueueMessage.class);
            if(send(queueMessage.getTitle(), queueMessage.getContent(), queueMessage.getReceivers())) {
                this.markDeliveryStatus(queueMessage.getLogId(), channel);

                return queueMessage.getReceivers().size();
            }
            return 0;
        } catch (Exception e) {
            logger.error("handle mail message error, caused by", e);
            return 0;
        }
    }

    @Override
    protected String getPushQuantityKey() {
        return ConfigKey.SMS_PUSH_QUANTITY.value();
    }

    @Override
    protected String getPushDailyKey() {
        return ConfigKey.SMS_PUSH_DAILY.value();
    }

    private boolean send(String title, String content, List<String> receivers) {
        try {
            Map<String,Object> sms = new HashMap<String,Object>();
            Map<String,Object> flow = new HashMap<String,Object>();
            sms.put("@id","100000");
            sms.put("@type","SmsSend");
            sms.put("smsGroupID","");
            sms.put("smsContent",content);
            sms.put("smsContacts",receivers);
            sms.put("smsFormat","sms");
            sms.put("smsType","single");
            flow.put("request",sms);
            String param = JSON.toJSONString(flow);
            System.out.println(param+"\r\n"+Configuration.SMS_SEND_URL+"\r\n"+Configuration.SMS_TOKEN);
            String res = HttpUtils.sendPost(Configuration.SMS_SEND_URL,param,Configuration.SMS_TOKEN);
            flow.clear();
            sms.clear();
            logger.info("send sms {} to {} success", res,JSON.toJSONString(receivers));
            return true;
        } catch (Exception e) {
            logger.error("send sms to {} fail, caused by", JSON.toJSONString(receivers), e);
            return false;
        }
    }
}
