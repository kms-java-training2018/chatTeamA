<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規グループ作成</title>
</head>
<body>
	<div align="right">
		${session.getUserName() }さん <br>
		<form name="log_out" action="/chat/logout" method="POST">
			<input type="button" value="ログアウト"
				onClick="if(confirm ('本当にログアウトしますか？')){submit();}">
		</form>
	</div>
	<hr>
	<h1>チャット研修プログラム</h1>
	<h2>グループ作成</h2>
	<p>${errorMessage}</p>
	<form action="/chat/makeGroup" method="POST">
		グループ名(最大30字)<input type="text" name="groupName" value="" size="30">
		<br>
		<table>
			<tr>
				<c:forEach var="obj" items="${bean}" varStatus="status">
					<c:if test="${status.index % 3 == 0}">
			</tr>
			<tr>
				</c:if>
				<td><input type="checkbox" name="userNo"
					value="${bean[status.index].userNo}">${bean[status.index].userName}
				</td>
				</c:forEach>
			</tr>
		</table>
		<br> <input type="submit" value="グループを作成する">

	</form>

	<form action="/chat/main" method="POST">
		<input type="submit" value="メインメニューに戻る">
	</form>
</body>
</html>