package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.util.regex.Pattern;

import daos.ForbiddenWordDao;

public class StringUtil {

	public static boolean checkWord(String str) {
		ForbiddenWordDao objWordDao = new ForbiddenWordDao();
		String[] arr = str.split("\\s");
		for (int i = 0; i <= arr.length - 1; i++) {
			if (objWordDao.checkWord(arr[i])) {
				return true;
			}
		}
		return false;
	}

	public static String tachChuoi(Timestamp time) {
		String kq = "";
		String str = time.toString();
		String[] arr = str.split("\\s");
		String s = arr[0];
		String[] arr2 = s.split("\\-");
		for (int i = arr2.length - 1; i >= 0; i--) {
			if (i == 0) {
				kq += arr2[i];
			} else {
				kq += arr2[i] + "/";
			}
		}
		return kq;
	}

	// Dùng để mã hoá password
	public static String md5(String str) {
		MessageDigest md;
		String result = "";
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			BigInteger bi = new BigInteger(1, md.digest());

			result = bi.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String makeSlug(String title) {
		String slug = Normalizer.normalize(title, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		slug = pattern.matcher(slug).replaceAll("");
		slug = slug.toLowerCase();
		// Thay đ thành d
		slug = slug.replaceAll("đ", "d");
		// Xóa các ký tự đặt biệt
		slug = slug.replaceAll("([^0-9a-z-\\s])", "");
		// Thay space thành dấu gạch ngang
		slug = slug.replaceAll("[\\s]", "-");
		// Đổi nhiều ký tự gạch ngang liên tiếp thành 1 ký tự gạch ngang
		slug = slug.replaceAll("(-+)", "-");
		// Xóa các ký tự gạch ngang ở đầu và cuối
		slug = slug.replaceAll("^-+", "");
		slug = slug.replaceAll("-+$", "");
		return slug;
	}

}
