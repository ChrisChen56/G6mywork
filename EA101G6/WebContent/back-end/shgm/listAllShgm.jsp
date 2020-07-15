<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.shgm.model.*" %>
<%@ page import="java.util.*" %>
<%
	ShgmService shgmsvc = new ShgmService();
	Set<ShgmVO> set = shgmsvc.getAllShgm();
	pageContext.setAttribute("shgmset", set);
%>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<meta charset="UTF-8">
<title>All_Shgm</title>
<style>
	table{
		border: 3px solid black;
		text-align: center;
	}
	th, td {
    	border: 1px solid black;
    	width: 135px;
  	}
  	.table td, .table th {
    	vertical-align: middle;
    }
  	#shgmall-mainarea{
  		margin:1% auto;
  		height:776px;
  	}
  	#intro{
	  	text-align:left;
  	}
  	#introbtn{
  		margin-top: 10%;
  	}
  	div.pageselect-area {
		display: flex;
		justify-content: center;
	}
  	.lefta{
		float: right;
		margin-left:1%;
	}
	#whichpage{
		width: 40px;
		margin:0 1%;
	}
	.right-area{
		width:48%;
		float:left;
	}
	.right-area a, .left-area  a, #soldtlbtn, #introbtn{
		background-color: #9999;
		border-color: #9999;
		color:black;
	}   
	.left-area{
		width:48%;
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
<div id="shgmall-mainarea">
		<%@ include file="page1.file" %> 
	<table id="table" class="table table-striped bg-white">
		<tr>
			<td>市集商品編號</td>
			<td>賣家會員編號</td>
			<td>市集商品名稱</td>
			<td>市集商品價錢</td>
			<td>市集商品圖片</td>
			<td>上架審核狀態</td>
			<td>上架時間</td>
			<td>市集商品詳情</td>
			<td>售出時間</td>
			<td>修改市集商品</td>
			<td>刪除市集商品</td>
		</tr>
		
		<c:forEach var="shgmvo" items="${shgmset}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr ${(shgmvo.shgmno == param.shgmno)? 'bgcolor=#b5b5b5':''}>
			<td>${shgmvo.shgmno}</td>
			<td>${shgmvo.sellerno}</td>
			<td>${shgmvo.shgmname}</td>
			<td>${shgmvo.price}</td>
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
        	<td>
        	<button id="soldtlbtn" type="button" class="btn btn-primary" data-toggle="modal" onclick="showmodal()" data-target="#modal1${shgmvo.shgmno}">
			  交易詳情
			</button>
        	<div class="modal fade" id="modal1${shgmvo.shgmno}" tabindex="-1" role="dialog" aria-labelledby="${shgmvo.shgmno}ModalLabel1">
			  <div class="modal-dialog modal-xl">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="${shgmvo.shgmno}ModalLabel1">市集商品交易詳情</h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
			        <table style="width:100%">
			        	<tr>
			        		<td>買家會員編號</td>
			        		<td>取貨方式</td>
							<td>取貨人姓名</td>
							<td>取貨人電話</td>
							<td>取貨地址</td>
							<td>出貨狀態</td>
							<td>付款狀態</td>
							<td>訂單狀態</td>
			        	</tr>
			        	<tr>
			        		<td>${(shgmvo.buyerno == null)? "尚未有買家":shgmvo.buyerno}</td>
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
			        	</tr>
			        </table>
			      </div>
			    </div>
			  </div>
			</div>
			<button id="introbtn" type="button" class="btn btn-primary" data-toggle="modal" onclick="showmodal()" data-target="#modal2${shgmvo.shgmno}">
			 簡介
			</button>
        	<div class="modal fade" id="modal2${shgmvo.shgmno}" tabindex="-1" role="dialog" aria-labelledby="${shgmvo.shgmno}ModalLabel2">
			  <div class="modal-dialog modal-dialog-scrollable">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="${shgmvo.shgmno}ModalLabel2">市集商品簡介</h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div id="intro"class="modal-body">
			        	${shgmvo.intro}
			      </div>
			    </div>
			  </div>
			</div>
        	</td>
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
					<input type="hidden" name="whichPage" value="<%=whichPage%>">
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
	</table>
	<input type="hidden" id="mbrno" value="shgmBackEnd">
	<input type="hidden" id="wsShgmno" value="${param.shgmno}">
</div>
		<%@ include file="page2.file" %>
		
	<br>
	<a href="<%= request.getContextPath()%>/back-end/shgm/shgm_select_page.jsp">回首頁</a>
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jsForShgm/wsForShgm.js"></script>
	<script>
	function showmodal(){
		$(this).modal("show");
	}

	</script>
</body>
</html>