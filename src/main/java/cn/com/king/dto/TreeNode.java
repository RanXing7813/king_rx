package cn.com.king.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 
  * @ClassName: TreeNode
  * @Description: easyui tree 数据实体类
  * @author lijiezhi_pc
  * @date 2017年11月23日 下午1:43:11
  *
 */
public class TreeNode implements Serializable {
	private static final long serialVersionUID = 9091582656283102263L;
	private String id;
	private String text;//树节点名称
	private String iconCls;//树节点图标
	private Boolean checked = false;//是否勾选状态
	private Map<String,Object> attributes;//其他参数
	private List<TreeNode> children;//子节点
	private String state = "closed";//是否展开（open,closed）
	private String  pid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
}
