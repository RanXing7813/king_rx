package cn.com.king.web.action.log;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTooles
{
  public static boolean hasChinese(String str)
  {
    if (str == null) {
      return false;
    }

    return (str.getBytes().length != str.length());
  }

  public static String getStringFromUID(Object uuid)
  {
    String _uuid = parseStr(uuid);
    if (_uuid.length() > 2) {
      _uuid = _uuid.substring(1, _uuid.length() - 1);
    }
    return _uuid;
  }

  public static boolean isNullOrEmpty(Object para)
  {
    return ((para == null) || (para.toString().trim().equals("")));
  }

  public static String parseStr(Object para)
  {
    String res = "";
    if (para == null) {
      return "";
    }
    res = para.toString().trim();
    return res;
  }

  public static Timestamp paraseTimestamp(Object para)
  {
    if (para instanceof Timestamp) {
      return ((Timestamp)para);
    }
    return null;
  }

  public static Date parseStrToDate(String dateStr, String splitStr)
  {
    String eL = "[0-9]{4}" + splitStr + "[0-9]{2}" + splitStr + "[0-9]{2}";
    Pattern p = Pattern.compile(eL);
    Matcher m = p.matcher(dateStr.trim());
    boolean dateFlag = m.matches();
    Date date = null;
    if (dateFlag)
    {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      try {
        return sdf.parse(dateStr.trim());
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    return date;
  }

  public static Date parseStrToDate(Object dateStr, String splitStr) {
    return parseStrToDate(parseStr(dateStr), splitStr);
  }

  public static int parseInt(Object para)
  {
    if (isNullOrEmpty(para)) {
      return 0;
    }
    return Integer.parseInt(para.toString());
  }

  public static long parseLong(Object para)
  {
    if (isNullOrEmpty(para)) {
      return 0L;
    }
    return Long.parseLong(para.toString());
  }

  public static double parseFloat(Object para)
  {
    if (isNullOrEmpty(para)) {
      return 0.0D;
    }
    return new BigDecimal(para.toString()).doubleValue();
  }

  public static BigDecimal parseBigDecimal(Object para)
  {
    if (isNullOrEmpty(para)) {
      return new BigDecimal(0);
    }
    return new BigDecimal(para.toString());
  }

  public static Date parseDate(Object para)
  {
    if ((!(isNullOrEmpty(para))) && (para instanceof Date)) {
      return ((Date)para);
    }
    return null;
  }

  public static Date parseTimeDate(Object para) {
    if ((!(isNullOrEmpty(para))) && (para instanceof Timestamp)) {
      Timestamp time = (Timestamp)para;
      
      return new Date(time.toString());
    }
    return null;
  }

  public static String getSerialCode(Object num, int length)
  {
    int count = length - String.valueOf(num).length();
    StringBuffer code = new StringBuffer();
    if (count < 0) {
      throw new RuntimeException("流水号过长异常.");
    }

    for (int i = 0; i < count; ++i) {
      code.append("0");
    }
    code.append(num);
    return code.toString();
  }

  public static int[] maoPaoSort(int[] x)
  {
    for (int i = 0; i < x.length; ++i) {
      for (int j = i + 1; j < x.length; ++j) {
        if (x[i] > x[j]) {
          int temp = x[i];
          x[i] = x[j];
          x[j] = temp;
        }
      }
    }
    return x;
  }

  public static String getBillStatusDesc(String status_flag)
  {
    if ("2".equals(status_flag))
      return "结束";
    if ("1".equals(status_flag))
      return "起始";
    if ("0".equals(status_flag))
      return "正常";
    if ("-1".equals(status_flag)) {
      return "驳回";
    }
    return "";
  }
}
