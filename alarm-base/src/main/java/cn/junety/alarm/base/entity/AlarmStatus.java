package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/1/28.
 */
public enum AlarmStatus {
    CREATE(0), SEND(1), LIMIT(2), TEST(3);

    private int number;

    AlarmStatus(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}