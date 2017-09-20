package cn.com.king.domain.db2;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/** 
* @ClassName: LogZJTaskInfo 
* @Description:  运行监控
* @author ranxing
* @date 2017年8月2日 下午2:55:43 
*  
*/
@Entity
@Table(name="rkrm$_log_zjtaskinfo")
@NamedQuery(name="LogZJTaskInfo.findAll", query="SELECT t FROM LogZJTaskInfo t")
public class LogZJTaskInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1332680945578333683L;




  public  LogZJTaskInfo(){
	  
  }
		private		String       appid;                
		private		String       menue;      
		@Id
		private		String       menuid;               
		private		String       spid;                 
		private		String       st;                   
		private		String       et;                   
		private		String       trigtype;              
		private		String       stoptype;              
		private		String       srcdatazhs;            
		private		String       srcdatabytes;          
		private		String       tgtdatazhs;            
		private		String       rkftpsfzs;             
		private		String       rkftpsfbytes;         
		private		String       rkftprfzs;            
		private		String       rkftprfbytes;         
		private		String       oftpsfzs;             
		private		String       oftprfzs;             
		private		String  		msg;               
		private		String       zyid;                 
		private		String       zylxid;               
		private		String       zyloc;                
		private		String       ywlxid;               
		private		String       dwid;


		public String getAppid() {
			return appid;
		}
		public void setAppid(String appid) {
			this.appid = appid;
		}
		public String getMenue() {
			return menue;
		}
		public void setMenue(String menue) {
			this.menue = menue;
		}
		public String getMenuid() {
			return menuid;
		}
		public void setMenuid(String menuid) {
			this.menuid = menuid;
		}
		public String getSpid() {
			return spid;
		}
		public void setSpid(String spid) {
			this.spid = spid;
		}
		public String getSt() {
			return st;
		}
		public void setSt(String st) {
			this.st = st;
		}
		public String getEt() {
			return et;
		}
		public void setEt(String et) {
			this.et = et;
		}
		public String getTrigtype() {
			return trigtype;
		}
		public void setTrigtype(String trigtype) {
			this.trigtype = trigtype;
		}
		public String getStoptype() {
			return stoptype;
		}
		public void setStoptype(String stoptype) {
			this.stoptype = stoptype;
		}
		public String getSrcdatazhs() {
			return srcdatazhs;
		}
		public void setSrcdatazhs(String srcdatazhs) {
			this.srcdatazhs = srcdatazhs;
		}
		public String getSrcdatabytes() {
			return srcdatabytes;
		}
		public void setSrcdatabytes(String srcdatabytes) {
			this.srcdatabytes = srcdatabytes;
		}
		public String getTgtdatazhs() {
			return tgtdatazhs;
		}
		public void setTgtdatazhs(String tgtdatazhs) {
			this.tgtdatazhs = tgtdatazhs;
		}
		public String getRkftpsfzs() {
			return rkftpsfzs;
		}
		public void setRkftpsfzs(String rkftpsfzs) {
			this.rkftpsfzs = rkftpsfzs;
		}
		public String getRkftpsfbytes() {
			return rkftpsfbytes;
		}
		public void setRkftpsfbytes(String rkftpsfbytes) {
			this.rkftpsfbytes = rkftpsfbytes;
		}
		public String getRkftprfzs() {
			return rkftprfzs;
		}
		public void setRkftprfzs(String rkftprfzs) {
			this.rkftprfzs = rkftprfzs;
		}
		public String getRkftprfbytes() {
			return rkftprfbytes;
		}
		public void setRkftprfbytes(String rkftprfbytes) {
			this.rkftprfbytes = rkftprfbytes;
		}
		public String getOftpsfzs() {
			return oftpsfzs;
		}
		public void setOftpsfzs(String oftpsfzs) {
			this.oftpsfzs = oftpsfzs;
		}
		public String getOftprfzs() {
			return oftprfzs;
		}
		public void setOftprfzs(String oftprfzs) {
			this.oftprfzs = oftprfzs;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getZyid() {
			return zyid;
		}
		public void setZyid(String zyid) {
			this.zyid = zyid;
		}
		public String getZylxid() {
			return zylxid;
		}
		public void setZylxid(String zylxid) {
			this.zylxid = zylxid;
		}
		public String getZyloc() {
			return zyloc;
		}
		public void setZyloc(String zyloc) {
			this.zyloc = zyloc;
		}
		public String getYwlxid() {
			return ywlxid;
		}
		public void setYwlxid(String ywlxid) {
			this.ywlxid = ywlxid;
		}
		public String getDwid() {
			return dwid;
		}
		public void setDwid(String dwid) {
			this.dwid = dwid;
		}                 
}
