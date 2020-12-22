<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Quản lý người dùng</h2>
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
                            		User user = null;
                            		if (session.getAttribute("login") != null) {
                            			user = (User) session.getAttribute("login");
                            		}
                            		int role = user.getRole();
                            	%>
                                <div class="col-sm-6">
                                    <a href="<%=request.getContextPath() %>/admin/user/add" class="btn btn-success btn-md">Thêm</a>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="get" action="<%=request.getContextPath() %>/admin/user/index">
                                        <input type="submit" name="search" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" name="searchContent" class="form-control input-sm" value="${searchContent}" placeholder="Nhập tên người dùng" style="float:right; width: 300px;" />
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
								if (request.getAttribute("listuser") != null) {
                        			ArrayList<User> listUser = (ArrayList<User>) request.getAttribute("listuser");
                        			if (listUser.size() > 0) {
                            %>
                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Username</th>
                                        <th>Fullname</th>
                                        <th>Role</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<%
                                		for (User objUser : listUser) {
                                	%>
                                    <tr>
                                        <td><%=objUser.getId() %></td>
                                        <td class="center"><%=objUser.getUsername() %></td>
                                        <td class="center"><%=objUser.getFullName() %></td>
                                        <%
                                        	if (objUser.getRole() == 0) {
                                        %>
                                        <td class="center">User</td>
                                        <%
                                        	} else if (objUser.getRole() == 1) {
                                        %>
                                        <td class="center">Admin</td>
                                        <%
                                        	} else {
                                        %>
                                        <td class="center">Administrator</td>
                                        <%
                                        	}
                                        %>
                                        <td class="center">
                                            <a href="<%=request.getContextPath() %>/admin/user/edit?id=<%=objUser.getId() %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
                                            <a href="<%=request.getContextPath() %>/admin/user/delete?id=<%=objUser.getId() %>" onclick="return confirm('Bạn có chắc muốn xoá <%=objUser.getUsername() %> không?')" title="" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
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
      								<span style="color: blue; font-style: italic">Không có người dùng nào!</span>
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
    document.getElementById("user").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>