<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>メインメニュー</title>
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
  <h1>チームAのチャット</h1>
  <h2>メインメニュー</h2>
  <br><div>■会員一覧</div>
  <c:forEach var="bean" items="${list}" varStatus="status">
    <form action="/chat/directMessage" method="GET">
    <a href="/chat/directMessage?user_no=${bean.userNo}&user_name=${bean.userName}"><c:out value="${bean.userName}"/></a>
    &nbsp;<c:out value="${bean.message}"/><br>
     </form>
    </c:forEach>


  <br>■グループ一覧

  <br>

<c:forEach var="bean" items="${talkG}" varStatus="status">
<a href="/chat/groupMessage?group_no=${bean.groupNo}"><c:out value="${bean.groupName}"/></a>
&nbsp;<c:out value="${bean.message}"/><br>
    </c:forEach>
  <br>
  <form action="/chat/makeGroup" method="GET">
    <input type="submit" value="グループの作成">
  </form>
  <br>
  <form action="/chat/myPage" method="GET">
    <input type="submit" value="プロフィール画面へ">
  </form>


</body>
</html>