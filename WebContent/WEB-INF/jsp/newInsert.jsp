<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./css/login.css"
	media="all">
<link rel="stylesheet" type="text/css" href="./css/all.css" media="all">
<link rel="stylesheet" type="text/css" href="./css/pyonpyon.css"
	media="all">
<title>新規登録</title>
<script type="text/javascript" src="./jQuery/jquery-3.3.1.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="./js/submit.js" charset="UTF-8"></script>
<script type="text/javascript" src="./js/showlength.js" charset="UTF-8"></script>
</head>
<body id="bglogincolor">
	<h1 id="changeTitleColor">チームAのチャット</h1>
	<div align="center" class="font">
		<h2 class="item_text">新規会員登録</h2>
		<form action="/chat/newInsert" method="POST" onSubmit="return doubleSubmit()">
			<table>
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
					<td align="right">表示名：</td>
					<td><input type="text" name="name"
						value="${newInsertBean.userName}"placeholder="30桁以内"></td>
				</tr>
				<tr>
				<td align="right">会員ID：</td>
				<td><input type="text" name="userId"
					value="${newInsertBean.userId}"placeholder="半角英数20字以内"></td></tr>
				<tr><td align="right">パスワード：</td>
				<td><input type="password" name="password1"
					value="${newInsert.password}"placeholder="半角英数20字以内" onkeyup="ShowLength(value)"></td></tr>
				<tr><td align="right">確認用パスワード：</td>
				<td><input type="password" name="password2"
					value="${newInsert.password}" placeholder="再度入力してください"></td>
				</tr>
			</table>
			<p id="inputlength">パスワード強度：弱い</p>
			<P id="changeErrorColor">${newInsertBean.errorMessage}</P>
			<input type="submit" value="新規登録">
		</form>
		<form action="/chat/login" method="GET">
			<input type="submit" value="ログイン画面に戻る">
		</form>
	</div>
</body>
</html>