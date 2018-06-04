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
  <h1>チャット研修プログラム</h1>
  <h2>マイページ</h2>
  <p>表示名</p>
  <input size="55" type="text" name="userName" value="${ProfileBean.userName}">
  <br>
  <p>自己紹介</p>
  <textarea name="myPageText" rows="4" cols="43" ><c:out value="${ProfileBean.myPageText}" /></textarea>
  <br>
  <br>
  <br>
  <form action="/chat/myPage" method="POST">
    <P>${ProfileBean.errorMessage}</P>
    <input type="submit" value="プロフィールを更新">
  </form>
  <br>
  <form action="/chat/main" method="POST">
    <input type="submit" value="メインメニューに戻る">
  </form>
</body>
</html>