package controllers.admin.forbiddenword;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.ForbiddenWordDao;
import utils.AuthUtil;

public class AdminDeleteWordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminDeleteWordController() {
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
		if (objWordDao.deleteWord(id) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/forbiddenword/index?msg=3");
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/admin/forbiddenword/index?err=10");
		return;
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
