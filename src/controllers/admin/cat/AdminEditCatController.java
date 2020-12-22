package controllers.admin.cat;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CategoryDao;
import models.Category;
import utils.AuthUtil;
import utils.StringUtil;

public class AdminEditCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminEditCatController() {
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
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?err=3");
			return;
		}
		CategoryDao objCatDao = new CategoryDao();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			if (!objCatDao.checkId(id)) {
				throw new Exception();
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?err=1");
			return;
		}
		Category objCat = objCatDao.getCat(id);
		request.setAttribute("cat", objCat);
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/cat/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		CategoryDao objCatDao = new CategoryDao();
		ArrayList<Category> listCat = objCatDao.getAll();
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("catid"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?err=1");
			return;
		}
		String name = request.getParameter("name");
		Category objCat = new Category(id, name);
		request.setAttribute("cat", objCat);
		if (name.equals("")) {
			request.setAttribute("err", "Tên danh mục không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/cat/edit.jsp");
			rd.forward(request, response);
			return;
		}
		
		

		if (listCat.size() > 0) {
			for (Category category : listCat) {
				if (name.equalsIgnoreCase(category.getName())) {
					request.setAttribute("err", "Tên danh mục đã tồn tại!");
					RequestDispatcher rd = request.getRequestDispatcher("/views/admin/cat/edit.jsp");
					rd.forward(request, response);
					return;
				}
			}
		}

		if (objCatDao.editCat(objCat) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=2");
			return;
		}
		
	}

}
