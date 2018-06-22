<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"type="text/css"href="./css/all.css" media="all">
<link rel="stylesheet" type="text/css" href="./css/pyonpyon.css" media="all">
<title>マイページ</title>
</head>
<body id="bgcolor">
	<div align="right">
		${session.getUserName() }さん <br>
		<form name="log_out" action="/chat/logout" method="POST">
			<input type="button" value="ログアウト"
				onClick="if(confirm ('本当にログアウトしますか？')){submit();}">
		</form>
	</div>
	<hr>
  <h1 id ="changeTitleColor">チームAのチャット</h1>
  <div align="center">
  <h2 class="item_text">マイページ</h2>

  <p>表示名</p>
  <form action="/chat/myPage" method="POST">
  <input size="55" type="text" name="userName" value="${ProfileBean.userName}">
  <br>
  <p>自己紹介</p>
  <textarea name="myPageText" rows="4" cols="43" ><c:out value="${ProfileBean.myPageText}" /></textarea>
  <br>
    <P id="changeErrorColor">${ProfileBean.errorMessage}<p>
    <input type="submit" value="プロフィール更新">
    <input type="hidden" name="userNo" value="${ProfileBean.userNo}">
  </form>
  <br>
  <form action="/chat/main" method="POST">
    <input type="submit" value="メインメニューに戻る">
  </form>
  </div>
</body>
</html>