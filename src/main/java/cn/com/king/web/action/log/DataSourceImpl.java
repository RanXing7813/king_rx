package cn.com.king.web.action.log;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import cn.com.king.dto.DBDto;
import cn.com.taiji.tools.RTools;


/**
 * 
 * 类名称：DataSourceImpl.java 类描述： 数据源管理 创建人：zhongdd 创建时间：2016年10月28日 上午9:50:21
 * 
 * @version
 */
public class DataSourceImpl implements ApplicationContextAware, BeanNameAware {

	private String beanName;
	private ApplicationContext applicationContext;

	public String getBeanName() {
		return beanName;
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = new FileSystemXmlApplicationContext(
					this.getClass().getResource("/") + "spring/app-datasouce-config.xml");
		}
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public DataSource getDriverManagerDataSource(DBDto dbDto) {
		if ("sgongsj".equals(dbDto.getDbid())) {
			DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName(dbDto.getDbdriver());
			ds.setUsername(dbDto.getUsername());
			ds.setPassword(dbDto.getPassword());
			ds.setDriverClassName("com.sybase.jdbc3.jdbc.SybDriver");
			ds.setUrl("jdbc:sybase:Tds:" + dbDto.getDblink() + "/" + dbDto.getDbname() + "?charset=cp936");
			//System.out.println("DBinfo:" + dbDto.getDbid() + "--" + dbDto.getDbname() + "--" + dbDto.getDbtype() + "--"
			//		+ dbDto.getDbdriver() + "--" + dbDto.getDblink());
			return ds;
		} else {
			DataSource dataSource = (DataSource) this.getApplicationContext().getBean(dbDto.getDbid());
			return dataSource;
		}
	}

	public DataSource getDriverManagerDataSource(String dbid) {
		DataSource dataSource = (DataSource) this.getApplicationContext().getBean(dbid);
		return dataSource;
	}

