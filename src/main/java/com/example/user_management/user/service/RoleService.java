/**
 * @mbg.generated generator on Tue Sep 06 09:51:34 GMT+07:00 2022
 */
package com.example.user_management.user.service;

import com.example.user_management.user.model.Role;

import java.util.List;

public interface RoleService {
    int deleteByPrimaryKey(Byte id);

    int insert(Role row);

    Role selectByPrimaryKey(Byte id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role row);
}