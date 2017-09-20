/**   
 *       
 * 名称：DBDto   
 * 描述：   
 * 创建人：wangli   
 * 创建时间：2016年9月27日 下午8:07:44 
 * @version       
 */ 

package cn.com.king.dto;

/**        
 * 类名称：DBDto   
 * 类描述：   
 * 创建人：wangli   
 * 创建时间：2016年9月27日 下午8:07:44 
 * @version      
 */

public class DBDto {
	private String dbid;
	private String dbname;
	private String dblink;
	private String port;
	private String username;
	private String password;
	private String dbdriver;
	private String dbtype;
	private String dbchname;
	/**
	 * dblink setter
	 * @return the dblink 
	 * @author wangli
	 */
	public String getDblink() {
		return dblink;
	}
	/**
	 * dblink setter
	 * @param dblink
	 * @author wangli
	 */
	public void setDblink(String dblink) {
		this.dblink = dblink;
	}
	/**
	 * username setter
	 * @return the username 
	 * @author wangli
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * username setter
	 * @param username
	 * @author wangli
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * password setter
	 * @return the password 
	 * @author wangli
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * password setter
	 * @param password
	 * @author wangli
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * dbname setter
	 * @return the dbname 
	 * @author wangli
	 */
	public String getDbname() {
		return dbname;
	}
	/**
	 * dbname setter
	 * @param dbname
	 * @author wangli
	 */
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	/**
	 * dbidf setter
	 * @return the dbidf 
	 * @author wangli
	 */
	public String getDbid() {
		return dbid;
	}
	/**
	 * dbidf setter
	 * @param dbidf
	 * @author wangli
	 */
	public void setDbid(String dbid) {
		this.dbid = dbid;
	}
	/**
	 * dbdriver setter
	 * @return the dbdriver 
	 * @author wangli
	 */
	public String getDbdriver() {
		return dbdriver;
	}
	/**
	 * dbdriver setter
	 * @param dbdriver
	 * @author wangli
	 */
	public void setDbdriver(String dbdriver) {
		this.dbdriver = dbdriver;
	}

	/**
	 * port setter
	 * @return the port 
	 * @author wangli
	 */
	public String getPort() {
		return port;
	}
	/**
	 * port setter
	 * @param port
	 * @author wangli
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 * dbtype setter
	 * @return the dbtype 
	 * @author wangli
	 */
	public String getDbtype() {
		return dbtype;
	}
	/**
	 * dbtype setter
	 * @param dbtype
	 * @author wangli
	 */
	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}
	
	public String getDbchname() {
		return dbchname;
	}
	public void setDbchname(String dbchname) {
		this.dbchname = dbchname;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * @author wangli
	 */
	@Override
	public String toString() {
		return "DBDto [dbid=" + dbid + ", dbname=" + dbname + ", dblink=" + dblink + ", port=" + port + ", username="
				+ username + ", password=" + password + ", dbdriver=" + dbdriver + ", dbtype=" + dbtype + "]";
	}
	
	
	
}
