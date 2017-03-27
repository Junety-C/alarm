package cn.junety.alarm.web.service;

import cn.junety.alarm.base.dao.GroupDao;
import cn.junety.alarm.base.dao.ReceiverDao;
import cn.junety.alarm.base.entity.Group;
import cn.junety.alarm.base.entity.Receiver;
import cn.junety.alarm.web.vo.GroupForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/3/27.
 */
@Service
public class GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private ReceiverDao receiverDao;

    public List<Group> getGroups(GroupForm groupForm) {
        int length = groupForm.getLength();
        int begin =  (groupForm.getPage() - 1) * length;

        if(groupForm.getName() != null) {
            return groupDao.getByName(groupForm.getName()+"%", begin, length);
        } else {
            return groupDao.get(begin, length);
        }
    }

    public List<Receiver> getReceivers() {
        return receiverDao.getAll();
    }

    public int getGroupCount(GroupForm groupForm) {
        if(groupForm.getName() != null) {
            return groupDao.getCountByName(groupForm.getName()+"%");
        } else {
            return groupDao.getCount();
        }
    }

    public List<Receiver> getReceiverByGroupId(Integer gid) {
        return receiverDao.getByGroupId(gid);
    }

    public int createGroup(Group group) {
        return groupDao.save(group);
    }

    public int deleteGroup(int id) {
        return groupDao.deleteById(id);
    }

    public int addReceiverFromGroup(int gid, int rid) {
        return groupDao.saveReceiverToGroup(gid, rid);
    }

    public int deleteReceiverFromGroup(int gid, int rid) {
        return groupDao.deleteReceiverFromGroup(gid, rid);
    }
}
