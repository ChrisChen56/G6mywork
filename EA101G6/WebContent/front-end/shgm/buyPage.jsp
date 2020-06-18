<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shgm.model.*"%>
<%
	ShgmVO shgmvo = (ShgmVO) session.getAttribute("shgmvo");
%>
<!doctype html>
<html lang="en">
<head>
<title>buy_page</title>
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

.icon {
	width: 20px;
	height: 20px;
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

.awrapper {
	width: 150px;
	display: inline;
	text-align: right;
	margin-left: 70%;
}

div.top-info {
	margin: 0 auto;
	border: green 1px solid;
}

.breadcrumb button {
	margin: 0;
}

.shgm-info-left img {
	height: 320px;
	width: 480px;
	object-fit: contain;
}

.shgm-info-left {
	width: 45%;
	display: table-cell;
	vertical-align: left;
	margin: 3% 0;
	padding-top: 1.6%;
	text-align: left;
}

.shgm-info-right {
	width: 45%;
	display: table-cell;
	vertical-align: middle;
	margin: 3% 0;
	padding-top: 2%;
	text-align: left;
}

.btn {
	margin: 10% auto;
	background-color: white;
}

.btn:hover {
	background-color: white;
	color: #FF8C00; /*ffa216*/
	box-shadow: 0 0 11px rgba(33, 33, 33, .2);
}

.button-wrapper {
	text-align: center;
}
.alert{
	color: #FF4500;
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
						<a href="#" class="text-white"><span class="d-md-inline-block"><img
								class="icon" src="images/User-icon.png">會員登入</span></a> <a href="#"
							class="text-white"><span class="d-md-inline-block"><img
								class="icon" src="images/man-icon.png">店家登入</span></a>
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
						class="text-primary">Gaming on board</span></a>
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
			<nav aria-label="breadcrumb" calss="breadcrumb-nav">
				<ol class="breadcrumb d-flex">
					<li class="breadcrumb-item"><a href="#">首頁</a></li>
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath()%>/front-end/shgm/mainPage.jsp">市集</a></li>
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath()%>/front-end/shgm/infoPage.jsp?shgmno=${shgmvo.shgmno}">商品頁面</a></li>
					<li class="breadcrumb-item active" aria-current="page">購買頁面</li>
					<li class="awrapper"><button type="button"
							class="btn btn-primary" data-toggle="modal"
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
					<form method="post" action="<%=request.getContextPath()%>/front-end/shgm/shgmrp.do?action=insertrp">
						<input type="hidden" name="shgmno" value="${shgmvo.shgmno}">
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
					</form>
				</div>
			</div>
		</div>
		<div class="shgm-info-allarea">
			<div class="shgm-info-toparea container">
				<div id="imgzoom" class="shgm-info-left col-6 ">
					<div
						class="d-flex align-items-left flex-column bd-highlight mb-3">
						<img
							src="<%=request.getContextPath() %>/back-end/shgm/displayimg?shgmno=${shgmvo.shgmno}"
							alt="..." class="img-thumbnail rounded float-left"> <br>
						<div class="p-2 bd-highlight">
							名稱
							<h1>${shgmvo.shgmname}</h1>
						</div>
						<div class="p-2 bd-highlight">
							售價
							<h1>${shgmvo.price}</h1>
						</div>
					</div>	
				</div>
				<div class="shgm-info-right col-6  justify-content-center">
					<div
						class="shgm-info-right-inner  align-items-center flex-column bd-highlight mb-3">
						<form method="post"
							action="<%=request.getContextPath()%>/front-end/shgm/shgm.do?action=dealingshgm">
							<input type="hidden" name="shgmno" value="${shgmvo.shgmno}">
							<div class="form-group">
								<label for="take">取貨方式</label><span class="alert">${errormap.get(1)}</span>
								<input type="text" class="form-control" id="take" 
									name="take" value="<%=(shgmvo.getTake() == null)? "":shgmvo.getTake()%>">
							</div>
							<br>
							<div class="form-group">
								<label for="takernm">取貨人姓名</label><span class="alert">${errormap.get(2)}</span>
								<input type="text" class="form-control" id="takernm"
									name="takernm" value="<%=(shgmvo.getTakernm() == null)? "":shgmvo.getTakernm()%>">
							</div>
							<br>
							<div class="form-group">
								<label for="takerph">取貨人電話</label><span class="alert">${errormap.get(3)}</span>
								<input type="text" class="form-control" id="takerph"
									name="takerph" value="<%=(shgmvo.getTakerph() == null)? "":shgmvo.getTakerph()%>">
							</div>
							<br>
							<div class="form-group">
								<label for="address">取貨地址</label><span class="alert">${errormap.get(4)}</span>
								<input type="text" class="form-control" id="address"
									name="address" value="<%=(shgmvo.getAddress() == null)? "":shgmvo.getAddress()%>">
							</div>
							<input type="hidden" name="boxstatus" value="0">
							<input type="hidden" name="paystatus" value="1">
							<input type="hidden" name="status" value="1">
							<br>
							<div class="button-wrapper">
								<button type="submit" class="btn btn-primary">確定購買</button>
								<a
									href="<%=request.getContextPath()%>/front-end/shgm/mainPage.jsp"
									class="btn btn-primary">取消購買</a>
							</div>
						</form>
						<b><span class="alert">${errormap.get(5)}</span></b> <b>還有付款要處理
							這裡先以正常出貨狀態來跑(0未出貨1已付款1已下訂)</b>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="random-area"></div>
	</div>





	<script type="text/javascript">
		$('#exampleModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget) // Button that triggered the modal
			var recipient = button.data('whatever') // Extract info from data-* attributes
			// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
			// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
			var modal = $(this)
			modal.find('.modal-title').text('New message to ' + recipient)
			modal.find('.modal-body input').val(recipient)
		})
		$(function() {
			$('#imgzoom img').mouseenter(function() {
				var wValue = 1.5 * $(this).width();
				var hValue = 1.5 * $(this).height();
				$(this).animate({
					width : wValue,
					height : hValue,
					left : ("-"(0.5 * $(this).width()) / 2),
					top : ("-"(0.5 * $(this).height()) / 2)
				}, 1000);
			}).mouseleave(function() {
				$(this).animate({
					width : "100",
					height : "80",
					left : "0px",
					top : "0px"
				}, 1000);
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