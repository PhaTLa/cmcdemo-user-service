/**
 * @mbg.generated generator on Tue Sep 06 09:51:46 GMT+07:00 2022
 */
package com.example.user_management.user.service;

import com.example.user_management.user.dto.req.ReqUserDto;
import com.example.user_management.user.dto.resp.RespUserDto;
import com.example.user_management.user.model.User;

import java.util.List;

public interface UserService {
    int deleteByPrimaryKey(Long id);

    RespUserDto insert(ReqUserDto dto);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User row);
}