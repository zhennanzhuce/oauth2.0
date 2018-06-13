package net.foreworld.oauth2.authorize.service.impl;

import java.util.List;

import net.foreworld.oauth2.authorize.mapper.UserAppMapper;
import net.foreworld.oauth2.authorize.service.UserAppService;
import net.foreworld.oauth2.model.UserApp;
import net.foreworld.service.impl.BaseService;

import org.springframework.stereotype.Service;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("userAppService")
public class UserAppServiceImpl extends BaseService<UserAppMapper, UserApp>
		implements UserAppService {

	@Override
	public UserApp getUserAuth(String id) {
		UserApp u = new UserApp();
		u.setId(id);
		u.setStatus(1);

		return selectByKey(u);
	}

	@Override
	public List<UserApp> getAll() {
		List<UserApp> l = selectByExample(null);
		return l;
	}

}
