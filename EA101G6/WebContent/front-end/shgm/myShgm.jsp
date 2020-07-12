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
	Set<ShgmVO> shgmset = shgmsvc.allForBuyer(member.getMbrno());
	pageContext.setAttribute("shgmset", shgmset);
	ShgmrpService shgmrpsvc = new ShgmrpService();
	pageContext.setAttribute("shgmrpsvc", shgmrpsvc);
%>

<html lang="en">
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<title>myShgame</title>
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
	width: 300px;
	display: inline;
	text-align: right;
	margin-left: 60%;
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
<body data-spy="scroll" data-target=".site-navbar-target"
	data-offset="300" background="images/bgimage3.jpg">



	<div class="site-wrap" id="home-section">

		<div class="site-mobile-menu site-navbar-target">
			<div class="site-mobile-menu-header">
				<div class="site-mobile-menu-close mt-3">
					<span class="icon-close2 js-menu-toggle"></span>
				</div>
			</div>
			<div class="site-mobile-menu-body"></div>
		</div>
	</div>

	<div class="top-bar">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<a href="#" class="text-white"><span class="d-md-inline-block"><img
							class="icon" src="images/add-icon.png">註冊</span></a>
					<div class="float-right">
						<c:choose>
							<c:when test="<%=member != null%>">
								<span class="d-md-inline-block text-white">歡迎你！${member.mbrname}</span>
								<input type="hidden" id="mbrno" value="${member.mbrno}"/>
							</c:when>
							<c:otherwise>
								<a href="#" class="text-white"><span
									class="d-md-inline-block"><img class="icon"
										src="images/User-icon.png">會員登入</span></a>
								<a href="#" class="text-white"><span
									class="d-md-inline-block"><img class="icon"
										src="images/man-icon.png">店家登入</span></a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>

	<header class="site-navbar js-sticky-header site-navbar-target"
		role="banner">

		<div class="container">
			<div class="row align-items-center position-relative">


				<div class="site-logo">
					<a href="index.html" class="text-black"><span
						class="text-primary">Gaming on Board</span></a>
				</div>

				<div class="col-12">
					<nav class="site-navigation text-right ml-auto " role="navigation">

						<ul
							class="site-menu main-menu js-clone-nav ml-auto d-none d-lg-block">
							<li><a href="" class="nav-link">首頁</a></li>

							<li class="has-children"><a href="" class="nav-link">會員專區</a>
								<ul class="dropdown arrow-top">
									<li><a href="#team-section" class="nav-link">Team</a></li>
									<li><a href="#pricing-section" class="nav-link">Pricing</a></li>
									<li><a href="#faq-section" class="nav-link">FAQ</a></li>
									<li class="has-children"><a href="#">More Links</a>
										<ul class="dropdown">
											<li><a href="#">Menu One</a></li>
											<li><a href="#">Menu Two</a></li>
											<li><a href="#">Menu Three</a></li>
										</ul></li>
								</ul></li>

							<li><a href="#mall" class="nav-link">商城</a></li>
							<li><a href="#shop" class="nav-link">市集</a></li>
							<li><a href="#play" class="nav-link">揪團區</a></li>
							<li><a href="#store" class="nav-link">店家列表</a></li>
							<li><a href="#forum" class="nav-link">討論區</a></li>
						</ul>
					</nav>
				</div>

				<div class="toggle-button d-inline-block d-lg-none">
					<a href="#" class="site-menu-toggle py-5 js-menu-toggle text-black"><span
						class="icon-menu h3"></span></a>
				</div>
			</div>
		</div>

	</header>

	<div class="main-area container col-10 align-self-center">
		<div class="top-info-wrapper">
			<nav aria-label="breadcrumb" class="breadcrumb-nav">
				<ol class="breadcrumb d-flex">
					<li class="breadcrumb-item"><a href="#">首頁</a></li>
					<li class="breadcrumb-item" aria-current="page"><a
						href="<%=request.getContextPath()%>/front-end/shgm/mainPage.jsp">市集</a></li>
					<li class="breadcrumb-item active" aria-current="page">我的市集商品</li>
				</ol>
			</nav>
		</div>
		<div class="shgm-area-wrapper">
			<div class="shgm-area ">
				<div class="row">
					<div class="col-2">
						<div class="list-group" id="list-tab" role="tablist">
							<a class="list-group-item list-group-item-action active"
								id="list-boxstatus0-list" data-toggle="list" href="#list-boxstatus0"
								role="tab" aria-controls="boxstatus0">待出貨</a>
							<a class="list-group-item list-group-item-action"
								id="list-boxstatus1-list" data-toggle="list" href="#list-boxstatus1"
								role="tab" aria-controls="boxstatus1">出貨中</a> 
							<a class="list-group-item list-group-item-action"
								id="list-boxstatus2-list" data-toggle="list" href="#list-boxstatus2" 
								role="tab" aria-controls="boxstatus2">待收貨</a>
							<a class="list-group-item list-group-item-action"
								id="list-status2-list" data-toggle="list" href="#list-status2"
								role="tab" aria-controls="status2">已完成</a>
							<a class="list-group-item list-group-item-action"
								id="list-status3-list" data-toggle="list" href="#list-status3"
								role="tab" aria-controls="status3">取消</a>
						</div>
					</div>
					<div class="col-10">
						<div class="tab-content" id="nav-tabContent">
						<div class="tab-pane fade active show" id="list-boxstatus0" role="tabpanel"
								aria-labelledby="list-boxstatus0-list">
								boxstatus0
								<ul class="list-group list-group-horizontal four-li">
									<li class="list-group-item firstlis">商品名稱</li>
									<li class="list-group-item firstlis">圖片</li>
									<li class="list-group-item firstlis">售價</li>
									<li class="list-group-item firstlis">商品狀態</li>
								</ul>
								<c:forEach var="shgmvo" items="${shgmset}">
									<c:if test="${shgmvo.upcheck == 1 && shgmvo.boxstatus == 0 && shgmvo.paystatus == 1 && shgmvo.status == 1}">
										<ul class="list-group list-group-horizontal four-li">
											<li class="list-group-item">${shgmvo.shgmname}</li>
											<li class="list-group-item"><div class="imgwrapper">
													<img src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}"></div></li>
											<li class="list-group-item">${shgmvo.price}</li>
											<li class="list-group-item">賣家尚未出貨<br>
												 <form method="post" action="<%=request.getContextPath()%>/front-end/shgm/shgm.do">
												 <input type="submit" class="btn btn-primary" value="修改取貨資訊"/>
												 <input type="hidden" name="action" value="getone_update"/>
												 <input type="hidden" name="shgmno" value="${shgmvo.shgmno}"/>
												 <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"/>
												 </form>
											<button id="${shgmvo.shgmno}" type="button"
													class="btn btn-primary status3" value="cancel">取消訂單</button></li>
										</ul>
									</c:if>
								</c:forEach>
							</div>
							<div class="tab-pane fade" id="list-boxstatus1" role="tabpanel"
								aria-labelledby="list-boxstatus1-list">
								boxstatus1
								<ul class="list-group list-group-horizontal four-li">
									<li class="list-group-item firstlis">商品名稱</li>
									<li class="list-group-item firstlis">圖片</li>
									<li class="list-group-item firstlis">售價</li>
									<li class="list-group-item firstlis">商品狀態</li>
								</ul>
								<c:forEach var="shgmvo" items="${shgmset}">
									<c:if test="${shgmvo.upcheck == 1 && shgmvo.boxstatus == 1 && shgmvo.paystatus == 1 && shgmvo.status == 1}">
										<ul class="list-group list-group-horizontal four-li">
											<li class="list-group-item">${shgmvo.shgmname}</li>
											<li class="list-group-item"><div class="imgwrapper">
													<img src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}"></div></li>
											<li class="list-group-item">${shgmvo.price}</li>
											<li class="list-group-item">商品配送中<br>
											<button id="${shgmvo.shgmno}" type="button"
													class="btn btn-primary status3" value="cancel">取消訂單</button></li>
										</ul>
									</c:if>
								</c:forEach>
							</div>
							<div class="tab-pane fade" id="list-boxstatus2" role="tabpanel"
								aria-labelledby="list-boxstatus2-list">
								boxstatus2
								<ul class="list-group list-group-horizontal four-li">
									<li class="list-group-item firstlis">商品名稱</li>
									<li class="list-group-item firstlis">圖片</li>
									<li class="list-group-item firstlis">售價</li>
									<li class="list-group-item firstlis">商品狀態</li>
								</ul>
								<c:forEach var="shgmvo" items="${shgmset}">
									<c:if test="${shgmvo.upcheck == 1 && shgmvo.boxstatus == 2 && shgmvo.paystatus == 1 && shgmvo.status == 1}">
										<ul class="list-group list-group-horizontal four-li">
											<li class="list-group-item">${shgmvo.shgmname}</li>
											<li class="list-group-item"><div class="imgwrapper">
													<img src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}"></div></li>
											<li class="list-group-item">${shgmvo.price}</li>
											<li class="list-group-item">商品已送達<br>
											<button id="${shgmvo.shgmno}" type="button"
													class="btn btn-primary boxstatus2">確認收貨</button>
											<button id="${shgmvo.shgmno}" type="button"
													class="btn btn-primary status3" value="cancel">取消訂單</button></li>
										</ul>
									</c:if>
								</c:forEach>
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
													<img src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}">
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
									<li class="list-group-item firstlis">商品狀態</li>
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
											<li class="list-group-item">已取消</li>
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
	<div class="pageselect-area-wrapper">
		<div class="pageselect-area ">
			<nav aria-label="Page navigation example">
				<ul class="pagination">
					<li class="page-item"><a class="page-link" href="#"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
							<span class="sr-only">Previous</span>
					</a></li>
					<li class="page-item"><a class="page-link" href="#">1</a></li>
					<li class="page-item"><a class="page-link" href="#">2</a></li>
					<li class="page-item"><a class="page-link" href="#">3</a></li>
					<li class="page-item"><a class="page-link" href="#"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
							class="sr-only">Next</span>
					</a></li>
				</ul>
			</nav>
		</div>
	</div>
	<input type="hidden" id="member" value="${member.mbrname}">
	<input type="hidden" id="success" value="${updateSuccess}">

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
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/wsForShgm.js"></script>
	<script>
	$(document).ready(function(){
		
		if($("#success").val() == "success"){
			Swal.fire({
				  icon: 'success',
				  title: '您已修改成功！',
				  showConfirmButton: false,
				  timer: 1500
				})
			$("#success").val('');
		}
		
		/*確定收貨購買完成*/
		$(".container").on("click",".boxstatus2",function(){
			var $shgmno = $(this).closest("button")[0].id;
			console.log($shgmno);
			$(this).closest("ul").fadeOut(function(){
				$(this).closest("ul")[0].remove();
			});
			
			$.ajax({
			    type: "POST",
			    url: '<%=request.getContextPath()%>/front-end/shgm/shgm.do?action=statusUpdate',
			    data: {"shgmno":$shgmno,"status":2},
			    dataType: "json",
			    cache: false,
			    success: function(response){
			    	$("#list-status2 ul:eq(0)").after('<ul class="list-group list-group-horizontal five-li"></ul>');
					$("#list-status2 ul:eq(1)").append('<li class="list-group-item">'+response.shgmname+'</li>');			    	
					$("#list-status2 ul:eq(1)").append('<li class="list-group-item"><div class="imgwrapper">'+
							'<img src="/EA101G6/shgm/displayimg?shgmno='+response.shgmno+'"></div></li>');			    	
					$("#list-status2 ul:eq(1)").append('<li class="list-group-item">'+response.price+'</li>');
					$("#list-status2 ul:eq(1)").append('<li class="list-group-item">'+response.uptime+'</li>');
					$("#list-status2 ul:eq(1)").append('<li class="list-group-item">'+response.soldtime+'</li>');
					//Gson 無法把將含有日期型態的json資料轉為物件，後面用不到日期所以直接賦予空值
			    	response.uptime = null;
			    	response.soldtime = null;
			    	jsondata = JSON.stringify(response);
					webSocket.send(jsondata);
			    },
			    error: function(result) {
                    console.log(result);
                }
			});
		});
		
		/*取消訂單*/
		$(".container").on("click",".status3",function(){
			var $shgmno = $(this).closest("button")[0].id;
			console.log($shgmno);
			$(this).closest("ul").fadeOut(function(){
				$(this).closest("ul")[0].remove();
			});
			
			$.ajax({
			    type: "POST",
			    url: '<%=request.getContextPath()%>/front-end/shgm/shgm.do?action=statusUpdate',
			    data: {"shgmno":$shgmno,"status":8},
			    dataType: "json",
			    cache: false,
			    success: function(response){
			    	jsondata = JSON.stringify(response);
					webSocket.send(jsondata);
			    	$("#list-status3 ul:eq(0)").after('<ul class="list-group list-group-horizontal five-li"></ul>');
					$("#list-status3 ul:eq(1)").append('<li class="list-group-item">'+response.shgmname+'</li>');			    	
					$("#list-status3 ul:eq(1)").append('<li class="list-group-item"><div class="imgwrapper">'+
							'<img src="/EA101G6/shgm/displayimg?shgmno='+response.shgmno+'"></div></li>');			    	
					$("#list-status3 ul:eq(1)").append('<li class="list-group-item">'+response.price+'</li>');
					$("#list-status3 ul:eq(1)").append('<li class="list-group-item">已取消</li>');
			    },
			    error: function(result) {
                    console.log(result);
                }
			});
		});
	});
	</script>

</body>
</html>