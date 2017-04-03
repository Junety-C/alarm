package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.Module;
import cn.junety.alarm.base.entity.Project;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.ProjectService;
import cn.junety.alarm.web.service.UserService;
import cn.junety.alarm.web.vo.ProjectSearch;
import cn.junety.alarm.web.vo.UserVO;
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
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProjectList(HttpServletRequest request) {
        User user = getUser(request);
        try {
            ProjectSearch projectSearch = new ProjectSearch(request, user);
            logger.info("GET /projects, user:{}, search:{}", JSON.toJSONString(user), JSON.toJSONString(projectSearch));

            List<Project> projects = projectService.getProjectList(user, projectSearch);
            List<Module> modules;
            List<UserVO> projectMembers;
            if (projects.size() > 0) {
                modules = projectService.getModuleByProjectId(projects.get(0).getId());
                projectMembers = projectService.getProjectMemberByProjectId(projects.get(0).getId());
            } else {
                modules = Collections.emptyList();
                projectMembers = Collections.emptyList();
            }
            int count = projectService.getProjectCount(user, projectSearch);
            List<UserVO> allUser = userService.getAllUser();

            return ResponseHelper.buildResponse(2000, "projects", projects, "modules", modules,
                    "all_user", allUser, "project_members", projectMembers, "count", count);
        } catch (Exception e) {
            logger.error("get project list error, caused by", e);
            return ResponseHelper.buildResponse(5000, "projects", Collections.emptyList(),
                    "modules", Collections.emptyList(), "all_user", Collections.emptyList(),
                    "project_members", Collections.emptyList(), "count", 0);
        }
    }

    @RequestMapping(value = "/projects/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createProject(HttpServletRequest request, @PathVariable String name) {
        User user = getUser(request);
        logger.info("POST /projects/{}, user:{}, name:{}", JSON.toJSONString(user), name);

        projectService.createProject(name);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/projects/{pid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteProjectById(HttpServletRequest request, @PathVariable Integer pid) {
        User user = getUser(request);
        logger.info("DELETE /projects/{}, user:{}", pid, JSON.toJSONString(user));

        projectService.deleteProjectById(pid);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/projects/{pid}/modules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getModuleByProjectId(HttpServletRequest request, @PathVariable Integer pid) {
        User user = getUser(request);
        logger.info("GET /projects/{}/modules, user:{}", pid, JSON.toJSONString(user));

        List<Module> modules = projectService.getModuleByProjectId(pid);

        return ResponseHelper.buildResponse(2000, "modules", modules);
    }

    @RequestMapping(value = "/projects/{pid}/modules/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createModule(HttpServletRequest request, @PathVariable Integer pid, @PathVariable String name) {
        User user = getUser(request);
        logger.info("POST /projects/{}/modules/{}, user:{}", pid, name, JSON.toJSONString(user));

        projectService.createModule(pid, name);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/modules/{mid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteModuleById(HttpServletRequest request, @PathVariable Integer mid) {
        User user = getUser(request);
        logger.info("DELETE /modules/{}, user:{}", mid, JSON.toJSONString(user));

        projectService.deleteModuleById(mid);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/projects/{pid}/members", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMemberByProjectId(HttpServletRequest request, @PathVariable Integer pid) {
        User user = getUser(request);
        logger.info("GET /projects/{}/members, user:{}", pid, JSON.toJSONString(user));

        List<UserVO> members = projectService.getProjectMemberByProjectId(pid);

        return ResponseHelper.buildResponse(2000, "members", members);
    }

    @RequestMapping(value = "/users/{uid}/from/projects/{pid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String removeUserFromProject(HttpServletRequest request, @PathVariable Integer uid, @PathVariable Integer pid) {
        User user = getUser(request);
        logger.info("DELETE /users/{}/from/projects/{}, user:{}", uid, pid, JSON.toJSONString(user));

        projectService.removeUserFromProject(uid, pid);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/users/{uid}/to/projects/{pid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addUserToProject(HttpServletRequest request, @PathVariable Integer uid, @PathVariable Integer pid) {
        User user = getUser(request);
        logger.info("POST /users/{}/to/projects/{}, user:{}", uid, pid, JSON.toJSONString(user));

        projectService.addUserToProject(uid, pid);

        return ResponseHelper.buildResponse(2000);
    }
}
