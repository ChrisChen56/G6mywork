<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.shgm.model.*"%>
<%@ page import="com.shgmrp.model.*"%>
<%@ page import="com.mbrpf.model.*"%>
<%@ page import="java.util.*"%>
<%
	MbrpfVO member = (MbrpfVO) session.getAttribute("member");
	ShgmService shgmsvc = new ShgmService();
	Set<ShgmVO> shgmset = shgmsvc.allForSeller(member.getMbrno());
	pageContext.setAttribute("shgmset", shgmset);
	ShgmrpService shgmrpsvc = new ShgmrpService();
	pageContext.setAttribute("shgmrpsvc", shgmrpsvc);
%>

<html lang="en">
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<title>sellerPage</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link
	href="https://fonts.googleapis.com/css?family=Rubik:300,400,700|Oswald:400,700"
	rel="stylesheet">
<!-- 登入圖示 -->
<link rel="stylesheet" href="fonts/icomoon/style.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/jquery.fancybox.min.css">
<link rel="stylesheet" href="css/owl.carousel.min.css">
<link rel="stylesheet" href="css/owl.theme.default.min.css">
<link rel="stylesheet" href="css/aos.css">
<!-- MAIN CSS -->
<link rel="stylesheet" href="css/style.css">

<!-- 顯示訊息的css -->
<link rel="stylesheet" href="css/alert-area.css">
</head>
<style>
body {
	background-color: #EEEEEE;
	background-size: repeat;
}

form {
	margin-block-end: 0px;
}

.icon {
	width: 20px;
	height: 20px;
}

div.main-area {
	position: relative;
	display: block;
	border: black 1px solid;
	background-color: white;
	margin: 2% auto;
	height: 1500px;
}

.top-info-wrapper {
	position: relative;
	text-align: center;
	margin-top: 3%;
}

.breadcrumb-nav {
	background-color: #EEEEEE;
}

.awrapper {
	display: block;
	text-align: right;
	width: 85%;
}
@media (max-width: 1496px) {
	.awrapper {
		width:75%;
	}
}
@media (max-width: 936px) {
	.awrapper {
		width:60%;
	}
}

.breadcrumb button {
	margin: 0;
}

div.top-info {
	margin: 0 auto;
	border: green 1px solid;
}

.btn {
	margin: 0 1%;
	background-color: white;
}

.btn:hover {
	background-color: white;
	color: #FF8C00; /*ffa216*/
	box-shadow: 0 0 11px rgba(33, 33, 33, .2);
}

.shgm-area-wrapper {
	text-align: center;
}

div.shgm-area {
	margin: 3% auto;
}

ul.list-group {
	display: table;
	width: 100%;
}

li.list-group-item {
	vertical-align: middle;
	display: table-cell;
}

ul.four-li li {
	width: 25%;
}

ul.five-li li {
	width: 20%;
}

ul.six-li li {
	width: 16.66%;
}

div .imgwrapper {
	display: flex;
	height: 200px;
}

ul li img {
	max-width: 100%;
	max-height: 100%;
	margin: auto;
}

li.firstlis {
	background-color: #e9ecef;
}

.pageselect-area-wrapper {
	text-align: center;
}

div.pageselect-area {
	display: block;
	position: relative;
	width: 100%;
	margin: 0 auto;
}

.pagination {
	margin-top: -5%;
	display: flex;
	justify-content: center;
}
</style>
<body data-offset="300" background="images/bgimage3.jpg">

