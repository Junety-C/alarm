package cn.junety.alarm.base.exception;

/**
 * Created by caijt on 2017/1/28.
 */
public class AlarmNotFoundException extends RuntimeException {

    public AlarmNotFoundException() {}

    public AlarmNotFoundException(String msg) {
        super(msg);
    }
}
