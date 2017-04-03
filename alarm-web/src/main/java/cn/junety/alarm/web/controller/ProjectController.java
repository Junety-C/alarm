package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.Module;
import cn.junety.alarm.base.entity.Project;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.ProjectService;
import cn.junety.alarm.web.vo.ProjectForm;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Created by caijt on 2017/3/24.
 */
@Controller
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ModelAndView toProjectPage(HttpServletRequest request, Model model) {
        User user = getUser(request);
        logger.info("GET /project, user:{}", JSON.toJSONString(user));

        model.addAttribute("user", user);

        return new ModelAndView("project-module");
    }

    @ResponseBody
    @RequestMapping(value = "/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProjectList(HttpServletRequest request) {
        User user = getUser(request);
        try {
            ProjectForm projectForm = new ProjectForm(request);
            logger.info("GET /projects, user:{}, form:{}", JSON.toJSONString(user), JSON.toJSONString(projectForm));

            List<Project> projects = projectService.getProjectList(projectForm);
            List<Module> modules;
            if (projects.size() > 0) {
                modules = projectService.getModuleByProjectId(projects.get(0).getId());
            } else {
                modules = Collections.emptyList();
            }
            int count = projectService.getProjectCount(projectForm);

            return ResponseHelper.buildResponse(2000, "projects", projects, "modules", modules,
                    "count", count);
        } catch (Exception e) {
            logger.error("get project list error, caused by", e);
            return ResponseHelper.buildResponse(5000, "projects", Collections.emptyList(),
                    "modules", Collections.emptyList(), "count", 0);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/projects/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createProject(HttpServletRequest request, @PathVariable String name) {
        User user = getUser(request);
        logger.info("POST /projects/{}, user:{}, name:{}", JSON.toJSONString(user), name);

        Project project = new Project();
        project.setName(name);
        projectService.createProject(project);

        return ResponseHelper.buildResponse(2000);
    }

    @ResponseBody
    @RequestMapping(value = "/projects/{pid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteProjectById(HttpServletRequest request, @PathVariable Integer pid) {
        User user = getUser(request);
        logger.info("DELETE /projects/{}, user:{}", pid, JSON.toJSONString(user));

        projectService.deleteProjectById(pid);

        return ResponseHelper.buildResponse(2000);
    }

    @ResponseBody
    @RequestMapping(value = "/projects/{pid}/modules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getModuleByProjectId(HttpServletRequest request, @PathVariable Integer pid) {
        User user = getUser(request);
        logger.info("GET /projects/{}/modules, user:{}", pid, JSON.toJSONString(user));

        List<Module> modules = projectService.getModuleByProjectId(pid);

        return ResponseHelper.buildResponse(2000, "modules", modules);
    }

    @ResponseBody
    @RequestMapping(value = "/projects/{pid}/modules/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createModule(HttpServletRequest request, @PathVariable Integer pid, @PathVariable String name) {
        User user = getUser(request);
        logger.info("POST /projects/{}/modules/{}, user:{}", pid, name, JSON.toJSONString(user));

        projectService.createModule(pid, name);

        return ResponseHelper.buildResponse(2000);
    }

    @ResponseBody
    @RequestMapping(value = "/modules/{mid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteModuleById(HttpServletRequest request, @PathVariable Integer mid) {
        User user = getUser(request);
        logger.info("DELETE /modules/{}, user:{}", mid, JSON.toJSONString(user));

        projectService.deleteModuleById(mid);

        return ResponseHelper.buildResponse(2000);
    }
}
