package cn.com.king.page.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.king.dto.MenuTree;
import cn.com.king.dto.TreeGrid;
import cn.com.king.dto.TreeNode;


public class TreeNodeUtil {
	public static List<TreeNode> tree(List<TreeNode>nodes,String id) {
        //递归转化为树形
         List<TreeNode> treeNodes=new ArrayList<TreeNode>();
            for(TreeNode treeNode : nodes) {
                TreeNode node=new TreeNode();
                node.setId(treeNode.getId());
                node.setText(treeNode.getText());
                node.setAttributes(treeNode.getAttributes());
                node.setIconCls(treeNode.getIconCls());
                node.setState(treeNode.getState());
                node.setPid(treeNode.getPid());
                if(id.equals(treeNode.getPid())){
                    node.setChildren(tree(nodes, node.getId()));
                    treeNodes.add(node);
                }
                  
            }
         List<TreeNode> treeNodes1=new ArrayList<TreeNode>();
         for(TreeNode node:treeNodes){
        	 if(node.getChildren().size() == 0){
        		 node.setState("open");
        	 }
        	 treeNodes1.add(node);
         }
            return treeNodes1;
    }  
    //转化为TreeNode节点
    public static TreeNode toNode(MenuTree menu){
        TreeNode node=new TreeNode();
        node.setId(menu.getId());
        node.setIconCls(menu.getIconCls());
        node.setPid(menu.getPid());
        node.setText(menu.getText());
        node.setState(menu.getState());
        Map<String,Object>attributes=new HashMap<String,Object>();
        attributes.put("url", menu.getUrl());
        node.setAttributes(attributes);
        return node;
    }
    public static List<TreeNode>toListNode(List<MenuTree>menus){
        List<TreeNode>nodes=new ArrayList<TreeNode>();
        for(MenuTree menu:menus){
            nodes.add(toNode(menu));
        }
        return nodes;
    }
	public static List<TreeGrid> toListTreeGrid(List<MenuTree> menus) {
		 List<TreeGrid>nodes=new ArrayList<TreeGrid>();
	        for(MenuTree menu:menus){
	            nodes.add(toTreeGrid(menu));
	        }
	        return nodes;
	}
	private static TreeGrid toTreeGrid(MenuTree menu) {
		TreeGrid node=new TreeGrid();
	        node.setId(menu.getId());
	        node.setPid(menu.getPid());
	        node.setName(menu.getText());
	        node.setState(menu.getState());
	        node.setUrl(menu.getUrl());
	        node.setIndex(menu.getIndex());
	        node.setIconCls(menu.getIconCls());
	        return node;
	}
	public static List<TreeGrid> treeGrid(List<TreeGrid> nodes, String id) {
		//递归转化为树形
        List<TreeGrid> treeNodes=new ArrayList<TreeGrid>();
           for(TreeGrid treeNode : nodes) {
        	   TreeGrid node=new TreeGrid();
        	   node.setId(treeNode.getId());
   	        node.setPid(treeNode.getPid());
   	        node.setName(treeNode.getName());
   	        node.setState(treeNode.getState());
   	        node.setUrl(treeNode.getUrl());
   	        node.setIndex(treeNode.getIndex());
   	        node.setIconCls(treeNode.getIconCls());
               if(id.equals(treeNode.getPid())){
                   node.setChildren(treeGrid(nodes, node.getId()));
                   treeNodes.add(node);
               }
           }
           return treeNodes;
	}
	
	public static void main(String[] args) {
		List<TreeGrid> nodes = new ArrayList();
		TreeGrid gen = new TreeGrid();
		gen.setId("0");
		gen.setName("根");
		
		TreeGrid zhangsan = new TreeGrid();
		zhangsan.setId("1");
		zhangsan.setPid("0");
		zhangsan.setName("张3");
		
//		TreeGrid lisi = new TreeGrid();
//		lisi.setId("2");
//		lisi.setPid("0");
//		lisi.setName("李4");
		
		TreeGrid zhangsan1 = new TreeGrid();
		zhangsan1.setId("3");
		zhangsan1.setPid("1");
		zhangsan1.setName("张31");
		
		TreeGrid zhangsan2 = new TreeGrid();
		zhangsan2.setId("4");
		zhangsan2.setPid("1");
		zhangsan2.setName("张32");
		
		nodes.add(gen);
		nodes.add(zhangsan);
		nodes.add(zhangsan1);
		nodes.add(zhangsan2);
		
		List<TreeGrid> list = treeGrid(nodes,"0");
		System.out.println(list.size());
	}
	
	
	
	
	
	
	
}
