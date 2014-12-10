/**
 * 
 */
package hilo.web.controller.account;

import hilo.core.pojos.User;
import hilo.core.service.UserService;
import hilo.web.controller.BaseController;

import mtons.commons.pojos.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author langhsu
 *
 */
@Controller
public class RegController extends BaseController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String view(ModelMap model) {
		return "/account/reg";
	}
	
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String reg(User user, ModelMap model) {
		Data data = Data.failure("注册失败");
		String ret = "/account/reg";
		
		try {
			userService.register(user);
			data = Data.success("恭喜您! 注册成功");
			ret = "/account/reg_result";
			
		} catch (Exception e) {
			data = Data.failure(e.getMessage());
		}
		model.put("data", data);
		
		return ret;
	}

}