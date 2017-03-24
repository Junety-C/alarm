package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.common.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
@RequestMapping
public class ReceiverController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverController.class);

    @RequestMapping(value = "/receivers", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReceivers(HttpServletRequest request) {
        logger.info("GET /receivers, body:{}");
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReceiver(HttpServletRequest request) {
        logger.info("GET /receivers, body:{}");
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/receivers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addReceiver(HttpServletRequest request) {
        logger.info("POST /receivers, body:{}");
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateReceiver(HttpServletRequest request, @PathVariable Integer rid) {
        logger.info("PUT /receivers/{}, body:{}", rid);
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteReceiver(HttpServletRequest request, @PathVariable Integer rid) {
        logger.info("DELETE /receivers/{}, body:{}", rid);
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }
}
