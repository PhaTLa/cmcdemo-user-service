/**
 * @mbg.generated generator on Tue Sep 06 09:51:34 GMT+07:00 2022
 */
package com.example.user_management.user.service.impl;

import com.example.user_management.user.dao.RoleMapper;
import com.example.user_management.user.model.Role;
import com.example.user_management.user.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public int deleteByPrimaryKey(Byte id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Role row) {
        return roleMapper.insert(row);
    }

    @Override
    public Role selectByPrimaryKey(Byte id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Role row) {
        return roleMapper.updateByPrimaryKey(row);
    }
}