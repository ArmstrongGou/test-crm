package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dto.TreeModel;
import com.xxxx.crm.mapper.ModuleMapper;
import com.xxxx.crm.vo.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService extends BaseService<Module,Integer> {

    @Autowired(required = false)
    private ModuleMapper moduleMapper;

    public List<TreeModel> querylModule(){
        return  moduleMapper.selectAllModule();
    }


    public Map<String,Object> queryAllModules(){
        Map<String,Object> map=new HashMap<>();
        //查询所有的数据
        List<Module> mlist = moduleMapper.selectAllModules();
        //准备数据
        map.put("code",0);
        map.put("msg","success");
        map.put("count",mlist.size());
        map.put("data",mlist);
        //返回目标map
        return map;
    }
}
