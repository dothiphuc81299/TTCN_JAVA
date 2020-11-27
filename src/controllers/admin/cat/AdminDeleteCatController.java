package controllers.admin.cat;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CategoryDao;

import daos.SongDao;
import models.Song;
import utils.AuthUtil;
import utils.FileUtil;

public class AdminDeleteCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminDeleteCatController() {
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
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?err=4");
			return;
		}
		CategoryDao objCatDao = new CategoryDao();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			if (!objCatDao.checkId(id)) {
				throw new Exception();
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?err=1");
			return;
		}
		SongDao objSongDao = new SongDao();
		
		ArrayList<Song> listSong = objSongDao.getSongByCat(id);
		int check = 0;
		if (listSong.size() > 0) {
			check = 1;
			for (Song song : listSong) {
				FileUtil.delFile(song.getPicture(), request);
				FileUtil.delFileMp3(song.getMp3(), request);
			
				
			}
		}
		if (check == 1) {
			if (objSongDao.deleteSongByCat(id) > 0) {
				if (objCatDao.deleteCat(id) > 0) {
					response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=3");
					return;
				}
			}
		} else {
			if (objCatDao.deleteCat(id) > 0) {
				response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=3");
				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
