<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h1>チャット研修プログラム</h1>
  <h2>グループ作成</h2>
  <form action="/chat/makeGroup" method="POST">
    <p>グループの名前</p>
    <input type="text" name="groupName"value=''>
    <p><font size="5" color="red">${error }</font></p>
      <button type='submit' name='action' value="Make">選択したユーザーでグループを作成する</button>
    <table border="1">
      <tr>
        <th>全ユーザーリスト</th>
      </tr>
      <c:forEach var="obj" items="${groupBean.userName}" varStatus="status">
        <tr>
          <td><input type="checkbox" name="userNo"
            value="${status.index}"> <c:out value="${obj}" /></td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <form action="/chat/main" method="POST">
    <input type="submit" value="メインメニューに戻る">
  </form>
</body>
</html>