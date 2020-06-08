<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.shgm.model.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%
	ShgmJDBCDAO shgmdao = new ShgmJDBCDAO();
	List<ShgmVO> list = shgmdao.getall();
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
		width: 2000px;
	}
	th, td {
    border: 1px solid black;
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
			<td>市集商品編號</td>
			<td>賣家會員編號</td>
			<td>買家會員編號</td>
			<td>市集商品名稱</td>
			<td>市集商品價錢</td>
			<td>市集商品簡介</td>
			<td>市集商品圖片</td>
			<td>上架審核狀態</td>
			<td>上架時間</td>
			<td>取貨方式</td>
			<td>取貨人姓名</td>
			<td>取貨人電話</td>
			<td>取貨地址</td>
			<td>出貨狀態</td>
			<td>付款狀態</td>
			<td>訂單狀態</td>
			<td>售出時間</td>
			<td>刪除市集商品</td>
		</tr>
		
		<c:forEach var="shgmvo" items="${list}">
		<tr>
			<td>${shgmvo.shgmno}</td>
			<td>${shgmvo.sellerno}</td>
			<td>${shgmvo.buyerno}</td>
			<td>${shgmvo.shgmname}</td>
			<td>${shgmvo.price}</td>
			<td width="375">${shgmvo.intro}</td>
			<td><img src="<%=request.getContextPath()%>/back-end/shgm/displayimg?shgmno=${shgmvo.shgmno}" width="200" height="150"/></td>
			<c:choose>
	            <c:when test="${shgmvo.upcheck == 0}">
	                <td>未審核</td>
	            </c:when>
	            <c:when test="${shgmvo.upcheck == 1}">
	                <td>已審核</td>
	            </c:when>
	            <c:otherwise>
	                 <td>審核未通過</td>
	            </c:otherwise>
        	</c:choose>
			<td><fmt:formatDate value="${shgmvo.uptime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
			<td>${shgmvo.take}</td>
			<td>${shgmvo.takernm}</td>
			<td>${shgmvo.takerph}</td>
			<td>${shgmvo.address}</td>
			<c:choose>
	            <c:when test="${shgmvo.boxstatus == 0}">
	                <td>未出貨</td>
	            </c:when>
	            <c:when test="${shgmvo.boxstatus == 1}">
	                <td>已出貨</td>
	            </c:when>
	            <c:otherwise>
	                 <td>送達</td>
	            </c:otherwise>
        	</c:choose>
			<c:choose>
	            <c:when test="${shgmvo.paystatus == 0}">
	                <td>未付款</td>
	            </c:when>
	            <c:otherwise>
	                 <td>已付款</td>
	            </c:otherwise>
        	</c:choose>
			<c:choose>
	            <c:when test="${shgmvo.status == 0}">
	                <td>未完成</td>
	            </c:when>
	            <c:when test="${shgmvo.status == 1}">
	                <td>已完成</td>
	            </c:when>
	            <c:otherwise>
	                 <td>取消</td>
	            </c:otherwise>
        	</c:choose>
			<td><fmt:formatDate value="${shgmvo.soldtime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
			<td>
				<form method="post" action="<%= request.getContextPath()%>/back-end/shgm/shgm.do">
					<input type="hidden" name="shgmno" value="${shgmvo.shgmno}">
					<input type="hidden" name="action" value="delete" >
					<input type="submit" value="刪除">
				</form>
			</td>
		</tr>
		</c:forEach>
	</table>
	<a href="/EA101G6/back-end/shgm/shgm_select_page.jsp">回首頁</a>
</body>
</html>