package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.Group;
import cn.junety.alarm.base.entity.Receiver;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.base.entity.UserTypeEnum;
import cn.junety.alarm.web.dao.GroupDao;
import cn.junety.alarm.web.dao.ReceiverDao;
import cn.junety.alarm.web.vo.GroupSearch;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/3/27.
 *
 * 接收组相关
 */
@Service
public class GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private ReceiverDao receiverDao;

    public List<Group> getAllGroup() {
        return groupDao.getAllGroup();
    }

    /**
     * 获取接收组列表
     * @param user 用户信息
     * @param groupSearch 查询参数
     * @return 接收组列表
     */
    public List<Group> getGroupList(User user, GroupSearch groupSearch) {
        // 管理员获取所有接收组, 普通用户获取自己所属的接收组
        if (user.getType() == UserTypeEnum.ADMIN_USER.value()) {
            logger.debug("get all group list, user:{}", JSON.toJSONString(user));
            return getAllGroup(groupSearch);
        } else {
            logger.debug("get user group list, user:{}", JSON.toJSONString(user));
            return getUserGroup(groupSearch);
        }
    }

    private List<Group> getAllGroup(GroupSearch groupSearch) {
        if(groupSearch.getGroupName() != null) {
            return groupDao.getGroupByName(groupSearch);
        } else {
            return groupDao.getGroup(groupSearch);
        }
    }

    private List<Group> getUserGroup(GroupSearch groupSearch) {
        if(groupSearch.getGroupName() != null) {
            return groupDao.getUserGroupByName(groupSearch);
        } else {
            return groupDao.getUserGroup(groupSearch);
        }
    }

    /**
     * 获取接收组列表的长度，用于分页
     * @param user 用户信息
     * @param groupSearch 查询参数
     * @return 接收组列表大小
     */
    public int getGroupCount(User user, GroupSearch groupSearch) {
        // 管理员获取所有项目, 普通用户获取自己所属的项目
        if (user.getType() == UserTypeEnum.ADMIN_USER.value()) {
            logger.debug("get all group count, user:{}", JSON.toJSONString(user));
            return getAllGroupCount(groupSearch);
        } else {
            logger.debug("get user group count, user:{}", JSON.toJSONString(user));
            return getUserGroupCount(groupSearch);
        }
    }

    private int getAllGroupCount(GroupSearch groupSearch) {
        if(groupSearch.getGroupName() != null) {
            return groupDao.getGroupCountByName(groupSearch);
        } else {
            return groupDao.getGroupCount();
        }
    }

    private int getUserGroupCount(GroupSearch groupSearch) {
        if(groupSearch.getGroupName() != null) {
            return groupDao.getUserGroupCountByName(groupSearch);
        } else {
            return groupDao.getUserGroupCount();
        }
    }

    /**
     * 获取所有接收者信息
     * @return 接收者列表
     */
    public List<Receiver> getReceiverList() {
        return receiverDao.getAllReceiver();
    }

    public List<Receiver> getReceiverByGroupId(Integer gid) {
        return receiverDao.getReceiverByGroupId(gid);
    }

    /**
     * 创建接收组
     * @param groupName 接收组名称
     */
    public void createGroup(String groupName) {
        groupDao.save(groupName);
    }

    /**
     * 删除接收组
     * @param id 接收组id
     */
    public void deleteGroup(int id) {
        groupDao.deleteReceiverByGroupId(id);
        groupDao.deleteById(id);
    }

    /**
     * 添加接收者到接收组里
     * @param gid 接收组id
     * @param rid 接收者id
     */
    public void addReceiverToGroup(int gid, int rid) {
        groupDao.saveReceiverToGroup(gid, rid);
    }

    /**
     * 从接收组里删除接收者
     * @param gid 接收组id
     * @param rid 接收者id
     */
    public void deleteReceiverFromGroup(int gid, int rid) {
        groupDao.deleteReceiverFromGroup(gid, rid);
    }

    /**
     * 把该接收者从所有接收组中移除
     * @param rid 接收者id
     */
    public void deleteReceiver(int rid) {
        groupDao.deleteReceiver(rid);
    }
}
