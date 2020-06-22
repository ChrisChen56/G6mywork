<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shgm.model.*" %>
    
<%
		byte[] img = (byte[])request.getAttribute("img");
    	ShgmVO shgmvo = (ShgmVO) request.getAttribute("shgmvo");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add_Shgame</title>
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
				<th>請輸入資料</th>
			</tr>
			<tr>
				<td>賣家會員編號</td>
				<td><input type="text" name="sellerno" size="15"
					value="<%= (shgmvo == null)? "BM00009": shgmvo.getSellerno()%>"/></td>
			</tr>
			<tr>
				<td>買家會員編號</td>
				<td><input type="text" name="buyerno" size="15"
					value="<%= (shgmvo == null)? "BM00008": shgmvo.getBuyerno()%>"/></td>
			</tr>
			<tr>
				<td>市集商品名稱</td>
				<td><input type="text" name="shgmname" size="15"
					value="<%= (shgmvo == null)? "企鵝敲冰磚": shgmvo.getShgmname()%>"/></td>
			</tr>
			<tr>
				<td>市集商品價錢</td>
				<td><input type="text" name="price" size="15"
					value="<%= (shgmvo == null)? "60": (shgmvo.getPrice() == null)? "":shgmvo.getPrice()%>"/></td>
					</tr>
			<tr>
				<td>市集商品簡介</td>
				<td><textarea name="intro" cols="32" rows="10"><%= (shgmvo == null)? "遊戲中玩家們輪流轉動轉盤，並根據轉盤指示內容，利用鎚子敲打指定顏色冰塊，想辦法讓目標冰塊掉落，且不能讓破冰台上的企鵝跌落。藉由遊戲進行，不僅能訓練玩家的肢體手感，還可測試玩家的應變力及平衡感，可說是極具挑戰性的肢體桌上遊戲。": shgmvo.getIntro()%>
					</textarea></td>
			
			</tr>
			<tr>
				<td>市集商品圖片</td>
				<td><input type="file" name="img" id="imgfile" onchange="document.getElementById('blah').src = window.URL.createObjectURL(this.files[0])" accept=".png, .jpg, .jpeg .gif"/>
				<img id="blah" alt="your image" width="100" height="100"/></td>
		</tr>
			<tr>
				<td>上架審核狀態</td>
				<td><select id="upcheck" name="upcheck">
						<c:forEach var="i" begin="0" end="2">
							<option value="${i}" ${(shgmvo.upcheck == i)? 'selected':'' }>${(i == 0)? "未審核": (i == 1)? "已審核": "審核未通過"}</option>
						</c:forEach>
					</select></td>
			</tr>
			<tr>
				<td>上架時間</td>
				<td>本商品尚未上架</td>
			</tr>
			<tr>
				<td>取貨方式</td>
				<td><input type="text" name="take" size="15"
					value="<%= (shgmvo == null)? "全家取": shgmvo.getTake()%>"/></td>
			</tr>
			<tr>
				<td>取貨人姓名</td>
				<td><input type="text" name="takernm" size="15"
					value="<%= (shgmvo == null)? "陳柏元": shgmvo.getTakernm()%>"/></td>
			</tr>
			<tr>
				<td>取貨人電話</td>
				<td><input type="text" name="takerph" size="15"
					value="<%= (shgmvo == null)? "0987878878":shgmvo.getTakerph()%>"/></td>
			</tr>
			<tr>
				<td>取貨地址</td>
				<td><input type="text" name="address" size="15"
					value="<%= (shgmvo == null)? "中壢區中央路216巷8號":shgmvo.getAddress()%>"/></td>
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
				<td>本商品尚未售出</td>
			</tr>
			<tr>
				<td colspan="2"><input type="hidden" name="action" value="insert">	
					<input type="submit" value="送出" ></td>
			</tr>
	</table>
</form>
	<a href="<%=request.getContextPath() %>/back-end/shgm/shgm_select_page.jsp">回首頁</a>
</body>

</html>