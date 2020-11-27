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

public class AdminAddCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminAddCatController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login");
			return;
		}
		if (AuthUtil.userAuth(request)) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?err=2");
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/views/admin/cat/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		CategoryDao objCatDao = new CategoryDao();
		ArrayList<Category> listCat = objCatDao.getAll();
		String name = request.getParameter("name");
		request.setAttribute("name", name);
		if (name.equals("")) {
			request.setAttribute("err", "Tên danh mục không được rỗng!");
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/cat/add.jsp");
			rd.forward(request, response);
			return;
		}
		
		
		
		if (listCat.size() > 0) {
			for (Category category : listCat) {
				if (name.equalsIgnoreCase(category.getName())) {
					request.setAttribute("err", "Tên danh mục đã tồn tại!");
					RequestDispatcher rd = request.getRequestDispatcher("/views/admin/cat/add.jsp");
					rd.forward(request, response);
					return;
				}
			}
		}
		
		Category objCat = new Category(0, name);
		if (objCatDao.addCat(objCat) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=1");
			return;
		}
		
	}

}
