package controllers.admin.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.UserDao;
import models.User;
import utils.AuthUtil;
import utils.StringUtil;

public class AdminEditUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminEditUserController() {
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
		int id = 0;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("login");
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
				if (userLogin.getId() != id) {
					response.sendRedirect(request.getContextPath() + "/admin/user/index?err=2");
					return;
				}
			}
		}
		User objUser = objUserDao.getUser(id);
		request.setAttribute("user", objUser);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("login");
		UserDao objUserDao = new UserDao();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/user/index?err=1");
			return;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String fullname = request.getParameter("fullname");
		int role = 0;
		if (userLogin.getRole() == 2) {
			role = 2;
		}
		if (userLogin.getRole() == 1) {
			role = 1;
		}
		if (request.getParameter("role") != null) {
			role = Integer.parseInt(request.getParameter("role"));
		}
		User objUser = new User(id, username, password, fullname, role);
		request.setAttribute("user", objUser);
		User user = objUserDao.getUser(id);

		if (password.equals("")) {
			objUser.setPassword(user.getPassword());
		} else {
			if (!password.equals(repassword)) {
				request.setAttribute("err", "Password không trùng khớp!");
				RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/edit.jsp");
				rd.forward(request, response);
				return;
			}
			objUser.setPassword(StringUtil.md5(password));
		}

		if (fullname.equals("")) {
			request.setAttribute("err", "Fullname không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/edit.jsp");
			rd.forward(request, response);
			return;
		}
		
		

		if (objUserDao.editUser(objUser) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/user/index?msg=2");
			return;
		}

	}

}