	/**
	 * 
		* 功能名称：分页查询
		* 参数： 表名、查询条件
		* 返回值：int
		* 作者: zhongdd
		* 创建时间: 2017年3月10日 下午4:49:07
		* 说明:
		* table_name 表名
		* filter  查询条件   
		*      filter.put("id:=","AAAA")  --->   id='AAAA'
		*      filter.put("name","BBBB")  --->   name like '%BBBB%'
		* sort  排序字段  
		* 		 filter.put("index_num","asc") ---> order by index_num asc 
		* 		 filter.put("index_num","desc") ---> order by index_num desc 
		* selectCloum  指定要查询的字段  例如   id,name,cretae_time
		* currentPage  当前第几页
		* pageSize   每页多少条
		* 
		* 
	 */
	public List<Map<String, Object>> selectPageListByTable(String table_name, Map<String, String> filter,
			Map<String, String> sort, int currentPage, int pageSize, String selectCloum) throws Exception {
		DataSource datasource = getDriverManagerDataSource("dataSource");// edas数据库
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = fillSelectSqlForTable(table_name, filter, sort, currentPage, pageSize, selectCloum, 0);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	
	public List<Map<String, Object>> selectPageListBySql(String sqlview, Map<String, String> filter,
			Map<String, String> sort, int currentPage, int pageSize, String selectCloum) throws Exception {
		DataSource datasource = getDriverManagerDataSource("dataSource");// edas数据库
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = fillSelectSqlForSqlView(sqlview, filter, sort, currentPage, pageSize, selectCloum, 0);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	/**
	 * 
		* 功能名称：求总数
		* 参数： 表名、查询条件
		* 返回值：int
		* 作者: zhongdd
		* 创建时间: 2017年3月10日 下午4:49:07
		* 说明:
		* table_name 表名
		* filter  查询条件   
		*      filter.put("id:=","AAAA")  --->   id='AAAA'
		*      filter.put("name","BBBB")  --->   name like '%BBBB%'
		* 
	 */
	public int selectCountForTable(String table_name, Map<String, String> filter) throws Exception {
		DataSource datasource = getDriverManagerDataSource("dataSource");// edas数据库
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = fillSelectSqlForTable(table_name, filter, null, 0, 0, null, 1);
		int rtn = jdbcTemplate.queryForObject(sql, int.class);
		return rtn;
	}
	
	public int selectCountForSqlView(String SqlView, Map<String, String> filter) throws Exception {
		DataSource datasource = getDriverManagerDataSource("dataSource");// edas数据库
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = fillSelectSqlForSqlView(SqlView, filter, null, 0, 0, null, 1);
		int rtn = jdbcTemplate.queryForObject(sql, int.class);
		return rtn;
	}

	public int selectCountForSqlView1(String SqlView, Map<String, String> filter) throws Exception {
		DataSource datasource = getDriverManagerDataSource("dataSource");// edas数据库
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = fillSelectSqlForSqlView1(SqlView, filter, null, 0, 0, null, 1);
		int rtn = jdbcTemplate.queryForObject(sql, int.class);
		return rtn;
	}
	/**
	 * 
		* 功能名称：sql 拼接
		* 参数： 表名、查询条件
		* 返回值：int
		* 作者: zhongdd
		* 创建时间: 2017年3月10日 下午4:49:07
		* 说明:
		* table_name 表名
		* filter  查询条件   
		*      filter.put("id:=","AAAA")  --->   id='AAAA'
		*      filter.put("name","BBBB")  --->   name like '%BBBB%'
		* sort  排序字段  
		* 		 filter.put("index_num","asc") ---> order by index_num asc 
		* 		 filter.put("index_num","desc") ---> order by index_num desc 
		* selectCloum  指定要查询的字段  例如   id,name,cretae_time
		* currentPage  当前第几页
		* pageSize   每页多少条
		* sqlType 拼接sql类型  0:查询  、 1: 求总数（count）
		* 
	 */
	public String fillSelectSqlForTable(String table_name, Map<String, String> filter, Map<String, String> sort,
			int currentPage, int pageSize, String selectCloum, int sqlType) {
		String sql = "";
		if (sqlType == 1) {
			sql = "select count(1) from " + table_name;
		} else {
			if (!RTools.string.isEmpty(selectCloum)) {
				sql = "select " + selectCloum + " from " + table_name;
			} else {
				sql = "select * from " + table_name;
			}
		}

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.contains(":")) {
					String[] ks = key.split(":");
					if (index == 0) {
						sql += " where " + ks[0] + "= '" + value+"' ";
					} else {
						sql += " and " + ks[0] + "= '" + value+"' ";
					}
				} else {
					if (index == 0) {
						sql += " where " + key + "like '%" + value + "%' ";
					} else {
						sql += " and " + key + "like '%" + value + "%' ";
					}
				}
				index++;
			}
		}

		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() == 1) {
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql += "  order by " + key + " " + value;
				}
			}
			sql += " LIMIT " + (currentPage - 1) * pageSize + "," + pageSize + "";
		}
		return sql;
	}
	
	/**
	 * 
		* 功能名称：sql 拼接
		* 参数： 查询sql
		* 返回值：int
		* 作者: zhongdd
		* 创建时间: 2017年3月10日 下午4:49:07
		* 说明:
		* table_name 表名
		* filter  查询条件   
		*      filter.put("id:=","AAAA")  --->   id='AAAA'
		*      filter.put("name","BBBB")  --->   name like '%BBBB%'
		* sort  排序字段  
		* 		 filter.put("index_num","asc") ---> order by index_num asc 
		* 		 filter.put("index_num","desc") ---> order by index_num desc 
		* selectCloum  指定要查询的字段  例如   id,name,cretae_time
		* currentPage  当前第几页
		* pageSize   每页多少条
		* sqlType 拼接sql类型  0:查询  、 1: 求总数（count）
		* 
	 */
	public String fillSelectSqlForSqlView(String sql, Map<String, String> filter, Map<String, String> sort,
			int currentPage, int pageSize, String selectCloum, int sqlType) {

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if(key.contains("<")){
					String[] ks = key.split("<");
					if (index == 0) {
						sql += " where " + ks[0] + " < " + value;
					} else {
						sql += " and " + ks[0] + " < " + value;
					}
				}else{
				if(value == null){
					if (key.contains(":")) {
						String[] ks = key.split(":");
						if (index == 0) {
							sql += " where " + ks[0] + " is " + value+" ";
						} else {
							sql += " and " + ks[0] + " is " + value+" ";
						}
					} 
				}else{
				if (key.contains(":")) {
					String[] ks = key.split(":");
					if (index == 0) {
						sql += " where " + ks[0] + "= '" + value+"' ";
					} else {
						sql += " and " + ks[0] + "= '" + value+"' ";
					}
				} else {
					if (index == 0) {
						if(key.contains("xxl_utime")){
							sql+=" where " + value;
						}else{
						sql += " where " + key + " like '%" + value + "%' ";
						}
					} else {
						if(key.contains("xxl_utime")){
							sql+=" and "+ value;
						}else{
						sql += " and " + key + " like '%" + value + "%' ";
						}
					}
				}
				}
				}
				index++;
			}
		}

		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() == 1) {
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql += "  order by " + key + " " + value;
				}
			}
			sql += " LIMIT " + (currentPage - 1) * pageSize + "," + pageSize + "";
		}
		return sql;
	}
	
	
	public String fillSelectSqlForSqlView1(String sql, Map<String, String> filter, Map<String, String> sort,
			int currentPage, int pageSize, String selectCloum, int sqlType) {

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if(key.contains("=in")){
					String[] ks = key.split("=in");
					sql += " and " + ks[0] + " in " + value;
				}else{
				if(key.contains("<")){
					String[] ks = key.split("<");
						sql += " and " + ks[0] + " < " + value;
				}else{
				if(value == null){
					if (key.contains(":")) {
						String[] ks = key.split(":");
							sql += " and " + ks[0] + " is " + value+" ";
					} 
				}else{
				if (key.contains(":")) {
					String[] ks = key.split(":");
						sql += " and " + ks[0] + "= '" + value+"' ";
				} else {
						if(key.contains("xxl_utime")){
							sql+=" and "+ value;
						}else{
						sql += " and " + key + " like '%" + value + "%' ";
					}
				}
				}
				}
				}
			}
		}

		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() == 1) {
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql += "  order by " + key + " " + value;
				}
			}
			sql += " LIMIT " + (currentPage - 1) * pageSize + "," + pageSize + "";
		}
		return sql;
	}
	
	/**
	 * 
		* 功能名称：sql 拼接
		* 参数： 查询sql
		* 返回值：int
		* 作者: zhongdd
		* 创建时间: 2017年3月10日 下午4:49:07
		* 说明:
		* table_name 表名
		* filter  查询条件   
		*      filter.put("id:=","AAAA")  --->   id='AAAA'
		*      filter.put("name","BBBB")  --->   name like '%BBBB%'
		* sort  排序字段  
		* 		 filter.put("index_num","asc") ---> order by index_num asc 
		* 		 filter.put("index_num","desc") ---> order by index_num desc 
		* selectCloum  指定要查询的字段  例如   id,name,cretae_time
		* currentPage  当前第几页
		* pageSize   每页多少条
		* sqlType 拼接sql类型  0:查询  、 1: 求总数（count）
		* 
	 */
	public String fillSelectSqlForSqlViewAddGroup(String sql, Map<String, String> filter, Map<String, String> sort, String group, int currentPage, int pageSize, String selectCloum, int sqlType) {

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.contains(":")) {
					String[] ks = key.split(":");
					if (index == 0) {
						sql += " where " + ks[0] + "= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + "= '" + value + "' ";
					}
				}else if(key.contains(">=")){
					String[] ks = key.split(">=");
					if (index == 0) {
						sql += " where " + ks[0] + ">= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + " >= '" + value + "' ";
					}
				}else if(key.contains("<=")){
					String[] ks = key.split("<=");
					if (index == 0) {
						sql += " where " + ks[0] + "<= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + " <= '" + value + "' ";
					}
				}else if(key.contains("<>")){
					String[] ks = key.split("<>");
					if (index == 0) {
						sql += " where " + ks[0] + "<> '" + value + "' ";
					} else {
						sql += " and " + ks[0] + "<> '" + value + "' ";
					}
				} else {
					if (index == 0) {
						if (key.contains("xxl_utime")) {
							sql += " where " + value;
						} else {
							sql += " where " + key + " like '%" + value + "%' ";
						}
					} else {
						if (key.contains("xxl_utime")) {
							sql += " and " + value;
						} else {
							sql += " and " + key + " like '%" + value + "%' ";
						}
					}
				}
				index++;
			}
		}
		if (sqlType != 1) {
			if (group != null && !group.isEmpty()) {
				sql += "  group by " + group;
			}
		}
		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() > 0) {
				sql += "  order by " ;
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql +=   key + " " + value +" ,";
				}
				sql = sql.substring(0, sql.length()-1);
			}
			sql += " LIMIT " + (currentPage - 1) * pageSize + "," + pageSize + "";
		}

		return sql;
	}
	
	public String fillSelectSqlForSqlViewAddGroup1(String sql, Map<String, String> filter, Map<String, String> sort, String group,
			int currentPage, int pageSize, String selectCloum, int sqlType) {

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.contains(":")) {
					String[] ks = key.split(":");
					if (index == 0) {
						sql += " where " + ks[0] + "= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + "= '" + value + "' ";
					}
				}else if(key.contains(">=")){
					String[] ks = key.split(">=");
					if (index == 0) {
						sql += " where " + ks[0] + ">= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + " >= '" + value + "' ";
					}
				}else if(key.contains("<=")){
					String[] ks = key.split("<=");
					if (index == 0) {
						sql += " where " + ks[0] + "<= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + " <= '" + value + "' ";
					}
				}else if(key.contains("=in")){
						String[] ks = key.split("=in");
						if (index == 0) {
							sql += " where " + ks[0] + " in (" + value + ") ";
						} else {
							sql += " and " + ks[0] + " in (" + value + ") ";
						}
				}else if(key.contains("<>")){
					String[] ks = key.split("<>");
					if (index == 0) {
						sql += " where " + ks[0] + "<> '" + value + "' ";
					} else {
						sql += " and " + ks[0] + "<> '" + value + "' ";
					}
				} else if(key.contains("is")){
					String[] ks = key.split("is");
					if (index == 0) {
						sql += " where " + ks[0] + " is null)";
					} else {
						sql += " and " + ks[0] + " is null)";
					}
				}else {
					if(index == 0){
						sql += " where " + key + " like '%" + value + "%' ";
					}else{
						sql += " and " + key + " like '%" + value + "%' ";
					}
				}
				index++;
			}
		}
		if (sqlType != 1) {
			if (group != null && !group.isEmpty()) {
				sql += "  group by " + group;
			}
		}
		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() > 0) {
				sql += "  order by " ;
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql +=   key + " " + value +" ,";
				}
				sql = sql.substring(0, sql.length()-1);
			}
			sql += " LIMIT " + (currentPage - 1) * pageSize + "," + pageSize + "";
		}

		return sql;
	}
	
	/**oracle----
	 * @param sql
	 * @param filter
	 * @param sort
	 * @param group
	 * @param currentPage
	 * @param pageSize
	 * @param selectCloum
	 * @param sqlType
	 * @return
	 */
	public String fillOracleSelectForSqlViewAddGroup(String sql, Map<String, String> filter, Map<String, String> sort, String group,
			int currentPage, int pageSize, String selectCloum, int sqlType) {

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.contains(":")) {
					String[] ks = key.split(":");
					if (index == 0) {
						sql += " where " + ks[0] + "= '" + value+"' ";
					} else {
						sql += " and " + ks[0] + "= '" + value+"' ";
					}
				} else {
					if (index == 0) {
						sql += " where " + key + "like '%" + value + "%' ";
					} else {
						sql += " and " + key + "like '%" + value + "%' ";
					}
				}
				index++;
			}
		}
		if (sqlType != 1) {
			if (group != null && !group.isEmpty()) {
					sql += "  group by " + group;
			}
		}
		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() == 1) {
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql += "  order by " + key + " " + value;
				}
			}
			sql =  "select * from ( select ROWNUM as rowno ,ta.* from ("+sql+ ") ta) tt where  tt.rowno > " + (currentPage - 1) * pageSize + " and tt.rowno <= " + currentPage*pageSize + "";
		}
		return sql;
	}
	
