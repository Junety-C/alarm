package cn.junety.alarm.web.controller;

import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.common.ResponseHelper;
import cn.junety.alarm.web.service.UserService;
import cn.junety.alarm.web.vo.UserSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Created by caijt on 2017/4/16.
 */
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserList(HttpServletRequest request) {
        User currentUser = getUser(request);
        try {
            UserSearch userSearch = new UserSearch(request, currentUser);
            logger.info("GET /users, current_user:{}, search:{}", currentUser, userSearch);

            List<User> userList = userService.getUserList(userSearch);
            int userCount = userService.getUserCount(userSearch);

            return ResponseHelper.buildResponse(2000, "user_list", userList, "user_count", userCount,
                    "current_user", currentUser);
        } catch (Exception e) {
            logger.error("get user list error, caused by", e);
            return ResponseHelper.buildResponse(5000, "user_list", Collections.emptyList(), "user_count", 0,
                    "current_user", currentUser);
        }
    }

    @RequestMapping(value = "/users/{uid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserById(HttpServletRequest request, @PathVariable Integer uid) {
        User currentUser = getUser(request);
        logger.info("GET /user/{}, current_user:{}", uid, currentUser);

        User user = userService.getUser(uid);

        return ResponseHelper.buildResponse(2000, "user", user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createUser(HttpServletRequest request, @RequestBody User user) {
        User currentUser = getUser(request);
        logger.info("POST /users, current_user:{}, user:{}", currentUser, user);

        try {
            userService.createUser(user);
            return ResponseHelper.buildResponse(2000);
        } catch (Exception e) {
            logger.error("create user error, user:{}, caused by", user, e);
            return ResponseHelper.buildResponse(5000);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(HttpServletRequest request, @RequestBody User user) {
        User currentUser = getUser(request);
        logger.info("PUT /users, current_user:{}, user:{}", currentUser, user);

        userService.updateUser(user);

        return ResponseHelper.buildResponse(2000);
    }

    @RequestMapping(value = "/users/{uid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(HttpServletRequest request, @PathVariable Integer uid) {
        User currentUser = getUser(request);
        logger.info("DELETE /users/{}, current_user:{}", uid, currentUser);

        userService.deleteUser(uid);

        return ResponseHelper.buildResponse(2000);
    }
}
