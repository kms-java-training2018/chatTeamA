<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"type="text/css"href="./css/login.css" media="all">
<link rel="stylesheet"type="text/css"href="./css/all.css" media="all">
<link rel="stylesheet" type="text/css" href="./css/pyonpyon.css" media="all">
<title>新規登録</title>
</head>
<body id="bglogincolor">
	<h1 id ="changeTitleColor">チームAのチャット</h1>
	<div align="center" class="font">
	<h2 class="item_text">新規会員登録</h2>
	<form action="/chat/login" method="POST">
		<p>表示名(30桁以内):
		<input type="text" name="name" value="${NewInsertBean.userName}">
		<br>
		<br>
		<p>会員ID(半角英数20字以内):
		<input type="text" name="userId" value="${NewInsertBean.userId}">
		<br>
		<p>パスワード(半角英数20字以内):
		<input type="password" name="password1" value="${NewInsert.password}">
		<br>
		<p>確認用パスワード:
		<input type="password" name="password2" value="${NewInsert.password}">
		<br>

		<P id="changeErrorColor">${NewInsertBean.errorMessage}</P>
		<input type="submit" value="新規登録">
	</form>
	</div>
</body>
</html>