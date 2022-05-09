package com.newcoder.community.controller;

import com.newcoder.community.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @author xiuxiaoran
 * @date 2022/5/7 15:57
 */
@Controller
public class DataController {
    @Autowired
    private DataService dataService;

    //统计页面,既可以处理get请求又可以处理POST请求
    @RequestMapping(path = "/data",method = {RequestMethod.GET,RequestMethod.POST})
    public String getDataPage(){
        return "/site/admin/data";
    }

    //统计网站UV
    @RequestMapping(path = "/data/uv",method = RequestMethod.POST)
    public String getUV(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, Model model){
        long uv = dataService.calculateUV(start,end);
        model.addAttribute("uvResult",uv);
        model.addAttribute("uvStart",start);
        model.addAttribute("uvEnd",end);
        return "forward:/data";   //转到 /data下面的逻辑处理,转发之后是post请求
    }

    //统计活跃的用户
    @RequestMapping(path = "/data/dav",method = RequestMethod.POST)
    public String getDAV(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, Model model){
        long dau = dataService.calculateDAU(start,end);
        model.addAttribute("dauResult",dau);
        model.addAttribute("dauStart",start);
        model.addAttribute("dauEnd",end);
        return "forward:/data";   //转到 /data下面的逻辑处理,转发之后是post请求
    }
}
