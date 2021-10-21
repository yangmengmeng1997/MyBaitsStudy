package com.xiuxiaoran.controller;

import java.io.IOException;
import java.io.PrintWriter;

public class BmiServlet extends javax.servlet.http.HttpServlet {
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
        //ajax响应数据,至此在别的数据没有影响的情况下，得到了新的数据可以用来进行更新
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw=response.getWriter();
        pw.println(msg);
        pw.flush();
        pw.close();

//
//        //把数据存入request
//        request.setAttribute("msg",msg);
//
//        //转发数据
//        request.getRequestDispatcher("/result.jsp").forward(request,response);
        //服务器端接受参数

    }
}
