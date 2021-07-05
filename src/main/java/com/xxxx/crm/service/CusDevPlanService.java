package com.xxxx.crm.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.CusDevPlanMapper;
import com.xxxx.crm.mapper.SaleChanceMapper;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CusDevPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {
    @Resource
    private SaleChanceMapper saleChanceMapper;
    @Autowired(required = false)
    private CusDevPlanMapper cusDevPlanMapper;

    public Map<String, Object> queryCusDevPlan(CusDevPlanQuery cusDevPlanQuery) {
        //实例化Map
       Map<String, Object> map = new HashMap<>();
        //开始分页
        PageHelper.startPage(cusDevPlanQuery.getPage(), cusDevPlanQuery.getLimit());
        PageInfo<CusDevPlan> pageInfo = new PageInfo<>(cusDevPlanMapper.selectByParams(cusDevPlanQuery));
        //存储
        map.put("code",0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        //返回map
        return map;
    }

    public void saveCusDevPlan(CusDevPlan cusDevPlan) {
        /*
        * 1、参数校验
        * 机会id非空 记录必须存在
        * 计划项内容非空
        * 计划项时间非空
        * 2、参数默认值
        * is_valid 1
        * createDate 系统时间
        * updateDate 系统
        * 3、执行添加，判断结果
        * */
        checkParms(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(cusDevPlan)<1,"计划项记录添加失败");
    }

    private void checkParms(Integer saleChanceId, String planItem, Date planDate) {
        AssertUtil.isTrue(null == saleChanceId || null == saleChanceMapper.selectByPrimaryKey(saleChanceId), "请设置营销机会id");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"请输入计划内容");
        AssertUtil.isTrue(null == planDate, "请指定计划日期");

    }

    public void updateCusDevPlan(CusDevPlan cusDevPlan) {
        /*
         * 1、参数校验
         * 机会id非空 记录必须存在
         * 计划项内容非空
         * 计划项时间非空
         * 2、参数默认值
         * is_valid 1
         * createDate 系统时间
         * updateDate 系统
         * 3、执行添加，判断结果
         * */
        AssertUtil.isTrue(null == cusDevPlan.getId() || null == selectByPrimaryKey(cusDevPlan.getId()), "待更新的记录不存在");
        checkParms(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"记录更新失败");

    }
    public void deleteCusDevPlan(Integer id){
        CusDevPlan cusDevPlan = selectByPrimaryKey(id);
        AssertUtil.isTrue(null==id||null==cusDevPlan,"待删除的记录不存在");
        cusDevPlan.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"记录删除失败");
    }
}
