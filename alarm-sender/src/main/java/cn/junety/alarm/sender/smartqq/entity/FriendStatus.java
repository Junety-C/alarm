package cn.junety.alarm.sender.smartqq.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by caijt on 2017/3/28.
 */
public class FriendStatus {
    private long uin;

    private String status;

    @JSONField(name = "client_type")
    private int clientType;

    public long getUin() {
        return uin;
    }

    public void setUin(long uin) {
        this.uin = uin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }
}
