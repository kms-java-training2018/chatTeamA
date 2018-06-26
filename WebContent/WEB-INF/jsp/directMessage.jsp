<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet"type="text/css"href="./css/all.css" media="all">
<title>${toSendUserName}さんとの会話</title>
<script src="./jQuery/jquery-3.3.1.min.js">
</script>

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
	<h1 id ="changeTitleColor">チームAのチャット</h1>
	<h2>
		<a href="/chat/showProfile?user_no=${toSendUserNo}" target="_blank">${toSendUserName}</a>
		さんとの会話
	</h2>
	<div
		style="padding: 10px; margin-bottom: 10px; border: 5px double #333333;
		border-radius: 10px; width: 1320px; height: 300px; overflow: auto;">
		<c:forEach var="bean" items="${messageList}" varStatus="status">
			<c:if test="${!bean.myMessageFlag }">
				<div align="left">
					<a href="/chat/showProfile?user_no=${bean.sendUserNo}"
						target="_blank"><c:out value="${bean.sendUserName}" /> </a>：
					<c:out value="${bean.message}" />
				</div>
			</c:if>
			<c:if test="${bean.myMessageFlag }">
				<div align="right">
					<c:out value="${bean.sendUserName }" />
					：
					<c:out value="${bean.message}" />
				</div>
			</c:if>
			<br>
			<c:if test="${bean.myMessageFlag }">
				<div align="right">
					<form action="/chat/directMessage" method="POST"
						onSubmit="return confirm('削除しますか？')">
						<input type="submit" name="delete" value="削除"> <input
							type="hidden" name="messageNo" value="${bean.messageNo}">
					</form>
				</div>
			</c:if>
		</c:forEach>
	</div>

	<form action="/chat/directMessage" method="POST"
		onSubmit="return doubleSubmit()">
		<input type="text" name="message" placeholder="100桁以内で入力してください"size="50"> <input type="hidden"
			name="toSend" value="${toSendUserNo}"> <input type="hidden"
			name="toSendUserName" value="${toSendUserName}"> <input
			type="submit" name="send" class="click" value="メッセージの送信">
		<P id="changeErrorColor">${errorMessage}</P>
	</form>

	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>