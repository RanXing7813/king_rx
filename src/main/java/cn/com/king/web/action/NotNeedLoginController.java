package cn.com.king.web.action;

import javax.servlet.http.HttpSession;

import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value="/rsm/rsm")
public class NotNeedLoginController {

	
	/**默认进入
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"noNeedLogin"})
	public String homePage(Model model,@RequestParam(value = "models", required = false) String models) {
		
		System.out.println("-----排除类");
		model.addAttribute("NAME","11111");
		
		return "index";
	}
	
	/**默认进入
	 * @param model
	 * @return
	 */
	@RequestMapping({"","/index"})
	public String homePage2(Model model,@RequestParam(value = "models", required = false) String models) {
		
		System.out.println("-----首页");
		model.addAttribute("NAME","11111");
		
		return "index";
	}
}
