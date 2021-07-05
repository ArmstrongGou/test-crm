package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.dto.TreeModel;
import com.xxxx.crm.query.RoleQuery;
import com.xxxx.crm.service.ModuleService;
import com.xxxx.crm.service.RoleService;
import com.xxxx.crm.vo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleService moduleService;

    @RequestMapping("queryModule")
    @ResponseBody
    public List<TreeModel> sayAllModule(){
        return moduleService.querylModule();
    }

    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String, Object>> sayList() {
        System.out.println("1111");
        return roleService.queryAllRoles();
    }

    @RequestMapping("index")
    public String index() {
        return "role/role";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> userList(RoleQuery roleQuery) {
        return roleService.queryByParamsForTable(roleQuery);
    }

    @RequestMapping("addOrUpdateRolePage")
    public String addUserPage(Integer id, Model model) {
        if (null != id) {
            model.addAttribute("role", roleService.selectByPrimaryKey(id));
        }
        return "role/add_update";
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveRole(Role role) {
        roleService.saveRole(role);
        return success("角色添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role) {
        roleService.updateRole(role);
        return success("角色记录更新成功");
    }
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer id) {
        roleService.deleteRole(id);
        return success("角色记录删除成功");
    }
    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "role/grant";
    }
}
