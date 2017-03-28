package cn.junety.alarm.sender.wechat.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by caijt on 2017/3/28.
 */
public class WechatApiParams {

    private String uuid;
    private String baseUri;
    private String redirectUri;
    private String deviceId;
    private String skey;
    private String wxsid;
    private String wxuin;
    private String passTicket;
    private String cookies;
    private JSONObject baseRequest;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getWxsid() {
        return wxsid;
    }

    public void setWxsid(String wxsid) {
        this.wxsid = wxsid;
    }

    public String getWxuin() {
        return wxuin;
    }

    public void setWxuin(String wxuin) {
        this.wxuin = wxuin;
    }

    public String getPassTicket() {
        return passTicket;
    }

    public void setPassTicket(String passTicket) {
        this.passTicket = passTicket;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public JSONObject getBaseRequest() {
        return baseRequest;
    }

    public void setBaseRequest(JSONObject baseRequest) {
        this.baseRequest = baseRequest;
    }

    @Override
    public String toString() {
        return "WechatApiParams{" +
                "uuid='" + uuid + '\'' +
                ", baseUri='" + baseUri + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", skey='" + skey + '\'' +
                ", wxsid='" + wxsid + '\'' +
                ", wxuin='" + wxuin + '\'' +
                ", passTicket='" + passTicket + '\'' +
                ", cookies='" + cookies + '\'' +
                ", baseRequest=" + baseRequest +
                '}';
    }
}
