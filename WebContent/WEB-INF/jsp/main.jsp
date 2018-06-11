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
  <h1>チャット研修プログラム</h1>
  <h2>メインメニュー</h2>
  <br><div>■会員一覧</div>
  <c:forEach var="bean" items="${list}" varStatus="status">
    <form action="/chat/directMessage" method="GET">
    <a href="/chat/directMessage?user_no=${bean.userNo}&user_name=${bean.userName}"><c:out value="${bean.userNo}"/><c:out value="${bean.userName}"/></a><br>
     </form>
    </c:forEach>

<c:forEach var="bean" items="${talkD}" varStatus="status">
<c:out value="${bean.message}"/><br>
    </c:forEach>



  <br>■グループ一覧
  <br>
  <a href="/chat/groupMessage">グループ名（グループメッセージへ）</a>
  <br>

<c:forEach var="bean" items="${talkG}" varStatus="status">
<p>グループ名</p><a href="/chat/groupMessage?group_no=${bean.userNo}"><c:out value="${bean.groupName}"/></a>
<p>最新グループトーク</p><c:out value="${bean.message}"/><br>
    </c:forEach>

  <br>
  <form action="/chat/makeGroup" method="GET">
    <input type="submit" value="グループの作成">
  </form>
  <form action="/chat/myPage" method="GET">
    <input type="submit" value="プロフィール画面へ">
  </form>


</body>
</html>