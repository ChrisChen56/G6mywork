<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shgm.model.*"%>
<%@ page import="java.util.*"%>

<%
	byte[] img = (byte[]) session.getAttribute("img");
	ShgmVO shgmsell = (ShgmVO) request.getAttribute("shgmsell");
	String imagefailed = (String) request.getAttribute("imagefailed");
%>
${session.scope }
<!doctype html>
<html lang="en">
<head>
<title>sell_page</title>
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

div.top-info {
	margin: 0 auto;
	border: green 1px solid;
}

.breadcrumb button {
	margin: 0;
}

#imgzoom{
	width: 540px;
	height: 400px;
	display: flex;
    flex-flow: row wrap;
    align-content: flex-end;
    text-align:center;
}

.shgm-info-left img {
	margin: 0;
	height: 320px;
	width: 480px;
	object-fit: contain;
}

.alert {
	color: #FF4500;
}

.shgm-info-right {
	display: table-cell;
	vertical-align: left;
	margin: 3% 0;
	padding-top: 5%;
	text-align: left;
}

.inputtext {
	width:290px;
	margin: 0 auto;
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
	margin: 0 auto;
	text-align: center;
}

.shgm-info-middle {
    margin: 2%;
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
						<c:when test="${member.mbrname != ''}">
						<span class="d-md-inline-block text-white">歡迎你！${member.mbrname}</span>
						</c:when>
						<c:otherwise>
						<a href="#" class="text-white"><span class="d-md-inline-block"><img
								class="icon" src="images/User-icon.png">會員登入</span></a> 
						<a href="#" class="text-white"><span class="d-md-inline-block"><img
								class="icon" src="images/man-icon.png">店家登入</span></a>
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
			<nav aria-label="breadcrumb" calss="breadcrumb-nav">
				<ol class="breadcrumb d-flex">
					<li class="breadcrumb-item"><a href="#">首頁</a></li>
					<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/shgm/mainPage.jsp">市集</a></li>
					<li class="breadcrumb-item active" aria-current="page">我要上架</li>
				</ol>
			</nav>
		</div>
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">New message</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<form>
							<div class="form-group">
								<label for="recipient-name" class="col-form-label">Recipient:</label>
								<input type="text" class="form-control" id="recipient-name">
							</div>
							<div class="form-group">
								<label for="message-text" class="col-form-label">Message:</label>
								<textarea class="form-control" id="message-text"></textarea>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary">Send
							message</button>
					</div>
				</div>
			</div>
		</div>
		<div class="shgm-info-allarea">
			<div class="shgm-info-toparea container">
				<form method="post" action="<%=request.getContextPath()%>/front-end/shgm/shgm.do" enctype="multipart/form-data">
					<div id="imgzoom" class="shgm-info-left col-6 rounded float-left">
						<span class="alert">${errormap.get(4)}</span>
						<label for="imgfile">
							<img name="imgtag" id="blah" alt="Click here to upload!" class="img-thumbnail rounded float-left" src="data:image/png;base64,${imagefailed}"/>
						</label>
						<div class="uploadwrapper">
							<input type="file" name="img" id="imgfile" onchange="document.getElementById('blah').src = window.URL.createObjectURL(this.files[0])" accept=".png, .jpg, .jpeg .gif" value="${img}"/>
						</div>
						<br>
					</div>
					<div class="shgm-info-right col-6 d-flex justify-content-center">
						<div
							class="shgm-info-right-inner d-flex align-items-center flex-column bd-highlight mb-3">
							<div class="form-group p-2 bd-highlight">
								<label for="shgmname">輸入桌遊名稱</label> <span class="alert">${errormap.get(1)}</span><input name="shgmname"
									class="form-control inputtext" id="shgmname" rows="3" value=${(shgmsell != null)? shgmsell.shgmname:""}>
							</div>
							<div class="form-group p-2 bd-highlight">
								<label for="price">輸入您欲販售之價格</label> <span class="alert">${errormap.get(2)}</span><input name="price"
									class="form-control inputtext" id="price" rows="3" value=${(shgmsell != null)? shgmsell.price:""}>
							</div>
							<div class="button-wrapper">
								<button type="submit" class="btn btn-primary">送出</button>
								<a href="<%=request.getContextPath()%>/front-end/shgm/mainPage.jsp" class="btn btn-primary">取消</a>
							</div>
						</div>
					</div>
					<br> <br> <br>
					<div class="shgm-info-middle">
						輸入此桌遊的詳情<span class="alert">${errormap.get(3)}</span>
						<div class="card">
							<textarea name="intro">${(shgmsell != null)? shgmsell.intro:""}</textarea>
						</div>
						${errormap.get(5)}
					</div>
					<input type="hidden" name="sellerno" value="${member.mbrno}">
					<input type="hidden" name="action" value="sellshgm">
				</form>
				<br>
			</div>
		</div>
	</div>


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