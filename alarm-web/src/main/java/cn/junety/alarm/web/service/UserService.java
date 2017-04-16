package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.dao.UserDao;
import cn.junety.alarm.web.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/4/2.
 * 用户登录相关
 */
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    public User getUserByAccount(String account) {
        try {
            return userDao.getUserByAccount(account);
        } catch (Exception e) {
            logger.error("get by account error, account:{}, caused by", account, e);
            return null;
        }
    }

    public User getUserByIdentification(String identification) {
        try {
            return userDao.getUserByIdentification(identification);
        } catch (Exception e) {
            logger.error("get by identification error, identification:{}, caused by", identification, e);
            return null;
        }
    }

    public List<UserVO> getAllUser() {
        return userDao.getAllUser();
    }
}
