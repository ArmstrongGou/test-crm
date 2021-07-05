package com.xxxx.crm.mapper;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.User;

public interface UserMapper extends BaseMapper<User,Integer> {
    //新增
    public User selectUserByName(String userName);

}