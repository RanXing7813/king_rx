package cn.com.taiji.tools.code;

import java.security.MessageDigest;

import cn.com.taiji.tools.code.encoding.Md5PasswordEncoder;

/**
 * MD5编码服务类
 * 
 */
public class MD5 {

	/**
	 * encode s to MD5 string
	 * @param s
	 * @return
	 */
	public static String encode(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String encode(String username,String password) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
			md5.setEncodeHashAsBase64(false);
		return md5.encodePassword(password, username);//混合模式
	} 
	public static void main(String args[]){
		MD5.encode("xxd11", "xxd@123456");
	}
	
}