<%@page import="utils.StringUtil"%>
<%@page import="models.Song"%>
<%@page import="daos.SongDao"%>
<%@page import="daos.CategoryDao"%>
<%@page import="models.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<div class="searchform">
	<form id="formsearch" name="formsearch" method="get" action="<%=request.getContextPath() %>/search">
		<span> <input name="editbox_search" class="editbox_search" id="editbox_search" maxlength="80" value="${searchContent}" placeholder="Tìm kiếm bài hát" type="text" />
		</span> <input name="button_search" src="<%=request.getContextPath() %>/templates/public/images/search.jpg" class="button_search" type="image" />
	</form>
</div>
<div class="clr"></div>
<div class="gadget">
	<h2 class="star">Danh mục bài hát</h2>
	<div class="clr"></div>
	<ul class="sb_menu">
		<%
			if (request.getAttribute("listcat") != null) {
				ArrayList<Category> listCat = (ArrayList<Category>) request.getAttribute("listcat");
				if (listCat.size() > 0) {
					for (Category objCat : listCat) {
						String urlSlug = request.getContextPath() + "/danh-muc/" + StringUtil.makeSlug(objCat.getName()) + "-" + objCat.getId() + ".html";
		%>
		<li><a href="<%=urlSlug %>"><%=objCat.getName() %></a></li>
		<%
					}
				}
			}
		%>
	</ul>
</div>

<div class="gadget">
	<h2 class="star">
		<span>Bài hát mới</span>
	</h2>
	<div class="clr"></div>
	<ul class="ex_menu">
		<%
			ArrayList<Song> listSong2 = new SongDao().getSongNew();
			if (listSong2.size() > 0) {
				for (Song song : listSong2) {
					String urlSlug = request.getContextPath() + "/chi-tiet/" + StringUtil.makeSlug(song.getName()) + "-" + song.getId() + ".html";
		%>
		<li><a href="<%=urlSlug %>"><%=song.getName() %></a><br /> <%=song.getDescription() %></li>
		<%
				}
			}
		%>
	</ul>
</div>