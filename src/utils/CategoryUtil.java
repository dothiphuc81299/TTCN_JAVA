package utils;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CategoryDao;
import models.Category;

public class CategoryUtil {
	public static void getCat(HttpServletRequest request, HttpServletResponse response) throws IOException{
		ArrayList<Category> listCat = new CategoryDao().getAll();
		request.setAttribute("listcat", listCat);
	}
}
