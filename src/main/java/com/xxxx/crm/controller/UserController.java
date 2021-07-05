package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.exceptions.ParamsException;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo sayLogin(String userName, String userPwd) {
        //实例化对象
        ResultInfo resultInfo = new ResultInfo();
        //登录操作
        try {
            UserModel userModel = userService.doLogin(userName, userPwd);

            resultInfo.setResult(userModel);
        } catch (ParamsException pe) {
            pe.printStackTrace();
            //初始化对象result
            resultInfo.setMsg(pe.getMsg());
            resultInfo.setCode(pe.getCode());
        } catch (Exception ex) {
            ex.printStackTrace();
            resultInfo.setCode(400);
            resultInfo.setMsg("操作失败");
        }
        return resultInfo;
    }

    @RequestMapping("toPasswordPage")
    public String sayPwd() {

        return "password";
    }

    @RequestMapping("toSettingPage")
    public String toSettingPage(HttpServletRequest req) {
        //重当前Cookie中获取对象userId
        int userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //根据userId查询用户信息
        User user = userService.selectByPrimaryKey(userId);
        //存储
        req.setAttribute("user", user);
        //转发
        return "user/setting";
    }


    @RequestMapping("update")
    @ResponseBody
    public ResultInfo sayUpdate(User user) {
        userService.updateByPrimaryKeySelective(user);
        return success("保存信息成功了");
    }

    @RequestMapping("updatePassword")
    @ResponseBody
    public ResultInfo sayPassword(HttpServletRequest req, String oldPassword, String newPassword, String confirmPwd) {
        //实例化对象
        ResultInfo resultInfo = new ResultInfo();
        //登录操作
        //try{
        int userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //修改密码
        userService.updateUserPwd(userId, oldPassword, newPassword, confirmPwd);
//        }catch (ParamsException pe){
//            pe.printStackTrace();
//            //初始化对象result
//            resultInfo.setMsg(pe.getMsg());
//            resultInfo.setCode(pe.getCode());
//        }catch (Exception ex){
//            ex.printStackTrace();
//            resultInfo.setCode(300);
//            resultInfo.setMsg("操作失败了");
//        }
        //返回目标对象
        return resultInfo;
    }

    /*
     *多条件查询用户数据
     * */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryUserByParams(UserQuery userQuery) {
        return userService.queryUserByParams(userQuery);
    }

    @RequestMapping("index")
    public String index() {
        return "user/user";
    }

    /*
     *添加用户
     * */
    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveUser(User user) {
        userService.saveUser(user);
        return success("用户添加成功");
    }




    @RequestMapping("update0")
    @ResponseBody
    public ResultInfo updateUser(User user) {
        userService.updateUser(user);
        return success("用户更新成功");
    }

    /**
     * 进⼊⽤户添加或更新⻚⾯
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateUserPage")
    public String addUserPage(Integer id, Model model) {
        if (id != null) {
            model.addAttribute("user", userService.selectByPrimaryKey(id));
        }
        return "/user/add_update";
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids) {
        userService.deleteBatch(ids);
        return success("用户记录删除成功");
    }
}
