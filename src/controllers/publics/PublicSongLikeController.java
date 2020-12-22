package controllers.publics;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.LikeDao;
import daos.SongDao;
import models.Song;
import models.User;
import utils.CategoryUtil;
import utils.DefineUtil;

public class PublicSongLikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PublicSongLikeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		if (session.getAttribute("publiclogin") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		User userLogin = (User) session.getAttribute("publiclogin");
		CategoryUtil.getCat(request, response);
		LikeDao objLikeDao = new LikeDao();
		SongDao objSongDao = new SongDao();
		ArrayList<Integer> listIdSong = objLikeDao.getLikeUser(userLogin.getId());
		int numberPage = DefineUtil.NUMBER_PER_PAGE;
		int first = 0, last = 0, pages = 1, totalPages = 1;
		int total = objSongDao.getCount(listIdSong);
		if (total % numberPage == 0) {
			totalPages = total / numberPage;
		} else {
			totalPages = total / numberPage + 1;
		}
		if (request.getParameter("pages") != null) {
			try {
				pages = Integer.parseInt(request.getParameter("pages"));
				if (pages < 1 || pages > totalPages) {
					response.sendRedirect(request.getContextPath() + "/error/loi-3");
					return;
				}
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath() + "/error/loi-1");
				return;
			}
		}
		if (total <= numberPage) {
			first = 0;
			last = total;
		} else {
			first = (pages - 1) * numberPage;
			last = numberPage;
		}
		ArrayList<Song> listSong = objSongDao.getSongLike(listIdSong, first, last);
		request.setAttribute("listsong", listSong);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("total", total);
		request.setAttribute("pages", pages);
		RequestDispatcher rd = request.getRequestDispatcher("/views/public/songlike.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
