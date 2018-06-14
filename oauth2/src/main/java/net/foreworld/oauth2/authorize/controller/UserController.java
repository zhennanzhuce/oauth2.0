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

	private static final String PATH_USER_LOGIN = "authorize/user/login";
	private static final String PATH_USER_LOGIN_ERROR = "authorize/user/login_error";
	private static final String PATH_USER_AUTHORIZE_ERROR = "authorize/user/authorize_error";

	private static final String CODE = "code";
	private static final String MSG = "msg";

	private static final String invalid_client_id = "invalid_client_id";
	private static final String invalid_redirect_uri = "invalid_redirect_uri";
	private static final String unsupported_response_type = "unsupported_response_type";
	private static final String invalid_authorize = "invalid_authorize";

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
			map.put(CODE, invalid_client_id);
			return PATH_USER_LOGIN_ERROR;
		}

		if (null == StringUtil.isEmpty(redirect_uri)) {
			map.put(CODE, invalid_redirect_uri);
			return PATH_USER_LOGIN_ERROR;
		}

		switch (response_type) {
		case "token":
		case "code":
			break;
		default:
			map.put(CODE, unsupported_response_type);
			return PATH_USER_LOGIN_ERROR;
		}

		UserApp ua = userAppService.getUserAuth(client_id);

		if (null == ua) {
			map.put(CODE, invalid_client_id);
			return PATH_USER_LOGIN_ERROR;
		}

		map.put("req_client_id", client_id);
		map.put("req_redirect_uri", redirect_uri);
		map.put("req_response_type", response_type);
		map.put("req_scope", scope);
		map.put("req_state", state);

		map.put("user_app", ua);
		return PATH_USER_LOGIN;
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
			map.put(CODE, invalid_client_id);
			return PATH_USER_AUTHORIZE_ERROR;
		}

		if (null == StringUtil.isEmpty(redirect_uri)) {
			map.put(CODE, invalid_redirect_uri);
			return PATH_USER_AUTHORIZE_ERROR;
		}

		switch (response_type) {
		case "code":
			break;
		default:
			map.put(CODE, unsupported_response_type);
			return PATH_USER_AUTHORIZE_ERROR;
		}

		UserApp ua = userAppService.getUserAuth(client_id);

		if (null == ua) {
			map.put(CODE, invalid_client_id);
			return PATH_USER_AUTHORIZE_ERROR;
		}

		ResultMap<User> m = userService.login(user_name, user_pass);

		if (!m.getSuccess()) {
			map.put(CODE, m.getCode());
			map.put(MSG, m.getMsg());
			return PATH_USER_AUTHORIZE_ERROR;
		}

		String code = userAppService.authorize(client_id, redirect_uri, m
				.getData().getId());

		if (null == code) {
			map.put(CODE, invalid_authorize);
			return PATH_USER_AUTHORIZE_ERROR;
		}

		return "redirect:" + redirect_uri + "?code=" + code + "&state=" + state;
	}
}
