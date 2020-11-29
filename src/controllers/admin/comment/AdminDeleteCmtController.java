package controllers.admin.comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CommentDao;
import utils.AuthUtil;

public class AdminDeleteCmtController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminDeleteCmtController() {
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
			response.sendRedirect(request.getContextPath() + "/admin/comment/index?err=2");
			return;
		}
		CommentDao objCmtDao = new CommentDao();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			if (!objCmtDao.checkId(id)) {
				throw new Exception();
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/comment/index?err=1");
			return;
		}
		if (objCmtDao.deleteComment(id) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/comment/index?msg=1");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
