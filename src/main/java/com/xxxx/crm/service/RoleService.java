package com.xxxx.crm.service;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.RoleMapper;
import com.xxxx.crm.mapper.permissionMapper;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {
    @Resource
    private RoleMapper roleMapper;
    public List<Map<String, Object>> queryAllRoles() {
        System.out.println(roleMapper.queryAllRoles());
        return roleMapper.queryAllRoles();
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRole(Role role) {
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "请输入角色名");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null != temp, "该角色已经存在");
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(role)<1,"角色记录添加失败");
    }
    @Transactional(propagation=Propagation.REQUIRED)
    public void updateRole(Role role) {
        AssertUtil.isTrue(null == role.getId() || null == selectByPrimaryKey(role.getId()),
                "待修改的记录不存在");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()), "请输入角色名");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(role.getId())), "该角色已存在");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(role)<1,"角色记录更新失败");
    }

    public void deleteRole(Integer roleId) {
        Role temp = selectByPrimaryKey(roleId);
        AssertUtil.isTrue(null==roleId||null==temp,"待删除的记录不存在");
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"角色记录删除失败");
    }

    public void addGrant(Integer[] mids, Integer roleId) {
        /**
         * 核⼼表-t_permission t_role(校验⻆⾊存在)
         * 如果⻆⾊存在原始权限 删除⻆⾊原始权限
         * 然后添加⻆⾊新的权限 批量添加权限记录到t_permission
         */
        Role temp = selectByPrimaryKey(roleId);
        AssertUtil.isTrue(null==roleId||null==temp,"待授权");

    }
}
