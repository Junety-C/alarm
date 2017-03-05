package cn.junety.alarm.base.entity;


import java.util.List;

/**
 * Created by caijt on 2017/2/3.
 */
public class QueueMessage {
    private long logId;
    private String title;
    private String content;
    private Level level;
    private List<String> receivers;

    public QueueMessage() {}

    public QueueMessage(String title, String content, List<String> receivers, long logId, Level level) {
        this.logId = logId;
        this.title = title;
        this.content = content;
        this.level = level;
        this.receivers = receivers;
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }
}
