package cn.junety.alarm.server.dao;

import cn.junety.alarm.base.entity.Module;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by caijt on 2017/1/28.
 */
public interface ModuleDao {
    @Select("select * from tb_module where id=#{id}")
    Module getModuleById(@Param("id") int id);
}
