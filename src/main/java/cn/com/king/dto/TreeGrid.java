package cn.com.king.dto;

import java.util.List;
/**
 * 
  * @ClassName: TreeGrid
  * @Description: 树表格
  * @author lijiezhi_pc
  * @date 2017年12月5日 下午12:30:50
  *
 */
public class TreeGrid {
	private String id;
	private String pid;
	private String name;
	private String url;
	private String index;
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	private String iconCls;
	private List<TreeGrid> children;//子节点
	private String state = "closed";//是否展开（open,closed）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<TreeGrid> getChildren() {
		return children;
	}
	public void setChildren(List<TreeGrid> children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	

}
