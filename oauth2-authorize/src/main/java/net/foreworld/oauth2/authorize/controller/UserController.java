package net.foreworld.oauth2.authorize.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.foreworld.controller.BaseController;
import net.foreworld.model.ResultMap;
import net.foreworld.oauth2.authorize.service.UserAppService;
import net.foreworld.oauth2.authorize.service.UserService;
import net.foreworld.oauth2.model.User;
import net.foreworld.oauth2.model.UserApp;
import net.foreworld.util.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Controller
public class UserController extends BaseController {

	@Resource
	private UserService userService;

	@Resource
	private UserAppService userAppService;

	/**
	 *
	 * @param map
	 * @param client_id
	 * @param redirect_uri
	 * @param response_type
	 * @param scope
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginUI(Map<String, Object> map,
			@RequestParam String client_id, @RequestParam String redirect_uri,
			@RequestParam String response_type,
			@RequestParam(required = false) String scope,
			@RequestParam(required = false) String state) {

		if (null == StringUtil.isEmpty(client_id)) {
			map.put("code", "invalid_client_id");
			return "user/login_error";
		}

		if (null == StringUtil.isEmpty(redirect_uri)) {
			map.put("code", "invalid_redirect_uri");
			return "user/login_error";
		}

		switch (response_type) {
		case "token":
		case "code":
			break;
		default:
			map.put("code", "unsupported_response_type");
			return "user/login_error";
		}

		UserApp ua = userAppService.getUserAuth(client_id);

		if (null == ua) {
			map.put("code", "invalid_client_id");
			return "user/login_error";
		}

		map.put("req_client_id", client_id);
		map.put("req_redirect_uri", redirect_uri);
		map.put("req_response_type", response_type);
		map.put("req_scope", scope);
		map.put("req_state", state);

		map.put("user_app", ua);
		return "user/login";
	}

	/**
	 *
	 * @param client_id
	 * @param redirect_uri
	 * @param response_type
	 * @param scope
	 * @param state
	 * @param user_name
	 * @param user_pass
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/" }, method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> authorize(@RequestParam String client_id,
			@RequestParam String redirect_uri,
			@RequestParam String response_type,
			@RequestParam(required = false) String scope,
			@RequestParam(required = false) String state,
			@RequestParam String user_name, @RequestParam String user_pass) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (null == StringUtil.isEmpty(client_id)) {
			map.put("code", "invalid_client_id");
			return map;
		}

		if (null == StringUtil.isEmpty(redirect_uri)) {
			map.put("code", "invalid_redirect_uri");
			return map;
		}

		switch (response_type) {
		case "code":
			break;
		default:
			map.put("code", "unsupported_response_type");
			return map;
		}

		ResultMap<User> m = userService.login(user_name, user_pass);

		if (!m.getSuccess()) {
			map.put("code", m.getCode());
			map.put("msg", m.getMsg());
			return map;
		}

		return map;
	}
}
