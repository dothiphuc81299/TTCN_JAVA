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

public class AdminAddWordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminAddWordController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login");
			return;
		}
		if (AuthUtil.userAuth(request)) {
			response.sendRedirect(request.getContextPath() + "/admin/forbiddenword/index");
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/forbiddenword/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		ForbiddenWordDao objWordDao = new ForbiddenWordDao();
		String word = request.getParameter("word");
		request.setAttribute("word", word);
		if (word.equals("")) {
			request.setAttribute("err", "Từ cấm không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/forbiddenword/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		if (objWordDao.checkWord(word)) {
			request.setAttribute("err", "Từ cấm đã tồn tại!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/forbiddenword/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		ForbiddenWord objWord = new ForbiddenWord(0, word);
		if (objWordDao.addWord(objWord) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/forbiddenword/index?msg=1");
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/admin/forbiddenword/index?err=10");
		return;
		
	}

}
