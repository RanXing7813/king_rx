package cn.com.king.domain.db1;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the dept database table.
 * 
 */
@Entity
@Table(name = "dept")
@NamedQuery(name = "Dept.findAll", query = "SELECT d FROM Dept d")

public class Dept implements Serializable {

	private static final long serialVersionUID = 5119673746393145493L;

	@Id
	@Column(name = "dept_id")
	private String deptId;

	@Column(name = "dept_desc")
	private String deptDesc;

	@Column(name = "dept_index")
	private Integer deptIndex;

	@Column(name = "dept_name")
	private String deptName;

	@Column(name = "dept_state")
	private String deptState;

	@Column(name = "dept_type")
	private String deptType;

	@Column(name = "dept_url")
	private String deptUrl;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "creator_id")
	private String creatorId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "updater_id")
	private String updaterId;

	private Integer flag;

	private String remark;
	@Column(name="parent_id")
	private String parentId;

	private String dept_fullname;

	@Column(name = "dept_zzjgdm")
	private String dept_zzjgdm;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
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
	
	
}