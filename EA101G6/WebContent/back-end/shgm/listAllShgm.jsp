<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.shgm.model.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%
	ShgmService shgmsvc = new ShgmService();
	List<ShgmVO> list = shgmsvc.getAllShgm();
	pageContext.setAttribute("shgmlist", list);
%>

<html>
<head>
<meta charset="UTF-8">
<title>All_Shgm</title>

<style>
	table{
		border: 3px solid black;
		text-align: center;
		width: 2600px;
	}
	th, td {
    	border: 1px solid black;
  	}
  	td.intro{
	  	width:300px;
	  	text-align:left;
  	}
  	.imgtd img{
  		width:200px;
  		height:150px;
  		objtec-fit:contain;
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
			<td>修改市集商品</td>
			<td>刪除市集商品</td>
		</tr>
		
		<%@ include file="page1.file" %> 
		
		<c:forEach var="shgmvo" items="${shgmlist}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${shgmvo.shgmno}</td>
			<td>${shgmvo.sellerno}</td>
			<td>${(shgmvo.buyerno == null)? "尚未有買家":shgmvo.buyerno}</td>
			<td>${shgmvo.shgmname}</td>
			<td>${shgmvo.price}</td>
			<td class="intro">${shgmvo.intro}</td>
			<td class="imgtd"><img src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}"/></td>
			<c:choose>
	            <c:when test="${shgmvo.upcheck == 0}">
	                <td>未審核</td>
	            </c:when>
	            <c:when test="${shgmvo.upcheck == 1}">
	                <td>審核上架</td>
	            </c:when>
	            <c:otherwise>
	                 <td>審核下架</td>
	            </c:otherwise>
        	</c:choose>
        	<c:choose>
        		<c:when test="${shgmvo.upcheck == 2}">
        			<td>本商品已審核下架</td>
        		</c:when>
        		<c:when test="${shgmvo.uptime == null}">
        			<td>本商品尚未上架</td>
        		</c:when>
        		<c:otherwise>
	        		<td><fmt:formatDate value="${shgmvo.uptime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
        		</c:otherwise>
        	</c:choose>
			<td>${(shgmvo.take == null)? "尚無資料":shgmvo.take}</td>
			<td>${(shgmvo.takernm == null)? "尚無資料":shgmvo.takernm}</td>
			<td>${(shgmvo.takerph == null)? "尚無資料":shgmvo.takerph}</td>
			<td>${(shgmvo.address == null)? "尚無資料":shgmvo.address}</td>
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
	                <td>未下訂</td>
	            </c:when>
	            <c:when test="${shgmvo.status == 1}">
	                <td>已下訂</td>
	            </c:when>
	            <c:when test="${shgmvo.status == 2}">
	                <td>已完成</td>
	            </c:when>
	            <c:otherwise>
	                 <td>取消</td>
	            </c:otherwise>
        	</c:choose>
        	<c:choose>
        		<c:when test="${shgmvo.upcheck == 2}">
        			<td>本商品已審核下架</td>
        		</c:when>
        		<c:when test="${shgmvo.soldtime == null}">
        			<td>本商品尚未售出</td>
        		</c:when>
        		<c:otherwise>
	        		<td><fmt:formatDate value="${shgmvo.soldtime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
        		</c:otherwise>
        	</c:choose>
			<td>
				<form method="post" action="<%= request.getContextPath()%>/shgm/shgm.do">
					<input type="hidden" name="shgmno" value="${shgmvo.shgmno}">
					<input type="hidden" name="action" value="getone_update" >
					<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"/>
					<input type="submit" value="修改">
				</form>
			</td>
			<td>
				<form method="post" action="<%= request.getContextPath()%>/shgm/shgm.do">
					<input type="hidden" name="shgmno" value="${shgmvo.shgmno}">
					<input type="hidden" name="action" value="delete" >
					<input type="submit" value="刪除">
				</form>
			</td>
		</tr>
		</c:forEach>
		
		<%@ include file="page2.file" %>
		
	</table>
	<a href="<%= request.getContextPath()%>/back-end/shgm/shgm_select_page.jsp">回首頁</a>
</body>
</html>