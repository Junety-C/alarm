package cn.junety.alarm.web.service;

import cn.junety.alarm.base.entity.Receiver;
import cn.junety.alarm.web.dao.ReceiverDao;
import cn.junety.alarm.web.vo.ReceiverSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caijt on 2017/3/27.
 * 接收者相关
 */
@Service
public class ReceiverService {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverService.class);

    @Autowired
    private ReceiverDao receiverDao;

    /**
     * 获取接收者列表
     * @param receiverSearch 查询参数
     * @return 接收者列表
     */
    public List<Receiver> getReceiverList(ReceiverSearch receiverSearch) {
        if(receiverSearch.getName() != null) {
            return receiverDao.getReceiverByName(receiverSearch);
        } else if(receiverSearch.getMail() != null) {
            return receiverDao.getReceiverByMail(receiverSearch);
        } else if(receiverSearch.getPhone() != null){
            return receiverDao.getReceiverByPhone(receiverSearch);
        } else if(receiverSearch.getWechat() != null) {
            return receiverDao.getReceiverByWechat(receiverSearch);
        } else if(receiverSearch.getQq() != null) {
            return receiverDao.getReceiverByQq(receiverSearch);
        } else {
            return receiverDao.getReceiver(receiverSearch);
        }
    }

    /**
     * 获取接收者列表的长度，用于分页
     * @param receiverSearch 查询参数
     * @return 接收者列表大小
     */
    public int getReceiverCount(ReceiverSearch receiverSearch) {
        if(receiverSearch.getName() != null) {
            return receiverDao.getReceiverCountByName(receiverSearch);
        } else if(receiverSearch.getMail() != null) {
            return receiverDao.getReceiverCountByMail(receiverSearch);
        } else if(receiverSearch.getPhone() != null){
            return receiverDao.getReceiverCountByPhone(receiverSearch);
        } else if(receiverSearch.getWechat() != null) {
            return receiverDao.getReceiverCountByWechat(receiverSearch);
        } else if(receiverSearch.getQq() != null) {
            return receiverDao.getReceiverCountByQq(receiverSearch);
        } else {
            return receiverDao.getReceiverCount();
        }
    }

    /**
     * 创建接收者
     * @param receiver 接收者信息
     */
    public void createReceiver(Receiver receiver) {
        receiverDao.save(receiver);
    }

    /**
     * 删除接收者
     * @param id 接收者id
     */
    public void deleteReceiver(int id) {
        receiverDao.deleteById(id);
    }

    /**
     * 更新接收者信息
     * @param receiver 接收者信息
     */
    public void updateReceiver(Receiver receiver) {
        receiverDao.updateById(receiver);
    }

    /**
     * 根据接收者id获取接收者信息
     * @param id 接收者id
     * @return 接收者信息
     */
    public Receiver getReceiverById(int id) {
        return receiverDao.getReceiverById(id);
    }
}
