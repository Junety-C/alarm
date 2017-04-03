package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.Receiver;
import cn.junety.alarm.web.dao.ReceiverDao;
import cn.junety.alarm.web.vo.ReceiverForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/3/27.
 */
@Service
public class ReceiverService {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverService.class);

    @Autowired
    private ReceiverDao receiverDao;

    /**
     * 获取接收者列表
     * @param receiverForm
     * @return
     */
    public List<Receiver> getReceiverList(ReceiverForm receiverForm) {
        int length = receiverForm.getLength();
        int begin =  (receiverForm.getPage() - 1) * length;

        if(receiverForm.getName() != null) {
            return receiverDao.getReceiverByName("%"+receiverForm.getName()+"%", begin, length);
        } else if(receiverForm.getMail() != null) {
            return receiverDao.getReceiverByMail("%"+receiverForm.getMail()+"%", begin, length);
        } else if(receiverForm.getPhone() != null){
            return receiverDao.getReceiverByPhone("%"+receiverForm.getPhone()+"%", begin, length);
        } else if(receiverForm.getWechat() != null) {
            return receiverDao.getReceiverByWechat("%"+receiverForm.getWechat()+"%", begin, length);
        } else if(receiverForm.getQq() != null) {
            return receiverDao.getReceiverByQq("%"+receiverForm.getQq()+"%", begin, length);
        } else {
            return receiverDao.getReceiver(begin, length);
        }
    }

    /**
     * 获取接收者列表的长度，用于分页
     * @param receiverForm
     * @return
     */
    public int getReceiverCount(ReceiverForm receiverForm) {
        if(receiverForm.getName() != null) {
            return receiverDao.getReceiverCountByName("%"+receiverForm.getName()+"%");
        } else if(receiverForm.getMail() != null) {
            return receiverDao.getReceiverCountByMail("%"+receiverForm.getMail()+"%");
        } else if(receiverForm.getPhone() != null){
            return receiverDao.getReceiverCountByPhone("%"+receiverForm.getPhone()+"%");
        } else if(receiverForm.getWechat() != null) {
            return receiverDao.getReceiverCountByWechat("%"+receiverForm.getWechat()+"%");
        } else if(receiverForm.getQq() != null) {
            return receiverDao.getReceiverCountByQq("%"+receiverForm.getQq()+"%");
        } else {
            return receiverDao.getReceiverCount();
        }
    }

    /**
     * 创建用户
     * @param receiver
     * @return
     */
    public int createReceiver(Receiver receiver) {
        return receiverDao.save(receiver);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public int deleteReceiver(int id) {
        return receiverDao.deleteById(id);
    }

    /**
     * 更新用户信息
     * @param receiver
     * @return
     */
    public int updateReceiver(Receiver receiver) {
        return receiverDao.updateById(receiver);
    }

    /**
     * 根据接收者id获取接收者信息
     * @param id
     * @return
     */
    public Receiver getReceiverById(int id) {
        return receiverDao.getReceiverById(id);
    }
}
