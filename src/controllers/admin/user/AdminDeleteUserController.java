package controllers.admin.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import daos.UserDao;
import models.User;
import utils.AuthUtil;

public class AdminDeleteUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminDeleteUserController() {
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
		UserDao objUserDao = new UserDao();
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("login");
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			if (!objUserDao.checkId(id)) {
				throw new Exception();
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/user/index?err=1");
			return;
		}
		if (userLogin.getRole() == 0) {
			if (userLogin.getId() != id) {
				response.sendRedirect(request.getContextPath() + "/admin/user/index?err=2");
				return;
			}
		} else if (userLogin.getRole() == 1) {
			User user = objUserDao.getUser(id);
			if (user.getRole() != 0) {
				if (userLogin.getId() != id) {
					response.sendRedirect(request.getContextPath() + "/admin/user/index?err=2");
					return;
				}
			}
		} else {
			User user = objUserDao.getUser(id);
			if (user.getRole() == 2) {
				response.sendRedirect(request.getContextPath() + "/admin/user/index?err=5");
				return;
			}
		}
		
		
		if (objUserDao.deleteUser(id) > 0) {
			if (userLogin.getId() == id) {
				response.sendRedirect(request.getContextPath() + "/auth/logout");
				return;
			}
			response.sendRedirect(request.getContextPath() + "/admin/user/index?msg=3");
			return;
		}
		response.sendRedirect(request.getContextPath() + "/admin/user/index?err=10");
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
