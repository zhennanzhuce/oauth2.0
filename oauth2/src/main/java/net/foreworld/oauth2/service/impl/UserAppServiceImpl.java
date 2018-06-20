package net.foreworld.oauth2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import net.foreworld.oauth2.mapper.UserAppMapper;
import net.foreworld.oauth2.model.UserApp;
import net.foreworld.oauth2.service.UserAppService;
import net.foreworld.oauth2.service.UserService;
import net.foreworld.service.impl.BaseService;
import net.foreworld.util.RedisUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("userAppService")
public class UserAppServiceImpl extends BaseService<UserAppMapper, UserApp>
		implements UserAppService {

	@Resource
	RedisUtil redisUtil;

	@Resource
	UserService userService;

	@Override
	public UserApp getUserAuth(String id) {
		UserApp u = new UserApp();
		u.setId(id);
		u.setStatus(1);

		return selectByKey(u);
	}

	@Value("${lua.authorize.seconds}")
	String lua_authorize_seconds;

	@Value("${lua.authorize.sha1}")
	String lua_authorize_sha1;

	@Override
	public String authorize(String client_id, String redirect_uri,
			String user_id) {
		List<String> s = new ArrayList<String>();
		s.add("code");
		s.add("client_id");
		s.add("redirect_uri");
		s.add("user_id");
		s.add("seconds");

		List<String> b = new ArrayList<String>();
		b.add(UUID.randomUUID().toString().replace("-", ""));
		b.add(client_id);
		b.add(redirect_uri);
		b.add(user_id);
		b.add(lua_authorize_seconds);

		Jedis j = redisUtil.getJedis();

		if (null == j)
			return null;

		Object o = j.evalsha(lua_authorize_sha1, s, b);
		j.close();

		if (null == o)
			return null;

		return o.toString();
	}

	public static void main(String[] args) {
		System.err.println(UUID.randomUUID().toString().replace("-", ""));
		System.err.println(UUID.randomUUID().version());
	}

}
