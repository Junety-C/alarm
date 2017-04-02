package cn.junety.alarm.web.service;

import cn.junety.alarm.base.dao.UserDao;
import cn.junety.alarm.base.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by caijt on 2017/4/2.
 */
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    public User getByUsername(String username) {
        try {
            return userDao.getByUsername(username);
        } catch (Exception e) {
            logger.error("get by username error, username:{}, caused by", username, e);
            return null;
        }
    }

    public User getByIdentification(String identification) {
        try {
            return userDao.getByIdentification(identification);
        } catch (Exception e) {
            logger.error("get by identification error, identification:{}, caused by", identification, e);
            return null;
        }
    }
}
