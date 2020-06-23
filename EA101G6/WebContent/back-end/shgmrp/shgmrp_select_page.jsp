<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>後台市集商品檢舉首頁</title>
</head>
<body>

	<ul>
		<c:if test="${not empty errormsgs}">
			<c:forEach var="errors" items="${errormsgs}">
				<li>${errors}</li>
			</c:forEach>
		</c:if>
	</ul>
	<ul>
	
 		<jsp:useBean id="shgmrpsvc" class="com.shgmrp.model.ShgmrpService"/>
 		<jsp:useBean id="shgmsvc" class="com.shgm.model.ShgmService"/>
 		
		<li>
			<form method="post" action="<%=request.getContextPath()%>/shgmrp/shgmrp.do">
			請選擇市集商品檢舉編號：
				<select size="1" name="shgmrpno">
					<c:forEach var="shgmrpvo" items="${shgmrpsvc.allShgmrp}">
						<option value="${shgmrpvo.shgmrpno}">${shgmrpvo.shgmrpno}
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="get_one" >
				<input type="submit" value="送出" >
			</form>
		</li>
		
		<li>
			<form method="post" action="<%=request.getContextPath()%>/shgmrp/shgmrp.do">
			請選擇被檢舉的市集商品：
				<select size="1" name="shgmrpno">
					<c:forEach var="shgmrpvo" items="${shgmrpsvc.allShgmrp}">
						<option value="${shgmrpvo.shgmrpno}">${shgmsvc.getOneShgm(shgmrpvo.shgmno).shgmname}
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="get_one" >
				<input type="submit" value="送出" >
			</form>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/back-end/shgmrp/addShgmrp.jsp">檢舉市集商品</a>		！！注意！！需要先通過審核
		</li>
		<li>
			<form method="post" action="<%=request.getContextPath()%>/shgmrp/shgmrp.do">
			請選擇要修改的被檢舉市集商品：
				<select size="1" name="shgmrpno">
					<c:forEach var="shgmrpvo" items="${shgmrpsvc.allShgmrp}">
						<option value="${shgmrpvo.shgmrpno}">${shgmsvc.getOneShgm(shgmrpvo.shgmno).shgmname}
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="getone_update" >
				<input type="submit" value="送出" >
			</form>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/back-end/shgmrp/listAllShgmrp.jsp">檢視全部的被檢舉市集商品</a>
		</li>
	</ul>
	<a href="<%=request.getContextPath()%>/back-end/shgmrp/shgmrp_select_page.jsp">回首頁</a>
</body>
</html>