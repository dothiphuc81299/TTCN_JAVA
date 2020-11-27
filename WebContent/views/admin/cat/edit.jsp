<%@page import="models.Category"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Sửa danh mục</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Form Elements -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <form role="form" method="post" id="form" action="<%=request.getContextPath() %>/admin/cat/edit">
                                	<%
                                		if (request.getAttribute("err") != null) {
                                	%>
                                	<div class="alert alert-danger" role="alert">
                                		<span style="color: red; font-style: italic">${err}</span>
                                	</div>
                                	<%
                                		}
                                		if (request.getAttribute("cat") != null) {
                                			Category objCat = (Category) request.getAttribute("cat");
                                	%>
                                	<div class="form-group">
                                        <label for="catid">ID</label>
                                        <input type="text" id="catid" value="<%=objCat.getId() %>" name="catid" class="form-control" readonly="readonly" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Tên danh mục</label>
                                        <input type="text" id="name" value="<%=objCat.getName() %>" name="name" class="form-control" />
                                    </div>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Sửa</button>
                                    <%
                                		}
                                    %>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End Form Elements -->
            </div>
        </div>
        <!-- /. ROW  -->
    </div>
    <!-- /. PAGE INNER  -->
</div>
<script>
    document.getElementById("category").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>