<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"type="text/css"href="./css/message.css" media="all">
<title>グループメッセージ</title>
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
	<script src="./directMessage.js"></script>
	<h1>チームAのチャット</h1>
	<h2></h2>
	<form action="/chat/groupMessage" method="POST">
	<h3>「${group_name }」の会話</h3>
		<input type="submit" name="escape" value="グループを脱退する">
	</form>
	<br>
	<c:forEach var="bean" items="${list}" varStatus="status">
		<c:if test="${bean.sendUserName == '送信者不明' }">
			<c:out value="${bean.sendUserName }" />
		</c:if>
		<c:if test="${bean.sendUserName != '送信者不明' }">
			<c:if test="${!bean.myMessageFlag }">
				<a href="/chat/showProfile?user_no=${bean.userNo }" target="_blank">${bean.sendUserName }</a>
			</c:if>

			<c:if test="${bean.myMessageFlag }">
				<c:out value="${bean.sendUserName }" />
			</c:if>
		</c:if>：<c:out value="${bean.message}" />
		<c:if test="${bean.myMessageFlag}">
			<form action="/chat/groupMessage" method="POST"
				onSubmit="return confirm('削除しますか？')">
				<input type="submit" name="delete" value="削除"> <input
					type="hidden" name="message_no" value="${bean.messageNo }">
			</form>
		</c:if>
		<br>
	</c:forEach>
	<br>
	<br>
	<form action="/chat/groupMessage" method="POST">
		<input type="text" name="message" value="${groupMessageBean.message}">
		<input type="submit" name="sendMessage" value="メッセージの送信">
	</form>
	<br>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>