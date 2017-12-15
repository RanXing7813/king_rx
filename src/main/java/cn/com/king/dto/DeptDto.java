package cn.com.king.dto;


/**
 * The persistent class for the dept database table.
 * 
 */

public class DeptDto {

	private String deptId;

	private Integer deptIndex;

	private String deptName;

	private String deptState;

	private String deptType;

	private String deptUrl;
	private String deptDesc;
	private Integer flag;

	private String remark;
	
	private String parentId;

	private String dept_fullname;

	private String dept_zzjgdm;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	public Integer getDeptIndex() {
		return deptIndex;
	}

	public void setDeptIndex(Integer deptIndex) {
		this.deptIndex = deptIndex;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptState() {
		return deptState;
	}

	public void setDeptState(String deptState) {
		this.deptState = deptState;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getDeptUrl() {
		return deptUrl;
	}

	public void setDeptUrl(String deptUrl) {
		this.deptUrl = deptUrl;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDept_fullname() {
		return dept_fullname;
	}

	public void setDept_fullname(String dept_fullname) {
		this.dept_fullname = dept_fullname;
	}

	public String getDept_zzjgdm() {
		return dept_zzjgdm;
	}

	public void setDept_zzjgdm(String dept_zzjgdm) {
		this.dept_zzjgdm = dept_zzjgdm;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}
	
	
}