<%@ include file="/front-end/shgm/front-end-nav.jsp"%>

	<div class="main-area container col-10 align-self-center">
		<div class="top-info-wrapper">
			<nav aria-label="breadcrumb" class="breadcrumb-nav">
				<ol class="breadcrumb d-flex">
					<li class="breadcrumb-item"><a href="#">首頁</a></li>
					<li class="breadcrumb-item" aria-current="page"><a
						href="<%=request.getContextPath()%>/front-end/shgm/mainPage.jsp">市集</a></li>
					<li class="breadcrumb-item active" aria-current="page">賣家專區</li>
					<li class="awrapper"><a id="upshgm"
						class="btn btn-primary ml-auto"
						href="<%=request.getContextPath()%>/front-end/shgm/sellPage.jsp"
						role="button">我要上架</a></li>
				</ol>
			</nav>
		</div>
		<div class="shgm-area-wrapper">
			<div class="shgm-area ">
				<div class="row">
					<div class="col-2">
						<div class="list-group" id="list-tab" role="tablist">
							<a class="list-group-item list-group-item-action active"
								id="list-upcheck-list" data-toggle="list" href="#list-upcheck"
								role="tab" aria-controls="upcheck">上架狀態</a> <a
								class="list-group-item list-group-item-action"
								id="list-boxstatus-list" data-toggle="list"
								href="#list-boxstatus" role="tab" aria-controls="boxstatus">出貨狀態</a>
							<a class="list-group-item list-group-item-action"
								id="list-status2-list" data-toggle="list" href="#list-status2"
								role="tab" aria-controls="status2">已完成</a> <a
								class="list-group-item list-group-item-action"
								id="list-status3-list" data-toggle="list" href="#list-status3"
								role="tab" aria-controls="status3">被取消的市集商品</a>
						</div>
					</div>
					<div class="col-10">
						<div class="tab-content" id="nav-tabContent">
							<div class="tab-pane fade show active" id="list-upcheck"
								role="tabpanel" aria-labelledby="list-upcheck-list">
								<ul class="nav nav-tabs" id="myTab" role="tablist">
									<li class="nav-item" role="presentation"><a
										class="nav-link active" id="upcheck0-tab" data-toggle="tab"
										href="#upcheck0" role="tab" aria-controls="upcheck0"
										aria-selected="true">待上架</a></li>
									<li class="nav-item" role="presentation"><a
										class="nav-link" id="upcheck1-tab" data-toggle="tab"
										href="#upcheck1" role="tab" aria-controls="upcheck1"
										aria-selected="false">上架中</a></li>
									<li class="nav-item" role="presentation"><a
										class="nav-link" id="upcheck2-tab" data-toggle="tab"
										href="#upcheck2" role="tab" aria-controls="upcheck2"
										aria-selected="false">下架中</a></li>
								</ul>
								<div class="tab-content" id="myTabContent">
									<div class="tab-pane fade show active" id="upcheck0"
										role="tabpanel" aria-labelledby="upcheck0-tab">
										未經審核的市集商品(可自行下架)商品名稱、圖片、售價、下架選項
										<ul class="list-group list-group-horizontal four-li">
											<li class="list-group-item firstlis">商品名稱</li>
											<li class="list-group-item firstlis">圖片</li>
											<li class="list-group-item firstlis">售價</li>
											<li class="list-group-item firstlis">市集商品狀態</li>
										</ul>
										<c:forEach var="shgmvo" items="${shgmset}">
											<c:if
												test="${shgmvo.upcheck == 0 and shgmvo.boxstatus == 0 and shgmvo.paystatus == 0 and shgmvo.status == 0}">
												<ul class="list-group list-group-horizontal four-li">
													<li class="list-group-item">${shgmvo.shgmname}</li>
													<li class="list-group-item"><div class="imgwrapper">
															<img
																src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}">
														</div></li>
													<li class="list-group-item">${shgmvo.price}</li>
													<li class="list-group-item">上架審核中<br>
														<button id="${shgmvo.shgmno}" value="0" type="button"
															class="btn btn-primary upcheck">自行下架</button></li>
												</ul>
											</c:if>
										</c:forEach>
									</div>
									<div class="tab-pane fade" id="upcheck1" role="tabpanel"
										aria-labelledby="upcheck1-tab">
										已上架的市集商品(可自行下架)商品名稱、圖片、售價、上架時間
										<ul class="list-group list-group-horizontal four-li">
											<li class="list-group-item firstlis">商品名稱</li>
											<li class="list-group-item firstlis">圖片</li>
											<li class="list-group-item firstlis">售價</li>
											<li class="list-group-item firstlis">上架時間</li>
										</ul>
										<c:forEach var="shgmvo" items="${shgmset}">
											<c:if
												test="${shgmvo.upcheck == 1 and shgmvo.boxstatus == 0 and shgmvo.paystatus == 0 and shgmvo.status == 0}">
												<ul class="list-group list-group-horizontal four-li">
													<li class="list-group-item">${shgmvo.shgmname}</li>
													<li class="list-group-item"><div class="imgwrapper">
															<img
																src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}">
														</div></li>
													<li class="list-group-item">${shgmvo.price}</li>
													<li class="list-group-item"><fmt:formatDate
															value="${shgmvo.uptime}" pattern="yyyy/MM/dd HH:mm:ss" /><br>
														<button id="${shgmvo.shgmno}" value="1" type="button"
															class="btn btn-primary upcheck">自行下架</button></li>
												</ul>
											</c:if>
										</c:forEach>
									</div>
									<div class="tab-pane fade" id="upcheck2" role="tabpanel"
										aria-labelledby="upcheck2-tab">
										已下架的市集商品(自行下架/被檢舉:都顯示可修改的按鈕)商品名稱、圖片、下架原因、修改(用上架頁面修改，內含申請重新上架？)
										<ul class="list-group list-group-horizontal five-li">
											<li class="list-group-item firstlis">商品名稱</li>
											<li class="list-group-item firstlis">圖片</li>
											<li class="list-group-item firstlis">下架原因</li>
											<li class="list-group-item firstlis">更新商品狀態</li>
										</ul>
										<c:forEach var="shgmvo" items="${shgmset}">
											<c:if
												test="${shgmvo.upcheck == 2 and shgmvo.boxstatus == 0 and shgmvo.paystatus == 0 and shgmvo.status == 0}">
												<form method="post"
													action="<%=request.getContextPath()%>/front-end/shgm/shgm.do">
													<ul class="list-group list-group-horizontal four-li">
														<li class="list-group-item">${shgmvo.shgmname}</li>
														<li class="list-group-item"><div class="imgwrapper">
																<img src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}">
															</div>
														</li>
														<li class="list-group-item">
														<c:choose>
															<c:when test="${shgmrpsvc.getOnerpByShgmno(shgmvo.shgmno).status != 1 || shgmrpsvc.getOnerpByShgmno(shgmvo.shgmno) == null}">
																後台下架
															</c:when>
															<c:when test="${shgmrpsvc.getOnerpByShgmno(shgmvo.shgmno).status == 1}">
																檢舉下架${shgmrpsvc.getOnerpByShgmno(shgmvo.shgmno).detail}
															</c:when>
															<c:otherwise>
																自行下架
															</c:otherwise>
														</c:choose>
														</li>
														<li class="list-group-item"><input type="submit"
															class="btn btn-primary" value="修改"><br>
															<button id="${shgmvo.shgmno}" value="2" type="button"
																class="btn btn-primary upcheck">重新上架</button></li>
													</ul>
													<input type="hidden" name="action"
														value="oneForSellerUpdate"> <input type="hidden"
														name="shgmno" value="${shgmvo.shgmno}">
												</form>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="tab-pane fade" id="list-boxstatus" role="tabpanel"
								aria-labelledby="list-boxstatus-list">
								<div class="tab-pane fade show active" id="list-upcheck"
									role="tabpanel" aria-labelledby="list-upcheck-list">
									<ul class="nav nav-tabs" id="myTab" role="tablist">
										<li class="nav-item" role="presentation"><a
											class="nav-link active" id="boxstatus0-tab" data-toggle="tab"
											href="#boxstatus0" role="tab" aria-controls="boxstatus0"
											aria-selected="true">待出貨</a></li>
										<li class="nav-item" role="presentation"><a
											class="nav-link" id="boxstatus1-tab" data-toggle="tab"
											href="#boxstatus1" role="tab" aria-controls="boxstatus1"
											aria-selected="false">出貨中</a></li>
										<li class="nav-item" role="presentation"><a
											class="nav-link" id="boxstatus2-tab" data-toggle="tab"
											href="#boxstatus2" role="tab" aria-controls="boxstatus2"
											aria-selected="false">已送達</a></li>
									</ul>
									<div class="tab-content" id="myTabContent">
										<div class="tab-pane fade show active" id="boxstatus0"
											role="tabpanel" aria-labelledby="boxstatus0-tab">
											boxstatus == 0 商品名稱、圖片、買家姓名、電話、地址、更改出貨狀態(0→1)
											<ul class="list-group list-group-horizontal six-li">
												<li class="list-group-item firstlis">商品名稱</li>
												<li class="list-group-item firstlis">圖片</li>
												<li class="list-group-item firstlis">買家姓名</li>
												<li class="list-group-item firstlis">電話</li>
												<li class="list-group-item firstlis">地址</li>
												<li class="list-group-item firstlis">出貨狀態</li>
											</ul>
											<c:forEach var="shgmvo" items="${shgmset}">
												<c:if
													test="${shgmvo.upcheck == 1 and shgmvo.boxstatus == 0 and shgmvo.paystatus == 1 and shgmvo.status == 1}">
													<ul class="list-group list-group-horizontal six-li">
														<li class="list-group-item">${shgmvo.shgmname}</li>
														<li class="list-group-item"><div class="imgwrapper">
																<img
																	src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}">
															</div></li>
														<li class="list-group-item">${shgmvo.takernm}</li>
														<li class="list-group-item">${shgmvo.takerph}</li>
														<li class="list-group-item">${shgmvo.address}</li>
														<li class="list-group-item">未出貨<br>
															<button id="${shgmvo.shgmno}" value="0" type="button"
																class="btn btn-primary boxstatus">進行出貨</button>
														</li>
													</ul>
												</c:if>
											</c:forEach>
										</div>
										<div class="tab-pane fade" id="boxstatus1" role="tabpanel"
											aria-labelledby="boxstatus1-tab">
											boxstatus == 1商品名稱、圖片、買家姓名、電話、地址、更改出貨狀態(1→2)
											<ul class="list-group list-group-horizontal six-li">
												<li class="list-group-item firstlis">商品名稱</li>
												<li class="list-group-item firstlis">圖片</li>
												<li class="list-group-item firstlis">買家姓名</li>
												<li class="list-group-item firstlis">電話</li>
												<li class="list-group-item firstlis">地址</li>
												<li class="list-group-item firstlis">出貨狀態</li>
											</ul>
											<c:forEach var="shgmvo" items="${shgmset}">
												<c:if
													test="${shgmvo.upcheck == 1 and shgmvo.boxstatus == 1 and shgmvo.paystatus == 1 and shgmvo.status == 1}">
													<ul class="list-group list-group-horizontal six-li">
														<li class="list-group-item">${shgmvo.shgmname}</li>
														<li class="list-group-item"><div class="imgwrapper">
																<img
																	src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}">
															</div></li>
														<li class="list-group-item">${shgmvo.takernm}</li>
														<li class="list-group-item">${shgmvo.takerph}</li>
														<li class="list-group-item">${shgmvo.address}</li>
														<li class="list-group-item">出貨中<br>
															<button id="${shgmvo.shgmno}" value="1" type="button"
																class="btn btn-primary boxstatus">送達商品</button>
														</li>
													</ul>
												</c:if>
											</c:forEach>
										</div>
										<div class="tab-pane fade" id="boxstatus2" role="tabpanel"
											aria-labelledby="boxstatus2-tab">
											boxstatus == 2商品名稱、圖片、買家姓名、電話、地址、等待買家收貨中
											<ul class="list-group list-group-horizontal six-li">
												<li class="list-group-item firstlis">商品名稱</li>
												<li class="list-group-item firstlis">圖片</li>
												<li class="list-group-item firstlis">買家姓名</li>
												<li class="list-group-item firstlis">電話</li>
												<li class="list-group-item firstlis">地址</li>
												<li class="list-group-item firstlis">出貨狀態</li>
											</ul>
											<c:forEach var="shgmvo" items="${shgmset}">
												<c:if
													test="${shgmvo.upcheck == 1 and shgmvo.boxstatus == 2 and shgmvo.paystatus == 1 and shgmvo.status == 1}">
													<ul class="list-group list-group-horizontal six-li">
														<li class="list-group-item">${shgmvo.shgmname}</li>
														<li class="list-group-item"><div class="imgwrapper">
																<img
																	src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}">
															</div></li>
														<li class="list-group-item">${shgmvo.takernm}</li>
														<li class="list-group-item">${shgmvo.takerph}</li>
														<li class="list-group-item">${shgmvo.address}</li>
														<li class="list-group-item">等待買家收貨中</li>
													</ul>
												</c:if>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane fade" id="list-status2" role="tabpanel"
								aria-labelledby="list-status2-list">
								status == 2商品名稱、圖片、售價、上架日期、售出日期
								<ul class="list-group list-group-horizontal five-li">
									<li class="list-group-item firstlis">商品名稱</li>
									<li class="list-group-item firstlis">圖片</li>
									<li class="list-group-item firstlis">售價</li>
									<li class="list-group-item firstlis">上架日期</li>
									<li class="list-group-item firstlis">售出日期</li>
								</ul>
								<c:forEach var="shgmvo" items="${shgmset}">
									<c:if
										test="${shgmvo.upcheck == 1 and shgmvo.boxstatus == 2 and shgmvo.paystatus == 1 and shgmvo.status == 2}">
										<ul class="list-group list-group-horizontal five-li">
											<li class="list-group-item">${shgmvo.shgmname}</li>
											<li class="list-group-item"><div class="imgwrapper">
													<img
														src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}">
												</div></li>
											<li class="list-group-item">${shgmvo.price}</li>
											<li class="list-group-item"><fmt:formatDate
													value="${shgmvo.uptime}" pattern="yyyy/MM/dd HH:mm:ss" /></li>
											<li class="list-group-item"><fmt:formatDate
													value="${shgmvo.soldtime}" pattern="yyyy/MM/dd HH:mm:ss" /></li>
										</ul>
									</c:if>
								</c:forEach>
							</div>
							<div class="tab-pane fade" id="list-status3" role="tabpanel"
								aria-labelledby="list-status3-list">
								status == 3
								<ul class="list-group list-group-horizontal four-li">
									<li class="list-group-item firstlis">商品名稱</li>
									<li class="list-group-item firstlis">圖片</li>
									<li class="list-group-item firstlis">售價</li>
									<li class="list-group-item firstlis">回收商品</li>
								</ul>
								<c:forEach var="shgmvo" items="${shgmset}">
									<c:if test="${shgmvo.status == 3}">
										<ul class="list-group list-group-horizontal four-li">
											<li class="list-group-item">${shgmvo.shgmname}</li>
											<li class="list-group-item"><div class="imgwrapper">
													<img
														src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}">
												</div></li>
											<li class="list-group-item">${shgmvo.price}</li>
											<li class="list-group-item"><button
													id="${shgmvo.shgmno}" type="button"
													class="btn btn-primary status">回收商品</button></li>
										</ul>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<input type="hidden" id="member" value="${member.mbrname}">
	
	<%@ include file="/front-end/shgm/alert-area.jsp"%>
	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jsForShgm/ajaxForMbrmsgs.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jsForShgm/wsForShgm.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jsForShgm/jsForAlert-area.js"></script>
	<script>
	$(document).ready(function(){
		
		/*下架中、待上架狀態切換*/
		$(".container").on("click",".upcheck",function(){
			var $shgmno = $(this).closest("button")[0].id;
			var $value = $(this).closest("button")[0].value;
			console.log($shgmno);
			console.log($value);
			$(this).closest("ul").fadeOut(function(){
				$(this).closest("ul")[0].remove();
			});
			
			$.ajax({
				type: "POST",
				url: '<%=request.getContextPath()%>/front-end/shgm/shgm.do?action=statusUpdate',
				data: {"shgmno":$shgmno,"upcheck":$value},
				dataType: "json",
				cache: false,
				success: function(response){
					jsondata = JSON.stringify(response);
					webSocket.send(jsondata);
					if(response.upcheck == 2){
						$("#upcheck2 ul:eq(0)").after('<form method="post" action="/EA101G6/front-end/shgm/shgm.do"></form>');
						$("form:first").append('<ul class="list-group list-group-horizontal four-li"></ul>');
						$("form:first ul").append('<li class="list-group-item">'+response.shgmname+'</li>');
						$("form:first ul").append('<li class="list-group-item"><div class="imgwrapper">'+
							'<img src="/EA101G6/shgm/displayimg?shgmno='+response.shgmno+'"></div></li>');
						$("form:first ul").append('<li class="list-group-item">'+response.detail+'</li>');
						$("form:first ul").append('<li class="list-group-item"><input type="submit" class="btn btn-primary" value="修改"><br>'+
							'<button id="'+response.shgmno+'"value="2" type="button" class="btn btn-primary upcheck">重新上架</button></li>');
						$("form:first").append('<input type="hidden" name="action" value="oneForSellerUpdate">');
						$("form:first").append('<input type="hidden" name="shgmno" value="'+response.shgmno+'">');
					} else if(response.upcheck == 0){
						$('#upcheck0 ul').eq(0).after('<ul class="list-group list-group-horizontal four-li"></ul>');
						$('#upcheck0 ul').eq(1).append('<li class="list-group-item">'+response.shgmname+'</li>');
						$('#upcheck0 ul').eq(1).append('<li class="list-group-item"><div class="imgwrapper">'+
							'<img src="/EA101G6/shgm/displayimg?shgmno='+response.shgmno+'"></div></li>');
						$('#upcheck0 ul').eq(1).append('<li class="list-group-item">'+response.price+'</li>');
						$('#upcheck0 ul').eq(1).append('<li class="list-group-item">上架審核中</li>');
						$('#upcheck0 ul:eq(1) li:eq(3)').append('<br>');
						$('#upcheck0 ul:eq(1) li:eq(3)').append('<button id="'+response.shgmno+'" value="0" type="button" class="btn btn-primary upcheck">自行下架</button>');
					}
				},
				error: function(result) {
					alert("目前不允許此操作");
				}
			});
		});
		
		/*出貨、送達狀態改變*/
		$(".container").on("click",".boxstatus",function(){
			var $shgmno = $(this).closest("button")[0].id;
			var $value = $(this).closest("button")[0].value;
			console.log($shgmno);
			console.log($value);
			$(this).closest("ul").fadeOut(function(){
				$(this).closest("ul")[0].remove();
			});
			
			$.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/front-end/shgm/shgm.do?action=statusUpdate",
				data: {"shgmno":$shgmno,"boxstatus":$value},
				dataType: "json",
				cache: false,
				success: function(response){
					jsondata = JSON.stringify(response);
					webSocket.send(jsondata);
					if(response.boxstatus == 1){
						$("#boxstatus1 ul:eq(0)").after('<ul class="list-group list-group-horizontal six-li"></ul>');
						$("#boxstatus1 ul:eq(1)").append('<li class="list-group-item">'+response.shgmname+'</li>');
						$("#boxstatus1 ul:eq(1)").append('<li class="list-group-item"><div class="imgwrapper">'+
							'<img src="/EA101G6/shgm/displayimg?shgmno='+response.shgmno+'"></div></li>');
						$("#boxstatus1 ul:eq(1)").append('<li class="list-group-item">'+response.takernm+'</li>');
						$("#boxstatus1 ul:eq(1)").append('<li class="list-group-item">'+response.takerph+'</li>');
						$("#boxstatus1 ul:eq(1)").append('<li class="list-group-item">'+response.address+'</li>');
						$("#boxstatus1 ul:eq(1)").append('<li class="list-group-item">出貨中</li>');
						$("#boxstatus1 ul:eq(1) li:eq(5)").append('<br>');
						$("#boxstatus1 ul:eq(1) li:eq(5)").append('<button id="'+response.shgmno+'" value="1" type="button" class="btn btn-primary boxstatus">送達商品</button>');
					} else if(response.boxstatus == 2){
						$("#boxstatus2 ul:eq(0)").after('<ul class="list-group list-group-horizontal six-li"></ul>');
						$("#boxstatus2 ul:eq(1)").append('<li class="list-group-item">'+response.shgmname+'</li>');
						$("#boxstatus2 ul:eq(1)").append('<li class="list-group-item"><div class="imgwrapper">'+
							'<img src="/EA101G6/shgm/displayimg?shgmno='+response.shgmno+'"></div></li>');
						$("#boxstatus2 ul:eq(1)").append('<li class="list-group-item">'+response.takernm+'</li>');
						$("#boxstatus2 ul:eq(1)").append('<li class="list-group-item">'+response.takerph+'</li>');
						$("#boxstatus2 ul:eq(1)").append('<li class="list-group-item">'+response.address+'</li>');
						$("#boxstatus2 ul:eq(1)").append('<li class="list-group-item">等待買家收貨中</li>');
					}
				},
				error:function(result){
					alert("目前不允許此操作");
				}
			});
		});
		
		/*回收被取消的商品，變成待上架狀態*/
		$(".container").on("click",".status",function(){
			var $shgmno = $(this).closest("button")[0].id;
			$(this).closest("ul").fadeOut(function(){
				$(this).closest("ul")[0].remove();
			});
			
			$.ajax({
				type: "POST",
				url: "<%=request.getContextPath()%>/front-end/shgm/shgm.do?action=statusUpdate",
				data: {"shgmno":$shgmno,"status":3},
				dataType: "json",
				cache: false,
				success: function(response){
					$('#upcheck0 ul').eq(0).after('<ul class="list-group list-group-horizontal four-li"></ul>');
					$('#upcheck0 ul').eq(1).append('<li class="list-group-item">'+response.shgmname+'</li>');
					$('#upcheck0 ul').eq(1).append('<li class="list-group-item"><div class="imgwrapper">'+
						'<img src="/EA101G6/shgm/displayimg?shgmno='+response.shgmno+'"></div></li>');
					$('#upcheck0 ul').eq(1).append('<li class="list-group-item">'+response.price+'</li>');
					$('#upcheck0 ul').eq(1).append('<li class="list-group-item">上架審核中</li>');
					$('#upcheck0 ul:eq(1) li:eq(3)').append('<br>');
					$('#upcheck0 ul:eq(1) li:eq(3)').append('<button id="'+response.shgmno+'" value="0" type="button" class="btn btn-primary upcheck">自行下架</button>');
				},
				error: function(result){
					alert("目前不允許此操作");
				}
			});
		});
	});
</script>
	<script src="js/jquery-3.3.1.min.js"></script>
	<!-- 看起來沒屁用 -->
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<!-- 重要廣告界面 -->
	<script src="js/owl.carousel.min.js"></script>
	<!-- 看起來沒屁用 -->
	<script src="js/jquery.sticky.js"></script>
	<script src="js/jquery.waypoints.min.js"></script>
	<script src="js/jquery.animateNumber.min.js"></script>
	<script src="js/jquery.fancybox.min.js"></script>


	<!-- 上介面連結動畫 -->
	<script src="js/jquery.easing.1.3.js"></script>

	<!-- 重要廣告界面 -->
	<script src="js/aos.js"></script>

	<script src="js/main.js"></script>
</body>
</html>