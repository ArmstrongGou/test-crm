package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.service.CusDevPlanService;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.vo.CusDevPlan;
import com.xxxx.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.Query;
import java.util.Map;

@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {
  @Autowired
    private CusDevPlanService cusDevPlanService;
  @Autowired
    private SaleChanceService saleChanceServicel;

  @RequestMapping("index")
  public String index() {
    return "cusDevPlan/cus_dev_plan";
  }
  @RequestMapping("toCusDevPlanDataPage")

  /*
  * 后端根据营销机会sid查询到数据后放到请求域里面 （显示营销机会数据）
  * */
  public String toCusDevPlanDataPage(Integer sid, Model model) {
      //存储数据到作用域
      model.addAttribute("saleChance", saleChanceServicel.selectByPrimaryKey(sid));
    //转发数据
    return "cusDevPlan/cus_dev_plan_data";
  }

  @RequestMapping("list")
  @ResponseBody
  public Map<String, Object> sayList(CusDevPlanQuery query) {
       return cusDevPlanService.queryCusDevPlan(query);
  }
  @RequestMapping("save")
  @ResponseBody
  public ResultInfo saveCusDevPlan(CusDevPlan cusDevPlan) {
    cusDevPlanService.saveCusDevPlan(cusDevPlan);
    return success("计划项数据添加成功");
  }
  @RequestMapping("update")
  @ResponseBody
  public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan) {
    cusDevPlanService.updateCusDevPlan(cusDevPlan);
    return success("计划项数据添加成功");
  }
  @RequestMapping("delete")
  @ResponseBody
  public ResultInfo saveCusDevPlan(Integer id) {
    cusDevPlanService.deleteCusDevPlan(id);
    return success("计划项数据添加成功");
  }
}
