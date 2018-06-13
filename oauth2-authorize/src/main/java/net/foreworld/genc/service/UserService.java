package net.foreworld.genc.service;

import net.foreworld.genc.model.User;
import net.foreworld.model.ResultMap;
import net.foreworld.service.IService;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public interface UserService extends IService<User> {

	ResultMap<User> login(String user_name, String user_pass);

	User getByName(String user_name);

	ResultMap<User> register(User entity);

	/**
	 *
	 * @param entity
	 * @return
	 */
	User getByUser(User entity);

	/**
	 *
	 * @param id
	 * @param old_pass
	 * @param new_pass
	 * @return
	 */
	ResultMap<Void> changePwd(String id, String old_pass, String new_pass);

	/**
	 *
	 * @param id
	 */
	void resetPwd(String id);

}
