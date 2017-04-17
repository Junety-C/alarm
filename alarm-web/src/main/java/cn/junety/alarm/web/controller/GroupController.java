package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.*;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.GroupService;
import cn.junety.alarm.web.service.ProjectMemberService;
import cn.junety.alarm.web.service.ProjectService;
import cn.junety.alarm.web.vo.ProjectSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Created by caijt on 2017/3/27.
 * 接收组API
 */
@RestController
public class GroupController extends BaseController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ProjectMemberService projectMemberService;

    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String initGroup(HttpServletRequest request) {
        User currentUser = getUser(request);
        try {
            ProjectSearch projectSearch = new ProjectSearch(request, currentUser);
            logger.info("GET /groups, current_user:{}, search:{}", currentUser, projectSearch);

            List<Project> projectList = projectService.getProjectList(projectSearch);
            int projectCount = projectService.getProjectCount(projectSearch);
            List<Group> groupList = Collections.emptyList();
            List<User> memberList = Collections.emptyList();
            int permissionType = ProjectMemberTypeEnum.NORMAL_MEMBER.value();
            if (projectList.size() > 0) {
                int projectId = projectList.get(0).getId();
                groupList = groupService.getGroupList(projectId);
                permissionType = projectService.getProjectMemberType(currentUser.getId(), projectId);
                if (groupList.size() > 0) {
                    int groupId = groupList.get(0).getId();
                    memberList = groupService.getMemberList(groupId);
                }
            }

            return ResponseHelper.buildResponse(2000, "project_list", projectList, "project_count", projectCount,
                    "group_list", groupList, "member_list", memberList, "permission_type", permissionType);
        } catch (Exception e) {
            logger.error("get group list error, caused by", e);
            return ResponseHelper.buildResponse(5000, "project_list", Collections.emptyList(),
                    "project_count", 0, "group_list", Collections.emptyList(), "member_list", Collections.emptyList(),
                    "permission_type", ProjectMemberTypeEnum.NORMAL_MEMBER.value());
        }
    }

    @RequestMapping(value = "/projects/{pid}/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getGroupList(HttpServletRequest request, @PathVariable Integer pid) {
        User currentUser = getUser(request);
        try {
            logger.info("GET /projects/{}/groups, current_user:{}", pid, currentUser);

            List<Group> groupList = groupService.getGroupList(pid);
            List<User> memberList = Collections.emptyList();
            int permissionType = projectService.getProjectMemberType(currentUser.getId(), pid);
            if (groupList.size() > 0) {
                int groupId = groupList.get(0).getId();
                memberList = groupService.getMemberList(groupId);
            }

            return ResponseHelper.buildResponse(2000, "group_list", groupList, "member_list", memberList,
                    "permission_type", permissionType);
        } catch (Exception e) {
            logger.error("init module error, caused by", e);
            return ResponseHelper.buildResponse(5000, "group_list", Collections.emptyList(),
                    "member_list", Collections.emptyList(), "permission_type", ProjectMemberTypeEnum.NORMAL_MEMBER.value());
        }
    }

    @RequestMapping(value = "/projects/{pid}/groups/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createGroup(HttpServletRequest request, @PathVariable Integer pid, @PathVariable String name) {
        User currentUser = getUser(request);
        logger.info("POST /projects/{}/groups/{}, current_user:{}", pid, name, currentUser);

        int groupId = groupService.createGroup(pid, name);
        groupService.addGroupMember(groupId, currentUser.getId());

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/projects/{pid}/groups/{gid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteGroup(HttpServletRequest request, @PathVariable Integer pid, @PathVariable Integer gid) {
        User currentUser = getUser(request);
        logger.info("DELETE /projects/{}/groups/{}, current_user:{}", pid, gid, currentUser);

        groupService.deleteGroup(gid);
        groupService.removeGroupMember(gid);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/projects/{pid}/groups/{gid}/members", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getGroupMemberList(HttpServletRequest request, @PathVariable Integer pid, @PathVariable Integer gid) {
        User currentUser = getUser(request);
        try {
            logger.info("GET /projects/{}/groups/{}/members, current_user:{}", pid, gid, currentUser);

            List<User> memberList = groupService.getMemberList(gid);
            int permissionType = projectService.getProjectMemberType(currentUser.getId(), pid);

            return ResponseHelper.buildResponse(2000, "member_list", memberList, "permission_type", permissionType);
        } catch (Exception e) {
            logger.error("init module error, caused by", e);
            return ResponseHelper.buildResponse(5000, "member_list", Collections.emptyList(),
                    "permission_type", ProjectMemberTypeEnum.NORMAL_MEMBER.value());
        }
    }

    @RequestMapping(value = "/projects/{pid}/groups/{gid}/members/{account}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addGroupMember(HttpServletRequest request, @PathVariable Integer pid, @PathVariable Integer gid, @PathVariable String account) {
        User currentUser = getUser(request);
        logger.info("POST /projects/{}/groups/{}/members/{}, current_user:{}", pid, gid, account, currentUser);

        User user = projectMemberService.getProjectMemberByAccount(pid, account);
        if (user != null) {
            groupService.addGroupMember(gid, user.getId());
            return ResponseHelper.buildResponse(2000);
        } else {
            logger.info("add group member fail, pid:{}, account:{}", pid, account);
            return ResponseHelper.buildResponse(4000, "reason", "项目成员中不存在该账号");
        }
    }

    @RequestMapping(value = "/projects/{pid}/groups/{gid}/members/{uid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String removeGroupMember(HttpServletRequest request, @PathVariable Integer pid, @PathVariable Integer gid, @PathVariable Integer uid) {
        User currentUser = getUser(request);
        logger.info("DELETE /projects/{}/groups/{}/members/{}, current_user:{}", pid, gid, uid, currentUser);

        groupService.removeGroupMember(gid, uid);

        return ResponseHelper.buildResponse(2000);
    }
}
