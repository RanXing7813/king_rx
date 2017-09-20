package cn.com.taiji.tools.log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.com.king.web.action.log.DataSourceImpl;
import cn.com.taiji.tools.RTools;

/**
 * 
 * 类名称：LogService.java   
 * 类描述：   
 * 创建人：zhongdd   
 * 创建时间：2017年1月10日 下午3:17:09
 * @version
 */
public class LogService  extends XLog {
	private static List<Log_uopr> UOPRLOGLIST = new ArrayList<Log_uopr>();
	private static String uoprsql = "insert into log_uopr (Uopr_id,Uopr_num,Uopr_state,Uopr_sysname,Uopr_class,Uopr_method,Uopr_line,Uopr_btime,Uopr_etime,Uopr_timelen,Uopr_param,Uopr_result,Uopr_user_id,Uopr_user_name,uopr_dept_id,uopr_dept_name) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private List<Log_uopr> secuoprloglist = new ArrayList();
	private final int MAXLOGNUM = 50;
	private DataSourceImpl dataSourceImpl = new DataSourceImpl();

	/**
	 * 
		* 
		* 功能名称：操作开始
		* 参数： 
		* 返回值：
		* 作者: zhongdd
		* 创建时间: 2017年1月10日 下午3:17:18
		* 说明:
	 */
	public LogBean begin(Object... param) {
		return super.begin(param);
	}

	/**
	 * 
		* 功能名称：操作结束
		* 参数： 
		* 返回值：Log_uopr
		* 作者: zhongdd
		* 创建时间: 2017年1月10日 下午3:17:30
		* 说明:
	 */
	public Log_uopr end(int state, LogBean logbean, Object rs, String userId, String deptId) {
		LogBean logbean_end;
		if (state == 1) {
			logbean_end = super.endSuccess(logbean, rs);
		} else {
			logbean_end = super.endFail(logbean, rs);
		}
		Log_uopr uopr = this.saveUoprLog(logbean, logbean_end);
		// 操作人信息
		uopr.setUopr_user_id(userId);
		uopr.setUopr_dept_id(deptId);
		addToUOPRCache(uopr);
		// HNTools.dao.insert(uopr);
		return uopr;
	}

	/**
	 * 
		* 功能名称：操作结束成功
		* 参数： 
		* 返回值：Log_uopr
		* 作者: zhongdd
		* 创建时间: 2017年1月10日 下午3:17:42
		* 说明:
	 */
	public Log_uopr endSuccess(LogBean logbean, Object rs, String userId, String deptId) {
		LogBean logbean_end = super.endSuccess(logbean, rs);
		Log_uopr uopr = this.saveUoprLog(logbean, logbean_end);
		// 操作人信息
		uopr.setUopr_user_id(userId);
		uopr.setUopr_dept_id(deptId);
		addToUOPRCache(uopr);
		return uopr;
	}

	/**
	 * 
		* 功能名称：操作结束失败
		* 参数： 
		* 返回值：Log_uopr
		* 作者: zhongdd
		* 创建时间: 2017年1月10日 下午3:17:50
		* 说明:
	 */
	public Log_uopr endFail(LogBean logbean, Object rs, String userId, String deptId) {
		LogBean logbean_end = super.endFail(logbean, rs);
		Log_uopr uopr = this.saveUoprLog(logbean, logbean_end);
		// 操作人信息
		uopr.setUopr_user_id(userId);
		uopr.setUopr_dept_id(deptId);
		addToUOPRCache(uopr);
		return uopr;
	}

	/**
	 * 
		* 功能名称：获取日志编号
		* 参数： 
		* 返回值：String
		* 作者: zhongdd
		* 创建时间: 2017年1月10日 下午3:17:57
		* 说明:
	 */
	private String getNum() {
		String data_num = RTools.encoding.getUUID32();
		return data_num;
	}

