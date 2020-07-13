<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.shgm.model.*"%>
<%@ page import="com.mbrpf.model.*"%>
<%@ page import="java.util.*"%>
<%
	ShgmService shgmsvc = new ShgmService();
	Set<ShgmVO> set = shgmsvc.getAllForMain();
	pageContext.setAttribute("shgmset", set);
%>
<!doctype html>
<html lang="en">
<head>
<title>main_page</title>
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

div.card-body{
	height:127px;
}

.top-info-wrapper {
	position: relative;
	text-align: center;
	margin-top: 3%;
}

.breadcrumb-nav {
	background-color: #EEEEEE;
	width:100%;
	float:right;
}
.breadcrumb{
	margin-bottom:0;
}
.awrapper {
	display: block;
	text-align: right;
	width:90%;
}
@media (max-width: 1200px) {
	.awrapper {
		width:80%;
	}
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
	margin: auto;
	text-align:center;
}

#word{
	margin-top:1%;
}
.card-deck {
	margin: 0 auto;
}

.card {
	width: 228px;
	float: left;
	margin: 3% 5%;
}

.card:hover {
	cursor: pointer;
	box-shadow: 0 0 11px rgba(33, 33, 33, .2);
}

.card img {
	width: 225px;
	height: 240px;
	object-fit: contain;
}

.pageselect-area-wrapper {
	text-align: center;
}

div.pageselect-area {
	display: flex;
	justify-content: center;
}

.lefta{
		float: right;
		margin-left:2%;
	}
	#whichpage{
		text-align:center;
		width: 40px;
		margin:0 1%;
	}
	.right-area{
		margin-bottom:1%;
		width:48%;
		float:left;
	}
	.left-area{
		margin-bottom:1%;
		width:48%;
	}

.pagination {
	margin-top: -5%;
	display: flex;
	justify-content: center;
}
footer{
	padding: 0.5% 0;
	margin:0 auto;
	background-color: gray;
	text-align: center;
	color: white;
	font-size: 100%;
	width: 100%;
	height: 150px;
}
.wrap{
	margin:auto;
	width:80%;
}
.footerdiv{
	display:inline-block;
}
.footer-left{
	border:1px red solid;
	width:33%;
	float:left;
}
.footer-mid{
	border:1px blue solid;
	width:33%;
	float:left;
}
.footer-right{
	border:1px green solid;
	width:33%;
	float:left;
}
</style>
<body data-offset="300" background="images/bgimage3.jpg">

<%@ include file="/front-end/shgm/front-end-nav.jsp"%>

	<div class="main-area container col-10 align-self-center">
		<div class="top-info-wrapper">
			<nav aria-label="breadcrumb" class="breadcrumb-nav">
				<ol class="breadcrumb d-flex">
					<li class="breadcrumb-item"><a href="#">首頁</a></li>
					<c:choose>
						<c:when test="${searchResult == null}">
							<li class="breadcrumb-item active" aria-current="page">市集</li>
						</c:when>
						<c:otherwise>
							<li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/shgm/mainPage.jsp">市集</a></li>
						</c:otherwise>
					</c:choose>
					<li class="awrapper">
					<a id="myshgm" class="btn btn-primary" href="<%=request.getContextPath()%>/front-end/shgm/myShgm.jsp" role="button">我的市集商品</a>
					<a id="seller" class="btn btn-primary" href="<%=request.getContextPath()%>/front-end/shgm/sellerPage.jsp" role="button">賣家專區</a></li>
				</ol>
			</nav>
		</div>
		
		<%@ include file="page1.file" %>
		 
		<div class="shgm-area-wrapper">
			<div class="shgm-area ">
				<form method="post" action="<%=request.getContextPath()%>/front-end/shgm/shgm.do">
					<input id="word" type="text" name="word" value="${(searchResult == null)? '':param.word}"/>
					<input id="findshgm"  class="btn btn-primary" type="submit" value="找桌遊"/>
					<input type="hidden" name="action" value="search"/>
				</form>
				<div class="card-deck">
					<c:forEach var="shgmvo" items="${(searchResult == null)? shgmset:searchResult}"  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<div class="mb-4">
							<a target="_self" href="<%=request.getContextPath()%>/front-end/shgm/shgm.do?action=getOneForMoreInfo&shgmno=${shgmvo.shgmno}">
								<div class="card">
									<img
										src="<%=request.getContextPath()%>/shgm/displayimg?shgmno=${shgmvo.shgmno}"
										class="card-img-top" alt="Sorry! there's no image...">
									<div class="card-body">
										<h5 class="card-title">${shgmvo.shgmname}</h5>
										<p class="card-text">$${shgmvo.price}</p>
									</div>
								</div>
							</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<c:if test="${(searchResult == null)? true:(setsize > 12)? true:false}">
			<%@ include file="page2.file" %>
		</c:if>
		<c:if test="${setsize == 0}">
		<div style="margin:20% 0; width:100%; text-align:center;">很抱歉！並沒有符合的搜尋結果</div>
		</c:if>
	</div>
	<input type="hidden" id="member" value="${member.mbrname}">
	
	<%@ include file="/front-end/shgm/alert-area.jsp"%>
	
	<footer class="top-bar">
		<div class="wrap">
			<div class="footer-left footerdiv">
			asdfadsf
			</div>
			<div class="footer-mid footerdiv">
			asdfasdfa
			</div>
			<div class="footer-right footerdiv">
			asdfasdfdsf	
			</div>
		</div>
	</footer>
	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jsForShgm/ajaxForMbrmsgs.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jsForShgm/wsForShgm.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jsForShgm/jsForAlert-area.js"></script>
	<script>
	$(document).ready(function(){
		$("#findshgm").click(function(){
			if($("#word").val().trim() === ''){
				event.preventDefault();
			}
		});
		
		$("#myshgm,#seller").click(function(){
			if($('#member').val() === ''){
				alert('您未登入');
				window.location.href = "<%= request.getContextPath()%>/front-end/shgm/simpleLogin.jsp";
				return false;
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