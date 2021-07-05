package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;
    @Autowired
    private UserService userService;

    @RequestMapping("index")
    public String index() {
        return "saleChance/sale_chance";
    }

    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdate(Integer id, Model model) {
        //修改和添加的主要区别是表单中是否有ID，有id修改操作，否则添加操作
        if (id != null) {
            //查询销售机会对象
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
            //存储
            model.addAttribute("saleChance", saleChance);
        }
        return "saleChance/add_update";
    }
    /*
     *多条件分页查询营销机会
     * @param query
     * @return
     * */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> sayList(SaleChanceQuery saleChanceQuery,Integer flag,HttpServletRequest req) {
        //判断
        if (flag != null && flag == 1) {
            //获取当前用户userId    从cookie中获取
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
            //分配人
            saleChanceQuery.setAssignMan(userId);
        }
        //查询
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo sayAdd(SaleChance saleChance, HttpServletRequest req) {
          //获取userId
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //调用方法查询
        String trueName = userService.selectByPrimaryKey(userId).getTrueName();
        //指定创建人
        saleChance.setAssignMan(trueName);
        //添加
        System.out.println("==>>"+saleChance);
        saleChanceService.saveSaleChance(saleChance);

        return success("营销机会添加成功");
    }



    @RequestMapping("update")
    @ResponseBody
    public ResultInfo sayUpdate(SaleChance saleChance, HttpServletRequest req) {
        //修改
        saleChanceService.updateByPrimaryKeySelective(saleChance);
        return success("营销机会添加成功");
    }

    @RequestMapping("dels")
    @ResponseBody
    public ResultInfo sayDels(Integer[] ids) {
        System.out.println(Arrays.toString(ids)+">>>");
        saleChanceService.removeSaleChance(ids);
        return success("批量删除营销机会成功");
    }
}
