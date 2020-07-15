<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="connectionpool.*"%>
<%
	WsMessage wsmsg = new WsMessage();
	pageContext.setAttribute("wsmsg", wsmsg);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${member != null}">
		<div class="alert-area">
			<button id="showMsg" class="btn btn-primary" style="width:110px;">顯示訊息</button>
			<div class="alert-area-msgs">
				<c:forEach var="msgvo" items="${wsmsg.getRead0Mbrmsg(member.mbrno)}" varStatus="count">
					<c:if test="${count.last}">
						<input type="hidden" id="last" value="${count.index}" >
						<div id="circle" class="circle">${count.index+1}</div>
					</c:if>
					<div class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-autohide="false" style="display:none;">
					  <div class="toast-header">
					    <img src="<%=request.getContextPath() %>/front-end/shgm/images/alertMsg.png" class="rounded mr-2" style="width:20px;height:20px;">
					    <strong class="mr-auto">市集新訊</strong>
					    <small class="text-muted">標示為已讀</small>
					    <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close" value="${msgvo.index}">
					      <span class="xx" aria-hidden="true">&times;</span>
					    </button>
					  </div>
					  <div class="toast-body">
					    ${msgvo.message}
					  </div>
					</div>
				</c:forEach>
			</div>
		</div>
	</c:if>
</body>
<input type="hidden" id="getContextPath" value="<%=request.getContextPath()%>"><!-- ajaxForMbrmsgs.jsp用的 -->
</html>