<%@page import="models.Song"%>
<%@page import="daos.SongDao"%>
<%@page import="daos.UserDao"%>
<%@page import="models.Comment"%>
<%@page import="utils.DefineUtil"%>
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
                <h2>Quản lý bình luận</h2>
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
                            	%>
                                <div class="col-sm-6"></div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="get" action="<%=request.getContextPath() %>/admin/comment/index">
                                        <input type="submit" name="search" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input type="search" name="searchContent" class="form-control input-sm" value="${searchContent}" placeholder="Nhập tên bài hát" style="float:right; width: 300px;" />
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
								int check = 0;
								int numberPage = 10;
								int total = 0, pages = 1, totalPages = 1, first = 0, last = 0;
								if (request.getAttribute("pages") != null) {
							        pages = (int) request.getAttribute("pages");
							    }
								if (request.getAttribute("total") != null) {
							        total = (int) request.getAttribute("total");
							    }
								if (request.getAttribute("totalPages") != null) {
									totalPages = (int) request.getAttribute("totalPages");
							    }
								if (request.getAttribute("first") != null) {
									first = (int) request.getAttribute("first");
							    }
								if (request.getAttribute("last") != null) {
									last = (int) request.getAttribute("last");
							    }
								if (request.getAttribute("listComment") != null) {
                        			ArrayList<Comment> listComment = (ArrayList<Comment>) request.getAttribute("listComment");
                        			if (listComment.size() > 0) {
                            %>
                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên người dùng</th>
                                        <th>Tên bài hát</th>
                                        <th>Bình luận</th>
                                        <%
                                        	if (objUser.getRole() != 0) {
                                        %>
                                        <th width="100px">Trạng thái</th>
                                        <th width="100px">Chức năng</th>
                                        <%
                                        	}
                                        %>
                                    </tr>
                                </thead>
                                <tbody>
                                	<%
                                		for (Comment objComment : listComment) {
                                			int idUser = objComment.getIdUser();
                                			User user = new UserDao().getUser(idUser);
                                			int idSong = objComment.getIdSong();
                                			Song song = new SongDao().getSong(idSong);
                                	%>
                                    <tr>
                                        <td><%=objComment.getId() %></td>
                                        <td><%=user.getFullName() %></td>
                                        <td><%=song.getName() %></td>
                                        <td><%=objComment.getComment() %></td>
                                        <%
                                        	if (objUser.getRole() != 0) {
                                        		if (objComment.getStatus() == 1) {
                                        %>
                                        <td align="center"><a href="javascript:void(0)"><img onclick="statusCmt(<%=objComment.getId() %>)" id="img<%=objComment.getId() %>" src="<%=request.getContextPath() %>/templates/admin/assets/img/active.gif" alt="active" /></a></td>
                                        <%
                                        		} else {
                                        %>
                                        <td align="center"><a href="javascript:void(0)"><img onclick="statusCmt(<%=objComment.getId() %>)" id="img<%=objComment.getId() %>" src="<%=request.getContextPath() %>/templates/admin/assets/img/deactive.gif" alt="deactive" /></a></td>
                                        <%
                                        		}
                                        %>
                                        <td align="center">
                                            <a href="<%=request.getContextPath() %>/admin/comment/delete?id=<%=objComment.getId() %>" onclick="return confirm('Bạn có chắc muốn xoá bình luận này không?')" title="" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
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
                        				check = 1;
                        	%>
                        		<div class="alert alert-success" role="alert">
      								<span style="color: blue; font-style: italic">Không có bình luận nào!</span>
      							</div>	
                        	<%
                        			}
								}
								if (check == 0) {
                            %>
                            <div class="row">
                                <div class="col-sm-6">
                                	<%
                                		if (total - first > numberPage) {
                                	%>
                                    <div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px">Hiển thị từ <%=first + 1 %> đến <%=first + last %> của <%=total %> bình luận</div>
                                	<%
                                		} else {
                                	%>
                                	<div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px">Hiển thị từ <%=first + 1 %> đến <%=total %> của <%=total %> bình luận</div>
                                	<%
                                		}
                                	%>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                        	<%
												// Button previous
												if (pages > 1) {
													int back = pages - 1;
													if (back == 1) {
		   									%>
                                            <li class="paginate_button previous" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath() %>/admin/comment/index">Trang trước</a></li>
                                            <%
													} else {
                                            %>
                                            <li class="paginate_button previous" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath() %>/admin/comment/index?pages=<%=back %>">Trang trước</a></li>
                                           	<%
													}
												}
	                                        	// Button Number page
	                            				int loop = 0, num = 0;
	                                            if ((total / numberPage) % 2 == 0) {
	                                                num = total / numberPage;
	                                            } else {
	                                                num = (total + 1) / numberPage;
	                                            }
	                                            //Nếu total lẻ thêm 1
	                                            if (total % 2 != 0) {
	                                                loop = (total / numberPage) + 1;
	                                            } else {
	                                                //Nếu total chẵn nhỏ hơn fullpage và không fullPage thì thêm 1
	                                                if (total < (num * numberPage) + numberPage && total != num * numberPage) {
	                                                    loop = (total / numberPage) + 1;
	                                                } else {
	                                                    //Nếu bằng fullPage thì không thêm
	                                                    loop = (total / numberPage);
	                                                }
	                                            }
	                                            for (int j = 1; j <= loop; j++) {
	                                        		if (pages == j) {
	                                        			if (j == 1) {
                                           	%>
                                            <li class="paginate_button active" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath() %>/admin/comment/index"><%=j %></a></li>
                                            <%
	                                        			} else {
                                            %>
                                            <li class="paginate_button active" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath() %>/admin/comment/index?pages=<%=j %>"><%=j %></a></li>
                                            <%
	                                        			}
	                                        		} else {
	                                        			if (j == 1) {
                                            %>
											<li class="paginate_button" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath() %>/admin/comment/index"><%=j %></a></li>
											<%
	                                        			} else {
											%>
											<li class="paginate_button" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath() %>/admin/comment/index?pages=<%=j %>"><%=j %></a></li>
											<%
	                                        			}
	                                        		}
	                                            }
		                                        //Button Next
		                                        int next = 0;
		                                           //Nếu total lẻ
		                                           if (total % 2 != 0) {
		                                               if (pages != (total / numberPage) + 1) {
		                                                   next = pages + 1;
											%>
											<li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath() %>/admin/comment/index?pages=<%=next %>">Trang tiếp</a></li>
								             <%
		                                               }
		                                           } else {
									                   //Nếu total chẵn nhỏ hơn fullpage và không fullPage thì thêm 1
									                   if (total < (num * numberPage) + numberPage && total != num * numberPage) {
									                       if (pages != (total / numberPage) + 1) {
									                           next = pages + 1;
								             %>
								             <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath() %>/admin/comment/index?pages=<%=next %>">Trang tiếp</a></li>
								             <%
									                       }
									                   } else {
									                       //Nếu fullPage đến trang cuối dừng chưa tới trang cuối thì được next
									                       if (pages != (total / numberPage)) {
									                           next = pages + 1;
								             %>
								             <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath() %>/admin/comment/index?pages=<%=next %>">Trang tiếp</a></li>
								             <%
									                       }
									                   }
		                                           }
										     %>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <%
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
<script type="text/javascript">
	function statusCmt(id) {
		var img = $("#img"+id).attr("src");
		$.ajax({
			url: '<%=request.getContextPath() %>/admin/comment/status',
			type: 'POST',
			cache: false,
			data: {
				aId: id,
				aImg: img
			},
			success: function(data){
				$("#img"+id).attr("src",data);
			},
			error: function (){
				alert('Có lỗi xảy ra');
			}
		});
		return false;
	}
</script>
<script>
    document.getElementById("comment").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>