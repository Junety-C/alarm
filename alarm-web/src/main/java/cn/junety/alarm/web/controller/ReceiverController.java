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
        ReceiverForm receiverForm = new ReceiverForm(request);
        logger.info("GET /receivers, body:{}", JSON.toJSONString(receiverForm));
        List<Receiver> receivers = receiverService.getReceivers(receiverForm);
        Map<String, Object> results = new HashMap<>();
        results.put("receivers", receivers);
        results.put("count", receiverService.getReceiverCount(receiverForm));
        return ResponseHelper.buildResponse(2000, results);
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReceiver(@PathVariable Integer rid) {
        logger.info("GET /receivers/{}", rid);
        Receiver receiver = receiverService.getReceiverById(rid);
        Map<String, Object> results = new HashMap<>();
        results.put("receiver", receiver);
        return ResponseHelper.buildResponse(2000, results);
    }

    @RequestMapping(value = "/receivers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addReceiver(@RequestBody Receiver receiver) {
        logger.info("POST /receivers, body:{}", JSON.toJSONString(receiver));
        receiverService.createReceiver(receiver);
        return ResponseHelper.buildResponse(2000, "success");
    }

    @RequestMapping(value = "/receivers", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateReceiver(@RequestBody Receiver receiver) {
        logger.info("PUT /receivers, body:{}", JSON.toJSONString(receiver));
        if(receiver.getId() != null && receiver.getId() > 0) {
            receiverService.updateReceiver(receiver);
        }
        return ResponseHelper.buildResponse(2000, "success");
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteReceiver(@PathVariable Integer rid) {
        logger.info("DELETE /receivers/{}", rid);
        receiverService.deleteReceiver(rid);
        return ResponseHelper.buildResponse(2000, "success");
    }
}
