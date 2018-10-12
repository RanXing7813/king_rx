package cn.com.taiji.rx.audit8421;


/**
 * 全局变量设置
 */
public interface GlobalFinal {
	    
	    
		// @Description: TODO(按钮权限枚举)
	    /** 
	    * @ClassName: AuditStatus 
	    * @Description: TODO(这里用一句话描述这个类的作用) 
	    * @author ranxing
	    * @date 2018年8月10日 上午10:11:53 
	    *  
	    */
	    enum AuditStatus implements GlobalFinal{
		
	    	NO_PRIVILEGE			(0,  "无权限"),
			ADD						(1,  "新增权限"),
			EDIT					(2,  "修改权限"),
			REMOVE					(4,  "删除权限"),
			FIND					(8,  "查看权限"),
			FENPEI					(16, "分配权限"),
			COMFIRM					(32, "批准权限"),
			IMPT					(64, "导入权限"),
			DOWNLOAD				(128, "下载权限"),
			PRINT					(256, "打印权限"),
			
			//ADMIN					(512, "管理员权限"),
			
			DEAFAULT				(ADD.getNumber()  |EDIT.getNumber()		|REMOVE.getNumber()
									|IMPT.getNumber() |FIND.getNumber(), "默认权限"),
			ADMIN					(ADD.getNumber()  |EDIT.getNumber()		|REMOVE.getNumber()
									|IMPT.getNumber() |FENPEI.getNumber()	|COMFIRM.getNumber()
									|DOWNLOAD.getNumber() 
									|PRINT.getNumber()|FIND.getNumber(), "所有权限")
			
			;  
		    private int number;  //权限值
		    private String name; //权限名称
		    public int getNumber() { return number; }
			public String getName() { return name; }
			
		    // 构造方法  
		    private AuditStatus( int num_, String name) {  
		        this.name = name;  
		        this.number = num_;  
		    } 
			
		 //  (通过权限值获取  权限说明) 
		    static String getAuditStatusName(int num_){
				//AuditStatus.values()获取枚举所有实例对象，
			  	//还有通过名称获取方式AuditStatus.valueOf("ADD")、AuditStatus.valueOf(AuditStatus.class, "ADD")
		        //遍历枚举所有实例对象
		        for (AuditStatus auditStatus : AuditStatus.values()) {
		            if (auditStatus.getNumber() == num_) {
		                return auditStatus.getName();
		            }
		        }
		        return null;
			}
	}// end AuditStatus
	
	  // @Description: TODO(返回状态枚举)
	  enum ReturnStatus implements GlobalFinal{
		
	    	SUCESS					(500,  "操作成功"),
			ERROR					(-1,   "操作失败") 
			
			;  
		    private int number;  //权限值
		    private String name; //权限名称
		    public int getNumber() { return number; }
			public String getName() { return name; }
			
		    // 构造方法  
		    private ReturnStatus( int num_, String name) {  
		        this.name = name;  
		        this.number = num_;  
		    } 
			
		    //(通过权限值获取  权限说明) 
		    static String getReturnStatusName(int num_){
				//AuditStatus.values()获取枚举所有实例对象，
			  	//还有通过名称获取方式AuditStatus.valueOf("ADD")、AuditStatus.valueOf(AuditStatus.class, "ADD")
		        //遍历枚举所有实例对象
		        for (ReturnStatus auditStatus : ReturnStatus.values()) {
		            if (auditStatus.getNumber() == num_) {
		                return auditStatus.getName();
		            }
		        }
		        return null;
			}
	}// end ReturnStatus
	
}
