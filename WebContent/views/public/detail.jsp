<%@page import="daos.LikeDao"%>
<%@page import="daos.UserDao"%>
<%@page import="models.User"%>
<%@page import="models.Comment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp"%>
<div class="content_resize">
	<div class="mainbar">
		<div class="article">
			<%
				if (request.getAttribute("song") != null) {
					Song objSong = (Song) request.getAttribute("song");
					int like = new LikeDao().getCount(objSong.getId());
					String ngayDang = StringUtil.tachChuoi(objSong.getCreatedAt());
			%>
			<h1><%=objSong.getCat().getName() %></h1>
			<div class="clr"></div>
			<h2><%=objSong.getName() %></h2>
			<p>Ngày đăng: <%=ngayDang %> - Lượt xem: <%=objSong.getCounter() %></p>
			<div class="vnecontent"><%=objSong.getDetail() %></div>
			<%
				if (!objSong.getMp3().equals("")) {
			%>
			<div style="margin-bottom: 10px"><audio src="<%=request.getContextPath() %>/uploads/mp3/<%=objSong.getMp3() %>" controls="controls" autoplay="autoplay"></audio></div>
			<%
				} else {
			%>
			<div style="margin-bottom: 10px">Không có Audio</div>
			<%
				}
			%>
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
			
			<h2>Bình luận</h2>
			<%
				if (session.getAttribute("publiclogin") != null) {
			%>
			<form>
				<div><span class="err-cmt" style="color: red; font-style: italic"></span></div>
				<input type="text" name="comment" id="comment" placeholder="Viết bình luận" />
				<button onclick="binhLuan(<%=objSong.getId() %>)" type="button" name="submit" >Bình luận</button>
			</form>
			<%
				} else {
			%>
			<div style="margin-top: 10px; font-style: italic">Vui lòng đăng nhập để thực hiện chức năng bình luận!</div>
			<%
				}
			%>
			<div class="kq">
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
						if (totalPages == 1) {
							check = 1;
						}
				    }
					if (request.getAttribute("listComment") != null) {
						ArrayList<Comment> listComment = (ArrayList<Comment>) request.getAttribute("listComment");
						if (listComment.size() > 0) {
							for (Comment cmt : listComment) {
								int idUser = cmt.getIdUser();
								User user = new UserDao().getUser(idUser);
				%>
				<div style="margin-top: 10px"><span style="color: red"><%=user.getFullName() %>:</span> <span style="margin: 0px 10px"><%=cmt.getComment() %></span> <span style="color: #FF66CC"><%=cmt.getDateCreate() %></span></div>
				<%
							}
						} else {
							check = 1;
				%>
				<div style="margin-top: 10px">Chưa có bình luận nào! Hãy là người bình luận đầu tiên!</div>
				<%
						}
					}
				%>
			</div>
				<%
					if (check == 0) {
				%>
				<div style="margin-top: 20px">
					<%
						if (pages > 1) {
							int back = pages - 1;
					%>
					<a style="margin-right: 30px" href="<%=request.getContextPath() %>/detail?id=<%=objSong.getId() %>&pages=<%=back %>" >Mới hơn &laquo;</a>
					<%
						}
						if (pages < totalPages) {
							int next = pages + 1;
					%>
					<a href="<%=request.getContextPath() %>/detail?id=<%=objSong.getId() %>&pages=<%=next %>" >Cũ hơn &raquo;</a>
					<%
						}
					%>
				</div>
			<%
					}
				}
			%>
		</div>
		<div class="article">
			<h2>Bài viết liên quan</h2>
			<div class="clr"></div>
			<div class="comment">
				<%
					if (request.getAttribute("songlienquan") != null) {
						Song song = (Song) request.getAttribute("songlienquan");
							String urlSlug = request.getContextPath() + "/chi-tiet/" + StringUtil.makeSlug(song.getName()) + "-" + song.getId() + ".html";
				%>
				<a href="<%=urlSlug %>">
					<%
						if (!"".equals(song.getPicture())) {
					%>
					<img src="<%=request.getContextPath() %>/uploads/images/<%=song.getPicture() %>" width="40" height="40" alt="" class="userpic" />
					<%
						} else {
					%>
					<img src="<%=request.getContextPath() %>/uploads/images/no-image.png" width="40" height="40" alt="" class="userpic" />
					<%
						}
					%>
				</a>
				<h2>
					<a href="<%=urlSlug %>"><%=song.getName() %></a>
				</h2>
				<p><%=song.getDescription() %></p>
				<%
					}
				%>
			</div>
		</div>
	</div>
	<div class="sidebar">
		<%@ include file="/templates/public/inc/leftbar.jsp"%>
	</div>
	<div class="clr"></div>
</div>
<script type="text/javascript">
	function binhLuan(id) {
		var cmt = $("#comment").val();
		$.ajax({
			url: '<%=request.getContextPath() %>/comment',
			type: 'POST',
			cache: false,
			data: {
				aId: id,
				aCmt: cmt
			},
			success: function(data){
				$(".kq").html(data);
				$("#comment").val("");
			},
			error: function (){
				alert('Có lỗi xảy ra');
			}
		});
		return false;
	}
</script>
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
