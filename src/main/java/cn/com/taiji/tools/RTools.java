package cn.com.taiji.tools;

import com.google.gson.Gson;

import cn.com.taiji.tools.code.XEncoding;
import cn.com.taiji.tools.date.DateTimeUtil;
import cn.com.taiji.tools.lang.XRegex;
import cn.com.taiji.tools.lang.XString;

public class RTools {

	public RTools() {
	}

	static {
		encoding = new XEncoding();
		string = new XString();
		regex = new XRegex();
		json = new Gson();
		dateTimeUtil = new DateTimeUtil();
	}
	public static final XEncoding encoding;
	public static final XString string;
	public static final XRegex regex;
	public static final Gson json;
	public static final DateTimeUtil dateTimeUtil;

}
