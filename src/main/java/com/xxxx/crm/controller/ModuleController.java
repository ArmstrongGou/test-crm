package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;

import com.xxxx.crm.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;


    @RequestMapping("index")
    public String index(){
        return "module/module";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> sayList(){
       return moduleService.queryAllModules();
    }

}
