package controllers.admin.song;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.SongDao;
import models.Song;
import utils.AuthUtil;
import utils.CategoryUtil;

public class AdminIndexSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminIndexSongController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		if (!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login");
			return;
		}
		if (request.getParameter("err") != null) {
			int err = Integer.parseInt(request.getParameter("err"));
			if (err == 1) {
				request.setAttribute("err", "Xoá thất bại!");
			} else if (err == 2) {
				request.setAttribute("err", "ID không tồn tại!");
			} else {
				request.setAttribute("err", "Không có quyền thực hiện chức năng này!");
			}
		}
		if (request.getParameter("msg") != null) {
			int msg = Integer.parseInt(request.getParameter("msg"));
			if (msg == 1) {
				request.setAttribute("success", "Thêm thành công!");
			} else if (msg == 2) {
				request.setAttribute("success", "Sửa thành công!");
			} else {
				request.setAttribute("success", "Xoá thành công!");
			}
		}
		CategoryUtil.getCat(request, response);
		SongDao objSongDao = new SongDao();
		ArrayList<Song> listSong = objSongDao.getAll();
		// Tìm kiếm
		if (request.getParameter("scat") != null || request.getParameter("searchContent") != null) {
			int catId = Integer.parseInt(request.getParameter("scat"));
			String searchContent = request.getParameter("searchContent");
			request.setAttribute("searchContent", searchContent);
			request.setAttribute("scat", catId);
			listSong = objSongDao.getSearchSong(searchContent, catId);
		}
		request.setAttribute("listsong", listSong);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
