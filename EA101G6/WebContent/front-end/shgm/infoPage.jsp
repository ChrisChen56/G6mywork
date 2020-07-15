<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shgm.model.*"%>
<%@ page import="com.mbrpf.model.*"%>
<%@ page import="java.util.*"%>
<%
	List<ShgmVO> list = (List<ShgmVO>) request.getAttribute("randlist");
	pageContext.setAttribute("list", list);
%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>${infoshgm.shgmname}</title>
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
<link rel="stylesheet" href="css/cssForShgm/alert-area.css">

<style>
body {
	background-color: #EEEEEE;
	background-size: repeat;
}

.icon {
	width: 20px;
	height: 20px;
}

.modal-footer{
	height: 80px;
}

div.main-area {
	position: relative;
	display: block;
	border: black 1px solid;
	background-color: white;
	max-height: 100%;
	margin: 2% auto;
}

.top-info-wrapper {
	text-align: center;
	margin-top: 3%;
}

.breadcrumb-nav {
	background-color: #EEEEEE;
}

.rpdiv {
	color: #FF4500;
	margin-right: 5%;
}

.awrapper {
	display: block;
	text-align: right;
	width: 85%;
}
@media (max-width: 1496px) {
	.awrapper {
		width:70%;
	}
}
@media (max-width: 936px) {
	.awrapper {
		width:45%;
	}
}

div.top-info {
	margin: 0 auto;
	border: green 1px solid;
}

.breadcrumb button {
	margin: 0;
}

.shgm-info-left img {
	margin: 0;
	height: 320px;
	width: 480px;
	object-fit: contain;
}

