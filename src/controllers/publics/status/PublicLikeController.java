package controllers.publics.status;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.LikeDao;
import models.Like;
import models.User;

public class PublicLikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PublicLikeController() {
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
		LikeDao objLikeDao = new LikeDao();
		User objUser = (User) session.getAttribute("publiclogin");
		int idSong = Integer.parseInt(request.getParameter("aId"));
		if (objLikeDao.check(idSong, objUser.getId())) {
			if (objLikeDao.checkLiked(idSong, objUser.getId())) {
				Like objLike = new Like(0, objUser.getId(), idSong, 1);
				if (objLikeDao.editLikeStatus(objLike) > 0) {
					int like = objLikeDao.getCount(idSong);
					out.print("<span style=\"margin-right: 20px\">Lượt like: " + like + "</span><a href=\"javascript:void(0)\" "
							+ "onclick=\"likeAction(" + idSong +")\"><i style=\"color: black\" class=\"fa fa-thumbs-up\"></i> Like</a>");
				}
			} else {
				Like objLike = new Like(0, objUser.getId(), idSong, 0);
				if (objLikeDao.editLikeStatus(objLike) > 0) {
					int like = objLikeDao.getCount(idSong);
					out.print("<span style=\"margin-right: 20px\">Lượt like: " + like + "</span><a href=\"javascript:void(0)\" "
							+ "onclick=\"likeAction(" + idSong +")\"><i style=\"color: red\" class=\"fa fa-thumbs-up\"></i> Liked</a>");
				}
			}
		} else {
			Like objLike = new Like(objUser.getId(), idSong);
			if (objLikeDao.addLike(objLike) > 0) {
				int like = objLikeDao.getCount(idSong);
				out.print("<span style=\"margin-right: 20px\">Lượt like: " + like + "</span><a href=\"javascript:void(0)\" "
						+ "onclick=\"likeAction(" + idSong +")\"><i style=\"color: red\" class=\"fa fa-thumbs-up\"></i> Liked</a>");
			}
		}
	}

}
