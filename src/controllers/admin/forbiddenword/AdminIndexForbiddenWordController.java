package controllers.admin.forbiddenword;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.ForbiddenWordDao;
import models.ForbiddenWord;
import utils.AuthUtil;
import utils.DefineUtil;

public class AdminIndexForbiddenWordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminIndexForbiddenWordController() {
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
			int msg = Integer.parseInt(request.getParameter("msg"));
			if (msg == 1) {
				request.setAttribute("success", "Thêm thành công!");
			} else if (msg == 2) {
				request.setAttribute("success", "Sửa thành công!");
			} else {
				request.setAttribute("success", "Xoá thành công!");
			}
		}
		if (request.getParameter("err") != null) {
			int err = Integer.parseInt(request.getParameter("err"));
			if (err == 1) {
				request.setAttribute("err", "ID không tồn tại!");
			} else if (err == 2) {
				request.setAttribute("err", "Không có quyền thực hiện chức năng này!");
			} else {
				request.setAttribute("err", "Có lỗi xảy ra");
			}
		}
		ForbiddenWordDao objWordDao = new ForbiddenWordDao();
		int numberPage = DefineUtil.NUMBER_PER_PAGE2;
		int first = 0, last = 0, pages = 1, totalPages = 1;
		int total = objWordDao.getCount();
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
		ArrayList<ForbiddenWord> listWord = objWordDao.getWord(first, last);
		request.setAttribute("listword", listWord);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("total", total);
		request.setAttribute("pages", pages);
		request.setAttribute("first", first);
		request.setAttribute("last", last);
		if (request.getParameter("searchContent") != null) {
			String searchContent = request.getParameter("searchContent");
			request.setAttribute("searchContent", searchContent);
			ArrayList<ForbiddenWord> listWord2 = objWordDao.getSearch(searchContent);
			request.setAttribute("listword", listWord2);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/forbiddenword/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