.shgm-info-right {
	display: table-cell;
	vertical-align: middle;
	margin: 3% 0;
	padding-top: 2%;
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

a img:hover{
	background-color:orange;
}
.carousel-item img{
	width: 250px;
	height: 250px;
	object-fit: contain;
}

.slide {
	min-height: 300px;
	background-size: cover;
}
</style>
</head>

<body data-offset="300" background="images/bgimage3.jpg">

	<%@ include file="/front-end/shgm/front-end-nav.jsp"%>

	<div class="main-area container col-10 align-self-center">
		<div class="top-info-wrapper">
			<nav aria-label="breadcrumb" class="breadcrumb-nav">
				<ol class="breadcrumb d-flex">
					<li class="breadcrumb-item"><a href="#">首頁</a></li>
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath()%>/front-end/shgm/mainPage.jsp">市集</a></li>
					<li class="breadcrumb-item active" aria-current="page">商品頁面</li>
					<li class="awrapper"><span class="rpdiv">${errormap.get("rp")}</span><button id="rp" type="button"
							class="btn btn-primary ml-auto" data-toggle="modal"
							data-target="#exampleModal" data-whatever="@mdo">檢舉</button></li>
				</ol>
			</nav>
		</div>
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">檢舉此商品</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<form method="post" action="<%=request.getContextPath()%>/front-end/shgm/shgmrp.do">
						<div class="modal-body">
							<div class="form-group">
								<label for="message-text" class="col-form-label">檢舉內容:</label>
								<textarea name="detail" class="form-control" id="message-text"></textarea>
							</div>

						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">確定</button>
							<button type="button" class="btn btn-primary"
								data-dismiss="modal">取消</button>
						</div>
						<input type="hidden" id="shgmno" name="shgmno" value="${infoshgm.shgmno}">
						<input type="hidden" name="suiterno" value="${mbrpfVO.mbrno}">
						<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
						<input type="hidden" name="action" value="insertrp">
					</form>
				</div>
			</div>
		</div>
		<div class="shgm-info-allarea">
			<div class="shgm-info-toparea container">
				<div id="imgzoom" class="shgm-info-left col-6 ">
					<img
						src="<%=request.getContextPath() %>/shgm/displayimg?shgmno=${infoshgm.shgmno}"
						alt="..." class="img-thumbnail rounded float-left">
				</div>
				<div class="shgm-info-right col-6 d-flex justify-content-center">
					<div
						class="shgm-info-right-inner d-flex align-items-center flex-column bd-highlight mb-3" style="text-align:center;">
						<div class="p-2 bd-highlight">
							名稱
							<h1 style="text-align:left;">${infoshgm.shgmname}</h1>
						</div>
						<div class="p-2 bd-highlight">
							售價
							<h1 id="price" style="text-align:left;">${infoshgm.price}</h1>
						</div>
						<c:choose>
							<c:when test="${infoshgm.paystatus == 1}">
							<a id="sold" class="btn btn-primary" role="button" style="margin-top:20%;width:240px;">本商品已售出</a>
							</c:when>
							<c:otherwise>
							<form method="post" action="<%=request.getContextPath()%>/front-end/shgm/shgm.do">
								<button id="buythis" type="submit" class="btn btn-primary" style="margin-top:20%;width:80px;">購買</button>
								<input type="hidden" name="shgmno" value="${infoshgm.shgmno}"/>
								<input type="hidden" name="action" value="getOneForMoreInfo"/>
								<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"/>
							</form>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<br>
				<div><a href="<%=request.getContextPath()%>/front-end/shgm/shgm.do?action=toPrsnlMkt&sellerno=${sellerinfo.mbrno}">${sellerinfo.nickname}的個人市集</a></div>
				<br>
				<div class="shgm-info-middle">
					簡介
					<div class="card">
						<div class="card-body">${infoshgm.intro}</div>
					</div>
				</div>
				<br>
				<div class="shgm-info-bottom">
					你可能也感興趣
					<div class="container">
						<div class="row blog">
							<div class="col-md-12">
								<div id="blogCarousel" class="carousel slide"
									data-ride="carousel" style="height:90px;">

									<ol class="carousel-indicators">
										<li data-target="#blogCarousel" data-slide-to="0"
											class="active"></li>
										<li data-target="#blogCarousel" data-slide-to="1"></li>
									</ol>

									<!-- Carousel items -->
									<div class="carousel-inner">
										<div class="carousel-item active">
											<div class="row">
											<c:forEach var="i" begin="0" end="3">
												<div class="col-md-3 ">
													<a
														href="<%=request.getContextPath()%>/front-end/shgm/shgm.do?action=getOneForMoreInfo&shgmno=${list.get(i).shgmno}">
														<img src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${list.get(i).shgmno}" alt="Image"
														style="max-width: 100%;">
													</a>
												</div>
											</c:forEach>
											</div>
										</div>
										<div class="carousel-item">
											<div class="row">
											<c:forEach var="i" begin="4" end="7">
												<div class="col-md-3 ">
													<a
														href="<%=request.getContextPath()%>/front-end/shgm/shgm.do?action=getOneForMoreInfo&shgmno=${list.get(i).shgmno}">
														<img src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${list.get(i).shgmno}" alt="Image"
														style="max-width: 100%;">
													</a>
												</div>
											</c:forEach>
											</div>											
										</div>
										<!--.item-->
									</div>
									<!--.carousel-inner-->
								</div>
								<!--.Carousel-->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="points" value="${mbrpfVO.points}">
		<input type="hidden" id="buysuccess" value="${buysuccess}">
		<input type="hidden" id="rpsuccess" value="${rpsuccess}">
	</div>
	
	<%@ include file="/front-end/shgm/alert-area.jsp"%>
	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jsForShgm/ajaxForMbrmsgs.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jsForShgm/wsForShgm.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jsForShgm/jsForAlert-area.js"></script>
	
	<script>
	$(document).ready(function(){
		var $buysuccess = $("#buysuccess").val();
		if($buysuccess === "success"){
			var $shgmno = $("#shgmno").val();
			Swal.fire({
				  icon: 'success',
				  title: '您已購買成功！',
				  showConfirmButton: false,
				  timer: 1500
				})
			var successObj = {
				"shgmno":$shgmno,
				"paystatus":1
			}
			var successJson = JSON.stringify(successObj);
			webSocket.send(successJson);
		}
		if($("#rpsuccess").val() == "success"){
			Swal.fire({
				  icon: 'success',
				  title: '您的檢舉已成功送出！',
				  showConfirmButton: false,
				  timer: 1500
				})
		}
		var $mbrname = $("#mbrname").text().substr(4);
		$("#buythis").click(function(){
			if($mbrname == ""){
				Swal.fire({
					  title: '您尚未登入',
					  icon: 'info',
					  html:'請先登入再執行動作',
					  showCancelButton: true,
					  focusConfirm: false,
					  confirmButtonText:
					    '<a style="color:white;" href="<%=request.getContextPath()%>/front-end/login.jsp" class="fa">前往登入</a>',
					  cancelButtonText:
					    '<span class="fa fa-thumbs-down">繼續逛逛</span>',
					})
				event.preventDefault();
			} else{
				var $price = parseInt($("#price").text());
				var $points = $("#points").val();
				if ( $price > $points ){
					Swal.fire({
						  icon: 'error',
						  title: '您的餘額不足',
						  text: '請進行儲值再繼續購物',
						  footer: '<a href="<%=request.getContextPath()%>/front-end/tfcord/buyPoint.jsp">點數儲值&nbsp&nbsp</a><span>尚餘'+$points+'點</span>'
						});
					event.preventDefault();
				}
			}
		});
		$("#rp").click(function(){
			if($mbrname == ""){
				Swal.fire({
					  title: '您尚未登入',
					  icon: 'info',
					  html:'請先登入再執行動作',
					  showCancelButton: true,
					  focusConfirm: false,
					  confirmButtonText:
					    '<a style="color:white;" href="<%=request.getContextPath()%>/front-end/login.jsp" class="fa">前往登入</a>',
					  cancelButtonText:
					    '<span class="fa fa-thumbs-down">繼續逛逛</span>',
					})
				event.stopPropagation();
			}
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