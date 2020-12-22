package controllers.admin.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.UserDao;
import models.User;
import utils.AuthUtil;
import utils.StringUtil;

public class AdminAddUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminAddUserController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login");
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		UserDao objUserDao = new UserDao();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String rePassword = request.getParameter("repassword");
		String fullname = request.getParameter("fullname");
		int role = 0;
		if (request.getParameter("role") != null) {
			role = Integer.parseInt(request.getParameter("role"));
		}
		request.setAttribute("username", username);
		request.setAttribute("fullname", fullname);
		if (username.equals("")) {
			request.setAttribute("err", "Username không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/add.jsp");
			rd.forward(request, response);
			return;
		}
		if (objUserDao.checkUser(username)) {
			request.setAttribute("err", "Username đã tồn tại!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		if (password.equals("")) {
			request.setAttribute("err", "Password không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		if (!rePassword.equals(password)) {
			request.setAttribute("err", "Password không trùng khớp!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		if (fullname.equals("")) {
			request.setAttribute("err", "Fullname không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		
		
		password = StringUtil.md5(password); // Mã hoá password
		User objUser = new User(0, username, password, fullname, role);
		if (objUserDao.addUser(objUser) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/user/index?msg=1");
			return;
		}
		
	}

}
