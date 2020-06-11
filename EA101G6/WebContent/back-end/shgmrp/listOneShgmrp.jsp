<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shgmrp.model.*" %>
<%@ page import="com.shgm.model.*" %>
<%
	ShgmrpVO shgmrpvo = (ShgmrpVO)request.getAttribute("shgmrpvo");
	ShgmService shgmsvc = new ShgmService();
%>

<html>
<head>
<meta charset="UTF-8">
<title>One_Shgm</title>

<style>
	table{
		border: 3px solid black;
		text-align: center;
	}
	th, td {
    border: 1px solid black;
  }
</style>
</head>
<body>
	<table>
		<tr>
			<td>市集商品檢舉編號</td>
			<td>市集商品編號</td>
			<td>市集商品名稱</td>
			<td>檢舉人會員編號</td>
			<td>檢舉內容</td>
			<td>檢舉狀態</td>
			
		</tr>
		<tr>
			<td>${shgmrpvo.shgmrpno}</td>
			<td>${shgmrpvo.shgmno}</td>
			<td>${shgmsvc.getOneShgm(shgmrpvo.shgmno).shgmname}</td>
			<td>${shgmrpvo.suiterno}</td>
			<td>${shgmrpvo.detail}</td>
			<td>${shgmrpvo.status}</td>
		</tr>
	</table>
	<a href="<%=request.getContextPath()%>/back-end/shgmrp/shgmrp_select_page.jsp">回首頁</a>
</body>
</html>