<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shgmrp.model.*" %>
<%@ page import="com.shgm.model.*" %>
<%@ page import="java.util.*" %>
<%
	ShgmService shgmsvc = new ShgmService();
	pageContext.setAttribute("shgmsvc", shgmsvc);
	ShgmrpService shgmrpsvc = new ShgmrpService();
	List<ShgmrpVO> list = shgmrpsvc.getAllShgmrp();
	pageContext.setAttribute("list",list);
%>

<html>
<head>
<meta charset="UTF-8">
<title>All_Shgm</title>

<style>
	table{
		border: 3px solid black;
		text-align: center;
	}
	th, td {
    	border: 1px solid black;

  	}
  	img{
  		height:100%;
  		width:100%;
  	}
</style>
</head>
<body>
<ul>
	<c:if test="${not empty errormsgs}">
		<c:forEach var="error" items="${errormsgs}">
			<li>${error}</li>
		</c:forEach>
	</c:if>
</ul>
	<table>
		<tr>
			<td>市集商品檢舉編號</td>
			<td>市集商品編號</td>
			<td>市集商品名稱</td>
			<td>市集商品價錢</td>
			<td>市集商品簡介</td>
			<td>市集商品圖片</td>
			<td>檢舉人會員編號</td>
			<td>檢舉內容</td>
			<td>檢舉狀態</td>
			<td>修改檢舉</td>
			<td>刪除檢舉</td>
		</tr>
		
		<c:forEach var="shgmrpvo" items="${list}">
		<tr>
			<td>${shgmrpvo.shgmrpno}</td>
			<td>${shgmrpvo.shgmno}</td>
			<td>${shgmsvc.getOneShgm(shgmrpvo.shgmno).shgmname}</td>
			<td>${shgmsvc.getOneShgm(shgmrpvo.shgmno).price}</td>
			<td style="width:300px">${shgmsvc.getOneShgm(shgmrpvo.shgmno).intro}</td>
			<td><img src="<%=request.getContextPath() %>/back-end/shgm/displayimg?shgmno=${shgmrpvo.shgmno}"></td>
			<td>${shgmrpvo.suiterno}</td>
			<td>${shgmrpvo.detail}</td>
			<c:choose>
				<c:when test="${shgmrpvo.status == 0}">
					<td>未審核</td>
				</c:when>
				<c:when test="${shgmrpvo.status == 1}">
					<td>審核通過</td>
				</c:when>
				<c:otherwise>
					<td>審核未通過</td>
				</c:otherwise>
			</c:choose>
			<td><input type="hidden"></td>
			<td>刪除檢舉</td>
		</tr>
		</c:forEach>
	</table>
	<a href="/EA101G6/back-end/shgm/shgm_select_page.jsp">回首頁</a>
</body>
</html>