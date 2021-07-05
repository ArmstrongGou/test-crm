package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.User;
import org.apache.ibatis.mapping.ResultMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends  BaseController {
   @Autowired
   private UserService userService;
    //登录页面
    @RequestMapping("index")
     public String index(){
        return "index";
     }
     //欢迎页面
     @RequestMapping("welcome")
     public String welcome(){
        return "welcome";
     }
     //主页面

    //在 IndexController控制器中，main ⽅法转发时，查询登录⽤户信息并放置到 request 域。
     @RequestMapping("main")
    public String main(HttpServletRequest req){
         // 通过⼯具类，从cookie中获取userId
         int userId = LoginUserUtil.releaseUserIdFromCookie(req);
         // 调⽤对应Service层的⽅法，通过userId主键查询⽤户对象
         User user = userService.selectByPrimaryKey(userId);
         // 将⽤户对象设置到request作⽤域中
         req.setAttribute("user",user);
         System.out.println(userId+">>>>");
        return "main";
     }

}
