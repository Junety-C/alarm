package cn.junety.alarm.server.dao;

import cn.junety.alarm.base.entity.Module;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by caijt on 2017/1/28.
 */
public interface ModuleDao {
    @Select("select id, project_id, name from tb_module where id=#{id}")
    Module getModuleById(@Param("id") int id);

    @Select("select id, project_id, name from tb_module where project_id=#{pid} order by id desc")
    List<Module> getModuleByPprojectId(@Param("pid") int pid);

    @Insert("insert into tb_module(project_id, name) values(#{projectId}, #{name})")
    int save(@Param("projectId") int projectId, @Param("name") String name);

    @Delete("delete from tb_module where id=#{id}")
    int deleteById(int id);

    @Delete("delete from tb_module where project_id=#{pid}")
    int deleteByProjectId(@Param("pid") int pid);
}
