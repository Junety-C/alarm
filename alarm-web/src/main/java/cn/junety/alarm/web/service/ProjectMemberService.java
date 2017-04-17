package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.dao.ProjectMemberDao;
import cn.junety.alarm.web.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/4/16.
 * 处理项目成员
 */
@Service
public class ProjectMemberService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectMemberService.class);

    @Autowired
    private ProjectMemberDao projectMemberDao;

    /**
     * 获取项目成员列表
     * @param projectId 项目id
     * @return 项目成员列表
     */
    public List<UserVO> getMemberList(int projectId) {
        return projectMemberDao.getMemberByProjectId(projectId);
    }

    /**
     * 添加项目成员
     * @param projectId 项目id
     * @param userId 用户id
     * @param type 成员类型
     */
    public void addProjectMember(int projectId, int userId, int type) {
        projectMemberDao.addProjectMember(projectId, userId, type);
    }

    /**
     * 移除项目成员
     * @param projectId 项目id
     * @param userId 用户id
     */
    public void removeProjectMember(int projectId, int userId) {
        projectMemberDao.removeProjectMember(projectId, userId);
    }

    /**
     * 根据用户账号获取用户信息
     * @param projectId 项目id
     * @param account 用户账号
     * @return 用户信息
     */
    public User getProjectMemberByAccount(int projectId, String account) {
        return projectMemberDao.getProjectMemberByAccount(projectId, account);
    }

    /**
     * 修改项目成员权限
     * @param projectId 项目id
     * @param userId 用户id
     * @param type 权限类型
     */
    public void changeMemberType(int projectId, int userId, int type) {
        projectMemberDao.updateType(projectId, userId, type);
    }
}
