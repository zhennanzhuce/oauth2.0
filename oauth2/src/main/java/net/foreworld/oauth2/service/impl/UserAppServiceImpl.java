package net.foreworld.oauth2.service.impl;

import java.util.List;

import net.foreworld.oauth2.mapper.UserAppMapper;
import net.foreworld.oauth2.model.UserApp;
import net.foreworld.oauth2.service.UserAppService;
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

	@Override
	public String authorize(String client_id, String redirect_uri,
			String user_id) {
		return "83d844d8ae06fbc1299ba3f5ed088834";
	}

}
