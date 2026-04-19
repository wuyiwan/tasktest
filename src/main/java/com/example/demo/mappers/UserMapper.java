package com.example.demo.mappers;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper{

    @Select("select * from task")
    List<User> selectAll();

    @Select("select id, level, description, date, pic, image_path, task_description from task where id = #{id}")
    User select(String id);

    @Delete("delete from task where id = #{id}")
    int delete(String id);

    @Insert({ "insert into task(id, level, description, date, pic, image_path, task_description) values(#{id}, #{level}, #{description}, #{date}, #{pic}, #{imagePath}, #{taskDescription})" })
    int insert(User user);

    @Update("update task set level=#{level}, description=#{description}, date=#{date}, pic=#{pic}, image_path=#{imagePath}, task_description=#{taskDescription} where id=#{id}")
    int update(User user);
}
