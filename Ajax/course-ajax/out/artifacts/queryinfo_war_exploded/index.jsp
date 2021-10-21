<%--
  Created by IntelliJ IDEA.
  User: xiu
  Date: 2021/10/12
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>ajax异步刷新</title>
    <script>
      function search() {
        //发起ajax请求
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.onreadystatechange=function () {
          if(xmlHttp.readyState==4 && xmlHttp.status==200){
            //alert(xmlHttp.responseText);
            //更新DOM对象
            document.getElementById("proname").value=xmlHttp.responseText;   //服务器返回对象来修改值
          }
        }
        var proid = document.getElementById("proid").value;
        xmlHttp.open("get","queryProvince?proid="+proid,true);  //仿照访问路劲加上传递参数

        xmlHttp.send();
      }
    </script>
  </head>
  <body>
  <p>省份id获取省份名称</p>
  <table>
    <tr>
      <td>省份编号</td>
      <td>
        <input type="text" id="proid">
        <input type="button" value="搜索" onclick="search()">
      </td>
    </tr>

    <tr>
      <td>省份名称：</td>
      <td>
        <input type="text" id="proname">
      </td>
    </tr>
  </table>
  </body>
</html>
