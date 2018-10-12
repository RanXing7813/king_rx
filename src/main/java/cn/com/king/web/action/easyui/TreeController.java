package cn.com.king.web.action.easyui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.king.dto.MenuTree;
import cn.com.king.dto.TreeGrid;
import cn.com.king.dto.TreeNode;
import cn.com.king.page.util.TreeNodeUtil;
import cn.com.king.web.service.easyui.TreeService;
import cn.com.taiji.web.service.sys.DeptService;

@Controller
@RequestMapping(value="/tree")
public class TreeController {
	@Inject
	TreeService treeService;
	
	@Inject
	DeptService deptService;
	
	

	@RequestMapping(value="toDeptAllTreeView")
	public String toDeptTreeView(HttpServletRequest request,HttpServletResponse response){
        return "page/easyuitree/treeDeptAll";
    }
	
	@RequestMapping(value="toDeptFristNodeTreeView")
	public String toDeptFristNodeTreeView(HttpServletRequest request,HttpServletResponse response){
        return "page/easyuitree/treeDeptFristNode";
    }
	
	
	@RequestMapping(value="deptAllTreeView")
	public @ResponseBody List<TreeNode> deptTreeView(HttpServletRequest request,HttpServletResponse response){
		//String id = request.getParameter("id")==null?"0":request.getParameter("id");
		String id = request.getParameter("id")==null?"fc652a7ef4ca444dbd6030f22c3aafaf":request.getParameter("id");
		List<MenuTree> menus = treeService.getDeptTree(id);
        List<TreeNode> nodes = TreeNodeUtil.toListNode(menus);
        List<TreeNode> treeNodes = TreeNodeUtil.tree(nodes, id);
        return treeNodes;
    }
	
	@RequestMapping(value="deptFristNodeTreeView")
	public @ResponseBody List<TreeNode> deptFristNodeTreeView(HttpServletRequest request,HttpServletResponse response){
		//String id = request.getParameter("id")==null?"0":request.getParameter("id");
		String id = request.getParameter("id")==null?"fc652a7ef4ca444dbd6030f22c3aafaf":request.getParameter("id");
		List<MenuTree> menus = treeService.getDeptFristNodeTree(id);
        List<TreeNode> nodes = TreeNodeUtil.toListNode(menus);
        List<TreeNode> treeNodes = TreeNodeUtil.tree(nodes, id);
        return treeNodes;
    }
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="deptGridView")
	public @ResponseBody List<TreeGrid> deptGridView(HttpServletRequest request,HttpServletResponse response){
		List<MenuTree> menus = treeService.getAllDept();
        List<TreeGrid> nodes = TreeNodeUtil.toListTreeGrid(menus);
        List<TreeGrid> treeNodes = TreeNodeUtil.treeGrid(nodes, "0");
        return treeNodes;
    }
	
	
	
	
	
	
	
	@RequestMapping(value="menuGridView")
	public @ResponseBody List<TreeGrid> menuGridView(HttpServletRequest request,HttpServletResponse response){
		List<MenuTree> menus = treeService.getAllMenu();
        List<TreeGrid> nodes = TreeNodeUtil.toListTreeGrid(menus);
        List<TreeGrid> treeNodes = TreeNodeUtil.treeGrid(nodes, "0");
        return treeNodes;
    }

	@RequestMapping(value="deptTreeChoose")
	public @ResponseBody List<TreeNode> deptTreeChoose(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id")==null?"0":request.getParameter("id");
		List<MenuTree> menus = treeService.getDeptTreeChoose(id);
        List<TreeNode> nodes = TreeNodeUtil.toListNode(menus);
        List<TreeNode> treeNodes = TreeNodeUtil.tree(nodes, id);
        return treeNodes;
    }

	@RequestMapping(value="areaTreeView")
	public @ResponseBody List<TreeNode> areaTreeView(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id")==null?"0":request.getParameter("id");
		List<MenuTree> menus = treeService.getAreaTree(id);
        List<TreeNode> nodes = TreeNodeUtil.toListNode(menus);
        List<TreeNode> treeNodes = TreeNodeUtil.tree(nodes, id);
        return treeNodes;
    }
	@RequestMapping(value="resTreeView")
	public @ResponseBody List<TreeGrid> resTreeView(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id")==null?"0":request.getParameter("id");
		List<MenuTree> menus = treeService.getResTree(id);
        List<TreeGrid> nodes = TreeNodeUtil.toListTreeGrid(menus);
        List<TreeGrid> treeNodes = TreeNodeUtil.treeGrid(nodes, id);
        return treeNodes;
    }
	@RequestMapping(value="resTreeNodeView")
	public @ResponseBody List<TreeNode> resTreeNodeView(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id")==null?"0":request.getParameter("id");
		List<MenuTree> menus = treeService.getResTree(id);
        List<TreeNode> nodes = TreeNodeUtil.toListNode(menus);
        List<TreeNode> treeNodes = TreeNodeUtil.tree(nodes, id);
        return treeNodes;
    }

	@RequestMapping(value="resTreeNodeViewByDeptId")
	public @ResponseBody Map<String,Object> resTreeNodeViewByDeptId(Model model,HttpServletRequest request,HttpServletResponse response){
		 String id = request.getParameter("id") ;
		 List<Map<String,Object>> menus = treeService.getResTreeByDeptId(id);
		 Map<String,Object> map = new HashMap<String,Object> ();
		 map.put("rows", menus);
		 model.addAttribute("map1", map.get("rows"));
        return map;
    }
	/**
	 * 
	  *
	  * @Title: menuGridViewByRoleid
	  * @Description: 根据roleid获取菜单树
	  * @param  request
	  * @param  response
	  * @return List<TreeGrid>    返回类型
	  * @throws
	 */
	@RequestMapping(value="menuGridViewByRoleid")
	public @ResponseBody List<TreeGrid> menuGridViewByRoleid(HttpServletRequest request,HttpServletResponse response){
		String roleid=request.getParameter("roleid");
		List<MenuTree> menus = treeService.getAllMenuByRoleId(roleid);
        List<TreeGrid> nodes = TreeNodeUtil.toListTreeGrid(menus);
        List<TreeGrid> treeNodes = TreeNodeUtil.treeGrid(nodes, "0");
        return treeNodes;
    }
	
}
