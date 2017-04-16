package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.base.entity.UserTypeEnum;
import cn.junety.alarm.web.dao.UserDao;
import cn.junety.alarm.web.vo.UserSearch;
import cn.junety.alarm.web.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by caijt on 2017/4/2.
 * 用户相关
 */
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户账号获取用户信息
     * @param account 用户账号
     * @return 用户信息
     */
    public User getUserByAccount(String account) {
        try {
            return userDao.getUserByExactAccount(account);
        } catch (Exception e) {
            logger.error("get by account error, account:{}, caused by", account, e);
            return null;
        }
    }

    /**
     * 根据用户标识获取用户信息
     * @param identification 用户标识
     * @return 用户信息
     */
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

    /**
     * 获取用户列表
     * @param userSearch 查询参数
     * @return 用户列表
     */
    public List<User> getUserList(UserSearch userSearch) {
        if(userSearch.getName() != null) {
            return userDao.getUserByName(userSearch);
        } else if(userSearch.getAccount() != null) {
            return userDao.getUserByAccount(userSearch);
        } else {
            return userDao.getUser(userSearch);
        }
    }

    /**
     * 获取用户列表的总长度，用于分页
     * @param userSearch 查询参数
     * @return 用户列表的总长度
     */
    public int getUserCount(UserSearch userSearch) {
        if(userSearch.getName() != null) {
            return userDao.getUserCountByName(userSearch);
        } else if(userSearch.getAccount() != null) {
            return userDao.getUserCountByAccount(userSearch);
        } else {
            return userDao.getUserCount();
        }
    }

    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    public User getUser(int id) {
        return userDao.getUserById(id);
    }

    /**
     * 创建用户
     * @param user 用户信息
     */
    public void createUser(User user) {
        user.setType(UserTypeEnum.NORMAL_USER.value());
        user.setIdentification(UUID.randomUUID().toString());
        userDao.save(user);
    }

    /**
     * 更新用户信息
     * @param user 用户信息
     */
    public void updateUser(User user) {
        userDao.update(user);
    }

    /**
     * 根据用户id删除用户
     * @param id 用户id
     */
    public void deleteUser(int id) {
        userDao.delete(id);
    }
}
