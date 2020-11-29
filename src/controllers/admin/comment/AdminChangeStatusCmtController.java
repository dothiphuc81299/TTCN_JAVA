package controllers.admin.comment;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CommentDao;

public class AdminChangeStatusCmtController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminChangeStatusCmtController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		CommentDao objCmtDao = new CommentDao();
		String img = request.getParameter("aImg");
		int id = Integer.parseInt(request.getParameter("aId"));
		if (img.equals(request.getContextPath() + "/templates/admin/assets/img/active.gif")) {
			if (objCmtDao.editStatus(0, id) > 0) {
				out.print(request.getContextPath() + "/templates/admin/assets/img/deactive.gif");
			}
		} else {
			if (objCmtDao.editStatus(1, id) > 0) {
				out.print(request.getContextPath() + "/templates/admin/assets/img/active.gif");
			}
		}
	}

}
