package cn.com.taiji.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class DemoObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 3686099481827641783L;

	public DemoObjectMapper() {
		registerModule(new JodaModule());
		registerModule(new CountDtoModule());
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		configure(SerializationFeature.INDENT_OUTPUT, true);
		setDateFormat(new ISO8601DateFormat());
        
	}
	
	
	
}
