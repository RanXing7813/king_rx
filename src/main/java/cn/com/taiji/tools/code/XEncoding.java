package cn.com.taiji.tools.code;

public class XEncoding {
	public static final String defaultEncoding = "utf-8";

	public String defaultEncoding() {
		return defaultEncoding;
	}

	public String Base64Encode(String str) {
		return new String(Base64.encode(str.getBytes()));
	}

	public String Base64Decode(String str) {
		return new String(Base64.decode(str.getBytes()));
	}

	public String MD5Encode(String str) {
		return MD5.encode(str);
	}
	public String MD5Encode(String username,String password) {
		return MD5.encode(username,password);
	}

	public String getUUID32() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}
}
