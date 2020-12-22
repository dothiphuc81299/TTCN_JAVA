<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/templates/public/inc/header.jsp"%>
	<div style="text-align: center">
		<h1>Lỗi không có dữ liệu</h1>
		<%
			if (request.getParameter("err") != null) {
				int err = Integer.parseInt(request.getParameter("err"));
				if (err == 1) {
					out.print("<h2>Lỗi đường dẫn!</h2>");
				} else if (err == 2) {
					out.print("<h2>ID danh mục không tồn tại!</h2>");
				} else if (err == 3) {
					out.print("<h2>Số trang không tồn tại!</h2>");
				} else if (err == 4) {
					out.print("<h2>ID bài hát không tồn tại!</h2>");
				}
			}
		%>
	</div>
<%@ include file="/templates/public/inc/footer.jsp"%>