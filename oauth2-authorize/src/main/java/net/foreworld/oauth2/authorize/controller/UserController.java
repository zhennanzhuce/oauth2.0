package net.foreworld.oauth2.authorize.controller;

import java.util.Map;

import javax.annotation.Resource;

import net.foreworld.controller.BaseController;
import net.foreworld.oauth2.authorize.service.UserService;
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

	/**
	 *
	 * @param map
	 * @param client_id
	 * @param redirect_uri
	 * @param response_type
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginUI(Map<String, Object> map,
			@RequestParam(required = true) String client_id,
			@RequestParam(required = true) String redirect_uri,
			@RequestParam(required = true) String response_type) {

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

		return "user/login";
	}

	/**
	 *
	 * @param map
	 * @param client_id
	 * @param redirect_uri
	 * @param response_type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/" }, method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> authorize(Map<String, Object> map,
			@RequestParam(required = true) String client_id,
			@RequestParam(required = true) String redirect_uri,
			@RequestParam(required = true) String response_type) {

		if (null == StringUtil.isEmpty(client_id)) {
			map.put("code", "invalid_client_id");
			return map;
		}

		if (null == StringUtil.isEmpty(redirect_uri)) {
			map.put("code", "invalid_redirect_uri");
			return map;
		}

		if (!"code".equals(response_type)) {
			map.put("code", "unsupported_response_type");
			return map;
		}

		return map;
	}
}