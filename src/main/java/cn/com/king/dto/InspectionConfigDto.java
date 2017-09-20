package cn.com.king.dto;

import java.util.Date;

public class InspectionConfigDto {  
  
  	private String configId;
    private String inspectionType;  
    private String inspectionName;  
    private String eventconfig;  
    private String planType;  
    private String execute;  
    private String inform;  
    private String informId;  
    private String informName;  
    private String createdId;  
    private String createdName;  
    private Date createdTime;  
    private String updateId;  
    private String updateName;  
    private Date updateTime;  
    private Integer status;  
    private String clob;  
    private Integer isDelete;  
    private String systemResult;  
    private String url;  
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
  
  public String toString(){
  	return "InspectionConfig [" 	
  		+ " inspectionType:"+inspectionType + "," 
  		+ " inspectionName:"+inspectionName + "," 
  		+ " eventconfig:"+eventconfig + "," 
  		+ " planType:"+planType + "," 
  		+ " execute:"+execute + "," 
  		+ " inform:"+inform + "," 
  		+ " informId:"+informId + "," 
  		+ " informName:"+informName + "," 
  		+ " createdId:"+createdId + "," 
  		+ " createdName:"+createdName + "," 
  		+ " createdTime:"+createdTime + "," 
  		+ " updateId:"+updateId + "," 
  		+ " updateName:"+updateName + "," 
  		+ " updateTime:"+updateTime + "," 
  		+ " status:"+status + "," 
  		+ " clob:"+clob + "," 
  		+ " isDelete:"+isDelete + "," 
  		+ " systemResult:"+systemResult + "," 
  		+ " url:"+url + "," 
  		+ " remarks1:"+remarks1 + "," 
  	+"]";
  }
  
}  