package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.Group;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.dao.GroupDao;
import cn.junety.alarm.web.dao.UserDao;
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
    private UserDao userDao;

    public List<Group> getAllGroup() {
        return groupDao.getAllGroup();
    }

    /**
     * 根据项目id获取接收组列表
     * @param projectId 项目id
     * @return 接收组列表
     */
    public List<Group> getGroupList(int projectId) {
        return groupDao.getGroupByProjectId(projectId);
    }

    /**
     * 根据接收组id获取接收组成员
     * @param groupId 接收组id
     * @return 成员列表
     */
    public List<User> getMemberList(int groupId) {
        return groupDao.getMemberListByGroupId(groupId);
    }

    /**
     * 新建接收组
     * @param projectId 项目id
     * @param groupName 接收组名称
     * @return 接收组id
     */
    public int createGroup(int projectId, String groupName) {
        Group group = new Group();
        group.setName(groupName);
        group.setProjectId(projectId);
        groupDao.save(group);
        return group.getId();
    }

    /**
     * 添加接收组成员
     * @param groupId 接收组id
     * @param userId 用户id
     */
    public void addGroupMember(int groupId, int userId) {
        groupDao.addGroupMember(groupId, userId);
    }

    /**
     * 删除接收组
     * @param groupId 接收组id
     */
    public void deleteGroup(int groupId) {
        groupDao.deleteGroupById(groupId);
    }

    /**
     * 移除指定接收组的所有成员
     * @param groupId 接收组id
     */
    public void removeGroupMember(int groupId) {
        groupDao.removeGroupMemberByGroupId(groupId);
    }

    /**
     * 移除指定接收组的指定成员
     * @param groupId 接收组id
     * @param userId 用户id
     */
    public void removeGroupMember(int groupId, int userId) {
        groupDao.removeGroupMember(groupId, userId);
    }
}
