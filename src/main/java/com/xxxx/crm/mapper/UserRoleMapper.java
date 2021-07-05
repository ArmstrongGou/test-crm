package com.xxxx.crm.mapper;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {
    public Integer countUserRoleByUserId(int userId);

    public Integer deleteUserRoleByUserId(int userId);
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(UserRole record);
//
//    int insertSelective(UserRole record);
//
//    UserRole selectByPrimaryKey(Integer id);
//
//    int updateByPrimaryKeySelective(UserRole record);
//
//    int updateByPrimaryKey(UserRole record);
}