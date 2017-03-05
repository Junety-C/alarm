package cn.junety.alarm.sender.client.impl;

import cn.junety.alarm.base.entity.QueueMessage;
import cn.junety.alarm.sender.client.Client;
import cn.junety.alarm.sender.common.Configure;
import cn.junety.alarm.sender.common.DeliveryStatusHelper;
import com.alibaba.fastjson.JSON;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by caijt on 2017/3/4.
 */
public class MailClient extends Client {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    private static final String CHANNEL = "mail";

    public MailClient(String queueName) {
        super(queueName);
    }

    @Override
    protected boolean send(QueueMessage queueMessage) {
        try {
            if(send(queueMessage.getTitle(), queueMessage.getContent(), queueMessage.getReceivers())) {
                DeliveryStatusHelper.mark(queueMessage.getLogId(), CHANNEL);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("handle mail message error, caused by", e);
            return false;
        }
    }

    private boolean send(String title, String content, List<String> receivers) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setAuthenticator(new DefaultAuthenticator(Configure.MAIL_SENDER, Configure.MAIL_SENDER_PASSWORD));
            email.setHostName(Configure.MAIL_SENDER_SMTP_HOST);
            email.setSmtpPort(Configure.MAIL_SENDER_SMTP_PORT);
            email.setFrom(Configure.MAIL_SENDER, Configure.MAIL_SENDER_NAME);
            email.setSubject(title);
            email.setHtmlMsg(content);
            email.addTo(receivers.stream().toArray(String[]::new));
            email.setCharset("UTF-8");
            email.setSSLOnConnect(false);
            email.send();
            logger.info("send mail to {} success", JSON.toJSONString(receivers));
            return true;
        } catch (Exception e) {
            logger.error("send mail to {} fail, caused by", JSON.toJSONString(receivers), e);
            return false;
        }
    }
}
