package utils;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import constants.GlobalConstant;

public class FileUtil {

	public static String rename(String fileName) {
		String nameFile = "";
		if (!fileName.isEmpty()) {
			String[] arrImg = fileName.split("\\.");
			String duoiFileImg = arrImg[arrImg.length - 1];

			for (int i = 0; i < (arrImg.length - 1); i++) {
				if (i == 0) {
					nameFile = arrImg[i];
				} else {
					nameFile += "-" + arrImg[i];
				}
			}
			nameFile = nameFile + "-" + System.nanoTime() + "." + duoiFileImg;
		}
		return nameFile;
	}

	public static String getName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	// Method upload file
	public static String upload(String nameInput, HttpServletRequest request) throws IOException, ServletException {
		Part filePart = request.getPart(nameInput);
		String fileName = rename(filePart.getSubmittedFileName());
		if (!fileName.equals("")) {
			String dirPath = request.getServletContext().getRealPath("") + GlobalConstant.DIR_UPLOAD;
			File saveDir = new File(dirPath);
			if (!saveDir.exists()) {
				saveDir.mkdirs();
			}
			String filePath = dirPath + File.separator + fileName;
			filePart.write(filePath);
		}
		return fileName;
	}
	
	public static String uploadMp3(String nameInput, HttpServletRequest request) throws IOException, ServletException {
		Part filePart = request.getPart(nameInput);
		String fileName = rename(filePart.getSubmittedFileName());
		if (!fileName.equals("")) {
			String dirPath = request.getServletContext().getRealPath("") + GlobalConstant.DIR_UPLOAD2;
			File saveDir = new File(dirPath);
			if (!saveDir.exists()) {
				saveDir.mkdirs();
			}
			String filePath = dirPath + File.separator + fileName;
			filePart.write(filePath);
		}
		return fileName;
	}
	
	// Method delete file
	public static boolean delFile(String fileName, HttpServletRequest request) {
		if (!fileName.equals("")) {
			String filePath = request.getServletContext().getRealPath("") + GlobalConstant.DIR_UPLOAD + File.separator + fileName;
			File file = new File(filePath);
			return file.delete();
		}
		return false;
	}

	public static boolean delFileMp3(String fileName, HttpServletRequest request) {
		if (!fileName.equals("")) {
			String filePath = request.getServletContext().getRealPath("") + GlobalConstant.DIR_UPLOAD2 + File.separator + fileName;
			File file = new File(filePath);
			return file.delete();
		}
		return false;
	}
	
}
