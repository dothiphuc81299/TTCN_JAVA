<%@page import="models.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%
	if (session.getAttribute("login") != null) {
		User objUser = (User) session.getAttribute("login");
%>
<nav class="navbar-default navbar-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav" id="main-menu">
            <li class="text-center">
                <img src="<%=request.getContextPath() %>/templates/admin/assets/img/find_user.png" class="user-image img-responsive" />
            </li>
            <li>
                <a id="index" href="<%=request.getContextPath() %>/admin/index"><i class="fa fa-dashboard fa-3x"></i> Trang chủ</a>
            </li>
            <li>
                <a id="category" href="<%=request.getContextPath() %>/admin/cat/index"><i class="fa fa-list fa-3x"></i> Quản lý danh mục</a>
            </li>
            <li>
                <a id="song" href="<%=request.getContextPath() %>/admin/song/index"><i class="fa fa-music fa-3x"></i> Quản lý bài hát</a>
            </li>
            <li>
                <a id="user" href="<%=request.getContextPath() %>/admin/user/index"><i class="fa fa-user fa-3x"></i> Quản lý người dùng</a>
            </li>
            <li>
                <a id="comment" href="<%=request.getContextPath() %>/admin/comment/index"><i class="fa fa-comment fa-3x"></i> Quản lý bình luận</a>
            </li>
            <%
            	if (objUser.getRole() != 0) {
            %>
            <li>
                <a id="contact" href="<%=request.getContextPath() %>/admin/contact/index"><i class="fa fa-envelope fa-3x"></i> Quản lý liên hệ</a>
            </li>
            <li>
                <a id="forbiddenword" href="<%=request.getContextPath() %>/admin/forbiddenword/index"><i class="fa fa-ban fa-3x"></i> Quản lý từ cấm</a>
            </li>
            <%
            	}
            %>
        </ul>
    </div>
</nav>
<%
	}
%>
<!-- /. NAV SIDE  -->