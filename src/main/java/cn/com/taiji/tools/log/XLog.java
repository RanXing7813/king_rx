package cn.com.taiji.tools.log; 

import java.util.Date;

import com.google.gson.Gson;

import cn.com.taiji.tools.code.XEncoding;

/**   
 * @模块名：
 * @版本号：
 * @创建时间： May 15, 2014   12:57:10 PM   
 * @作者：
 * @说明：
 *
 */
/*--------------------修改记录(begin)-------------------------------------------------*/
/*--例：2013-10-21  张三    新增xxx方法,优化xxx功能                                   */
/*--------------------修改记录(end)---------------------------------------------------*/
public class XLog {
	
	private Gson jGson = new Gson();
	private final int STATE_SUCCESS=1;
	private final int STATE_FAIL=0;
	
	public LogBean begin(Object ... param){
		LogBean lb = new LogBean();
		lb.setLog_id(new XEncoding().getUUID32());
		//StackTraceElement s[] = Thread.currentThread().getStackTrace();		
		lb.setLog_btime(new Date());
		if(param!=null){
			lb.setLog_parm(jGson.toJson(param));
		}
		return lb;
	}
	public LogBean endSuccess(LogBean log,Object rs){
		log.setLog_etime(new Date());
		log.setLog_class(Thread.currentThread().getStackTrace()[3].getClassName());
		log.setLog_method(Thread.currentThread().getStackTrace()[3].getMethodName());
		log.setLog_lineNum(Thread.currentThread().getStackTrace()[3].getLineNumber());
		if(rs !=null ) {
			log.setLog_result(jGson.toJson(rs));
		}
		log.setLog_timeLength(log.getLog_etime().getTime()-log.getLog_btime().getTime());
		log.setLog_state(STATE_SUCCESS);
		return log;
	}
	public LogBean endFail(LogBean log,Object rs){
		log.setLog_etime(new Date());
		log.setLog_class(Thread.currentThread().getStackTrace()[3].getClassName());
		log.setLog_method(Thread.currentThread().getStackTrace()[3].getMethodName());
		log.setLog_lineNum(Thread.currentThread().getStackTrace()[3].getLineNumber());
		if(rs!=null) {
			log.setLog_result(jGson.toJson(rs));
		}
		log.setLog_timeLength(log.getLog_etime().getTime()-log.getLog_btime().getTime());
		log.setLog_state(STATE_FAIL);
		return log;
	}	

}

