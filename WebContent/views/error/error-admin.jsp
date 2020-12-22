<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/templates/admin/inc/header.jsp"%>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="table-responsive">
                        	<%
                        		if (request.getParameter("err") != null) {
                        			int err = Integer.parseInt(request.getParameter("err"));
                        			if (err == 1) {
                        	%>
							<div class="alert alert-danger" role="alert">
							  	<span style="color: red; font-style: italic">ERROR 404 - Không tìm thấy trang!</span>
							</div>
							<%
                        			} else if (err == 2) {
							%>
							<div class="alert alert-danger" role="alert">
							  	<span style="color: red; font-style: italic">ERROR Không có dữ liệu - Số trang không tồn tại!</span>
							</div>
							<%
                        			}
                        		}
							%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
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
<%@ include file="/templates/admin/inc/footer.jsp"%>