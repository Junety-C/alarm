package cn.junety.alarm.sender.common;

import cn.junety.alarm.base.entity.QueueMessage;
import cn.junety.alarm.base.redis.JedisFactory;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by caijt on 2017/3/4.
 */
public class Consumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private static final int MAX_RETRY_TIMES = 3;

    private ConsumerHandler consumerHandler;
    private String queueName;

    public Consumer(ConsumerHandler consumerHandler, String queueName) {
        this.consumerHandler = consumerHandler;
        this.queueName = queueName;
    }

    @Override
    public void run() {
        Jedis client = null;
        logger.info("start {} queue consumer", consumerHandler.getClass().getSimpleName());
        while (true) {
            try {
                client = JedisFactory.getJedisInstance(queueName);
                QueueMessage queueMessage = JSON.parseObject(client.blpop(0, queueName).get(1), QueueMessage.class);
                if (!consumerHandler.handle(queueMessage)) {
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
            success = consumerHandler.handle(queueMessage);
        }
        logger.info("send alarm always fail, content:{}", JSON.toJSONString(queueMessage));
    }
}
