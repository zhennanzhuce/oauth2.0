package net.foreworld.oauth2.sec.service;

import javax.annotation.Resource;

import net.foreworld.oauth2.model.User;
import net.foreworld.oauth2.sec.model.SecUser;
import net.foreworld.oauth2.service.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("secUserService")
public class SecUserService implements UserDetailsService {

	@Resource
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String user_name)
			throws UsernameNotFoundException {

		User user = userService.getByName(user_name);

		if (null == user)
			return null;

		SecUser secUser = new SecUser();
		secUser.setId(user.getId());
		secUser.setUsername(user_name);
		secUser.setPassword(user.getUser_pass() + "," + user.getSalt());
		return secUser;
	}

}
