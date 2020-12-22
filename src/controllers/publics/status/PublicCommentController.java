package controllers.publics.status;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.CommentDao;
import daos.UserDao;
import models.Comment;
import models.User;
import utils.StringUtil;

public class PublicCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PublicCommentController() {
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
		HttpSession session = request.getSession();
		CommentDao objCmtDao = new CommentDao();
		int idSong = Integer.parseInt(request.getParameter("aId"));
		User objUser = (User) session.getAttribute("publiclogin");
		String cmt = request.getParameter("aCmt");
		if (cmt.equals("")) {
			out.print("<p style='color: red; font-style: italic'>Vui lòng nhập nội dung trước khi bình luận!</p>");
			return;
		}
		
		if (StringUtil.checkWord(cmt)) {
			out.print("<p style='color: red; font-style: italic'>Bình luận của bạn không hợp lệ!</p>");
			return;
		}
		
		Comment objCmt = new Comment(objUser.getId(), idSong, cmt);
		if (objCmtDao.addComment(objCmt) > 0) {
			ArrayList<Comment> listCmt = objCmtDao.getCmtShow(idSong);
			if (listCmt.size() > 0) {
				for (Comment comment : listCmt) {
					int idUser = comment.getIdUser();
					User user = new UserDao().getUser(idUser);
					out.print("<div style=\"margin-top: 10px\"><span style=\"color: red\">" + user.getFullName()
							+ ":</span> <span style=\"margin: 0px 10px\">" + comment.getComment()
							+ "</span> <span style=\"color: #FF66CC\">" + comment.getDateCreate() + "</span></div>");
				}
			} else {
				out.print(
						"<div style=\"margin-top: 10px\">Chưa có bình luận nào! Hãy là người bình luận đầu tiên!</div>");
			}
		}

	}

}
