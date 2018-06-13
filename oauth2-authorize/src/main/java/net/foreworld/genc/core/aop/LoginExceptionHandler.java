package net.foreworld.genc.core.aop;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@ControllerAdvice
public class LoginExceptionHandler {

	@Resource
	private MessageSourceAccessor msa;

	/**
	 *
	 * @param e
	 * @param model
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(CredentialsException.class)
	public Map<String, Object> credentials(CredentialsException e, Model model) {
		model.addAttribute("tips", msa.getMessage("err_login"));

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", false);
		result.put("msg", msa.getMessage("err_login"));
		return result;
	}

	/**
	 *
	 * @param e
	 * @param model
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(DisabledAccountException.class)
	public Map<String, Object> disabledAccount(DisabledAccountException e,
			Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", false);
		result.put("msg", "DisabledAccountException");
		return result;
	}

	/**
	 *
	 * @param e
	 * @param model
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(UnknownAccountException.class)
	public Map<String, Object> unknownAccount(UnknownAccountException e,
			Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", false);
		result.put("msg", "UnknownAccountException");
		return result;
	}
}
