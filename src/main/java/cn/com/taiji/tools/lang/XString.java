package cn.com.taiji.tools.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XString {
	public static final String CHAR_SECIAL_IOS_SPACES = " ";

	/**
	 * @param cs
	 *            字符串
	 * @return 是不是为空白字符串
	 */
	public boolean isBlank(CharSequence cs) {
		if (null == cs)
			return true;
		int length = cs.length();
		for (int i = 0; i < length; i++) {
			if (!(Character.isWhitespace(cs.charAt(i))))
				return false;
		}
		return true;
	}

	/**
	 * @param cs
	 *            字符串
	 * @return 是不是为空字符串
	 */
	public boolean isEmpty(CharSequence cs) {
		return null == cs || cs.length() == 0;
	}

	public List<String> toList(String... str) {
		List list = new ArrayList();
		for (String s : str) {
			list.add(s);
		}
		return list;
	}

	public Map<String, String> toMap(String petx, String... str) {
		Map map = new HashMap();
		for (String s : str) {
			String[] tmp = s.split(petx);
			if (s.indexOf(petx) < 0 || tmp.length != 2) {
				//throw Exception("toMap格式不对要求为:key:value,key:value....");
			}
			map.put(tmp[0], tmp[1]);

		}
		return map;
	}

	/**
	 * 传入String值如下 test1=1 test2=1 test3=1 封装Map返回
	 * 
	 * @param petx
	 * @return
	 */
	public Map<String, String> toMap(String petx) {
		Map map = new HashMap();
		String[] str = petx.split("\n");
		for (String s : str) {
			String[] tmp = s.split("=");
			map.put(tmp[0], tmp.length > 1 ? tmp[1] : "");
		}
		return map;
	}

	/**
	 * 传入Map值,返回如下值 test1=1 test2=1 test3=1
	 * 
	 * @param map
	 * @return
	 */
	public String toString(Map map) {
		Iterator<String> iter = map.keySet().iterator();
		String petx = "";
		while (iter.hasNext()) {
			String key = iter.next();
			String value = (String) map.get(key);
			petx += key + "=" + value + "\n";
		}
		return petx;
	}

	public String toString(Throwable cause) {
		if (cause == null) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		cause.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * 把InputStream转换成String
	 * 
	 * @param is
	 * @return
	 */
	public String toString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	/**
	 * 
	 * @名称：字符串截取
	 * @作者：
	 * @创建时间： May 3, 2014 3:31:47 PM
	 * @说明： str==输入值如:s1,s2,s3,s4,s5,s6 124124314 c==截取值如：, null i==截取位如：3
	 *      last==省略语如：... (特殊可以是"等%i人") 返回值：s1,s2,s3...
	 * 
	 * 
	 */
	public String toString(String str, String c, int i, String last) {
		StringBuilder strb = new StringBuilder();
		if (this.isEmpty(str)) {
			return "";
		}
		try {
			if (this.isEmpty(c)) {
				if (str.length()> i) {
					strb.append(str.substring(0, (i-1)) + last);
				} else {
					strb.append(str);
				}
			} else {
				String s[] = str.split(c);
				for (int j = 0; j < i && j < s.length; j++) {
					if (j == i - 1) {
						strb.append(s[j]);
					} else {
						strb.append(s[j] + ",");
					}
				}
				last = last == null ? "" : last.replaceFirst("%i", Integer.toString(s.length));
				strb.append(last);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strb.toString();
	}

	/**
	 * 去除空格和特殊符号
	 * 
	 * @param s
	 * @return
	 */
	public String trimSecialChar(String s) {
		if (s != null) {
			return s.trim().replaceAll(CHAR_SECIAL_IOS_SPACES, " ");
		}
		return null;
	}

	/**
	 * 
	 * @名称：去除去除字符串中的空格、回车、换行符、制表符
	 * @作者：钟代冬
	 * @创建时间： May 7, 2014 4:12:15 PM
	 * @说明：
	 */
	public static String replaceSpecialChar(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 
	 * @名称：空值处理
	 * @作者：钟代冬
	 * @创建时间： Jun 10, 2014 3:40:41 PM
	 * @说明：
	 */
	public String dealEmptyValue(String value) {
		if (value == null || "null".equals(value) || "NULL".equals(value)) {
			return "";
		} else {
			return value;
		}
	}

	/**
	 * 
	 * @功能描述:判断输入的字符串是否含有特殊字符
	 * @作者:郭凯
	 * @创建时间:2014年8月5日
	 * @说明：
	 */
	public boolean isHasSpecialChar(String str) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @功能描述:去除字符串中的特殊字符
	 * @作者:郭金林
	 * @创建时间:2014年8月5日
	 * @说明：
	 */
	public String delSpecialChar(String str) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，？ ]";
		Pattern p = Pattern.compile(regEx);
		str = p.matcher(str).replaceAll("");
		return str;
	}

	/**
	 * 
	 * @名称：字符串截取
	 * @作者：钟代冬
	 * @创建时间： Aug 15, 2014 6:32:34 PM
	 * @说明：截取指定字节长度的字符串，不能返回半个汉字</b> 例如： 如果网页最多能显示17个汉字，那么 length 则为 34
	 */
	public static String getSubString(String str, int length) {
		int count = 0;
		int offset = 0;
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] > 256) {
				offset = 2;
				count += 2;
			} else {
				offset = 1;
				count++;
			}
			if (count == length) {
				return str.substring(0, i + 1) + "...";
			}
			if ((count == length + 1 && offset == 2)) {
				return str.substring(0, i) + "...";
			}
		}
		return str;
	}

	/**
	 * 
	 * @名称：判断是否数字
	 * @作者：钟代冬
	 * @创建时间： Aug 18, 2014 10:52:04 PM
	 * @说明：
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static boolean like(String test, String key) {
		if (test.length() < 2) {
			return false;
		} else if (key.equals(test)) {
			return true;
		} else if (test.startsWith("%") && test.endsWith("%")) {
			String tmp = test.substring(0, test.length() - 1);
			tmp = tmp.substring(1);
			int site = key.indexOf(tmp);
			if (site > -1) {
				return true;
			} else {
				return false;
			}
		} else if (test.startsWith("%")) {
			String tmp = test.substring(1);
			int site = key.indexOf(tmp);
			int length = tmp.length();
			if (site + length == key.length()) {
				return true;
			} else {
				return false;
			}
		} else if (test.endsWith("%")) {
			String tmp = test.substring(0, test.length() - 1);
			int site = key.indexOf(tmp);
			if (site == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean like1(String test, String key) {
		Pattern pattern = Pattern.compile(test);
		Matcher matcher = pattern.matcher(key);
		return matcher.matches();
	}

	public static void main(String args[]) {
		System.out.println(XString.like1("test.*q", "test.test"));
	}
}
