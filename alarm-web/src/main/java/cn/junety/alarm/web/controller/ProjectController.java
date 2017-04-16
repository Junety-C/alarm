package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.Project;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.common.ResponseHelper;
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
 */
@RestController
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

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
        projectService.addProjectMember(currentUser.getId(), projectId, ProjectService.ADMIN_MEMBER);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateProjectById(HttpServletRequest request, @RequestBody Project project) {
        User currentUser = getUser(request);
        logger.info("PUT /projects, current_user:{}", currentUser, project);

        projectService.updateProject(project);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/projects/{pid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteProjectById(HttpServletRequest request, @PathVariable Integer pid) {
        User currentUser = getUser(request);
        logger.info("DELETE /projects/{}, current_user:{}", pid, currentUser);

        projectService.deleteProject(pid);

        return ResponseHelper.buildResponse(2000);
    }
}
