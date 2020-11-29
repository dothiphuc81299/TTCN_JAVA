package controllers.admin.forbiddenword;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.ForbiddenWordDao;
import models.ForbiddenWord;
import utils.AuthUtil;

public class AdminEditWordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminEditWordController() {
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
			response.sendRedirect(request.getContextPath() + "/admin/forbiddenword/index");
			return;
		}
		ForbiddenWordDao objWordDao = new ForbiddenWordDao();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			if (!objWordDao.checkId(id)) {
				throw new Exception();
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/forbiddenword/index?err=1");
			return;
		}
		ForbiddenWord objWord = objWordDao.getWord(id);
		request.setAttribute("fword", objWord);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/forbiddenword/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		ForbiddenWordDao objWordDao = new ForbiddenWordDao();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("wid"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/forbiddenword/index?err=1");
			return;
		}
		String word = request.getParameter("word");
		ForbiddenWord objWord = new ForbiddenWord(id, word);
		request.setAttribute("word", word);
		request.setAttribute("fword", objWord);
		if (word.equals("")) {
			request.setAttribute("err", "Từ cấm không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/forbiddenword/edit.jsp");
			rd.forward(request, response);
			return;
		}
		
		if (objWordDao.checkWord(word)) {
			request.setAttribute("err", "Từ cấm đã tồn tại!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/forbiddenword/edit.jsp");
			rd.forward(request, response);
			return;
		}
		
		if (objWordDao.editWord(objWord) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/forbiddenword/index?msg=2");
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/admin/forbiddenword/index?err=10");
		return;
		
	}

}
