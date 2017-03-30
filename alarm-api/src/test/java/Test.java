import cn.junety.alarm.api.AlarmClient;

/**
 * Created by caijt on 2017/3/29.
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            AlarmClient.debug(2, "alarm.test.limit", "告警测试");
        }
    }
}
