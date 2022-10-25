package com.example.user_management.user.dao;

import com.example.user_management.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User row);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User row);

    User selectByEmail(String email);
    
    Long getUserIdByUserName(String userName);
}