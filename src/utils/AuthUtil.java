package utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;

public class AuthUtil {
	
	public static boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null) {
			return false;
		}
		return true;
	}
	
	public static void isLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login");
			return;
		}
	}
	
	// kiểm tra phân quyền user: Nếu là User
	public static boolean userAuth(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("login") != null) {
			User objUser = (User) session.getAttribute("login");
			if (objUser.getRole() == 0) {
				return true;
			}
		}
		return false;
	}
}
