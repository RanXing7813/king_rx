package cn.com.king.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * The persistent class for the operation_question database table.
 * 
 */
public class MonitorDto implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5220314950271419706L;
	/**
	 * 
	 */

	private String monitorid;
	private String systemName;
	private String systemUrl;
	private String systemStatus;
	private Date logTime;
	private String faultMethod;
	private String systemResult	;
	private String messageMethod;
	private int isDelete;
    private String createdId;  
    private String createdName;  
    private Date createdTime;  
    private String updateId;  
    private String updateName;  
    private Date updateTime;  

	public String getCreatedId() {
		return createdId;
	}

	public void setCreatedId(String createdId) {
		this.createdId = createdId;
	}

	public String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMonitorid() {
		return monitorid;
	}

	public void setMonitorid(String monitorid) {
		this.monitorid = monitorid;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemUrl() {
		return systemUrl;
	}

	public void setSystemUrl(String systemUrl) {
		this.systemUrl = systemUrl;
	}

	public String getSystemStatus() {
		return systemStatus;
	}

	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public String getFaultMethod() {
		return faultMethod;
	}

	public void setFaultMethod(String faultMethod) {
		this.faultMethod = faultMethod;
	}

	public String getMessageMethod() {
		return messageMethod;
	}

	public void setMessageMethod(String messageMethod) {
		this.messageMethod = messageMethod;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getSystemResult() {
		return systemResult;
	}

	public void setSystemResult(String systemResult) {
		this.systemResult = systemResult;
	}

	//---------------------
	private String token = "";

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void generateToken(String salt) {
		if (salt == null)
			token = DigestUtils.sha1Hex(StringUtils.trimToEmpty(monitorid) + Long.toString(serialVersionUID));
		else
			token = DigestUtils.sha1Hex(StringUtils.trimToEmpty(monitorid) + salt);

	}

	public boolean tokenKeeped(String salt) {
		if (salt == null)
			return (DigestUtils.sha1Hex(StringUtils.trimToEmpty(monitorid) + Long.toString(serialVersionUID))).equals(token);
		else
			return DigestUtils.sha1Hex(StringUtils.trimToEmpty(monitorid) + salt).equals(token);
	}
	
}