package cn.com.king.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseAction {
	
	private static final Logger log = LoggerFactory.getLogger(BaseAction.class);
	
	@Autowired
	  ObjectMapper objectMapper;

	/** 
	* @Title: init 
	* @Description: TODO 初始化方法前台json 转  map
	* @param @param models
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	@SuppressWarnings("rawtypes")
	public  Map<String, Object>  init (String models){
		Map<String, Object> searchParameters = new HashMap<String, Object>();
		if (models != null && models.length() > 0) {
			try {//json转map
				searchParameters = objectMapper.readValue(models,
						new TypeReference<Map>() {
						});
			} catch (JsonParseException e) {
				log.error("JsonParseException{}:", e.getMessage());
			} catch (JsonMappingException e) {
				log.error("JsonMappingException{}:", e.getMessage());
			} catch (IOException e) {
				log.error("IOException{}:", e.getMessage());
			}
		}
		
		return searchParameters;
	}
	

}
