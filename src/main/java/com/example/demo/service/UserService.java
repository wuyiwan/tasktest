package com.example.demo.service;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService{
    List<User> getAll();

    User getOne(Integer id);

    int remove(Integer id);

    int add(User user);

    int modify(User user);

    void saveAll(List<User> list);
}
