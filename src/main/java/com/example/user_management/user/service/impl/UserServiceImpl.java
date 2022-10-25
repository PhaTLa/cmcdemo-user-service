/**
 * @mbg.generated generator on Tue Sep 06 09:51:46 GMT+07:00 2022
 */
package com.example.user_management.user.service.impl;

import com.example.user_management.exception.UserException;
import com.example.user_management.user.dao.UserMapper;
import com.example.user_management.user.dto.req.ReqUserDto;
import com.example.user_management.user.dto.resp.RespUserDto;
import com.example.user_management.user.model.User;
import com.example.user_management.user.service.UserService;
import com.example.user_management.user.util.UserMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public RespUserDto insert(ReqUserDto dto) {
        if (userMapper.selectByEmail(dto.getEmail()) != null) {
            throw new UserException("Email đã tồn tại!");
        } else if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new UserException("Password không đúng");
        } else {
            User user = UserMap.DtoMapUser(dto);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRoleId((byte) 2);
            userMapper.insert(user);
            return UserMap.UserMapDto(user);
        }
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(User row) {
        return userMapper.updateByPrimaryKey(row);
    }

}