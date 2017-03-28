package cn.junety.alarm.sender.client.impl;

import cn.junety.alarm.base.entity.QueueMessage;
import cn.junety.alarm.sender.client.Client;
import cn.junety.alarm.sender.common.Configure;
import cn.junety.alarm.sender.wechat.WechatClientProxy;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by caijt on 2017/3/29.
 */
public class WechatClient extends Client {

    private static final Logger logger = LoggerFactory.getLogger(WechatClient.class);

    private WechatClientProxy client;

    public WechatClient(String name, String queueName) {
        super(name, queueName, "wechat");
        client = new WechatClientProxy();
    }

    @Override
    protected boolean send(String message) {
        try {
            QueueMessage queueMessage = JSON.parseObject(message, QueueMessage.class);
            client.send(queueMessage.getReceivers(), queueMessage.getContent());
            this.markDeliveryStatus(queueMessage.getLogId(), channel);
            return true;
        } catch (Exception e) {
            logger.error("handle mail message error, caused by", e);
            return false;
        }
    }

    public static void main(String[] args) {
        WechatClient wechatClient = new WechatClient("wechat", Configure.WECHAT_QUEUE);
        wechatClient.start();
    }
}
