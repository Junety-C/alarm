package cn.junety.alarm.web.vo;

/**
 * Created by caijt on 2017/3/26.
 */
public class AlarmConfig {
    private boolean sms;
    private boolean mail;
    private boolean wechat;
    private boolean qq;

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public boolean isMail() {
        return mail;
    }

    public void setMail(boolean mail) {
        this.mail = mail;
    }

    public boolean isWechat() {
        return wechat;
    }

    public void setWechat(boolean wechat) {
        this.wechat = wechat;
    }

    public boolean isQq() {
        return qq;
    }

    public void setQq(boolean qq) {
        this.qq = qq;
    }
}
