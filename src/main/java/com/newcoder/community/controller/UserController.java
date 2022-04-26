package com.newcoder.community.controller;

import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityUtil;
import com.newcoder.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author xiuxiaoran
 * @date 2022/4/25 20:58
 */
@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;   //需要知道当前用户，存储在这里

    //访问账号设置页面
    @RequestMapping(path = "/setting" , method = RequestMethod.GET)
    public String getSettingPage(){
        return "/site/setting";
    }

    //**********************************************
    //这里重新设置修改密码的功能，独立实现
    @RequestMapping(path = "/updatePassword" , method = RequestMethod.POST)
    public String updatePassword(Model model,String originPassword,String newPassword,String confirmPassword){
        //处理相关的逻辑
        User user = hostHolder.getUser();
        //密码不能为空
        if(StringUtils.isBlank(originPassword)){
            model.addAttribute("originMsg","原始密码不能为空");
            return "/site/setting";  //返回当前页面继续输入
        }
        if(StringUtils.isBlank(newPassword)){
            model.addAttribute("newPasswordMsg","新密码不能为空");
            return "/site/setting";  //返回当前页面继续输入
        }
        if(StringUtils.isBlank(confirmPassword)){
            model.addAttribute("confirmPasswordMsg","确认密码不能为空");
            return "/site/setting";  //返回当前页面继续输入
        }

        //如果原始密码输入不正确，返回错误信息
        if(!user.getPassword().equals(CommunityUtil.md5(originPassword)+user.getSalt())){
            model.addAttribute("originMsg","原始密码输入不正确");
            return "/site/setting";  //返回当前页面继续输入
        }
        //原始密码输入正确
        //两次输入的密码是否一致，一致，okok，可以修改
        if(!newPassword.equals(confirmPassword)){
            model.addAttribute("newPasswordMsg","两次密码输入不一致");
            model.addAttribute("confirmPassword","两次密码输入不一致");
            return "/site/setting";  //返回当前页面继续输入
        }
        //验证都通过了，更新密码
        userService.updatePassword(user.getId(),newPassword);
        return "redirect:/index";   //重定向会首页表示成功，不然就结束，其实可以写个页面通知说面修改完毕的，这里就没写
    }
    //******************************************************

    //更换头像
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImg, Model model){
        if(headerImg==null){
            model.addAttribute("error","没有选择任何文件!");
            return "/site/setting";  //返回当前页面继续上传
        }

        //获取图片名称
        String originalFilename = headerImg.getOriginalFilename();
        //获取图片后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if(StringUtils.isBlank(suffix)){
            model.addAttribute("error","选择后缀为jpg,jepg,png的图片文件!");
            return "/site/setting";  //返回当前页面继续上传
        }

        //生成随机字符串
        String fileName = CommunityUtil.generatedUUId()+suffix;
        //确定存放的路劲
        File dest = new File(uploadPath+ "/" + fileName);
        try {
            headerImg.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败"+e.getMessage());
            throw new RuntimeException("上传文件失败，服务器内部错误",e);
        }
        //上传文件成功,更新用户的头像信息(web访问的路劲)
        //；例如： http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(),headerUrl);
        return "redirect:/index";
    }

    //手动输出图片，就和那个访问验证码是一样的
    //借用response来返回图片信息
    //java IO流就是空白
    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response){
        //服务器存放的图片路径
        fileName = uploadPath + "/" +fileName;  //带上本地路劲的filename
        //输出图片
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //响应图片文件
        response.setContentType("/image/"+suffix);

        // 小括号自动关闭打开的流
        try (
                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(fileName);)
        {
            byte[] buffer = new byte[1024];
            int b=0;
            while((b=fis.read(buffer))!=-1){
                os.write(buffer,0,b);
            }
        } catch (IOException e) {
            logger.error("读取图像失败"+e.getMessage());
        }
    }
}
