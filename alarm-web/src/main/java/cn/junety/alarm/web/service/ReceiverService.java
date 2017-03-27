package cn.junety.alarm.web.service;

import cn.junety.alarm.base.dao.ReceiverDao;
import cn.junety.alarm.base.entity.Receiver;
import cn.junety.alarm.web.vo.ReceiverForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/3/27.
 */
@Service
public class ReceiverService {

    @Autowired
    private ReceiverDao receiverDao;

    public List<Receiver> getReceivers(ReceiverForm receiverForm) {
        int length = receiverForm.getLength();
        int begin =  (receiverForm.getPage() - 1) * length;

        if(receiverForm.getName() != null) {
            return receiverDao.getByName("%"+receiverForm.getName()+"%", begin, length);
        } else if(receiverForm.getMail() != null) {
            return receiverDao.getByMail("%"+receiverForm.getMail()+"%", begin, length);
        } else if(receiverForm.getPhone() != null){
            return receiverDao.getByPhone("%"+receiverForm.getPhone()+"%", begin, length);
        } else if(receiverForm.getWechat() != null) {
            return receiverDao.getByWechat("%"+receiverForm.getWechat()+"%", begin, length);
        } else if(receiverForm.getQq() != null) {
            return receiverDao.getByQq("%"+receiverForm.getQq()+"%", begin, length);
        } else {
            return receiverDao.get(begin, length);
        }
    }

    public int getReceiverCount(ReceiverForm receiverForm) {
        if(receiverForm.getName() != null) {
            return receiverDao.getCountByName("%"+receiverForm.getName()+"%");
        } else if(receiverForm.getMail() != null) {
            return receiverDao.getCountByMail("%"+receiverForm.getMail()+"%");
        } else if(receiverForm.getPhone() != null){
            return receiverDao.getCountByPhone("%"+receiverForm.getPhone()+"%");
        } else if(receiverForm.getWechat() != null) {
            return receiverDao.getCountByWechat("%"+receiverForm.getWechat()+"%");
        } else if(receiverForm.getQq() != null) {
            return receiverDao.getCountByQq("%"+receiverForm.getQq()+"%");
        } else {
            return receiverDao.getCount();
        }
    }

    public int createReceiver(Receiver receiver) {
        return receiverDao.save(receiver);
    }

    public int deleteReceiver(int id) {
        return receiverDao.deleteById(id);
    }

    public int updateReceiver(Receiver receiver) {
        return receiverDao.updateById(receiver);
    }

    public Receiver getReceiverById(int id) {
        return receiverDao.getById(id);
    }
}
