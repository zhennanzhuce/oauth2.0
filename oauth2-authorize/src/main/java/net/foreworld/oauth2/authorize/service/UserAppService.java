package net.foreworld.oauth2.authorize.service;

import java.util.List;

import net.foreworld.oauth2.model.UserApp;
import net.foreworld.service.IService;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public interface UserAppService extends IService<UserApp> {

	UserApp getUserAuth(String id);

	List<UserApp> getAll();

}
