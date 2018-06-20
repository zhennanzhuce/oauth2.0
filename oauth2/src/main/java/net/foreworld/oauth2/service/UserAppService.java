package net.foreworld.oauth2.service;

import net.foreworld.oauth2.model.UserApp;
import net.foreworld.service.IService;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public interface UserAppService extends IService<UserApp> {

	UserApp getUserAuth(String id);

	String authorize(String client_id, String redirect_uri, String user_id);

}
