package cn.com.king.page.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class PageUtil {
    
    private int pageSize;  
      
    private Object total;  
      
    private int page;  
      
    private String orderStr;  
      
    private String sort;  
      
    private String order;  
      
    private Object queryObj;  
      
    private List<?> rows;  
      
	private Map<?,?> sechMap;
	
	private String message;
    
    public String getOrderStr() {  
        orderStr = "";  
        if (StringUtils.isNotEmpty(order)) {  
            String[] orders = order.split(",");  
            String[] sorts = sort.split(",");  
            for (int i = 0; i < sorts.length; i++) {  
                orderStr += sorts[i] + " " + orders[i] + ", ";  
            }  
            orderStr = orderStr.endsWith(", ") ? orderStr.substring(0, orderStr.length() - 2) : orderStr;  
        }  
        return orderStr;  
    }  
    
    public Map<?, ?> getSechMap() {
    	return sechMap;
    }
    
    public void setSechMap(Map<?, ?> sechMap) {
    	this.sechMap = sechMap;
    }
      
    public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public Object getTotal() {  
        return total;  
    }  
  
    public void setTotal(Object total) {  
        this.total = total;  
    }  
  
    public int getPage() {  
        return page;  
    }  
  
    public void setPage(int page) {  
        this.page = page;  
    }  
  
    public String getSort() {  
        return sort;  
    }  
  
    public void setSort(String sort) {  
        this.sort = sort;  
    }  
  
    public String getOrder() {  
        return order;  
    }  
  
    public void setOrder(String order) {  
        this.order = order;  
    }  
  
    public Object getQueryObj() {  
        return queryObj;  
    }  
  
    public void setQueryObj(Object queryObj) {  
        this.queryObj = queryObj;  
    }  
  
    public void setOrderStr(String orderStr) {  
        this.orderStr = orderStr;  
    }  
  
  
    @Override  
    public String toString() {  
        return "PageUtil [pageSize=" + pageSize + ", total=" + total + ", page=" + page  
                + ", orderStr=" + orderStr + ", sort=" + sort + ", order="  
                + order + ", queryObj=" + queryObj + ", rows=" + rows + "]";  
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}  
  
}
