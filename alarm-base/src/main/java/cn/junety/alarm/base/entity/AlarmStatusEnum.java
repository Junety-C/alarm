package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/1/28.
 */
public enum AlarmStatusEnum {
    CREATE(0), SEND(1), LIMIT(2), TEST(3);

    private int value;

    AlarmStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return "{" +
                "value=" + value +
                '}';
    }
}