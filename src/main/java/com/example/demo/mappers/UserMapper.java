package com.example.demo.mappers;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper{

    @Select("select id, level, description, date, pic, image_path as imagePath, description_detail as descriptionDetail from task")
    List<User> selectAll();

    @Select("select id, level, description, date, pic, image_path as imagePath, description_detail as descriptionDetail from task where id = #{id}")
    User select(Integer id);

    @Delete("delete from task where id = #{id}")
    int delete(Integer id);

    @Insert({ "insert into task(id, level, description, date, pic, image_path, description_detail) values(#{id}, #{level}, #{description}, #{date}, #{pic}, #{imagePath}, #{descriptionDetail})" })
    int insert(User user);

    @Update("update task set level=#{level}, description=#{description}, date=#{date}, pic=#{pic}, image_path=#{imagePath}, description_detail=#{descriptionDetail} where id=#{id}")
    int update(User user);
}
