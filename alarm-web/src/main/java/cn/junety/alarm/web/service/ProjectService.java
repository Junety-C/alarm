package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.*;
import cn.junety.alarm.web.dao.GroupDao;
import cn.junety.alarm.web.dao.ModuleDao;
import cn.junety.alarm.web.dao.ProjectDao;
import cn.junety.alarm.web.dao.ProjectMemberDao;
import cn.junety.alarm.web.vo.ProjectSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/3/26.
 * 处理项目
 */
@Service
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectMemberDao projectMemberDao;
    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private GroupDao groupDao;

    /**
     * 获取所有项目信息
     * @return 项目列表
     */
    public List<Project> getProjectList(User user) {
        // 管理员获取所有项目, 普通用户获取自己所属的项目
        if (UserTypeEnum.ADMIN_USER.value().equals(user.getType())) {
            logger.debug("get all project info, user:{}", user);
            return projectDao.getAllProject();
        } else {
            logger.debug("get user project info, user:{}", user);
            return projectDao.getAllUserProject(user.getId());
        }
    }

    /**
     * 获取项目列表
     * @param projectSearch 查询参数
     * @return 项目列表
     */
    public List<Project> getProjectList(ProjectSearch projectSearch) {
        // 管理员获取所有项目, 普通用户获取自己所属的项目
        if (UserTypeEnum.ADMIN_USER.value().equals(projectSearch.getUserType())) {
            logger.debug("get all project info, search:{}", projectSearch);
            return getAllProject(projectSearch);
        } else {
            logger.debug("get user project info, search:{}", projectSearch);
            return getUserProject(projectSearch);
        }
    }

    private List<Project> getAllProject(ProjectSearch projectSearch) {
        return projectDao.getProject(projectSearch);
    }

    private List<Project> getUserProject(ProjectSearch projectSearch) {
        return projectDao.getUserProject(projectSearch);
    }

    /**
     * 获取项目列表的大小，用于分页
     * @param projectSearch 查询参数
     * @return 项目列表大小
     */
    public int getProjectCount(ProjectSearch projectSearch) {
        // 管理员获取所有项目, 普通用户获取自己所属的项目
        if (UserTypeEnum.ADMIN_USER.value().equals(projectSearch.getUserType())) {
            logger.debug("get all project count, search:{}", projectSearch);
            return getAllProjectCount();
        } else {
            logger.debug("get user project count, search:{}", projectSearch);
            return getUserProjectCount(projectSearch);
        }
    }

    private int getAllProjectCount() {
        return projectDao.getProjectCount();
    }

    private int getUserProjectCount(ProjectSearch projectSearch) {
        return projectDao.getUserProjectCount(projectSearch.getUserId());
    }

    /**
     * 获取项目成员类型
     * @param userId 用户id
     * @param projectId 项目id
     * @return 0管理员, 1成员
     */
    public int getProjectMemberType(int userId, int projectId) {
        Integer type = projectMemberDao.getProjectMemberTypeByProjectId(userId, projectId);
        return type == null ? ProjectMemberTypeEnum.NORMAL_MEMBER.value() : type;
    }

    /**
     * 根据项目id获取项目信息
     * @param pid 项目id
     * @return 项目信息
     */
    public Project getProjectById(int pid) {
        return projectDao.getProjectById(pid);
    }

    /**
     * 新建项目
     * @param user 用户信息
     * @param project 项目信息
     * @return 项目id
     */
    public int createProject(User user, Project project) {
        project.setCreater(user.getName());
        project.setCreateTime(System.currentTimeMillis());
        projectDao.save(project);
        return project.getId();
    }

    /**
     * 更新项目
     * @param project 项目信息
     */
    public void updateProject(Project project) {
        projectDao.update(project);
    }

    /**
     * 删除项目
     * @param projectId 项目id
     */
    public void deleteProject(int projectId) {
        projectDao.delete(projectId);
        projectMemberDao.deleteProjectMemberByProjectId(projectId);
        moduleDao.deleteByProjectId(projectId);
        List<Group> groupList = groupDao.getGroupByProjectId(projectId);
        for(Group group : groupList) {
            groupDao.deleteGroupById(group.getId());
        }
    }
}
