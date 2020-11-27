
<%@page import="models.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Quản lý danh mục</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Advanced Tables -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="table-responsive">
                            <div class="row">
                            	<%
                            		User objUser = null;
                            		if (session.getAttribute("login") != null) {
                            			objUser = (User) session.getAttribute("login");
                            		}
                            		if (objUser.getRole() != 0) {
                            	%>
                                <div class="col-sm-6">
                                    <a href="<%=request.getContextPath() %>/admin/cat/add" class="btn btn-success btn-md">Thêm</a>
                                </div>
                                <%
                            		} else {
                                %>
                                <div class="col-sm-6"></div>
                                <%
                            		}
                                %>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="get" action="<%=request.getContextPath() %>/admin/cat/index">
                                        <input type="submit" name="search" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" name="searchContent" class="form-control input-sm" value="${searchContent}" placeholder="Nhập tên danh mục" style="float:right; width: 300px;" />
                                        <div style="clear:both"></div>
                                    </form><br />
                                </div>
                            </div>
							<%
                              	if (request.getAttribute("success") != null) {
                            %>
                            <div class="alert alert-success" role="alert">
                            	<span style="color: blue; font-style: italic">${success}</span>
                            </div>
                            <%
                            	}
								if (request.getAttribute("err") != null) {
                            %>
                            <div class="alert alert-danger" role="alert">
                              	<span style="color: red; font-style: italic">${err}</span>
                            </div>
                            <%
								}
								if (request.getAttribute("listcat") != null) {
                        			ArrayList<Category> listCat = (ArrayList<Category>) request.getAttribute("listcat");
                        			if (listCat.size() > 0) {
                            %>
                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên danh mục</th>
                                        <%
                                       		if (objUser.getRole() != 0) {
                                        %>
                                        <th width="160px">Chức năng</th>
                                        <%
                                       		}
                                        %>
                                    </tr>
                                </thead>
                                <tbody>
                                	<%
                                		for (Category objCat : listCat) {
                                	%>
                                    <tr>
                                        <td><%=objCat.getId() %></td>
                                        <td class="center"><%=objCat.getName() %></td>
                                        <%
                                        	if (objUser.getRole() != 0) {
                                        %>
                                        <td class="center">
                                            <a href="<%=request.getContextPath() %>/admin/cat/edit?id=<%=objCat.getId() %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
                                            <a href="<%=request.getContextPath() %>/admin/cat/delete?id=<%=objCat.getId() %>" onclick="return confirm('Bạn có chắc muốn xoá <%=objCat.getName() %> không?')" title="" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                        <%
                                        	}
                                        %>
                                    </tr>
                                    <%
                                		}
                                    %>
                                </tbody>
                            </table>
                            <%
                        			} else {
                        	%>
                        		<div class="alert alert-success" role="alert">
      								<span style="color: blue; font-style: italic">Không có danh mục nào!</span>
      							</div>	
                        	<%
                        			}
								}
                            %>
                        </div>

                    </div>
                </div>
                <!--End Advanced Tables -->
            </div>
        </div>
    </div>
</div>
<script>
    document.getElementById("category").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>