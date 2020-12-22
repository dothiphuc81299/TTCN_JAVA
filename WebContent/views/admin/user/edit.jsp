<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<%
	User user = null;
	if (session.getAttribute("login") != null) {
		user = (User) session.getAttribute("login");
	}
%>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Sửa người dùng</h2>
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
                                <form role="form" method="post" id="form" action="">
                                    <%
                                		if (request.getAttribute("err") != null) {
                                	%>
                                	<div class="alert alert-danger" role="alert">
                                		<span style="color: red; font-style: italic">${err}</span>
                                	</div>
                                	<%
                                		}
                                    	if (request.getAttribute("user") != null) {
                                    		User objUser = (User) request.getAttribute("user");
                                	%>
                                	<div class="form-group">
                                        <label for="uid">ID</label>
                                        <input type="text" id="uid" value="<%=objUser.getId() %>" name="uid" class="form-control" readonly="readonly" />
                                    </div>
                                    <div class="form-group">
                                        <label for="username">Username</label>
                                        <input type="text" id="username" value="<%=objUser.getUsername() %>" name="username" class="form-control" readonly="readonly"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Password</label>
                                        <input type="password" id="password" value="" name="password" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="repassword">RePassword</label>
                                        <input type="password" id="repassword" value="" name="repassword" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="fullname">Fullname</label>
                                        <input type="text" id="fullname" value="<%=objUser.getFullName() %>" name="fullname" class="form-control" />
                                    </div>
                                    <%
                                    	if (user.getRole() == 2 && objUser.getRole() != 2) {
                                    %>
                                    <div class="form-group">
                                        <label for="role">Phân quyền</label>
                                        <select id="role" name="role" class="form-control">
                                        	<%
                                        		if (objUser.getRole() == 0) {
                                        	%>
	                                        <option value="0" selected="selected">User</option>
											<option value="1">Admin</option>
											<option value="2">Administrator</option>
											<%
                                        		} else if (objUser.getRole() == 1) {
											%>
											<option value="0">User</option>
											<option value="1" selected="selected">Admin</option>
											<option value="2">Administrator</option>
											<%
                                        		} else {
											%>
											<option value="0">User</option>
											<option value="1">Admin</option>
											<option value="2" selected="selected">Administrator</option>
											<%
                                        		}
											%>
                                        </select>
                                    </div>
                                    <%
                                    	} else if (user.getRole() == 1 && objUser.getRole() != 1) {
                                    %>
                                    <div class="form-group">
                                        <label for="role">Phân quyền</label>
                                        <select id="role" name="role" class="form-control">
	                                        <option value="0" selected="selected">User</option>
											<option value="1">Admin</option>
                                        </select>
                                    </div>
                                    <%
                                    	}
                                    %>
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
    document.getElementById("user").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>