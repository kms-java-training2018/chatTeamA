<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./css/all.css" media="all">
<title>グループメッセージ</title>
<script type="text/javascript" src="./jQuery/jquery-3.3.1.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="./js/submit.js" charset="UTF-8"></script>
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

	<h1 id="changeTitleColor">チームAのチャット</h1>
	<h2>「${group_name }」の会話</h2>
	<c:forEach var="groupMember" items="${groupMemberList }">
		<c:out escapeXml="false" value="${groupMember }"></c:out>,
	</c:forEach>
	<form action="/chat/groupMessage" method="POST"
		onSubmit="return confirm('脱退しますか？')">
		<input type="submit" name="escape" value="グループを脱退する">
	</form>
	<form action="/chat/groupMessage" method="POST"
		onSubmit="return confirm('グループを消去しますか？')">
		<input type="submit" name="deleteGroup" value="グループを消去する">
	</form>
	<div class="overview"
		style="padding: 10px; margin-bottom: 10px; border: 5px double #333333; border-radius: 10px; width: 1305px;
		height: 300px; overflow:auto;"id="auto_scroll">
		<c:forEach var="bean" items="${list}" varStatus="status">
			<c:if test="${bean.sendUserName == '送信者不明' }">
				<c:out escapeXml="false" value="${bean.sendUserName }" />
			</c:if>
			<c:if test="${bean.sendUserName != '送信者不明' }">
				<c:if test="${!bean.myMessageFlag }">
					<div align="left">
						<a href="/chat/showProfile?user_no=${bean.userNo }"
							target="_blank">${bean.sendUserName }</a> ：
						<c:out escapeXml="false" value="${bean.message}" />
					</div>
				</c:if>

				<c:if test="${bean.myMessageFlag }">
					<div align="right">
						<c:out escapeXml="false" value="${bean.sendUserName }" />
						：
						<c:out escapeXml="false" value="${bean.message}" />
					</div>
				</c:if>
			</c:if>
			<c:if test="${bean.myMessageFlag}">
				<div align="right">
					<form action="/chat/groupMessage" method="POST"
						onSubmit="return confirm('削除しますか？')">
						<input type="submit" name="delete" value="削除"> <input
							type="hidden" name="message_no" value="${bean.messageNo }">
					</form>
				</div>
			</c:if>
			<br>
		</c:forEach>
	</div>
	<br>
	<br>
	<p id="changeErrorColor">${errorMessage }</p>
	<form action="/chat/groupMessage" method="POST"
		onSubmit="return doubleSubmit()">
		<input type="text" name="message" value="${groupMessageBean.message}"placeholder="100桁以内"size="50">
		<input type="submit" name="sendMessage" value="メッセージの送信" />
	</form>
	<br>
	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>