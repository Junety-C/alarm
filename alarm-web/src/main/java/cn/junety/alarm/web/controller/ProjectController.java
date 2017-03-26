package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.Project;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.ProjectService;
import cn.junety.alarm.web.vo.ProjectForm;
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
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProjects(HttpServletRequest request) {
        ProjectForm projectForm = new ProjectForm(request);
        logger.info("GET /projects, body:{}", JSON.toJSONString(projectForm));
        List<Project> projects = projectService.getProjects(projectForm);
        Map<String, Object> results = new HashMap<>();
        results.put("projects", projects);
        results.put("modules", projectService.getModuleByPid(projects.get(0).getId()));
        results.put("count", projectService.getProjectCount(projectForm));
        return ResponseHelper.buildResponse(2000, results);
    }

    @RequestMapping(value = "/projects/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addProject(@PathVariable String name) {
        logger.info("POST /projects/{}, body:{}", name);
        Project project = new Project();
        project.setName(name);
        projectService.createProject(project);
        return ResponseHelper.buildResponse(2000, "success");
    }

    //@RequestMapping(value = "/projects/{pid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    //public String updateProject(HttpServletRequest request, @PathVariable Integer pid) {
    //    logger.info("PUT /projects/{}, body:{}", pid);
    //    return ResponseHelper.buildResponse(2000, "PUT /projects/"+pid);
    //}

    @RequestMapping(value = "/projects/{pid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteProject(@PathVariable Integer pid) {
        logger.info("DELETE /projects/{}, body:{}", pid);
        projectService.deleteProject(pid);
        return ResponseHelper.buildResponse(2000, "success");
    }

    @RequestMapping(value = "/projects/{pid}/modules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getModules(HttpServletRequest request, @PathVariable Integer pid) {
        logger.info("GET /projects/{}/modules", pid);
        Map<String, Object> results = new HashMap<>();
        results.put("modules", projectService.getModuleByPid(pid));
        return ResponseHelper.buildResponse(2000, results);
    }

    @RequestMapping(value = "/projects/{pid}/modules/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addModule(@PathVariable Integer pid, @PathVariable String name) {
        logger.info("POST /projects/{}/modules/{}, body:{}", pid, name);
        projectService.createModule(pid, name);
        return ResponseHelper.buildResponse(2000, "success");
    }

    //@RequestMapping(value = "/modules/{mid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    //public String updateModule(@PathVariable Integer mid) {
    //    logger.info("PUT /modules/{}", mid);
    //    return ResponseHelper.buildResponse(2000, "success");
    //}

    @RequestMapping(value = "/modules/{mid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteModule(@PathVariable Integer mid) {
        logger.info("DELETE /modules/{}", mid);
        projectService.deleteModule(mid);
        return ResponseHelper.buildResponse(2000, "success");
    }
}
