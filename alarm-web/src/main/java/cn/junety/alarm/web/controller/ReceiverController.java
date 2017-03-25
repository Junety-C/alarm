package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.common.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
public class ReceiverController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverController.class);

    @RequestMapping(value = "/receivers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReceivers(HttpServletRequest request) {
        logger.info("GET /receivers, body:{}");
        return ResponseHelper.buildResponse(2000, "GET /receivers");
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReceiver(HttpServletRequest request, @PathVariable Integer rid) {
        logger.info("GET /receivers/{}, body:{}", rid);
        return ResponseHelper.buildResponse(2000, "GET /receivers/"+rid);
    }

    @RequestMapping(value = "/receivers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addReceiver(HttpServletRequest request) {
        logger.info("POST /receivers, body:{}");
        return ResponseHelper.buildResponse(2000, "POST /receivers");
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateReceiver(HttpServletRequest request, @PathVariable Integer rid) {
        logger.info("PUT /receivers/{}, body:{}", rid);
        return ResponseHelper.buildResponse(2000, "PUT /receivers/"+rid);
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteReceiver(HttpServletRequest request, @PathVariable Integer rid) {
        logger.info("DELETE /receivers/{}, body:{}", rid);
        return ResponseHelper.buildResponse(2000, "DELETE /receivers/"+rid);
    }
}
