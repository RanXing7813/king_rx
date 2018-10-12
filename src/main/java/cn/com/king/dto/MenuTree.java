package cn.com.king.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 
  * @ClassName: MenuTree
  * @Description: 菜单树
  * @author lijiezhi_pc
  * @date 2017年12月5日 下午12:30:31
  *
 */
public class MenuTree implements Serializable{
	private String id;
	private String text;//树节点名称
	private String iconCls;//树节点图标
	private Boolean checked = false;//是否勾选状态
	private String state = "closed";//是否展开（open,closed）
	private String  pid;
	private String index;//排序
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	private String url;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
