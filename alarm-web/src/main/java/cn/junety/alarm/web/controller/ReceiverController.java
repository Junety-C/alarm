package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.Receiver;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.GroupService;
import cn.junety.alarm.web.service.ReceiverService;
import cn.junety.alarm.web.vo.ReceiverSearch;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
public class ReceiverController extends BaseController {

    @Autowired
    private ReceiverService receiverService;
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/receivers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReceiverList(HttpServletRequest request) {
        User user = getUser(request);
        try {
            ReceiverSearch receiverSearch = new ReceiverSearch(request);
            logger.info("GET /receivers, user:{}, search:{}", JSON.toJSONString(user), JSON.toJSONString(receiverSearch));

            List<Receiver> receivers = receiverService.getReceiverList(receiverSearch);
            int receiverId = receiverService.getReceiverIdByUserId(user.getId());
            int count = receiverService.getReceiverCount(receiverSearch);

            return ResponseHelper.buildResponse(2000, "user", user, "receivers", receivers,
                    "receiver_id", receiverId, "count", count);
        } catch (Exception e) {
            logger.error("get receiver list error, caused by", e);
            return ResponseHelper.buildResponse(5000, "user", user, "receivers", Collections.emptyList(),
                    "receiver_id", 0, "count", 0);
        }
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReceiverById(HttpServletRequest request, @PathVariable Integer rid) {
        User user = getUser(request);
        logger.info("GET /receivers/{}, user:{}", rid, JSON.toJSONString(user));

        Receiver receiver = receiverService.getReceiverById(rid);

        return ResponseHelper.buildResponse(2000, "receiver", receiver);
    }

    @RequestMapping(value = "/receivers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addReceiver(HttpServletRequest request, @RequestBody Receiver receiver) {
        User user = getUser(request);
        logger.info("POST /receivers, user:{}, receiver:{}", JSON.toJSONString(user), JSON.toJSONString(receiver));

        receiverService.createReceiver(receiver);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/receivers", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateReceiver(HttpServletRequest request, @RequestBody Receiver receiver) {
        User user = getUser(request);
        logger.info("PUT /receivers, user:{}, body:{}", JSON.toJSONString(user), JSON.toJSONString(receiver));

        if(receiver.getId() != null && receiver.getId() > 0) {
            receiverService.updateReceiver(receiver);
        }

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/receivers/{rid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteReceiver(HttpServletRequest request, @PathVariable Integer rid) {
        User user = getUser(request);
        logger.info("DELETE /receivers/{}, user:{}", rid, JSON.toJSONString(user));

        receiverService.deleteReceiver(rid);
        groupService.deleteReceiver(rid);

        return ResponseHelper.buildResponse(2000);
    }
}
