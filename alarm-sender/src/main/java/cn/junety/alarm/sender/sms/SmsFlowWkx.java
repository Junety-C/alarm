package cn.junety.alarm.sender.sms;

import java.util.HashMap;
import java.util.Map;

public class SmsFlowWkx {

    private String request;
    private Map<String,Object> smsbody;

    public SmsFlowWkx() {
    }

    public SmsFlowWkx(String request, Map<String,Object> smsbody) {
        this.request = request;
        this.smsbody = smsbody;
    }

}
