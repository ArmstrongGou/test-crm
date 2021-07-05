package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.UserMapper;
import com.xxxx.crm.mapper.UserRoleMapper;
import com.xxxx.crm.model.UserModel;


import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.Md5Util;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.utils.UserIDBase64;
import com.xxxx.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService extends BaseService<User,Integer> {
    @Autowired(required = false)
    private UserMapper userMapper;
    /**
     *
     * @param userName
     * @param userPwd
     */
    private void checkUser(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户密码不能为空");
    }

    public void updateUserPwd(Integer userId,String oldPassword,String newPassword,String confirmPwd){
        //获取对象信息
        User user =userMapper.selectByPrimaryKey(userId);
        //验证用户参数
        checkUserPassword(user,oldPassword,newPassword,confirmPwd);
        //设定密码
        user.setUserPwd(Md5Util.encode(newPassword));
        //修改
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"修改失败了");


    }

    private void checkUserPassword(User user, String oldPassword, String newPassword, String confirmPwd) {
        // user对象 非空验证
        AssertUtil.isTrue(null == user, "用户未登录或不存在！");
        // 原始密码 非空验证
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "请输入原始密码！");
        // 原始密码要与数据库中的密文密码保持一致
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))), "原始密码不正确！");
        // 新密码 非空校验
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "请输入新密码！");
        // 新密码与原始密码不能相同
        AssertUtil.isTrue(oldPassword.equals(newPassword), "新密码不能与原始密码相同！");
        // 确认密码 非空校验
        AssertUtil.isTrue(StringUtils.isBlank(confirmPwd), "请输入确认密码！");
        // 新密码要与确认密码保持一致
        AssertUtil.isTrue(!(newPassword.equals(confirmPwd)), "新密码与确认密码不一致！");
    }

    /**
     * 1)验证用户名，密码
     * 2）用户是否存在
     * 3）用户密码是否正确
     * 4）返回UserModel
     * @param userName
     * @param userPwd
     * @return
     */

    public UserModel doLogin(String userName, String userPwd){
        //1)验证用户名，密码
        checkUser(userName,userPwd);
        //2）用户是否存在   查询到的信息temp=null 说明已经注销
        User temp = userMapper.selectUserByName(userName);
        //判断用户对象是否存在(用户对象为空，记录不存在，方法结束)
        AssertUtil.isTrue(temp ==null ,"用户不存在或用户已经注销");
        //3）用户密码是否正确
         checkUserPwd(temp.getUserPwd(), userPwd);
        //构建返回对象
        return builderInfo(temp);
    }

    /**
     *
     * @param user
     * @return
     */
    private UserModel builderInfo(User user) {
        UserModel userModel=new UserModel();
        //设置用户信息(将 userId 加密)
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }


    /**
     *
     * @param userPwd 数据中查询的密码   原始密码
     * @param uPwd 新输入的密码       加密的密码
     */
    private void checkUserPwd(String userPwd, String uPwd) {
        //加密
        uPwd=Md5Util.encode(uPwd);
       //对比
        AssertUtil.isTrue(!userPwd.equals(uPwd),"密码不正确");
    }

    public Map<String, Object> queryUserByParams(UserQuery query) {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<User> pageInfo=new PageInfo<>(userMapper.selectByParams(query));
        map.put("code",0);
        map.put("msg","");
        map.put("count", pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    /*
     *添加用户
     * @param user
     * */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user) {
       //1、参数校验
        checkParams(user.getUserName(),user.getEmail(),user.getPhone());
        //2、设置默认参数
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("1234560"));
        //3、执行添加，判断结果
        AssertUtil.isTrue(userMapper.insertSelective(user)<1,"用户添加失败");
    }
//
    private void checkParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        //验证用户名是否存在
        User user = userMapper.selectUserByName(userName);
        AssertUtil.isTrue(null != user,"该用户已存在");
        AssertUtil.isTrue(StringUtils.isBlank(email),"请输入邮箱地址！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号码格式不争取");

    }

    /**
     * 更新⽤户
     * 1. 参数校验
     * id ⾮空 记录必须存在
     * ⽤户名 ⾮空 唯⼀性
     * email ⾮空
     * ⼿机号 ⾮空 格式合法
     * 2. 设置默认参数
     * updateDate
     * 3. 执⾏更新，判断结果
     * @param user
     */


    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        User temp = userMapper.selectByPrimaryKey(user.getId());
        //通过id获取用户对象进行校验
        AssertUtil.isTrue(temp.equals(null),"待更新的记录不存在");
        //验证参数
        checkParams(user.getUserName(), user.getEmail(), user.getPhone());
        //设置默认参数
       temp.setUpdateDate(new Date());
       //执行更新，判断结果
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"用户更新失败");
    }

    public void deleteUserByIds(Integer[] ids) {
        AssertUtil.isTrue(null == ids || ids.length == 0, "请选择删除的用户记录");
        AssertUtil.isTrue(deleteBatch(ids)!=ids.length,"用户记录删除 失败 ");
    }

}
