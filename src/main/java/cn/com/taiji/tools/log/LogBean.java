package cn.com.taiji.tools.log;

import java.io.Serializable;
import java.util.Date;

public class LogBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5930002597379780714L;
	private String log_id;
	private Date log_btime;
	private Date log_etime;
	private long log_timeLength;
	private String log_class;
	private String log_method;
	private int log_lineNum;
	private String log_parm;
	private String log_result;
	private int log_state;

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public long getLog_timeLength() {
		return log_timeLength;
	}

	public void setLog_timeLength(long log_timeLength) {
		this.log_timeLength = log_timeLength;
	}

	public String getLog_class() {
		return log_class;
	}

	public void setLog_class(String log_class) {
		this.log_class = log_class;
	}

	public String getLog_method() {
		return log_method;
	}

	public void setLog_method(String log_method) {
		this.log_method = log_method;
	}

	public int getLog_lineNum() {
		return log_lineNum;
	}

	public void setLog_lineNum(int log_lineNum) {
		this.log_lineNum = log_lineNum;
	}

	public String getLog_parm() {
		return log_parm;
	}

	public void setLog_parm(String log_parm) {
		this.log_parm = log_parm;
	}

	public String getLog_result() {
		return log_result;
	}

	public void setLog_result(String log_result) {
		this.log_result = log_result;
	}

	public int getLog_state() {
		return log_state;
	}

	public void setLog_state(int log_state) {
		this.log_state = log_state;
	}

	public Date getLog_btime() {
		return log_btime;
	}

	public void setLog_btime(Date log_btime) {
		this.log_btime = log_btime;
	}

	public Date getLog_etime() {
		return log_etime;
	}

	public void setLog_etime(Date log_etime) {
		this.log_etime = log_etime;
	}

}
