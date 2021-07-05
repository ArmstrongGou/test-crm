package com.xxxx.crm.mapper;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {

    int updateByPrimaryKey(Role record);

     List<Map<String,Object>> queryAllRoles();

    Role queryRoleByRoleName(String roleName);
}