<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.shgm.model.*" %>
<%
    	ShgmVO shgmvo = (ShgmVO) request.getAttribute("shgmvo");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update_Shgame</title>
<style>
	table, td,tr{
		text-align:center;
		border: black 2px solid;
	}
</style>
</head>
<body>
	<ul>
		<c:if test="${not empty errormsgs}">
			<c:forEach var="errors" items="${errormsgs}">
				<li>${errors}</li>
			</c:forEach>
		</c:if>
	</ul>
<form method="post" action="<%=request.getContextPath() %>/shgm/shgm.do" enctype="multipart/form-data">
	<table>
			<tr>
				<th>市集商品欄位</th>
				<th>請輸入市集商品資料</th>
			</tr>
			<tr>
				<td>市集商品編號</td>
				<td><%= shgmvo.getShgmno()%><input type="hidden" name="shgmno" value="<%= shgmvo.getShgmno()%>"></td>
			</tr>
			<tr>
				<td>賣家會員編號</td>
				<td><input type="text" name="sellerno" size="15"
					value="<%= shgmvo.getSellerno()%>"/></td>
			</tr>
			<tr>
				<td>買家會員編號</td>
				<td><input type="text" name="buyerno" size="15" placeholder="<%= (shgmvo.getBuyerno() == null)? "尚未有買家":""%>"
				value="<%= (shgmvo.getBuyerno() == null)? "":shgmvo.getBuyerno()%>"/></td>
			</tr>
			<tr>
				<td>市集商品名稱</td>
				<td><input type="text" name="shgmname" size="15"
					value="<%= shgmvo.getShgmname()%>"/></td>
			</tr>
			<tr>
				<td>市集商品價錢</td>
				<td><input type="text" name="price" size="15"
					value="<%= shgmvo.getPrice()%>"/></td>
			</tr>
			<tr>
				<td>市集商品簡介</td>
				<td><textarea name="intro" cols="32" rows="10"><%= shgmvo.getIntro()%></textarea></td>
			
			</tr>
			<tr>
				<td>市集商品圖片</td>
				<td><input type="file" name="img" id="img" onchange="document.getElementById('blah').src = window.URL.createObjectURL(this.files[0])" accept=".png, .jpg, .jpeg .gif"/>
							<img id="blah" alt="your image" width="100" height="100" src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=<%= shgmvo.getShgmno()%>"/>
			</tr>
			<tr>
				<td>上架審核狀態</td>
				<td><select id="upcheck" name="upcheck">
						<c:forEach var="i" begin="0" end="2">
							<option value="${i}" ${(shgmvo.upcheck == i)? 'selected':'' }>${(i == 0)? "未審核": (i == 1)? "審核上架": "審核下架"}</option>
						</c:forEach>
					</select></td>
			</tr>
			<tr>
				<td>上架時間</td>
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
			</tr>
			<tr>
				<td>取貨方式</td>
				<td><input type="text" name="take" size="15" placeholder="<%= (shgmvo.getTake() == null)? "尚無資料":""%>"
				value="<%= (shgmvo.getTake() == null)? "": shgmvo.getTake()%>"/></td>
			</tr>
			<tr>
				<td>取貨人姓名</td>
				<td><input type="text" name="takernm" size="15" placeholder="<%= (shgmvo.getTakernm() == null)? "尚無資料":""%>"
				value="<%= (shgmvo.getTakernm() == null)? "": shgmvo.getTakernm()%>"/></td>
			</tr>
			<tr>
				<td>取貨人電話</td>
				<td><input type="text" name="takerph" size="15" placeholder="<%= (shgmvo.getTakerph() == null)? "尚無資料":""%>"
				value="<%= (shgmvo.getTakerph() == null)? "": shgmvo.getTakerph()%>"/></td>
			</tr>
			<tr>
				<td>取貨地址</td>
				<td><input type="text" name="address" size="15" placeholder="<%= (shgmvo.getAddress() == null)? "尚無資料":""%>"
				value="<%= (shgmvo.getAddress() == null)? "": shgmvo.getAddress()%>"/></td>
			</tr>
			<tr>
				<td>出貨狀態</td>
				<td><select id="boxstatus" name="boxstatus">
						<c:forEach var="i" begin="0" end="2">
							<option value="${i}" ${(shgmvo.boxstatus == i)? 'selected':'' }>${(i == 0)? "未出貨": (i == 1)? "已出貨": "送達"}</option>
						</c:forEach>
					</select></td>
			</tr>
			<tr>
				<td>付款狀態</td>
				<td><select id="paystatus" name="paystatus">
						<c:forEach var="i" begin="0" end="1">
							<option value="${i}" ${(shgmvo.paystatus == i)? 'selected':'' }>${(i == 0)? "未付款": "已付款"}</option>
						</c:forEach>
					</select></td>
			</tr>
			<tr>
				<td>訂單狀態</td>
				<td><select id="status" name="status">
						<c:forEach var="i" begin="0" end="3">
							<option value="${i}" ${(shgmvo.status == i)? 'selected':'' }>${(i == 0)? "未下訂": (i == 1)? "已下訂": (i == 2)? "已完成":"取消"}</option>
						</c:forEach>
					</select></td>
			</tr>
			<tr>
				<td>售出時間</td>
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
			</tr>
			<tr>
				<td colspan="2">
				<input type="hidden" name="action" value="update">	
				<input type="submit" value="送出" ></td>
			</tr>
	</table>
</form>
	<a href="<%=request.getContextPath() %>/back-end/shgm/shgm_select_page.jsp">回首頁</a>
</body>

</html>