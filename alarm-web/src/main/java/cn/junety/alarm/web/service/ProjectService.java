package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.Module;
import cn.junety.alarm.base.entity.Project;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.base.entity.UserTypeEnum;
import cn.junety.alarm.web.dao.ModuleDao;
import cn.junety.alarm.web.dao.ProjectDao;
import cn.junety.alarm.web.vo.ProjectSearch;
import cn.junety.alarm.web.vo.UserVO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/3/26.
 * 项目/模块相关
 */
@Service
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private ProjectDao projectDao;

    /**
     * 获取项目列表（全部）
     * @return 项目列表
     */
    public List<Project> getProjectList() {
        return projectDao.getAllProject();
    }

    /**
     * 根据项目id获取对应模块信息
     * @param pid 项目id
     * @return 模块列表
     */
    public List<Module> getModuleByProjectId(int pid) {
        return moduleDao.getModuleByPprojectId(pid);
    }

    /**
     * 获取项目列表
     * @param user 用户信息
     * @param projectSearch 查询参数
     * @return 项目列表
     */
    public List<Project> getProjectList(User user, ProjectSearch projectSearch) {
        // 管理员获取所有项目, 普通用户获取自己所属的项目
        if (user.getType() == UserTypeEnum.ADMIN_USER.value()) {
            logger.debug("get all project info, user:{}", JSON.toJSONString(user));
            return getAllProject(projectSearch);
        } else {
            logger.debug("get user project info, user:{}", JSON.toJSONString(user));
            return getUserProject(projectSearch);
        }
    }

    private List<Project> getAllProject(ProjectSearch projectSearch) {
        if(projectSearch.getProjectName() != null) {
            return projectDao.getProjectByName(projectSearch);
        } else {
            return projectDao.getProject(projectSearch);
        }
    }

    private List<Project> getUserProject(ProjectSearch projectSearch) {
        if(projectSearch.getProjectName() != null) {
            return projectDao.getUserProjectByName(projectSearch);
        } else {
            return projectDao.getUserProject(projectSearch);
        }
    }

    /**
     * 获取项目列表的长度，用于分页
     * @param user 用户信息
     * @param projectSearch 查询参数
     * @return 项目列表大小
     */
    public int getProjectCount(User user, ProjectSearch projectSearch) {
        // 管理员获取所有项目, 普通用户获取自己所属的项目
        if (user.getType() == UserTypeEnum.ADMIN_USER.value()) {
            logger.debug("get all project count, user:{}", JSON.toJSONString(user));
            return getAllProjectCount(projectSearch);
        } else {
            logger.debug("get user project count, user:{}", JSON.toJSONString(user));
            return getUserProjectCount(projectSearch);
        }
    }

    private int getAllProjectCount(ProjectSearch projectSearch) {
        if(projectSearch.getProjectName() != null) {
            return projectDao.getProjectCountByName(projectSearch);
        } else {
            return projectDao.getProjectCount();
        }
    }

    private int getUserProjectCount(ProjectSearch projectSearch) {
        if(projectSearch.getProjectName() != null) {
            return projectDao.getUserProjectCountByName(projectSearch);
        } else {
            return projectDao.getUserProjectCount();
        }
    }

    /**
     * 新建项目
     * @param name 项目名称
     * @return 项目id
     */
    public int createProject(String name) {
        Project project = new Project();
        project.setName(name);
        projectDao.save(project);
        return project.getId();
    }

    /**
     * 删除项目
     * @param id 项目id
     */
    public void deleteProjectById(int id) {
        moduleDao.deleteByProjectId(id);
        projectDao.deleteById(id);
        projectDao.deleteProjectMemberById(id);
    }

    /**
     * 新建模块
     * @param pid 项目id
     * @param name 模块名称
     */
    public void createModule(Integer pid, String name) {
        moduleDao.save(pid, name);
    }

    /**
     * 删除模块
     * @param id 模块id
     */
    public void deleteModuleById(int id) {
        moduleDao.deleteById(id);
    }

    public List<UserVO> getProjectMemberByProjectId(int pid) {
        return projectDao.getProjectMemberBytId(pid);
    }

    /**
     * 把用户从项目中移除
     * @param uid 用户id
     * @param pid 项目id
     */
    public void removeUserFromProject(int uid, int pid) {
        projectDao.removeUserFromProject(uid, pid);
    }

    /**
     * 添加用户到项目中
     * @param uid 用户id
     * @param pid 项目id
     */
    public void addUserToProject(int uid, int pid) {
        projectDao.addUserToProject(uid, pid);
    }
}
