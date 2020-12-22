<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
	<title>BSong</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=request.getContextPath()%>/templates/public/css/style.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/templates/public/css/coin-slider.css" />
	<link href="<%=request.getContextPath() %>/templates/public/fonts/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/templates/public/js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/templates/public/js/script.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/templates/public/js/coin-slider.min.js"></script>
	<script src="<%=request.getContextPath() %>/templates/admin/assets/ckeditor/ckeditor.js"></script>
</head>
<body>
	<div class="main">
		<div class="header">
			<div class="header_resize">
				<div class="logo">
					<h1>
						<a href="<%=request.getContextPath() %>/index">BSong <small></small></a>
					</h1>
				</div>
				<div class="menu_nav">
					<ul>
						<li class="active"><a href="<%=request.getContextPath() %>/index"><span>Trang chủ</span></a>
						<li><a href="<%=request.getContextPath() %>/contact"><span>Liên hệ</span></a></li>
						<%
							if (session.getAttribute("publiclogin") != null) {
						%>
						<li><a href="<%=request.getContextPath() %>/song/like"><span>Yêu thích</span></a></li>
						<li><a href="<%=request.getContextPath() %>/logout"><span>Đăng xuất</span></a></li>
						<%
							} else {
						%>
						<li><a href="<%=request.getContextPath() %>/login"><span>Đăng nhập</span></a></li>
						<%
							}
						%>
					</ul>
				</div>
				<div class="clr"></div>
				<div class="slider">
					<div id="coin-slider">
						<a href="#"><img src="<%=request.getContextPath()%>/templates/public/images/slide1.jpg" width="935" height="307" alt="" /></a>
						<a href="#"><img src="<%=request.getContextPath()%>/templates/public/images/slide2.jpg" width="935" height="307" alt="" /></a>
						<a href="#"><img src="<%=request.getContextPath()%>/templates/public/images/slide3.jpg" width="935" height="307" alt="" /></a>
					</div>
					<div class="clr"></div>
				</div>
				<div class="clr"></div>
			</div>
		</div>
		<div class="content">