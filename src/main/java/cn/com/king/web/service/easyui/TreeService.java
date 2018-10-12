package cn.com.king.web.service.easyui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import cn.com.king.dto.MenuTree;


@Service
public class TreeService {
	  @Inject
	  JdbcTemplate jdbcTemplate;
    /**
      * @Title: getMenuByLoginName
      * @Description: 根据用户登录名获取用户菜单信息
      * @param  username 用户登录名
      * @return List<MenuTree>    返回类型
      * @throws
     */
	public List<MenuTree> getMenuByUserId(String userid) {
		// TODO Auto-generated method stub
		String sql="select m.menu_id,m.menu_name,m.menu_url,m.PARENT_ID,m.icon_cls "
				+ "from MENU m where m.MENU_ID in"
				+ " (select rm.menu_id from role_user ru,ROLE_MENU rm "
				+ "where ru.user_id=? and rm.role_id=ru.role_id) order by m.MENU_INDEX";
		List<MenuTree> menuTreeList = jdbcTemplate.query(sql, new Object[]{userid}, 
				new RowMapper<MenuTree>() {
            @Override
            public MenuTree mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
            	MenuTree menuTree = new MenuTree();
            	menuTree.setId(rs.getString("menu_id"));
            	menuTree.setPid(rs.getString("PARENT_ID")==null?"":rs.getString("PARENT_ID"));
                menuTree.setText(rs.getString("menu_name"));
                menuTree.setState("closed");
                menuTree.setIconCls(rs.getString("icon_cls"));
                menuTree.setChecked(false);
                menuTree.setUrl(rs.getString("menu_url"));
            	return menuTree;
            }
        }
	  );
		return menuTreeList;
	}

	public List<MenuTree> getAllMenu() {
		String sql="select m.menu_id,m.menu_name,m.menu_url,m.PARENT_ID,m.icon_cls "
				+ "from MENU m order by m.MENU_INDEX";
		List<MenuTree> menuTreeList = jdbcTemplate.query(sql, new Object[]{}, 
				new RowMapper<MenuTree>() {
            @Override
            public MenuTree mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
            	MenuTree menuTree = new MenuTree();
            	menuTree.setId(rs.getString("menu_id"));
            	menuTree.setPid(rs.getString("PARENT_ID")==null?"":rs.getString("PARENT_ID"));
                menuTree.setText(rs.getString("menu_name"));
                menuTree.setState(rs.getString("PARENT_ID")==null?"closed":"open");
                menuTree.setIconCls(rs.getString("icon_cls"));
                menuTree.setChecked(false);
                menuTree.setUrl(rs.getString("menu_url"));
            	return menuTree;
            }
        }
	  );
		return menuTreeList;
	}
	public String getMenuIdsByRoleid(String roleid) {
		// TODO Auto-generated method stub
		StringBuffer menuids=new StringBuffer("");
		String sql="select rm.menu_id,rm.add_priv,rm.edit_priv,rm.view_priv,"
				+ " rm.delete_priv,rm.imp_priv,rm.exp_priv from ROLE_MENU rm  where rm.role_id = ?";
			List<Map<String, Object>> menuList=jdbcTemplate.queryForList(sql, new Object[]{roleid});	
		for(Map map:menuList){
			menuids.append(map.get("menu_id")).append(",");
			if("1".equals(map.get("add_priv"))){
				menuids.append(map.get("menu_id")).append("add_priv").append(",");
			}
			if("1".equals(map.get("edit_priv"))){
				menuids.append(map.get("menu_id")).append("edit_priv").append(",");
			}
			if("1".equals(map.get("view_priv"))){
				menuids.append(map.get("menu_id")).append("view_priv").append(",");
			}
			if("1".equals(map.get("delete_priv"))){
				menuids.append(map.get("menu_id")).append("delete_priv").append(",");
			}
			if("1".equals(map.get("imp_priv"))){
				menuids.append(map.get("menu_id")).append("imp_priv").append(",");
			}
			if("1".equals(map.get("exp_priv"))){
				menuids.append(map.get("menu_id")).append("exp_priv").append(",");
			}
		}
		if(menuids.length()>0){
			return (menuids.substring(0, menuids.length()-1)).toString();
		}	
		else{
			return "";
		}
		
	}

	public List<MenuTree> getAllMenuByRoleId(String roleid) {
		String sql="select m.menu_id,m.menu_name,m.menu_url,m.PARENT_ID,m.icon_cls "
				+ "from MENU m, ROLE_MENU rm where m.menu_id=rm.menu_id and rm.role_id=? order by m.MENU_INDEX";
		List<MenuTree> menuTreeList = jdbcTemplate.query(sql, new Object[]{roleid}, 
				new RowMapper<MenuTree>() {
            @Override
            public MenuTree mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
            	MenuTree menuTree = new MenuTree();
            	menuTree.setId(rs.getString("menu_id"));
            	menuTree.setPid(rs.getString("PARENT_ID")==null?"":rs.getString("PARENT_ID"));
                menuTree.setText(rs.getString("menu_name"));
                menuTree.setState("open");
                menuTree.setIconCls(rs.getString("icon_cls"));
                menuTree.setChecked(false);
                menuTree.setUrl(rs.getString("menu_url"));
            	return menuTree;
            }
        }
	  );
		return menuTreeList;
	}

	public List<MenuTree> getAllDept() {
		String sql="select m.dept_id,m.dept_name,m.dept_url,m.PARENT_ID,m.dept_index"
				+ " from deptinfo m order by m.dept_index";
		List<MenuTree> menuTreeList = jdbcTemplate.query(sql, new Object[]{}, 
				new RowMapper<MenuTree>() {
            @Override
            public MenuTree mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
            	MenuTree menuTree = new MenuTree();
            	menuTree.setId(rs.getString("dept_id"));
            	menuTree.setPid(rs.getString("PARENT_ID")==null?"":rs.getString("PARENT_ID"));
                menuTree.setText(rs.getString("dept_name"));
                menuTree.setIndex(rs.getString("dept_index"));
                menuTree.setState("open");
                menuTree.setIconCls("");
                menuTree.setChecked(false);
                menuTree.setUrl("");
            	return menuTree;
            }
        }
	  );
		return menuTreeList;
	}

	public List<MenuTree> getDeptTree(String id) {   // ,new Object[]{id}, 
		// TODO Auto-generated method stub
				//String sql="select dept_id,dept_name,parent_id,dept_index from deptinfo start with parent_id=? connect by prior dept_id=parent_id order by dept_index";
				String sql="select id,dept_name,parent_id,dept_index from dept  where flag = '1' and parent_id!='0'  order by dept_index";
				List<MenuTree> menuTreeList = jdbcTemplate.query(sql , 
						new RowMapper<MenuTree>() {
		            @Override 
		            public MenuTree mapRow(ResultSet rs, int rowNum)
		                    throws SQLException {
		            	MenuTree menuTree = new MenuTree();
		            	menuTree.setId(rs.getString("id"));
		            	menuTree.setPid(rs.getString("parent_id")==null?"":rs.getString("parent_id"));
		                menuTree.setText(rs.getString("dept_name"));
		                menuTree.setState("closed");
		                menuTree.setChecked(false);
		            	return menuTree;
		            }
		        }
			  );
				return menuTreeList;
	}
	public List<MenuTree> getDeptFristNodeTree(String id) {
		// TODO Auto-generated method stub
		//String sql="select dept_id,dept_name,parent_id,dept_index from deptinfo start with parent_id=? connect by prior dept_id=parent_id order by dept_index";
		String sql="select id,dept_name,parent_id,dept_index from dept  where flag = '1' and parent_id!='0' and  parent_id = ? order by dept_index";
		List<MenuTree> menuTreeList = jdbcTemplate.query(sql  ,new Object[]{id}, 
				new RowMapper<MenuTree>() {
            @Override 
            public MenuTree mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
            	MenuTree menuTree = new MenuTree();
            	menuTree.setId(rs.getString("id"));
            	menuTree.setPid(rs.getString("parent_id")==null?"":rs.getString("parent_id"));
                menuTree.setText(rs.getString("dept_name"));
                menuTree.setState("closed");
                menuTree.setChecked(false);
            	return menuTree;
            }
        }
	  );
		return menuTreeList;
	}
	
	
	public List<MenuTree> getDeptTreeChoose(String id) {
		// TODO Auto-generated method stub
				String sql="select dept_id,dept_name,parent_id,dept_index from deptinfo start with parent_id=? connect by prior dept_id=parent_id order by dept_index";
				List<MenuTree> menuTreeList = jdbcTemplate.query(sql, new Object[]{id}, 
						new RowMapper<MenuTree>() {
		            @Override
		            public MenuTree mapRow(ResultSet rs, int rowNum)
		                    throws SQLException {
		            	MenuTree menuTree = new MenuTree();
		            	menuTree.setId(rs.getString("dept_id"));
		            	menuTree.setPid(rs.getString("parent_id")==null?"":rs.getString("parent_id"));
		                menuTree.setText(rs.getString("dept_name"));
		                menuTree.setState("open");
		                menuTree.setChecked(true);
		            	return menuTree;
		            }
		        }
			  );
				return menuTreeList;
	}

	public List<MenuTree> getAreaTree(String id) {
		String sql="select areaid,areaname,pid,index_num from t_area_info where state='0' start with pid=? connect by prior areaid=pid order by index_num";
		List<MenuTree> menuTreeList = jdbcTemplate.query(sql, new Object[]{id}, 
				new RowMapper<MenuTree>() {
            @Override
            public MenuTree mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
            	MenuTree menuTree = new MenuTree();
            	menuTree.setId(rs.getString("areaid"));
            	menuTree.setPid(rs.getString("pid")==null?"":rs.getString("pid"));
                menuTree.setText(rs.getString("areaname"));
                menuTree.setState("closed");
                menuTree.setChecked(false);
            	return menuTree;
            }
        }
	  );
		return menuTreeList;
	}
	/**
	 * 
	
	  * getResTree(获取资源信息分类树)
	  * @Title: getResTree
	  * @Description: TODO
	  * @param  id
	  * @return List<MenuTree>    返回类型
	  * @throws
	 */
	public List<MenuTree> getResTree(String id) {
		// TODO Auto-generated method stub
		String sql="";
		 List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
		if(id.equals("0")){
			 sql="select resid,presid,resname,rescode from t_res_classify where state='0'start with presid is null connect by prior resid=presid order by indexnum";
			 menuTreeList = jdbcTemplate.query(sql, new Object[]{}, 
						new RowMapper<MenuTree>() {
		            @Override
		            public MenuTree mapRow(ResultSet rs, int rowNum)
		                    throws SQLException {
		            	MenuTree menuTree = new MenuTree();
		            	menuTree.setId(rs.getString("resid"));
		            	menuTree.setPid(rs.getString("presid")==null?"0":rs.getString("presid"));
		                menuTree.setText(rs.getString("resname"));
		                menuTree.setState("closed");
		                menuTree.setUrl(rs.getString("rescode"));
		                menuTree.setChecked(false);
		            	return menuTree;
		            }
		        }
			  );
		}
		else{
			 sql="select resid,presid,resname,rescode from t_res_classify where state='0'start with presid=? connect by prior resid=presid order by indexnum";
			menuTreeList = jdbcTemplate.query(sql, new Object[]{id}, 
						new RowMapper<MenuTree>() {
		            @Override
		            public MenuTree mapRow(ResultSet rs, int rowNum)
		                    throws SQLException {
		            	MenuTree menuTree = new MenuTree();
		             	menuTree.setId(rs.getString("resid"));
		            	menuTree.setPid(rs.getString("presid")==null?"":rs.getString("presid"));
		                menuTree.setText(rs.getString("resname"));
		                menuTree.setState("closed");
		                menuTree.setChecked(false);
		                menuTree.setState("closed");
		                menuTree.setChecked(false);
		            	return menuTree;
		            }
		        }
			  );
		}
		
		return menuTreeList;
	}

	public List<Map<String, Object>> getResTreeByDeptId(String id) {
		 List<Map<String, Object>>  resList = new ArrayList<Map<String, Object>>();
		
		 String sql="select resid as \"id\" ,presid as \"_parentId\",resname,rescode from t_res_classify where state='0'  order by indexnum";
		 resList = jdbcTemplate.queryForList(sql);
		 
//		 List<Map<String, Object>>  linkResList = new ArrayList<Map<String, Object>>();
//		 String sql2 = "select RES_ID from dept_res where dept_id= ?";
//		 linkResList = jdbcTemplate.queryForList(sql2, new Object[]{id});
//		 Map<String, String>  residMap = new HashMap<String, String> ();
//		 for (Map<String, Object> map : linkResList) {
//			 residMap .put(StringTool.null2Empty(map.get("RES_ID"))  , StringTool.null2Empty(map.get("RES_ID")) ) ;
//		 }
//		 
//		 for (Map<String, Object> map : resList) {
//			if( residMap.containsKey(  StringTool.null2Empty(map.get("id"))  )  ){
//				map.put("check", true);
//			}
//		 }
		 
		return resList;
	}



}
