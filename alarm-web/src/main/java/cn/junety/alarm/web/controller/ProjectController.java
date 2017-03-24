package cn.junety.alarm.web.controller;

import cn.junety.alarm.web.common.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by caijt on 2017/3/24.
 */
@RestController
@RequestMapping
public class ProjectController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @RequestMapping(value = "/projects", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjects(HttpServletRequest request) {
        logger.info("GET /projects, body:{}");
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProject(HttpServletRequest request) {
        logger.info("POST /projects, body:{}");
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/projects/{pid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProject(HttpServletRequest request, @PathVariable Integer pid) {
        logger.info("PUT /projects/{}, body:{}", pid);
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/projects/{pid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProject(HttpServletRequest request, @PathVariable Integer pid) {
        logger.info("DELETE /projects/{}, body:{}", pid);
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/projects/{pid}/modules", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getModules(HttpServletRequest request, @PathVariable Integer pid) {
        logger.info("GET /projects/{}/modules, body:{}", pid);
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/projects/{pid}/modules", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addModule(HttpServletRequest request, @PathVariable Integer pid) {
        logger.info("POST /projects/{}/modules, body:{}", pid);
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/projects/{pid}/modules/{mid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateModule(HttpServletRequest request, @PathVariable Integer pid, @PathVariable Integer mid) {
        logger.info("DELETE /projects/{}/modules/{}, body:{}", pid, mid);
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }

    @RequestMapping(value = "/projects/{pid}/modules/{mid}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteModule(HttpServletRequest request, @PathVariable Integer pid, @PathVariable Integer mid) {
        logger.info("DELETE /projects/{}/modules/{}, body:{}", pid, mid);
        return HttpHelper.buildResponse(500, 5000, "bad service");
    }
}
