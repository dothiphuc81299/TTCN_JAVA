<%@page import="models.Song"%>
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
                <h2>Sửa bài hát</h2>
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
                                <form role="form" method="post" enctype="multipart/form-data" id="form" action="<%=request.getContextPath() %>/admin/song/edit">
                                    <%
                                		if (request.getAttribute("err") != null) {
                                	%>
                                	<div class="alert alert-danger" role="alert">
                                		<span style="color: red; font-style: italic">${err}</span>
                                	</div>
                                	<%
                                		}
                                    	if (request.getAttribute("song") != null) {
                                    		Song objSong = (Song) request.getAttribute("song");
                                	%>
                                	<div class="form-group">
                                        <label for="songid">ID</label>
                                        <input type="text" id="songid" value="<%=objSong.getId() %>" name="id" class="form-control" readonly="readonly" />
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Tên bài hát</label>
                                        <input type="text" id="name" value="<%=objSong.getName() %>" name="name" class="form-control" />
                                    </div>
                                    <div class="form-group">
                                        <label for="category">Danh mục bài hát</label>
                                        <select id="category" name="category" class="form-control">
                                        <%
                                        	if (request.getAttribute("listcat") != null) {
	                                        	ArrayList<Category> listCat = (ArrayList<Category>) request.getAttribute("listcat");
	                                        	if (listCat.size() > 0) {
	                                        		for (Category cat : listCat) {
	                                        			if (cat.getId() == objSong.getCat().getId()) {
                                        %>
	                                        <option value="<%=cat.getId() %>" selected="selected"><%=cat.getName() %></option>
	                                    <%
	                                        			} else {
	                                    %>
	                                    	<option value="<%=cat.getId() %>"><%=cat.getName() %></option>
										<%
	                                        			}
	                                        		}
	                                        	}
                                        	}
										%>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="picture">Hình ảnh</label>
                                        <%
                                        	if (!objSong.getPicture().equals("")) {
                                        %>
                                        <p><img width="200px" height="140px" src="<%=request.getContextPath() %>/uploads/images/<%=objSong.getPicture() %>" /></p>
                                        <%
                                        	} else {
                                        %>
                                        <p><img width="200px" height="140px" src="<%=request.getContextPath() %>/uploads/images/no-image.png" /></p>
                                        <%
                                        	}
                                        %>
                                        <input type="file" name="picture" id="picture" />
                                    </div>
                                    <div class="form-group">
                                        <label for="mp3">Audio</label>
                                        <%
                                        	if (!objSong.getMp3().equals("")) {
                                        %>
                                        <p><audio src="<%=request.getContextPath() %>/uploads/mp3/<%=objSong.getMp3() %>" controls="controls" ></audio></p>
                                        <%
                                        	} else {
                                        %>
                                        <p>Không có Audio</p>
                                        <%
                                        	}
                                        %>
                                        <input type="file" name="mp3" id="mp3" />
                                    </div>
                                    <div class="form-group">
                                        <label for="preview">Mô tả</label>
                                        <textarea id="preview" class="form-control" rows="3" name="preview"><%=objSong.getDescription() %></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="detail">Chi tiết</label>
                                        <textarea id="detail" class="form-control" id="detail" rows="5" name="detail"><%=objSong.getDetail() %></textarea>
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
    var editor = CKEDITOR.replace('detail');
</script>
<script>
    document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>