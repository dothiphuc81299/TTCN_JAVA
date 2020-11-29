package controllers.admin.contact;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.ContactDao;
import models.Contact;
import utils.AuthUtil;
import utils.DefineUtil;

public class AdminIndexContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminIndexContactController() {
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
		if (request.getParameter("msg") != null) {
			request.setAttribute("success", "Xoá thành công!");
		}
		if (request.getParameter("err") != null) {
			int err = Integer.parseInt(request.getParameter("err"));
			if (err == 1) {
				request.setAttribute("err", "Có lỗi xảy ra!");
			} else {
				request.setAttribute("err", "ID không tồn tại!");
			}
		}
		ContactDao objContactDao = new ContactDao();
		int numberPage = DefineUtil.NUMBER_PER_PAGE;
		int first = 0, last = 0, pages = 1, totalPages = 1;
		int total = objContactDao.getCount();
		if (total % numberPage == 0) {
			totalPages = total / numberPage;
		} else {
			totalPages = total / numberPage + 1;
		}
		if (request.getParameter("pages") != null) {
			try {
				pages = Integer.parseInt(request.getParameter("pages"));
				if (pages < 1 || pages > totalPages) {
					response.sendRedirect(request.getContextPath() + "/error/admin/loi-2");
					return;
				}
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath() + "/error/admin/loi-1");
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
		ArrayList<Contact> listContact = objContactDao.getContact(first, last);
		request.setAttribute("listcontact", listContact);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("total", total);
		request.setAttribute("pages", pages);
		request.setAttribute("first", first);
		request.setAttribute("last", last);
		if (request.getParameter("searchContent") != null) {
			String searchContent = request.getParameter("searchContent");
			request.setAttribute("searchContent", searchContent);
			ArrayList<Contact> listContact2 = objContactDao.getSearchContact(searchContent);
			request.setAttribute("listcontact", listContact2);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/contact/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
