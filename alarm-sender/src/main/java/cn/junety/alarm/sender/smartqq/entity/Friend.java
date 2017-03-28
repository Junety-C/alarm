package cn.junety.alarm.sender.smartqq.entity;

/**
 * Created by caijt on 2017/3/28.
 */
public class Friend {
    private long userId;

    private String markname = "";

    private String nickname;

    private boolean vip;

    private int vipLevel;

    @Override
    public String toString() {
        return "Friend{"
                + "userId=" + userId
                + ", markname='" + markname + '\''
                + ", nickname='" + nickname + '\''
                + ", vip=" + vip
                + ", vipLevel=" + vipLevel
                + '}';
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMarkname() {
        return markname;
    }

    public void setMarkname(String markname) {
        this.markname = markname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }
}
