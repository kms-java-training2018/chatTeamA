<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"type="text/css"href="./css/all.css" media="all">
<link rel="stylesheet" type="text/css" href="./css/pyonpyon.css" media="all">
<title>${ProfileBean.userName}さんのプロフィール</title>

</head>
<body id="bgcolor">
  <h1 id ="changeTitleColor">チームAのチャット</h1>
  <div align="center">
  <h2 class="item_text">プロフィール確認</h2>
  <table class ="menu" border ="3" style="table-layout:fixed;">
   <tr align ="center">
   <td class="name">名前</td>
   <td colspan ="3" class ="message">自己紹介文</td>
   </tr>
  <form action="/showProfile" method="POST">
    <br><tr align ="center">
<td>
   <c:out value="${ProfileBean.userName}" /></td>

    <td colspan="3">
   <c:out value="${ProfileBean.myPageText}" /></td>
   </tr>
   </table>
   <br>
  <input type="button" value="閉じる" onclick="window.close();">
  </form>
  </div>
</body>
</html>