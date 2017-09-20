package cn.com.king.domain.db1;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;


/**
 * The persistent class for the Monitor database table.
 * 
 */
@Entity
@Table(name="monitor")
@NamedQuery(name="Monitor.findAll", query="SELECT i FROM Monitor i ")
public class Monitor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1262795504634831394L;


	public Monitor(){
		
	}


	/**
	 * 
	 */
	@Id
	private String monitorid;
	private String systemName;
	private String systemUrl;
	private String systemResult;
	private String systemStatus;
	private Date logTime;
	private String faultMethod;
	private String messageMethod;
	private int isDelete;
	@Column(name="created_id")
    private String createdId;  
    
  	@Column(name="created_name")
    private String createdName;  
    
  	
  	@Column(name="created_time", updatable=false)
    private Date createdTime;  
    
  	@Column(name="update_id")
    private String updateId;  
    
  	@Column(name="update_name")
    private String updateName;  
    
  	@Column(name="update_time")
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

	
}