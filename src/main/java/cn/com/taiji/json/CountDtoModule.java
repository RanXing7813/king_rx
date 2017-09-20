/**   
 *       
 * 名称：CountDto   
 * 描述：   
 * 创建人：wangli   
 * 创建时间：2016年9月27日 下午3:57:54 
 * @version       
 */ 

package cn.com.taiji.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;


/**        
 * 类名称：CountDto   
 * 类描述：   
 * 创建人：wangli   
 * 创建时间：2016年9月27日 下午3:57:54 
 * @version      
 */

public class CountDtoModule extends SimpleModule {
	private static final long serialVersionUID = 4853911469612460504L;

	public CountDtoModule() {
		super("countDtoModule", new Version(0, 0, 1, null, "cn.com.taiji", "countdto"));
	}

	@Override
	public void setupModule(SetupContext context) {
		//context.setMixInAnnotations(CountDto.class, CountDtoMixIn.class);

	}
}
