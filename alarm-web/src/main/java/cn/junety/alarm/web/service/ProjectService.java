package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.Module;
import cn.junety.alarm.base.entity.Project;
import cn.junety.alarm.base.entity.User;
import cn.junety.alarm.base.entity.UserTypeEnum;
import cn.junety.alarm.web.dao.ModuleDao;
import cn.junety.alarm.web.dao.ProjectDao;
import cn.junety.alarm.web.vo.ProjectSearch;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/3/26.
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
     * @return
     */
    public List<Project> getProjectList() {
        return projectDao.getAllProject();
    }

    /**
     * 根据项目id获取对应模块信息
     * @param pid
     * @return
     */
    public List<Module> getModuleByProjectId(int pid) {
        return moduleDao.getModuleByPprojectId(pid);
    }

    /**
     * 获取项目列表
     * @param user
     * @param projectSearch
     * @return
     */
    public List<Project> getProjectList(User user, ProjectSearch projectSearch) {
        List<Project> projects;
        // 管理员获取所有项目, 普通用户获取自己所属的项目
        if (user.getType() == UserTypeEnum.ADMIN_USER.value()) {
            logger.debug("get all project info, user:{}", JSON.toJSONString(user));
            projects = getAllProject(projectSearch);
        } else {
            logger.debug("get user project info, user:{}", JSON.toJSONString(user));
            projects = getUserProject(projectSearch);
        }
        return projects;
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
     * @param user
     * @param projectSearch
     * @return
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
     * @param name
     * @return
     */
    public int createProject(String name) {
        return projectDao.save(name);
    }

    /**
     * 删除项目
     * @param id
     */
    public void deleteProjectById(int id) {
        moduleDao.deleteByProjectId(id);
        projectDao.deleteById(id);
    }

    /**
     * 新建模块
     * @param pid
     * @param name
     * @return
     */
    public int createModule(Integer pid, String name) {
        return moduleDao.save(pid, name);
    }

    /**
     * 删除模块
     * @param id
     * @return
     */
    public int deleteModuleById(int id) {
        return moduleDao.deleteById(id);
    }
}