	/**
	 * 
		* 功能名称：保存操作日志表
		* 参数： 
		* 返回值：Log_uopr
		* 作者: zhongdd
		* 创建时间: 2017年1月10日 下午3:18:04
		* 说明:
	 */
	private Log_uopr saveUoprLog(LogBean logbean_begin, LogBean logbean_end) {
		Log_uopr uopr = new Log_uopr();
		uopr.setUopr_id(logbean_begin.getLog_id());
		// uopr.setUopr_num(this.getNum("S", 5));
		uopr.setUopr_state(logbean_end.getLog_state());
		uopr.setUopr_sysname(getIp());
		uopr.setUopr_class(logbean_end.getLog_class());
		uopr.setUopr_method(logbean_end.getLog_method());
		uopr.setUopr_line(logbean_end.getLog_lineNum());
		uopr.setUopr_btime(logbean_begin.getLog_btime());
		uopr.setUopr_etime(logbean_end.getLog_etime());
		uopr.setUopr_timelen((int) logbean_end.getLog_timeLength());
		uopr.setUopr_param(logbean_begin.getLog_parm());
		uopr.setUopr_result(logbean_end.getLog_result());
		uopr.setUopr_dept_name(getMACNAME());
		return uopr;
	}

	/**
	 * 
		* 功能名称：添加到前台访问操作日志缓存
		* 参数： 
		* 返回值：void
		* 作者: zhongdd
		* 创建时间: 2017年1月10日 下午3:18:21
		* 说明:
	 */
	private void addToUOPRCache(Log_uopr uopr) {
		/* 设置初始值 */
		if (UOPRLOGLIST.size() >= MAXLOGNUM) {
			for (Log_uopr uopr1 : UOPRLOGLIST) {
				secuoprloglist.add(uopr1);
			}
			new UoprThread().start();
			UOPRLOGLIST.clear();
		}
		UOPRLOGLIST.add(uopr);
		//System.out.println("UOPRLOGLIST.size()：" + UOPRLOGLIST.size());
	}

	class UoprThread extends Thread {
		public void run() {
			long start = System.currentTimeMillis();
			Object[] params = null;
			List<Object[]> pList = new ArrayList();
			for (int i = 0; i < secuoprloglist.size(); i++) {
				Log_uopr uopr = (Log_uopr) secuoprloglist.get(i);
				
				params = new Object[16];
				
				params[0] = uopr.getUopr_id();
				params[1] = uopr.getUopr_num();
				params[2] = uopr.getUopr_state();
				params[3] = uopr.getUopr_sysname();
				params[4] = uopr.getUopr_class();
				params[5] = uopr.getUopr_method();
				params[6] = uopr.getUopr_line();
				Timestamp btime = new Timestamp(uopr.getUopr_btime().getTime());
				Timestamp etime = new Timestamp(uopr.getUopr_etime().getTime());
				params[7] = btime;
				params[8] = etime;
				params[9] = uopr.getUopr_timelen();
				params[10] = uopr.getUopr_param();
				params[11] = uopr.getUopr_result();
				params[12] = uopr.getUopr_user_id();
				params[13] = uopr.getUopr_user_name();
				params[14] = uopr.getUopr_dept_id();
				params[15] = uopr.getUopr_dept_name();
				
				pList.add(params);
			}
			JdbcTemplate drds = new JdbcTemplate(dataSourceImpl.getDriverManagerDataSource("dataSource1"));
			drds.batchUpdate(uoprsql, pList);
			
			//System.out.println("Log_uopr save  入库完成,用时：" + (System.currentTimeMillis() -start));
			secuoprloglist.clear();
		}
	}

	private static String getMACNAME() {
		if (ip == null) {
			setSysInfo();
		}
		return MACNAME;
	}

	private static String getIp() {
		if (ip == null) {
			setSysInfo();
		}
		return ip;
	}

	private static String ip = null;
	private static String MACNAME = null;

	private static void setSysInfo() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();
			MACNAME = addr.getHostName();
		} catch (Exception e) {
			ip = "error";
			MACNAME = "error";
			e.printStackTrace();
		}

	}

	public String catchLocalIP() {
		InetAddress localIP = null;
		try {
			localIP = InetAddress.getLocalHost();

		} catch (UnknownHostException e) {

		}
		return localIP.getHostAddress();

	}
}