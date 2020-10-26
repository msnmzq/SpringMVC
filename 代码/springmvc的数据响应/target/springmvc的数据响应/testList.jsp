<%--
  Created by IntelliJ IDEA.
  User: 18654
  Date: 2020/10/6
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery.js"></script>
</head>
<body>

<%--<form action="quick11" method="post">--%>
<%--    <input type="text" name="userList[0].name">--%>
<%--    <input type="text" name="userList[1].name">--%>
<%--    <input type="text" name="userList[2].name">--%>
<%--    <input type="submit" value="提交">--%>

<%--</form>--%>


<script>
    var userList=new Array();
    userList.push({name:'孟胜男'})
    userList.push({name:'孟子淇'})

    $.ajax({
        url:'quick12',
        data:JSON.stringify(userList),
        type:'post',
        contentType : 'application/json;charset=utf-8'
    })
</script>



</body>
</html>
