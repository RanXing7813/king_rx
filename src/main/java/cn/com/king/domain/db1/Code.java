package cn.com.king.domain.db1;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import javax.persistence.Id;

@Entity
@Table(name = "code")
@NamedQuery(name = "Code.findAll", query = "SELECT d FROM Code d")
public class Code  implements Serializable{

	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1637502462934694081L;


	public Code(){
		
	}
	
	@Id
	private String		id;
	private int			level;
	private int			code_index;
	private String		code_varchar_value;
	private int			code_int_value;
	private String		code_show_name;
	private String		parent_id;
	private String		state;
	private Date		create_time;
	private String		creator_id;
	private Date		update_time;
	private String		updater_id;
	private String		isEffective;
	private String		remark;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getCode_varchar_value() {
		return code_varchar_value;
	}
	public void setCode_varchar_value(String code_varchar_value) {
		this.code_varchar_value = code_varchar_value;
	}
	public int getCode_int_value() {
		return code_int_value;
	}
	public void setCode_int_value(int code_int_value) {
		this.code_int_value = code_int_value;
	}
	public String getCode_show_name() {
		return code_show_name;
	}
	public void setCode_show_name(String code_show_name) {
		this.code_show_name = code_show_name;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getUpdater_id() {
		return updater_id;
	}
	public void setUpdater_id(String updater_id) {
		this.updater_id = updater_id;
	}
	public String getIsEffective() {
		return isEffective;
	}
	public void setIsEffective(String isEffective) {
		this.isEffective = isEffective;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCode_index() {
		return code_index;
	}
	public void setCode_index(int code_index) {
		this.code_index = code_index;
	}

	
	

}
