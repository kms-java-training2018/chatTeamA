<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
  <script src="./directMessage.js"></script>
  <h1>チャット研修プログラム</h1>
  <h2>メッセージ</h2>
  <div
    style="padding: 10px; margin-bottom: 10px; border: 5px double #333333; border-radius: 10px;">
    <c:forEach var="bean" items="${messageList}" varStatus="status">
      <c:if test="${!bean.myMessageFlag }">
        <a href="/chat/showProfile">${bean.sendUserName }</a>
      </c:if>
      <c:if test="${bean.myMessageFlag }">
        <c:out value="${bean.sendUserName }" />
      </c:if>
      <c:out value="${bean.message}" />
      <br>
      <c:if test="${!bean.myMessageFlag }">
        <form action="/chat/directMessage" method="POST"
          onSubmit="return confirm('削除しますか？')">
          <input type="submit" name="delete" value="削除"> <input
            type="hidden" name="messageNo" value="${bean.messageNo}">
        </form>
      </c:if>
    </c:forEach>
  </div>

  <br>
  <a href="/chat/showProfile">あいて</a>：いえーい（｀・ω・´）
  <br>
  <br>

  <form action="/chat/directMessage" method="POST">
    <input type="text" name="message"> <input type="submit"
      value="メッセージの送信">
  </form>
  <form action="/chat/main" method="POST">
    <input type="submit" value="メインメニューへ戻る">
  </form>
</body>
</html>