package cn.com.king.domain.db1;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;




/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="menuinfo")
@NamedQuery(name="Menuinfo.findAll", query=" SELECT r FROM Menuinfo r ")
public class Menuinfo implements Serializable {
	
     /**
	 * 
	 */
	private static final long serialVersionUID = 4483292377064609558L;

	public Menuinfo(){
    	 
     }

	/**
	 * 
	 */

	@Id
	@Column(name="menu_id")
	private String menuId;

	@Column(name="menu_name")
	private String menuName;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInsetTime() {
		return insetTime;
	}

	public void setInsetTime(String insetTime) {
		this.insetTime = insetTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMenuImg() {
		return menuImg;
	}

	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}

	@Column(name="parent_id")
	private String parentId;

	@Column(name="request_url")
	private String requestUrl;

	@Column(name="menu_icon")
	private String menuIcon;
	
	@Column(name="state")
	private String state;
	
	@Column(name="insert_time")
	private String insetTime;

	@Column(name="order_id")
	private String orderId;
	
	@Column(name="status")
	private String status;
	
	@Column(name="menu_img")
	private String menuImg;

}