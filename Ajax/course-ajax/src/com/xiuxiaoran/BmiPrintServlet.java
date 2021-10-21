package com.xiuxiaoran;

import java.io.IOException;
import java.io.PrintWriter;

public class BmiPrintServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //接受请求参数
        String name=request.getParameter("name");
        String height=request.getParameter("height");
        String weight=request.getParameter("weight");

        //
        float h=Float.valueOf(height);
        float w=Float.valueOf(weight);
        float bmi=w/(h*h);

        //
        String msg="";
        if(bmi<18.5){
            msg="偏瘦";
        }else if(bmi<23.9){
            msg="正常";
        }else{
            msg="偏胖";
        }

        //把数据存入request
        response.setContentType("text/html;charset=utf-8");
        //获取输出数据
        PrintWriter pw=response.getWriter();
        pw.println(msg);
        pw.flush();
        pw.close();
    }
}
