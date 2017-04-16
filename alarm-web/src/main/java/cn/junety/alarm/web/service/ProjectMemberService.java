package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.web.dao.ProjectMemberDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/4/16.
 * 项目成员
 */
@Service
public class ProjectMemberService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectMemberService.class);

    public static final int ADMIN_MEMBER = 0;
    public static final int NORMAL_MEMBER = 1;

    @Autowired
    private ProjectMemberDao projectMemberDao;

    /**
     * 获取项目成员列表
     * @param projectId 项目id
     * @return 项目成员列表
     */
    public List<User> getMemberList(int projectId) {
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
}
