<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shgmrp.model.*" %>
    
<%
    	ShgmrpVO shgmrpvo = (ShgmrpVO) request.getAttribute("shgmrpvo");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type"content="text/html; charset=UTF-8">
<title>Add_Shgamerp</title>
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
<form method="post" action="<%=request.getContextPath() %>/shgmrp/shgmrp.do">
	<table>
			<tr>
				<th>檢舉市集商品欄位</th>
				<th>請輸入資料</th>
			</tr>
			<tr>
				<td>市集商品編號</td>
				<td><input type="text" name="shgmno" size="15"
					value="<%= (shgmrpvo == null)? "CA00004": shgmrpvo.getShgmno()%>"/></td>
			</tr>
			<tr>
				<td>檢舉人會員編號</td>
				<td><input type="text" name="suiterno" size="15"
					value="<%= (shgmrpvo == null)? "BM00010": shgmrpvo.getSuiterno()%>"/></td>
			</tr>
			<tr>
				<td>檢舉內容</td>
				<td><input type="text" name="detail" size="15"
					value="<%= (shgmrpvo == null)? "感覺不太對先檢舉再說": shgmrpvo.getDetail()%>"/></td>
			</tr>
			<tr>
				<td>檢舉狀態</td>
				<td><select name="status">
						<c:forEach var="i" begin="0" end="2">
							<option value="${i}" ${(shgmrpvo.status == i)? 'selected':''}>${(i == 0)? "未審查": (i == 1)? "確認檢舉": "取消檢舉"}</option>
						</c:forEach>
					</select></td>
			</tr>
			<tr>
				<td colspan="2"><input type="hidden" name="action" value="insert">	
					<input type="submit" value="送出" ></td>
			</tr>
	</table>
</form>
	<a href="<%=request.getContextPath() %>/back-end/shgmrp/shgmrp_select_page.jsp">回首頁</a>
</body>
</html>