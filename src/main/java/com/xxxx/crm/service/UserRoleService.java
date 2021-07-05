package com.xxxx.crm.service;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.UserMapper;
import com.xxxx.crm.mapper.UserRoleMapper;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.User;
import com.xxxx.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRoleService extends BaseService<UserRole,Integer> {

    @Autowired(required = false)
    private UserRoleMapper userRoleMapper;
    private void relaionUserRole(int userId, String roleIds) {
        Integer count = userRoleMapper.countUserRoleByUserId(userId);
        if (count > 0) {
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != count,
                    "用户角色分配失败");
        }
        if (StringUtils.isBlank(roleIds)) {
            //重新添加新的角色
            List<UserRole> userRoles = new ArrayList<>();
            for (String s:roleIds.split(",")
                 ) {
                UserRole userRole=new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(Integer.parseInt(s));
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoles.add(userRole);
            }
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoles)<
                    userRoles.size(),"用户角色分配失败");
        }
    }
    @Autowired
    private UserMapper userMapper;
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(null == userId || null == user, "待删除的记录不纯在");
        Integer count = userRoleMapper.countUserRoleByUserId(userId);
        if (count > 0) {
            AssertUtil.isTrue(
                    userRoleMapper.deleteUserRoleByUserId(userId)!=count,
                    "用户角色删除失败！"
            );
            user.setIsValid(0);
            AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"用户记录删除失败");
        }
    }
}
