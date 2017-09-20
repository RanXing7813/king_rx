package cn.com.king.web.action.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogUtils
{
  private static final String LOG_LEVEL_FILTER_KEY = "#LEVEL:filter";
  private static  DataSourceImpl accessService;
  private static  Map<String, Map<String, String>> conf;
  private static final String SEQ_LOG_ID = "SEQ_LOG_ID";

  public LogUtils()
  {
    this.conf = new HashMap();
  }

  public static  void setAccessService(DataSourceImpl accessService) {
    accessService = accessService;
  }

  public  void loadConfig(String name) {
    BufferedReader reader = null;
    Map level = null;
    String line = "";
    try {
      reader = new BufferedReader(
        new InputStreamReader(super
        .getClass().getClassLoader().getResourceAsStream(name), "utf8"));
      while ((line = reader.readLine()) != null)
        if (!line.isEmpty())
          if (line.startsWith("#")) {
            level = new HashMap();
            this.conf.put(line, level);
          }
          else {
            String[] les = line.split("=");
            level.put(les[0], les[1]);
          }
    }
    catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (reader != null) reader.close();
      } catch (IOException e) {				
        e.printStackTrace();
      }
    }
  }

  public static Map<String, Object> parseUrl(String url) {
    Map<String, Object> log = null;   
     if(url.indexOf("!")!=-1)
    for (String regex : (conf.get("#LEVEL:filter")).keySet()) {
      if (matches(regex, url)) {
        log = new HashMap();
        String logCode = url;//.substring(0, url.lastIndexOf("!"));
        String operName = (String)((Map)conf.get("#LEVEL:filter")).get(regex);
        log.put("LOG_CODE", logCode);
        log.put("LOG_OPER", regex);
        log.put("LOG_OPER_NAME", operName);
        log.put("LOG_DETAILS", getLogDetails(logCode, operName));
        break;
      }
    }
    return log;
  }

  private static String getLogDetails(String logCode, String operName) {
    String logCodeDetails = parseLogCode(logCode);
    if (!(logCodeDetails.isEmpty()))
      return logCodeDetails + ":" + operName;
    return "";
  }

  private static String parseLogCode(String logCode) {
    String[] details = new String[conf.size() - 1];
    for (String levelKey : conf.keySet()) {
      if (!(levelKey.equals("#LEVEL:filter"))) {
        int levelIndex = Integer.parseInt(levelKey.substring(levelKey.indexOf(":") + 1));
        for (String mkey : (conf.get(levelKey)).keySet()) {
          if (logCode.indexOf(mkey) != -1)
            details[levelIndex] = ((String)((Map)conf.get(levelKey)).get(mkey));
        }
      }
    }
    return org.apache.commons.lang.StringUtils.join(clearNull(details), ">>");
  }

  private static String[] clearNull(String[] details) {
    List tmp = new ArrayList();
    for (String str : details) {
      if ((str != null) && (str.length() != 0)) {
        tmp.add(str);
      }
    }
    return ((String[])tmp.toArray(new String[0]));
  }

  private static boolean matches(String regex, String url) {
    String regCent = url.substring(url.indexOf("!") + 1);//, url.indexOf(".")
    Pattern pattern = Pattern.compile(regex, 8);
    Matcher matcher = pattern.matcher(regCent);
    return matcher.matches();
  }

  public static  void insertlog(Map<String, Object> log) {
    try {
      int id = accessService.getNextSeq("SEQ_LOG_ID");
      log.put("LOG_ID", Integer.valueOf(id));
      accessService.insert("Log.saveLog", log);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    LogUtils logUtils = new LogUtils();
    logUtils.loadConfig("logkey.conf");
    System.out.println(logUtils.parseUrl("/ND-GZFWPT/umng/crud/PortalsIndexCsontrollerss!reportlogin.action"));
    //System.out.println(logUtils.parseUrl("/ND-GZFWPT/rsm/erm/ErmrisAction!asdf_add.action"));
//    {LOG_CODE=/ND-GZFWPT/LoginAction!login.action, LOG_OPER=^login.*, 
//    		LOG_DETAILS=, LOG_OPER_NAME=登录}
//    {LOG_CODE=/ND-GZFWPT/rsm/erm/ErmrisAction!asdf_add.action, LOG_OPER=.+_add$, 
//  	    LOG_DETAILS=风险源申报、评级管理系统>>企业风险源管理库>>危险品管理:新增页, LOG_OPER_NAME=新增页}
  }
}