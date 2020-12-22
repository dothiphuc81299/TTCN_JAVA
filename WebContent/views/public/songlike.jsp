<%@page import="models.User"%>
<%@page import="daos.LikeDao"%>
<%@page import="utils.DefineUtil"%>
<%@page import="models.Song"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp"%>
<div class="content_resize">
	<div class="mainbar">
		<%
			int check = 0;
			int total = 0, pages = 1, totalPages = 1;
			if (request.getAttribute("pages") != null) {
		        pages = (int) request.getAttribute("pages");
		    }
			if (request.getAttribute("total") != null) {
		        total = (int) request.getAttribute("total");
		    }
			if (request.getAttribute("totalPages") != null) {
				totalPages = (int) request.getAttribute("totalPages");
		    }
			int i = 1;
			if (request.getAttribute("listsong") != null) {
				ArrayList<Song> listSong = (ArrayList<Song>) request.getAttribute("listsong");
				if (listSong.size() > 0) {
		%>
		<div class="article">
			<h1>Bài hát Yêu thích</h1>
		</div>
		<%
			for (Song objSong : listSong) {
				int like = new LikeDao().getCount(objSong.getId());
				String urlSlug = request.getContextPath() + "/chi-tiet/" + StringUtil.makeSlug(objSong.getName()) + "-" + objSong.getId() + ".html";
				String ngayDang = StringUtil.tachChuoi(objSong.getCreatedAt());
		%>
		<div class="article">
			<h2>
				<a href="<%=urlSlug %>" title="<%=objSong.getName() %>"><%=objSong.getName() %></a>
			</h2>
			<p class="infopost">
				Ngày đăng: <%=ngayDang %> - Lượt xem: <%=objSong.getCounter() %> <a href="#" class="com"><span><%=i++ %></span></a>
			</p>
			<div class="clr"></div>
			<div class="img">
				<%
					if (!"".equals(objSong.getPicture())) {
				%>
				<img src="<%=request.getContextPath() %>/uploads/images/<%=objSong.getPicture() %>" width="177" height="213" alt="<%=objSong.getName() %>" class="fl" />
				<%
					} else {
				%>
				<img src="<%=request.getContextPath() %>/uploads/images/no-image.png" width="177" height="213" alt="no image" class="fl" />
				<%
					}
				%>
			</div>
			<div class="post_content">
				<p><%=objSong.getDescription() %></p>
				<div class="kq-like-<%=objSong.getId() %>">
					<span style="margin-right: 20px">Lượt like: <%=like %></span>
					<%
						if (session.getAttribute("publiclogin") != null) {
							LikeDao objLikeDao = new LikeDao();
							User objUser = (User) session.getAttribute("publiclogin");
							if (objLikeDao.checkLiked(objSong.getId(), objUser.getId())) {
					%>
					<a href="javascript:void(0)" onclick="likeAction(<%=objSong.getId() %>)"><i style="color: red" class="fa fa-thumbs-up"></i> Liked</a>
					<%
							} else {
					%>
					<a href="javascript:void(0)" onclick="likeAction(<%=objSong.getId() %>)"><i style="color: black" class="fa fa-thumbs-up"></i> Like</a>
					<%
							}
						} else {
					%>
					<span style="font-style: italic">Vui lòng đăng nhập để thực hiện chức năng like!</span>
					<%
						}
					%>
				</div>
				<p class="spec">
					<a href="<%=urlSlug %>" class="rm">Chi tiết &raquo;</a>
				</p>
			</div>
			<div class="clr"></div>
		</div>
		<%
			}
				} else {
					check = 1;
		%>
		<div class="article">
			<h1>Bài hát Yêu thích</h1>
		</div>
		<div class="article"></div>
		<p class="infopost">Không có bài hát yêu thích!</p>
		<%
				}
			}
			if (check == 0) {
		%>
		<p class="pages">
			<small>Trang <%=pages %> của <%=totalPages %></small>
			<%
				// Button previous
				if (pages > 1) {
					int back = pages - 1;
		    %>
		    <a href="<%=request.getContextPath() %>/song/like?pages=<%=back %>">&laquo;</a>
		    <%
				}
				// Button Number page
				int loop = 0, num = 0;
                if ((total / 4) % 2 == 0) {
                    num = total / 4;
                } else {
                    num = (total + 1) / 4;
                }
                //Nếu total lẻ thêm 1
                if (total % 2 != 0) {
                    loop = (total / 4) + 1;
                } else {
                    //Nếu total chẵn nhỏ hơn fullpage và không fullPage thì thêm 1
                    if (total < (num * 4) + 4 && total != num * 4) {
                        loop = (total / 4) + 1;
                    } else {
                        //Nếu bằng fullPage thì không thêm
                        loop = (total / 4);
                    }
                }
                for (int j = 1; j <= loop; j++) {
            		if (pages == j) {
            %>
            <span><%=j %></span>
            <%
            		} else {
            %>
            <a href="<%=request.getContextPath() %>/song/like?pages=<%=j %>"><%=j %></a>
            <%
            		}
                }
           		//Button Next
                int next = 0;
                //Nếu total lẻ
                if (total % 2 != 0) {
                    if (pages != (total / 4) + 1) {
                        next = pages + 1;
             %>
             <a href="<%=request.getContextPath() %>/song/like?pages=<%=next %>">&raquo;</a>
             <%
                    }
                } else {
                    //Nếu total chẵn nhỏ hơn fullpage và không fullPage thì thêm 1
                    if (total < (num * 4) + 4 && total != num * 4) {
                        if (pages != (total / 4) + 1) {
                            next = pages + 1;
             %>
             <a href="<%=request.getContextPath() %>/song/like?pages=<%=next %>">&raquo;</a>
             <%
                        }
                    } else {
                        //Nếu fullPage đến trang cuối dừng chưa tới trang cuối thì được next
                        if (pages != (total / 4)) {
                            next = pages + 1;
             %>
             <a href="<%=request.getContextPath() %>/song/like?pages=<%=next %>">&raquo;</a>
             <%
                        }
                    }
                }
		     %>
		</p>
		<%
			}
		%>
	</div>
	<div class="sidebar">
		<%@ include file="/templates/public/inc/leftbar.jsp"%>
	</div>
	<div class="clr"></div>
</div>
<script type="text/javascript">
	function likeAction(id) {
		$.ajax({
			url: '<%=request.getContextPath() %>/like',
			type: 'POST',
			cache: false,
			data: {
				aId: id
			},
			success: function(data){
				$(".kq-like-"+id).html(data);
			},
			error: function (){
				alert('Có lỗi xảy ra');
			}
		});
		return false;
	}
</script>
<%@ include file="/templates/public/inc/footer.jsp"%>