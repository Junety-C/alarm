package cn.junety.alarm.base.dao;

import cn.junety.alarm.base.entity.Module;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public interface ModuleDao {
    @Select("select id, project_id, name from tb_module where id=#{id}")
    Module getById(@Param("id") int id);

    @Select("select id, project_id, name from tb_module where project_id=#{pid}")
    List<Module> getByPid(@Param("pid") int pid);

    @Insert("insert into tb_module(project_id, name) values(#{projectId}, #{name})")
    int save(@Param("projectId") int projectId, @Param("name") String name);

    @Update("update tb_module set name=#{name} where id=#{id}")
    int updateById(@Param("name") String name);

    @Delete("delete from tb_module where id=#{id}")
    int deleteById(int id);

    @Delete("delete from tb_module where project_id=#{pid}")
    int deleteByPid(@Param("pid") int pid);

    @Select("select count(id) from tb_module")
    int count();
}
