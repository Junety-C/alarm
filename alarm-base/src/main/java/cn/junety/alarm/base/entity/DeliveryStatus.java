package cn.junety.alarm.base.entity;

/**
 * Created by caijt on 2017/3/6.
 */
public class DeliveryStatus {
    private long logId;
    private String channel;

    public DeliveryStatus() {}

    public DeliveryStatus(long logId, String channel) {
        this.logId = logId;
        this.channel = channel;
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "{" +
                "logId=" + logId +
                ", channel='" + channel + '\'' +
                '}';
    }
}
