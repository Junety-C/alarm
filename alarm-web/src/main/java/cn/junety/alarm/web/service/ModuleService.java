package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.Module;
import cn.junety.alarm.web.dao.ModuleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/4/16.
 */
@Service
public class ModuleService {

    private static final Logger logger = LoggerFactory.getLogger(ModuleService.class);

    @Autowired
    private ModuleDao moduleDao;

    /**
     * 根据项目id获取模块列表
     * @param projectId 项目id
     * @return 模块列表
     */
    public List<Module> getModuleList(int projectId) {
        return moduleDao.getModuleByPprojectId(projectId);
    }

    /**
     * 创建项目模块
     * @param module 模块信息
     */
    public void createModule(Module module) {
        moduleDao.save(module);
    }

    /**
     * 根据模块id删除模块
     * @param moduleId 模块id
     */
    public void deleteModule(int moduleId) {
        moduleDao.deleteById(moduleId);
    }

    /**
     * 根据项目id删除其所有模块
     * @param projectId 项目id
     */
    public void deleteModuleByProjectId(int projectId) {
        moduleDao.deleteByProjectId(projectId);
    }
}
