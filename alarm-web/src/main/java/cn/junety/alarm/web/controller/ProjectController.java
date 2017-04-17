package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.*;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.GroupService;
import cn.junety.alarm.web.service.ModuleService;
import cn.junety.alarm.web.service.ProjectMemberService;
import cn.junety.alarm.web.service.ProjectService;
import cn.junety.alarm.web.vo.ProjectSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caijt on 2017/3/24.
 * 项目API
 */
@RestController
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ProjectMemberService projectMemberService;
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProjectList(HttpServletRequest request) {
        User currentUser = getUser(request);
        try {
            ProjectSearch projectSearch = new ProjectSearch(request, currentUser);
            logger.info("GET /projects, current_user:{}, search:{}", currentUser, projectSearch);

            List<Project> projectList = projectService.getProjectList(projectSearch);
            Map<String, Integer> permissionMapper = new HashMap<>();
            for(Project project : projectList) {
                int projectId = project.getId();
                int type = projectService.getProjectMemberType(currentUser.getId(), projectId);
                permissionMapper.put(""+projectId, type);
            }
            int projectCount = projectService.getProjectCount(projectSearch);

            return ResponseHelper.buildResponse(2000, "project_list", projectList,
                    "project_count", projectCount, "permission_mapper", permissionMapper);
        } catch (Exception e) {
            logger.error("get project list error, caused by", e);
            return ResponseHelper.buildResponse(5000, "project_list", Collections.emptyList(),
                    "project_count", 0, "permission_mapper", Collections.emptyMap());
        }
    }

    @RequestMapping(value = "/projects/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProjectById(HttpServletRequest request, @PathVariable Integer pid) {
        User currentUser = getUser(request);
        logger.info("GET /projects/{}, current_user:{}", pid, currentUser);

        Project project = projectService.getProjectById(pid);

        return ResponseHelper.buildResponse(2000, "project", project);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createProject(HttpServletRequest request, @RequestBody Project project) {
        User currentUser = getUser(request);
        logger.info("POST /projects, current_user:{}, project:{}", currentUser, project);

        int projectId = projectService.createProject(currentUser, project);
        projectMemberService.addProjectMember(projectId, currentUser.getId(), ProjectMemberTypeEnum.ADMIN_MEMBER.value());

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateProject(HttpServletRequest request, @RequestBody Project project) {
        User currentUser = getUser(request);
        logger.info("PUT /projects, current_user:{}", currentUser, project);

        projectService.updateProject(project);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/projects/{pid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteProject(HttpServletRequest request, @PathVariable Integer pid) {
        User currentUser = getUser(request);
        logger.info("DELETE /projects/{}, current_user:{}", pid, currentUser);

        projectService.deleteProject(pid);
        moduleService.deleteModuleByProjectId(pid);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/modules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String initModule(HttpServletRequest request) {
        User currentUser = getUser(request);
        try {
            ProjectSearch projectSearch = new ProjectSearch(request, currentUser);
            logger.info("GET /modules, current_user:{}, search:{}", currentUser, projectSearch);

            List<Project> projectList = projectService.getProjectList(projectSearch);
            int projectCount = projectService.getProjectCount(projectSearch);
            List<Module> moduleList = Collections.emptyList();
            int permissionType = ProjectMemberTypeEnum.NORMAL_MEMBER.value();
            if (projectList.size() > 0) {
                int projectId = projectList.get(0).getId();
                moduleList = moduleService.getModuleList(projectId);
                permissionType = projectService.getProjectMemberType(currentUser.getId(), projectId);
            }

            return ResponseHelper.buildResponse(2000, "project_list", projectList,
                    "project_count", projectCount, "module_list", moduleList, "permission_type", permissionType);
        } catch (Exception e) {
            logger.error("init module error, caused by", e);
            return ResponseHelper.buildResponse(5000, "project_list", Collections.emptyList(),
                    "project_count", 0, "module_list", Collections.emptyList(),
                    "permission_type", ProjectMemberTypeEnum.NORMAL_MEMBER.value());
        }
    }

    @RequestMapping(value = "/projects/{pid}/modules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getModuleList(HttpServletRequest request, @PathVariable Integer pid) {
        User currentUser = getUser(request);
        try {
            logger.info("GET /projects/{}/modules, current_user:{}", pid, currentUser);

            List<Module> moduleList = moduleService.getModuleList(pid);
            int permissionType = projectService.getProjectMemberType(currentUser.getId(), pid);

            return ResponseHelper.buildResponse(2000, "module_list", moduleList, "permission_type", permissionType);
        } catch (Exception e) {
            logger.error("init module error, caused by", e);
            return ResponseHelper.buildResponse(5000, "module_list", Collections.emptyList(),
                    "permission_type", ProjectMemberTypeEnum.NORMAL_MEMBER.value());
        }
    }

    @RequestMapping(value = "/modules", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createModule(HttpServletRequest request, @RequestBody Module module) {
        User currentUser = getUser(request);
        logger.info("POST /modules, current_user:{}, module:{}", currentUser, module);

        moduleService.createModule(module);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/modules/{mid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteModuleById(HttpServletRequest request, @PathVariable Integer mid) {
        User currentUser = getUser(request);
        logger.info("DELETE /modules/{}, current_user:{}", mid, currentUser);

        moduleService.deleteModule(mid);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String initMember(HttpServletRequest request) {
        User currentUser = getUser(request);
        try {
            ProjectSearch projectSearch = new ProjectSearch(request, currentUser);
            logger.info("GET /members, current_user:{}, search:{}", currentUser, projectSearch);

            List<Project> projectList = projectService.getProjectList(projectSearch);
            int projectCount = projectService.getProjectCount(projectSearch);
            List<User> memberList = Collections.emptyList();
            int permissionType = ProjectMemberTypeEnum.NORMAL_MEMBER.value();
            if (projectList.size() > 0) {
                int projectId = projectList.get(0).getId();
                memberList = projectMemberService.getMemberList(projectId);
                permissionType = projectService.getProjectMemberType(currentUser.getId(), projectId);
            }

            return ResponseHelper.buildResponse(2000, "project_list", projectList,
                    "project_count", projectCount, "member_list", memberList, "permission_type", permissionType);
        } catch (Exception e) {
            logger.error("init module error, caused by", e);
            return ResponseHelper.buildResponse(5000, "project_list", Collections.emptyList(),
                    "project_count", 0, "member_list", Collections.emptyList(),
                    "permission_type", ProjectMemberTypeEnum.NORMAL_MEMBER.value());
        }
    }

    @RequestMapping(value = "/projects/{pid}/members", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMemberList(HttpServletRequest request, @PathVariable Integer pid) {
        User currentUser = getUser(request);
        try {
            logger.info("GET /projects/{}/members, current_user:{}", pid, currentUser);

            List<User> memberList = projectMemberService.getMemberList(pid);
            int permissionType = projectService.getProjectMemberType(currentUser.getId(), pid);

            return ResponseHelper.buildResponse(2000, "member_list", memberList, "permission_type", permissionType);
        } catch (Exception e) {
            logger.error("init module error, caused by", e);
            return ResponseHelper.buildResponse(5000, "member_list", Collections.emptyList(),
                    "permission_type", ProjectMemberTypeEnum.NORMAL_MEMBER.value());
        }
    }

    @RequestMapping(value = "/projects/{pid}/members/{account}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addProjectMember(HttpServletRequest request, @PathVariable Integer pid, @PathVariable String account) {
        User currentUser = getUser(request);
        logger.info("POST /projects/{}/members/{}, current_user:{}", pid, account, currentUser);

        User user = userService.getUserByAccount(account);
        if (user != null) {
            projectMemberService.addProjectMember(pid, user.getId(), ProjectMemberTypeEnum.NORMAL_MEMBER.value());
            return ResponseHelper.buildResponse(2000);
        } else {
            logger.info("add project member fail, pid:{}, account:{}", pid, account);
            return ResponseHelper.buildResponse(4000, "reason", "用户信息不存在");
        }
    }

    @RequestMapping(value = "/project/{pid}/members/{uid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String removeProjectMember(HttpServletRequest request, @PathVariable Integer pid, @PathVariable Integer uid) {
        User currentUser = getUser(request);
        logger.info("DELETE /project/{}/members/{}, current_user:{}", pid, uid, currentUser);

        projectMemberService.removeProjectMember(pid, uid);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/projects/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllProjectInfo(HttpServletRequest request) {
        User currentUser = getUser(request);
        logger.info("GET /projects/all, current_user:{}", currentUser);

        List<Project> projectList = projectService.getProjectList(currentUser);

        return ResponseHelper.buildResponse(2000, "project_list", projectList);
    }

    @RequestMapping(value = "/projects/{pid}/config", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProjectConfig(HttpServletRequest request, @PathVariable Integer pid) {
        User currentUser = getUser(request);

        try {
            logger.info("GET /projects/{}/config, current_user:{}", pid, currentUser);

            List<Module> moduleList = moduleService.getModuleList(pid);
            List<Group> groupList = groupService.getGroupList(pid);

            return ResponseHelper.buildResponse(2000, "module_list", moduleList, "group_list", groupList);
        } catch (Exception e) {
            logger.error("get project config error, caused by", e);
            return ResponseHelper.buildResponse(5000, "module_list", Collections.emptyList(),
                    "group_list", Collections.emptyList());
        }
    }
}
