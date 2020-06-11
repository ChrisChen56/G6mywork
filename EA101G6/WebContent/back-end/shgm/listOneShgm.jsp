<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.shgm.model.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<%
	ShgmVO shgmvo = (ShgmVO)request.getAttribute("shgmvo");
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
  	img{
  	 	width: 200px;
  	 	height: 150px;
  	}
</style>
</head>
<body>
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
		</tr>
		<tr>
			<td>${shgmvo.shgmno}</td>
			<td>${shgmvo.sellerno}</td>
			<td>${shgmvo.buyerno}</td>
			<td>${shgmvo.shgmname}</td>
			<td>${shgmvo.price}</td>
			<td width="375">${shgmvo.intro}</td>
			<td><img src="<%=request.getContextPath()%>/back-end/shgm/displayimg?shgmno=${shgmvo.shgmno}"/></td>
			<td><%=(shgmvo.getUpcheck() == 0)? "未審核":(shgmvo.getUpcheck() == 1)? "審核通過": "審核未通過" %></td>
			<td><fmt:formatDate value="${shgmvo.uptime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
			<td>${shgmvo.take}</td>
			<td>${shgmvo.takernm}</td>
			<td>${shgmvo.takerph}</td>
			<td>${shgmvo.address}</td>
			<td><%=(shgmvo.getBoxstatus() == 0)? "未出貨": (shgmvo.getBoxstatus() == 1)? "已出貨": "送達" %></td>
			<td><%=(shgmvo.getPaystatus() == 0)? "未付款": "已付款" %></td>
			<td><%=(shgmvo.getStatus() == 0)? "未完成": (shgmvo.getStatus() == 1)? "已完成": "取消" %></td>
			<td>${shgmvo.soldtime}</td>
		</tr>
	</table>
	<a href="<%=request.getContextPath()%>/back-end/shgm/shgm_select_page.jsp">回首頁</a>
</body>
</html>