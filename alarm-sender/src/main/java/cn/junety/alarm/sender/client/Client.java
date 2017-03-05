package cn.junety.alarm.sender.client;

import cn.junety.alarm.base.entity.QueueMessage;
import cn.junety.alarm.base.redis.JedisFactory;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by caijt on 2017/3/5.
 */
public abstract class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private static final int MAX_RETRY_TIMES = 3;

    private String queueName;

    protected abstract boolean send(QueueMessage queueMessage);

    public Client(String queueName) {
        this.queueName = queueName;
    }

    public void start() {
        Jedis client = null;
        logger.info("start {} queue consumer", queueName);
        while (true) {
            try {
                client = JedisFactory.getJedisInstance(queueName);
                QueueMessage queueMessage = JSON.parseObject(client.blpop(0, queueName).get(1), QueueMessage.class);
                if (!send(queueMessage)) {
                    // 消费失败，重入队列
                    resend(queueMessage);
                }
            } catch (Exception e) {
                logger.error("consumer queue {} error, caused by", queueName, e);
            } finally {
                if (client != null) {
                    client.close();
                }
            }
        }
    }

    private void resend(QueueMessage queueMessage) {
        boolean success = false;
        for(int i = 1; i <= MAX_RETRY_TIMES && !success; i++) {
            logger.info("send alarm fail, retry times:{}, logId:{}", i, queueMessage.getLogId());
            success = send(queueMessage);
        }
        logger.info("send alarm always fail, content:{}", JSON.toJSONString(queueMessage));
    }
}
