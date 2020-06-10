<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>市集商品檢舉後台首頁</title>
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
		<li>
			<form method="post" action="<%=request.getContextPath()%>/back-end/shgmrp/shgmrp.do" >
				請輸入市集商品檢舉編號(CB00001)：
				<input type="text" name="shgmrpno" >
				<input type="hidden" name="action" value="get_one" >
				<input type="submit" value="送出" >
			</form>
		</li>
		
		<jsp:useBean id="shgmjdbc" class="com.shgmrp.model.ShgmrpJDBCDAO"/>
		
		<li>
			<form method="post" action="<%=request.getContextPath()%>/back-end/shgm/shgm.do">
			請選擇市集商品檢舉編號：
				<select size="1" name="shgmno">
					<c:forEach var="shgmvo" items="${shgmjdbc.all}">
						<option value="${shgmvo.shgmno}">${shgmvo.shgmname}
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="get_one" >
				<input type="submit" value="送出" >
			</form>
		</li>
		
		<li>
			<a href="<%=request.getContextPath()%>/back-end/shgm/addShgm.jsp">上架新的市集商品</a>		！！注意！！需要先通過審核
		</li>
		
		<li>
			<form method="post" action="<%=request.getContextPath()%>/back-end/shgm/shgm.do">
			請選擇要修改的市集商品：
				<select size="1" name="shgmno">
					<c:forEach var="shgmvo" items="${shgmjdbc.all}">
						<option value="${shgmvo.shgmno}">${shgmvo.shgmname}
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="getone_update" >
				<input type="submit" value="送出" >
			</form>
		</li>
		
		<li>
			<a href="<%=request.getContextPath()%>/back-end/shgm/listAllShgm.jsp">檢視全部市集商品</a>
		</li>
	</ul>
	<a href="<%=request.getContextPath()%>/back-end/shgm/shgm_select_page.jsp">回首頁</a>
</body>
</html>