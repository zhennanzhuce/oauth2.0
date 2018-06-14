package net.foreworld.oauth2.authorize.controller;

import java.util.Map;

import javax.annotation.Resource;

import net.foreworld.controller.BaseController;
import net.foreworld.model.ResultMap;
import net.foreworld.oauth2.model.User;
import net.foreworld.oauth2.model.UserApp;
import net.foreworld.oauth2.service.UserAppService;
import net.foreworld.oauth2.service.UserService;
import net.foreworld.util.StringUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	private static final String ROOT = "authorize/";

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
			return ROOT + "user/login_error";
		}

		if (null == StringUtil.isEmpty(redirect_uri)) {
			map.put("code", "invalid_redirect_uri");
			return ROOT + "user/login_error";
		}

		switch (response_type) {
		case "token":
		case "code":
			break;
		default:
			map.put("code", "unsupported_response_type");
			return ROOT + "user/login_error";
		}

		UserApp ua = userAppService.getUserAuth(client_id);

		if (null == ua) {
			map.put("code", "invalid_client_id");
			return ROOT + "user/login_error";
		}

		map.put("req_client_id", client_id);
		map.put("req_redirect_uri", redirect_uri);
		map.put("req_response_type", response_type);
		map.put("req_scope", scope);
		map.put("req_state", state);

		map.put("user_app", ua);
		return ROOT + "user/login";
	}

	/**
	 *
	 * @param map
	 * @param client_id
	 * @param redirect_uri
	 * @param response_type
	 * @param scope
	 * @param state
	 * @param user_name
	 * @param user_pass
	 * @return
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.POST)
	public String authorize(Map<String, Object> map,
			@RequestParam String client_id, @RequestParam String redirect_uri,
			@RequestParam String response_type,
			@RequestParam(required = false) String scope,
			@RequestParam(required = false) String state,
			@RequestParam String user_name, @RequestParam String user_pass) {

		if (null == StringUtil.isEmpty(client_id)) {
			map.put("code", "invalid_client_id");
			return ROOT + "user/authorize_error";
		}

		if (null == StringUtil.isEmpty(redirect_uri)) {
			map.put("code", "invalid_redirect_uri");
			return ROOT + "user/authorize_error";
		}

		switch (response_type) {
		case "code":
			break;
		default:
			map.put("code", "unsupported_response_type");
			return ROOT + "user/authorize_error";
		}

		UserApp ua = userAppService.getUserAuth(client_id);

		if (null == ua) {
			map.put("code", "invalid_client_id");
			return ROOT + "user/authorize_error";
		}

		ResultMap<User> m = userService.login(user_name, user_pass);

		if (!m.getSuccess()) {
			map.put("code", m.getCode());
			map.put("msg", m.getMsg());
			return ROOT + "user/authorize_error";
		}

		String code = userAppService.authorize(client_id, redirect_uri,
				"user_id");

		if (null == code) {
			map.put("code", "invalid_authorize");
			return ROOT + "user/authorize_error";
		}

		return "redirect:" + redirect_uri + "?code=" + code + "&state=" + state;
	}
}