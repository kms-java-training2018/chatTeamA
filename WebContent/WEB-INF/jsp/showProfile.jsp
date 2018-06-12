<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${ProfileBean.userName}さんのプロフィール</title>
</head>
<body>
  <h1>チャット研修プログラム</h1>
  <h2>プロフィール確認</h2>
  <form action="/showProfile" method="POST">
    <br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;表示名：&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${ProfileBean.userName}" />
    <br>
    <br>
    &nbsp;&nbsp;自己紹介文：&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${ProfileBean.myPageText}" />
    <br>
    <br>
    <br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="閉じる" onclick="window.close();">
  </form>
</body>
</html>