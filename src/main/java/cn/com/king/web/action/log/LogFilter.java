package cn.com.king.web.action.log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class LogFilter implements Filter{
  private LogUtils logUtils;

  public void destroy()
  {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException{
//    chain.doFilter(request, response);
//    HttpServletRequest req = (HttpServletRequest)request;
//    String requestUrl = req.getRequestURI();
//    Map log = this.logUtils.parseUrl(requestUrl);
//    if (log == null) return;
//    log.put("LOG_IP", req.getRemoteAddr());
//    log.put("LOG_URL", requestUrl);
//    log.put("LOGIN_USER", getCurrentUserID(req));
//    log.put("USER_NAME", getCurrentUserName(req));
//    log.put("LOG_DATE", getCurrDate());
//    log.put("OPER_PARAM", getRequestParameterStrList(req));
//    try {
//      this.logUtils.insertlog(log);
//    } catch (Exception e) {
//      System.out.println("日志记录失败！");
//    }
  }
  public static java.util.Date getCurrDate()
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      return dateFormat.parse(dateFormat.format(new java.util.Date()));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getCurrentUserID(HttpServletRequest request)
  {
    Map userInfo = getCurrUserInfo(request);
    String userId = "";
    if (!(StringTooles.isNullOrEmpty(userInfo))) {
      userId = StringTooles.parseStr(userInfo.get("user_id"));
    }
    return userId;
  }

  public String getCurrentUserName(HttpServletRequest request)
  {
    Map userInfo = getCurrUserInfo(request);
    String loginName = "";
    if (!(StringTooles.isNullOrEmpty(userInfo)))
      loginName = StringTooles.parseStr(userInfo.get("login_name"));
    else {
      loginName = "匿名";
    }
    return loginName;
  }

  public Map<Object, Object> getCurrUserInfo(HttpServletRequest request)
  {
    return ((Map)request.getSession().getAttribute("userInfo"));
  }

  private String getRequestParameterStrList(HttpServletRequest request)
  {
    Map<String, String[]> params = request.getParameterMap();
    String paramlist = "";
    for (String key : params.keySet()) {
      String[] values = (String[])params.get(key);
      for (int i = 0; i < values.length; ++i) {
        String value = values[i];
        paramlist = paramlist + key + "=" + value + "&";
      }
    }
    return paramlist;
  }

  @Override
  public void init(FilterConfig config) throws ServletException
  {
    ServletContext context = config.getServletContext();
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
    DataSourceImpl accessService = (DataSourceImpl)ctx.getBean("accessService");
    this.logUtils = new LogUtils();
    this.logUtils.setAccessService(accessService);
    this.logUtils.loadConfig("log-key.conf");
  }

}