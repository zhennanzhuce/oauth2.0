package net.foreworld.oauth2.authorize.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.foreworld.controller.BaseController;
import net.foreworld.oauth2.service.UserAppService;
import net.foreworld.oauth2.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@RequestMapping("/token")
@Controller
public class TokenController extends BaseController {

	@Resource
	private UserService userService;

	@Resource
	private UserAppService userAppService;

	/**
	 *
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = { "/auth/" }, method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> auth(Principal principal) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("name", principal.getName());
		result.put("success", true);
		return result;
	}

}
