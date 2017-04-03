package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.Group;
import cn.junety.alarm.base.entity.Receiver;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.GroupService;
import cn.junety.alarm.web.vo.GroupSearch;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caijt on 2017/3/27.
 */
@RestController
public class GroupController extends BaseController {

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getGroups(HttpServletRequest request) {
        User user = getUser(request);

        try {
            GroupSearch groupSearch = new GroupSearch(request, user);
            logger.info("GET /groups, user:{}, search:{}", JSON.toJSONString(user), JSON.toJSONString(groupSearch));

            List<Group> groups = groupService.getGroupList(user, groupSearch);
            List<Receiver> allReceiver = groupService.getReceiverList();
            List<Receiver> receivers;
            if (groups.size() > 0) {
                receivers = groupService.getReceiverByGroupId(groups.get(0).getId());
            } else {
                receivers = Collections.emptyList();
            }
            int count = groupService.getGroupCount(user, groupSearch);

            return ResponseHelper.buildResponse(2000, "groups", groups, "all_receiver", allReceiver,
                    "receivers", receivers, "count", count);
        } catch (Exception e) {
            logger.error("get group list error, caused by", e);
            return ResponseHelper.buildResponse(5000, "groups", Collections.emptyList(),
                    "all_receiver", Collections.emptyList(), "receivers", Collections.emptyList(), "count", 0);
        }
    }

    @RequestMapping(value = "/groups/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createGroup(HttpServletRequest request, @PathVariable String name) {
        User user = getUser(request);
        logger.info("POST /groups/{}, user:{}", name, JSON.toJSONString(user));

        groupService.createGroup(name);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/groups/{gid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteGroup(HttpServletRequest request, @PathVariable Integer gid) {
        User user = getUser(request);
        logger.info("DELETE /groups/{}, user:{}", gid, JSON.toJSONString(user));

        groupService.deleteGroup(gid);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/groups/{gid}/receivers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getReceiversFromGroup(HttpServletRequest request, @PathVariable Integer gid) {
        User user = getUser(request);
        logger.info("GET /groups/{}/receivers, user:{}", gid, JSON.toJSONString(user));

        List<Receiver> receivers = groupService.getReceiverByGroupId(gid);

        return ResponseHelper.buildResponse(2000, "receivers", receivers);
    }

    @RequestMapping(value = "/receivers/{rid}/to/groups/{gid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addReceiverToGroup(HttpServletRequest request, @PathVariable Integer gid, @PathVariable Integer rid) {
        User user = getUser(request);
        logger.info("POST /receivers/{}/to/groups/{}, user:{}", rid, gid, JSON.toJSONString(user));

        groupService.addReceiverToGroup(gid, rid);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/receivers/{rid}/from/groups/{gid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String removeReceiverFromGroup(HttpServletRequest request, @PathVariable Integer gid, @PathVariable Integer rid) {
        User user = getUser(request);
        logger.info("DELETE /receivers/{}/from/groups/{}, user:{}", rid, gid, JSON.toJSONString(user));

        groupService.removeReceiverFromGroup(gid, rid);

        return ResponseHelper.buildResponse(2000);
    }
}
