package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.Receiver;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.ReceiverService;
import cn.junety.alarm.web.vo.ReceiverForm;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
public class ReceiverController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverController.class);

    @Autowired
    private ReceiverService receiverService;

    @RequestMapping(value = "/receivers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReceivers(HttpServletRequest request) {
        String reqId = request.getSession().getId();
        ReceiverForm receiverForm = new ReceiverForm(request);
        logger.info("reqId:{}, GET /receivers, body:{}", reqId, JSON.toJSONString(receiverForm));
        List<Receiver> receivers = receiverService.getReceivers(receiverForm);
        Map<String, Object> results = new HashMap<>();
        results.put("receivers", receivers);
        results.put("count", receiverService.getReceiverCount(receiverForm));
        return ResponseHelper.buildResponse(2000, reqId, results);
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReceiver(HttpServletRequest request, @PathVariable Integer rid) {
        String reqId = request.getSession().getId();
        logger.info("reqId:{}, GET /receivers/{}", reqId, rid);
        Receiver receiver = receiverService.getReceiverById(rid);
        Map<String, Object> results = new HashMap<>();
        results.put("receiver", receiver);
        return ResponseHelper.buildResponse(2000, reqId, results);
    }

    @RequestMapping(value = "/receivers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addReceiver(HttpServletRequest request, @RequestBody Receiver receiver) {
        String reqId = request.getSession().getId();
        logger.info("reqId:{}, POST /receivers, body:{}", reqId, JSON.toJSONString(receiver));
        receiverService.createReceiver(receiver);
        return ResponseHelper.buildResponse(2000, reqId, "success");
    }

    @RequestMapping(value = "/receivers", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateReceiver(HttpServletRequest request, @RequestBody Receiver receiver) {
        String reqId = request.getSession().getId();
        logger.info("reqId:{}, PUT /receivers, body:{}", reqId, JSON.toJSONString(receiver));
        if(receiver.getId() != null && receiver.getId() > 0) {
            receiverService.updateReceiver(receiver);
        }
        return ResponseHelper.buildResponse(2000, reqId, "success");
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteReceiver(HttpServletRequest request, @PathVariable Integer rid) {
        String reqId = request.getSession().getId();
        logger.info("reqId:{}, DELETE /receivers/{}", reqId, rid);
        receiverService.deleteReceiver(rid);
        return ResponseHelper.buildResponse(2000, reqId, "success");
    }
}
