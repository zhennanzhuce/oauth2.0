package net.foreworld.genc.core.shiro.factory;

import javax.annotation.Resource;

import net.foreworld.core.shiro.ShiroUser;
import net.foreworld.core.shiro.factory.IShiro;
import net.foreworld.core.util.SpringContextHolder;
import net.foreworld.genc.model.User;
import net.foreworld.genc.service.UserService;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 * @param <T>
 */
@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ShiroFactroy implements IShiro<User> {

	@Resource
	private UserService userService;

	@SuppressWarnings("unchecked")
	public static IShiro<User> me() {
		return SpringContextHolder.getBean(IShiro.class);
	}

	@Override
	public ShiroUser shiroUser(User entity) {
		ShiroUser shiroUser = new ShiroUser();

		shiroUser.setId(entity.getId());
		shiroUser.setUser_name(entity.getUser_name());

		return shiroUser;
	}

	@Override
	public User user(String user_name) {
		User user = userService.getByName(user_name);

		if (null == user)
			throw new UnknownAccountException();

		if (1 != user.getStatus())
			throw new DisabledAccountException();

		return user;
	}

}
