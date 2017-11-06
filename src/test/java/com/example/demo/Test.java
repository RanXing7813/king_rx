package com.example.demo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	
	
	
	public static void main(String[] args) {
		 System.out.println(System.getProperty("line.separator"));;
		 
		try {
		  //get URL string from command line or use default
			 String urlString;
			  if(args.length>0) urlString = args[0];
			  else urlString = "http://java.sun.com";
			  
			 //open reader for URL
			InputStreamReader in = new InputStreamReader(new URL(urlString).openStream());
			
			//read contents into string builder
			StringBuilder input = new StringBuilder();
			int ch;
			while((ch = in.read()) != -1 )
				input.append((char) ch);
			
			//search for all occurrences of pattern
			//匹配(\"[^\"]*\"|[^\\s>]*)  的意思是,   已"开头 中间无"字符 已"结尾   或者是 非空格和>字符的  
			String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*>";
			Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
			
			Matcher matcher = pattern.matcher(input);
			
			//find方法  一个一个找
			while(matcher.find()){
				int start = matcher.start();
				int end = matcher.end();
				String match = input.substring(start, end);
				System.out.println(match);
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	}
}
