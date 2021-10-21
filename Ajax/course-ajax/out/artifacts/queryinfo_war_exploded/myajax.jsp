<%--
  Created by IntelliJ IDEA.
  User: xiu
  Date: 2021/10/13
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>json文件读取多行数据</title>
    <script>
        function DoSearch() {

            var xmlHttp=new XMLHttpRequest();
            xmlHttp.onreadystatechange=function () {
                if(xmlHttp.readyState==4 && xmlHttp.status==200){
                    var data=xmlHttp.responseText;
                    //更新数据
                    var jsonobj=eval("("+data+")");
                    //封装函数
                    updatedata(jsonobj);
                }
            }
            var proid=document.getElementById("proid").value;
            xmlHttp.open("get","queryJson?proid="+proid,true);

            xmlHttp.send();
        }
        function updatedata(jsonobj) {
            document.getElementById("proname").value=jsonobj.name;
            document.getElementById("projiancheng").value=jsonobj.jiancheng;
            document.getElementById("proshenghui").value=jsonobj.shenghui;
        }

    </script>
</head>
<body>
      <table>
          <tr>
              <td>省份编号:</td>
              <td>
                  <input type="text" id="proid">
                  <input type="button" value="搜索" onclick="DoSearch()">
              </td>
          </tr>
          <tr>
              <td>省份名称:</td>
              <td><input type="text" id="proname"></td>
          </tr>
          <tr>
              <td>省份简称:</td>
              <td><input type="text" id="projiancheng"></td>
          </tr>
          <tr>
              <td>省会:</td>
              <td><input type="text" id="proshenghui"></td>
          </tr>
      </table>
</body>
</html>
