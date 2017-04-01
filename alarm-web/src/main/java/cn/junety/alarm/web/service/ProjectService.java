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

    public List<Module> getModuleByPid(int pid) {
        return moduleDao.getByPid(pid);
    }

    public List<Project> getProjects(ProjectForm projectForm) {
        int length = projectForm.getLength();
        int begin =  (projectForm.getPage() - 1) * length;
        List<Project> projects;

        if(projectForm.getName() != null) {
            projects = projectDao.getByName(projectForm.getName()+"%", begin, length);
        } else {
            projects = projectDao.get(begin, length);
        }

        return projects;
    }

    public int getProjectCount(ProjectForm projectForm) {
        if(projectForm.getName() != null) {
            return projectDao.getCountByName(projectForm.getName()+"%");
        } else {
            return projectDao.getCount();
        }
    }

    public int createProject(Project project) {
        return projectDao.save(project);
    }

    public void deleteProject(int id) {
        moduleDao.deleteByPid(id);
        projectDao.deleteById(id);
    }

    public int createModule(Integer pid, String name) {
        return moduleDao.save(pid, name);
    }

    public int deleteModule(int id) {
        return moduleDao.deleteById(id);
    }
}
