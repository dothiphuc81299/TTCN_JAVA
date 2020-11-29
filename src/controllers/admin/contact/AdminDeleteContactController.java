package controllers.admin.contact;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.ContactDao;
import utils.AuthUtil;

public class AdminDeleteContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminDeleteContactController() {
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
			response.sendRedirect(request.getContextPath() + "/admin/contact/index");
			return;
		}
		ContactDao objContactDao = new ContactDao();
		if (request.getParameter("id") != null) {
			int id = 0;
			try {
				id = Integer.parseInt(request.getParameter("id"));
				if (!objContactDao.checkId(id)) {
					throw new Exception();
				}
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath() + "/admin/contact/index?err=2");
				return;
			}
			if (objContactDao.deleteContact(id) > 0) {
				response.sendRedirect(request.getContextPath() + "/admin/contact/index?msg=1");
				return;
			}
		}
		response.sendRedirect(request.getContextPath() + "/admin/contact/index?err=1");
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
