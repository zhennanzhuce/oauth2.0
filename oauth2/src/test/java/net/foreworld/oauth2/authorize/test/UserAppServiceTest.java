package net.foreworld.oauth2.authorize.test;

import java.util.List;

import javax.annotation.Resource;

import net.foreworld.oauth2.RunOauth2AppClient;
import net.foreworld.oauth2.model.UserApp;
import net.foreworld.oauth2.service.UserAppService;
import net.foreworld.util.FileUtil;
import net.foreworld.util.RedisUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(RunOauth2AppClient.class)
public class UserAppServiceTest {

	@Resource
	UserAppService userAppService;

	@Test
	public void test_getUserAuth() {
		// 513ae2a0f0d611e68376e3b0bc3e1d71
		UserApp ua = userAppService.getUserAuth("1");

		if (null == ua) {
			System.err.println("obj is null");
			return;
		}

		System.err.println(ua.getApp_name());
	}

	@Test
	public void test_selectByExample() {
		List<UserApp> l = userAppService.selectByExample(null);
		System.err.println(l.size());

		for (int i = 0; i < l.size(); i++) {
			System.err.println(l.get(i).getId() + ":" + l.get(i).getApp_name());
		}
	}

	@Resource
	RedisUtil redisUtil;

	@Test
	public void test_scriptLoad() {
		System.err
				.println("authorize: "
						+ lua_sha1("D:/git/zhennanzhuce/oauth2.0/assets/lua/authorize.lua"));
		System.err
				.println("getSession: "
						+ lua_sha1("D:/git/zhennanzhuce/oauth2.0/assets/lua/getSession.lua"));
		System.err.println("pw: "
				+ lua_sha1("D:/git/zhennanzhuce/oauth2.0/assets/lua/pw.lua"));
		System.err
				.println("token: "
						+ lua_sha1("D:/git/zhennanzhuce/oauth2.0/assets/lua/token.lua"));
	}

	private String lua_sha1(String filePath) {
		String file = FileUtil.read(filePath);

		Jedis j = redisUtil.getJedis();

		if (null == j)
			return null;

		Object o = j.scriptLoad(file);

		if (null == o)
			return null;

		return o.toString();
	}

}
