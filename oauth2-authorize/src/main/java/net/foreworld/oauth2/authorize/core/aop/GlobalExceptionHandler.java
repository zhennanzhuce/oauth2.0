package net.foreworld.oauth2.authorize.core.aop;

import javax.annotation.Resource;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@Resource
	private MessageSourceAccessor msa;
}
