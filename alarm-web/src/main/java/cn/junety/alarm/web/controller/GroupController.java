package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.Group;
import cn.junety.alarm.base.entity.Receiver;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.GroupService;
import cn.junety.alarm.web.vo.GroupForm;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caijt on 2017/3/27.
 */
@RestController
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getGroups(HttpServletRequest request) {
        GroupForm groupForm = new GroupForm(request);
        logger.info("GET /groups, body:{}", JSON.toJSONString(groupForm));
        List<Group> groups = groupService.getGroups(groupForm);
        List<Receiver> allReceiver = groupService.getReceivers();
        List<Receiver> receivers = groupService.getReceiverByGroupId(groups.get(0).getId());
        Map<String, Object> results = new HashMap<>();
        results.put("groups", groups);
        results.put("all_receiver", allReceiver);
        results.put("receivers", receivers);
        results.put("count", groupService.getGroupCount(groupForm));
        return ResponseHelper.buildResponse(2000, results);
    }

    @RequestMapping(value = "/groups/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addGroup(@PathVariable String name) {
        logger.info("POST /groups/{}", name);
        Group group = new Group();
        group.setName(name);
        groupService.createGroup(group);
        return ResponseHelper.buildResponse(2000, "success");
    }

    @RequestMapping(value = "/groups/{gid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteGroup(@PathVariable Integer gid) {
        logger.info("DELETE /groups/{}", gid);
        groupService.deleteGroup(gid);
        return ResponseHelper.buildResponse(2000, "success");
    }

    @RequestMapping(value = "/groups/{gid}/receivers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReceiversFromGroup(@PathVariable Integer gid) {
        logger.info("GET /groups/{}/receivers", gid);
        List<Receiver> receivers = groupService.getReceiverByGroupId(gid);
        Map<String, Object> results = new HashMap<>();
        results.put("receivers", receivers);
        return ResponseHelper.buildResponse(2000, results);
    }

    @RequestMapping(value = "/groups/{gid}/receivers/{rid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addReceiverFromGroup(@PathVariable Integer gid, @PathVariable Integer rid) {
        logger.info("POST /groups/{}/receivers/{}", gid, rid);
        groupService.addReceiverFromGroup(gid, rid);
        return ResponseHelper.buildResponse(2000, "success");
    }

    @RequestMapping(value = "/groups/{gid}/receivers/{rid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteReceiverFromGroup(@PathVariable Integer gid, @PathVariable Integer rid) {
        logger.info("DELETE /groups/{}/receivers/{}", gid, rid);
        groupService.deleteReceiverFromGroup(gid, rid);
        return ResponseHelper.buildResponse(2000, "success");
    }
}
