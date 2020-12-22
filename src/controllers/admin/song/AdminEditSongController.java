package controllers.admin.song;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import daos.CategoryDao;
import daos.SongDao;
import models.Category;
import models.Song;
import utils.AuthUtil;
import utils.FileUtil;
import utils.StringUtil;

@MultipartConfig
public class AdminEditSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminEditSongController() {
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
			response.sendRedirect(request.getContextPath() + "/admin/song/index?err=3");
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
		Song song = objSongDao.getSong(id);
		request.setAttribute("song", song);
		ArrayList<Category> listCat = new CategoryDao().getAll();
		request.setAttribute("listcat", listCat);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		CategoryDao objCatDao = new CategoryDao();
		SongDao objSongDao = new SongDao();
		int id = Integer.parseInt(request.getParameter("id"));
		Song objSong2 = objSongDao.getSong(id);
		String name = request.getParameter("name");
		int catId = Integer.parseInt(request.getParameter("category"));
		Category objCat = objCatDao.getCat(catId);
		String description = request.getParameter("preview");
		String detail = request.getParameter("detail");
		Part filePart = request.getPart("picture");
		String fileName2 = filePart.getSubmittedFileName();
		String fileName = "";
		if (fileName2.equals("")) {
			fileName = objSong2.getPicture();
		} else {
			fileName = FileUtil.upload("picture", request);
			FileUtil.delFile(objSong2.getPicture(), request);
		}
		Part filPart2 = request.getPart("mp3");
		String mp32 = filPart2.getSubmittedFileName();
		String mp3 = "";
		if (mp32.equals("")) {
			mp3 = objSong2.getMp3();
		} else {
			mp3 = FileUtil.uploadMp3("mp3", request);
			FileUtil.delFileMp3(objSong2.getMp3(), request);
		}
		ArrayList<Category> listCat = new CategoryDao().getAll();
		request.setAttribute("listcat", listCat);
		Song objSong = new Song(objSong2.getId(), name, description, detail, objSong2.getCreatedAt(), fileName, mp3,
				objSong2.getCounter(), objCat);
		request.setAttribute("song", objSong);

		if (name.equals("")) {
			request.setAttribute("err", "Tên bài hát không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/edit.jsp");
			rd.forward(request, response);
			return;
		}
		
	
		if (description.equals("")) {
			request.setAttribute("err", "Tên bài hát không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/edit.jsp");
			rd.forward(request, response);
			return;
		}
		
		

		if (detail.equals("")) {
			request.setAttribute("err", "Tên bài hát không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/edit.jsp");
			rd.forward(request, response);
			return;
		}
		
		
		if (objSongDao.editSong(objSong) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/song/index?msg=2");
			return;
		}

	}

}
