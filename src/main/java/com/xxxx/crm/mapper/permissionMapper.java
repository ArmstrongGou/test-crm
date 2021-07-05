package com.xxxx.crm.mapper;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.vo.permission;

public interface permissionMapper extends BaseMapper<permission,Integer> {
    int countPermissionByRoleId(Integer roleId);



    /*int deleteByPrimaryKey(Integer id);

    int insert(permission record);

    int insertSelective(permission record);

    permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(permission record);

    int updateByPrimaryKey(permission record);*/
}