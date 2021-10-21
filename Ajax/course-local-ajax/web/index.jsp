<%--
  Created by IntelliJ IDEA.
  User: xiu
  Date: 2021/10/11
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>局部刷新</title>
    <script>
      function doAjax() {
        //1.创建异步对象
        var xmlHttp=new XMLHttpRequest()
        //2.绑定对象
        xmlHttp.onreadystatechange = function () {
          //alert("readystate的属性值："+xmlHttp.readyState)
          if(xmlHttp.readyState==4 && xmlHttp.status==200){
            alert(xmlHttp.responseText)
          }
        }
        //3.初始请求数据
        //获取dom对象的值
        var name=document.getElementById("name").value;
        var w=document.getElementById("weight").value;
        var h=document.getElementById("height").value;

        var para="name="+name+"&weight="+w+"&height="+h
        alert("parameter="+para)
        xmlHttp.open("get","bmiServletAjax?"+para,true);
        //4.发起请求;
        xmlHttp.send();
      }
    </script>
  </head>
  <body>
  <p>局部刷新ajax-计算bmi</p>
  <div>
    姓名：<input type="text" id="name" /> <br />
    体重：<input type="text" id="weight" /> <br />
    身高：<input type="text" id="height" /> <br />
    <input type="button" value="计算bmi" onclick="doAjax()">
  </div>
  </body>
</html>
