package cn.junety.alarm.sender.common;

import cn.junety.alarm.base.entity.QueueMessage;

/**
 * Created by caijt on 2017/3/4.
 */
public interface ConsumerHandler {
    boolean handle(QueueMessage message);
}
