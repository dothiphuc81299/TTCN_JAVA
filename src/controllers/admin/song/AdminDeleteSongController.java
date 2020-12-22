package controllers.admin.song;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import daos.SongDao;
import models.Song;
import utils.AuthUtil;
import utils.FileUtil;

public class AdminDeleteSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminDeleteSongController() {
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
		if (AuthUtil.userAuth(request)) {
			response.sendRedirect(request.getContextPath() + "/admin/song/index?err=4");
			return;
		}
		SongDao objSongDao = new SongDao();
	
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			if (!objSongDao.checkId(id)) {
				throw new Exception();
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/song/index?err=2");
			return;
		}
	
		Song objSong = objSongDao.getSong(id);
		if (objSongDao.deleteSong(id) > 0) {
			FileUtil.delFile(objSong.getPicture(), request);
			FileUtil.delFileMp3(objSong.getMp3(), request);
			response.sendRedirect(request.getContextPath() + "/admin/song/index?msg=3");
			return;
		}
		response.sendRedirect(request.getContextPath() + "/admin/song/index?err=1");
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
