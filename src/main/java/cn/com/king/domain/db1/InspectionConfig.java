package cn.com.king.domain.db1;

import java.util.Date;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="inspectionconfig")
@NamedQuery(name="InspectionConfig.findAll", query="SELECT i FROM InspectionConfig i ")
public class InspectionConfig implements Serializable{  
  
  	/**
	 * 
	 */
	private static final long serialVersionUID = -7722964899297823215L;
	
	public InspectionConfig(){
		
	}

	@Id
  	@Column(name="config_id")
  	private String configId;
  	
  	@Column(name="inspection_type")
    private String inspectionType;  
    
  	@Column(name="inspection_name")
    private String inspectionName;  
    
  	@Column(name="eventconfig")
    private String eventconfig;  
    
  	@Column(name="plan_type")
    private String planType;  
    
  	@Column(name="execute")
    private String execute;  
    
  	@Column(name="inform")
    private String inform;  
    
  	@Column(name="inform_id")
    private String informId;  
    
  	@Column(name="inform_name")
    private String informName;  
    
  	@Column(name="createdId")
    private String createdId;  
    
  	@Column(name="createdName")
    private String createdName;  
    
  	
  	@Column(name="createdTime",updatable=false)
    private Date createdTime;  
    
  	@Column(name="updateId")
    private String updateId;  
    
  	@Column(name="updateName")
    private String updateName;  
    
  	@Column(name="updateTime",insertable=false)
    private Date updateTime;  
    
  	@Column(name="status")
    private Integer status;  
    
  	@Column(name="clob")
    private String clob;  
    
  	@Column(name="isDelete")
    private Integer isDelete;  
    
  	@Column(name="systemResult")
    private String systemResult;  
    
  	@Column(name="url")
    private String url;  
    
  	@Column(name="remarks1")
    private String remarks1;  
    
  
  	public String getConfigId(){  
      return configId;  
    }  
    public void setConfigId(String configId){  
      this.configId = configId;  
    }  
  
    public String getInspectionType(){  
      return inspectionType;  
    }  
    public void setInspectionType(String inspectionType){  
      this.inspectionType = inspectionType;  
    }  
    
    public String getInspectionName(){  
      return inspectionName;  
    }  
    public void setInspectionName(String inspectionName){  
      this.inspectionName = inspectionName;  
    }  
    
    public String getEventconfig(){  
      return eventconfig;  
    }  
    public void setEventconfig(String eventconfig){  
      this.eventconfig = eventconfig;  
    }  
    
    public String getPlanType(){  
      return planType;  
    }  
    public void setPlanType(String planType){  
      this.planType = planType;  
    }  
    
    public String getExecute(){  
      return execute;  
    }  
    public void setExecute(String execute){  
      this.execute = execute;  
    }  
    
    public String getInform(){  
      return inform;  
    }  
    public void setInform(String inform){  
      this.inform = inform;  
    }  
    
    public String getInformId(){  
      return informId;  
    }  
    public void setInformId(String informId){  
      this.informId = informId;  
    }  
    
    public String getInformName(){  
      return informName;  
    }  
    public void setInformName(String informName){  
      this.informName = informName;  
    }  
    
    public String getCreatedId(){  
      return createdId;  
    }  
    public void setCreatedId(String createdId){  
      this.createdId = createdId;  
    }  
    
    public String getCreatedName(){  
      return createdName;  
    }  
    public void setCreatedName(String createdName){  
      this.createdName = createdName;  
    }  
    
    public Date getCreatedTime(){  
      return createdTime;  
    }  
    public void setCreatedTime(Date createdTime){  
      this.createdTime = createdTime;  
    }  
    
    public String getUpdateId(){  
      return updateId;  
    }  
    public void setUpdateId(String updateId){  
      this.updateId = updateId;  
    }  
    
    public String getUpdateName(){  
      return updateName;  
    }  
    public void setUpdateName(String updateName){  
      this.updateName = updateName;  
    }  
    
    public Date getUpdateTime(){  
      return updateTime;  
    }  
    public void setUpdateTime(Date updateTime){  
      this.updateTime = updateTime;  
    }  
    
    public Integer getStatus(){  
      return status;  
    }  
    public void setStatus(Integer status){  
      this.status = status;  
    }  
    
    public String getClob(){  
      return clob;  
    }  
    public void setClob(String clob){  
      this.clob = clob;  
    }  
    
    public Integer getIsDelete(){  
      return isDelete;  
    }  
    public void setIsDelete(Integer isDelete){  
      this.isDelete = isDelete;  
    }  
    
    public String getSystemResult(){  
      return systemResult;  
    }  
    public void setSystemResult(String systemResult){  
      this.systemResult = systemResult;  
    }  
    
    public String getUrl(){  
      return url;  
    }  
    public void setUrl(String url){  
      this.url = url;  
    }  
    
    public String getRemarks1(){  
      return remarks1;  
    }  
    public void setRemarks1(String remarks1){  
      this.remarks1 = remarks1;  
    }  
    
  
  
}  