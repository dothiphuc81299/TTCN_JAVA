package controllers.admin.song;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CategoryDao;
import daos.SongDao;
import models.Category;
import models.Song;
import utils.AuthUtil;
import utils.CategoryUtil;
import utils.FileUtil;
import utils.StringUtil;

@MultipartConfig
public class AdminAddSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminAddSongController() {
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
			response.sendRedirect(request.getContextPath() + "/admin/song/index?err=2");
			return;
		}
		CategoryUtil.getCat(request, response);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		CategoryUtil.getCat(request, response);
		CategoryDao objCatDao = new CategoryDao();
		SongDao objSongDao = new SongDao();
		String name = request.getParameter("name");
		int catId = Integer.parseInt(request.getParameter("category"));
		Category objCat = objCatDao.getCat(catId);
		String description = request.getParameter("preview");
		String detail = request.getParameter("detail");
		request.setAttribute("name", name);
		request.setAttribute("description", description);
		request.setAttribute("detail", detail);
		request.setAttribute("catid", catId);
		
		if (name.equals("")) {
			request.setAttribute("err", "Tên bài hát không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		
		
		if (description.equals("")) {
			request.setAttribute("err", "Mô tả không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		
		if (detail.equals("")) {
			request.setAttribute("err", "Chi tiết không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/song/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		
		
		String fileName = FileUtil.upload("picture", request);
		String mp3 = FileUtil.uploadMp3("mp3", request);
		Song objSong = new Song(name, description, detail, fileName, mp3, objCat);
		if (objSongDao.addSong(objSong) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/song/index?msg=1");
			return;
		}
		
	}

}
