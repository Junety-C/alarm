package cn.junety.alarm.web.service;

import cn.junety.alarm.base.dao.ModuleDao;
import cn.junety.alarm.base.dao.ProjectDao;
import cn.junety.alarm.base.entity.Module;
import cn.junety.alarm.base.entity.Project;
import cn.junety.alarm.web.vo.ProjectForm;
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
     * 获取所有项目信息
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
     * 获取项目信息（分页）
     * @param projectForm
     * @return
     */
    public List<Project> getProjectList(ProjectForm projectForm) {
        int length = projectForm.getLength();
        int begin =  (projectForm.getPage() - 1) * length;
        List<Project> projects;

        if(projectForm.getName() != null) {
            projects = projectDao.getProjectByName(projectForm.getName()+"%", begin, length);
        } else {
            projects = projectDao.getProject(begin, length);
        }

        return projects;
    }

    public int getProjectCount(ProjectForm projectForm) {
        if(projectForm.getName() != null) {
            return projectDao.getProjectCountByName(projectForm.getName()+"%");
        } else {
            return projectDao.getProjectCount();
        }
    }

    public int createProject(Project project) {
        return projectDao.save(project);
    }

    public void deleteProjectById(int id) {
        moduleDao.deleteByProjectId(id);
        projectDao.deleteById(id);
    }

    public int createModule(Integer pid, String name) {
        return moduleDao.save(pid, name);
    }

    public int deleteModuleById(int id) {
        return moduleDao.deleteById(id);
    }
}