public String fillOracleSelectForSqlViewAddGroup1(String sql, Map<String, String> filter, Map<String, String> sort, String group,
		int currentPage, int pageSize, String selectCloum, int sqlType) {

	String key = null;
	String value = null;
	if (filter != null && !filter.isEmpty()) {
		// Map<String, String> map = new HashMap<String, String>();
		int index = 0;
		for (Map.Entry<String, String> entry : filter.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if (key.contains(":")) {
				String[] ks = key.split(":");
				if (index == 0) {
					sql += " where " + ks[0] + "= '" + value + "' ";
				} else {
					sql += " and " + ks[0] + "= '" + value + "' ";
				}
			}else if(key.contains(">=")){
				String[] ks = key.split(">=");
				if (index == 0) {
					sql += " where " + ks[0] + ">= '" + value + "' ";
				} else {
					sql += " and " + ks[0] + " >= '" + value + "' ";
				}
			}else if(key.contains("<=")){
				String[] ks = key.split("<=");
				if (index == 0) {
					sql += " where " + ks[0] + "<= '" + value + "' ";
				} else {
					sql += " and " + ks[0] + " <= '" + value + "' ";
				}
			}else if(key.contains("=in")){
					String[] ks = key.split("=in");
					if (index == 0) {
						sql += " where " + ks[0] + " in (" + value + ") ";
					} else {
						sql += " and " + ks[0] + " in (" + value + ") ";
					}
			}else if(key.contains("<>")){
				String[] ks = key.split("<>");
				if (index == 0) {
					sql += " where " + ks[0] + " is not null " + value + " ";
				} else {
					sql += " and " + ks[0] + " is not null " + value + " ";
				}
			} else if(key.contains("is")){
				String[] ks = key.split("is");
				if (index == 0) {
					sql += " where " + ks[0] + " is null";
				} else {
					sql += " and " + ks[0] + " is null";
				}
			}else {
				if(index == 0){
					sql += " where " + key + " like '%" + value + "%' ";
				}else{
					sql += " and " + key + " like '%" + value + "%' ";
				}
			}
			index++;
		}
	}
	if (sqlType != 1) {
		if (group != null && !group.isEmpty()) {
			sql += "  group by " + group;
		}
	}
	if (sqlType != 1) {
		if (sort != null && !sort.isEmpty() && sort.size() == 1) {
			for (Map.Entry<String, String> entry : sort.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				sql += "  order by " + key + " " + value;
			}
		}
		sql =  "select * from ( select ROWNUM as rowno ,ta.* from ("+sql+ ") ta) tt where  tt.rowno > " + (currentPage - 1) * pageSize + " and tt.rowno <= " + currentPage*pageSize + "";
	}
	return sql;
}

public int getNextSeq(String string) {
	return 0;
}

public void insert(String string, Map<String, Object> log) {
	
}
}