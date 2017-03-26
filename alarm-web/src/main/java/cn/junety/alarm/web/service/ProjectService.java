package cn.junety.alarm.web.service;

import cn.junety.alarm.base.dao.ModuleDao;
import cn.junety.alarm.base.entity.Module;
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

    public List<Module> getModuleByPid(int pid) {
        return moduleDao.getByPid(pid);
    }
}
