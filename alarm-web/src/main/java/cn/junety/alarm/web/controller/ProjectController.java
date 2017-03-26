package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.ProjectService;
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
        logger.info("GET /projects, body:{}");
        return ResponseHelper.buildResponse(2000, "GET /projects");
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addProject(HttpServletRequest request) {
        logger.info("POST /projects, body:{}");
        return ResponseHelper.buildResponse(2000, "POST /projects");
    }

    @RequestMapping(value = "/projects/{pid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateProject(HttpServletRequest request, @PathVariable Integer pid) {
        logger.info("PUT /projects/{}, body:{}", pid);
        return ResponseHelper.buildResponse(2000, "PUT /projects/"+pid);
    }

    @RequestMapping(value = "/projects/{pid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteProject(HttpServletRequest request, @PathVariable Integer pid) {
        logger.info("DELETE /projects/{}, body:{}", pid);
        return ResponseHelper.buildResponse(2000, "DELETE /projects/"+pid);
    }

    @RequestMapping(value = "/projects/{pid}/modules", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getModules(HttpServletRequest request, @PathVariable Integer pid) {
        logger.info("GET /projects/{}/modules", pid);
        Map<String, Object> results = new HashMap<>();
        results.put("modules", projectService.getModuleByPid(pid));
        return ResponseHelper.buildResponse(2000, results);
    }

    @RequestMapping(value = "/projects/{pid}/modules", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addModule(HttpServletRequest request, @PathVariable Integer pid) {
        logger.info("POST /projects/{}/modules, body:{}", pid);
        return ResponseHelper.buildResponse(2000, "POST /projects/"+pid+"/modules");
    }

    @RequestMapping(value = "/projects/{pid}/modules/{mid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateModule(HttpServletRequest request, @PathVariable Integer pid, @PathVariable Integer mid) {
        logger.info("PUT /projects/{}/modules/{}, body:{}", pid, mid);
        return ResponseHelper.buildResponse(2000, "PUT /projects/"+pid+"/modules/"+mid);
    }

    @RequestMapping(value = "/projects/{pid}/modules/{mid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteModule(HttpServletRequest request, @PathVariable Integer pid, @PathVariable Integer mid) {
        logger.info("DELETE /projects/{}/modules/{}, body:{}", pid, mid);
        return ResponseHelper.buildResponse(2000, "DELETE /projects/"+pid+"/modules/"+mid);
    }
}
