package net.foreworld.oauth2.authorize.test;

import java.util.List;

import javax.annotation.Resource;

import net.foreworld.oauth2.authorize.RunOauth2AppClient;
import net.foreworld.oauth2.authorize.service.UserAppService;
import net.foreworld.oauth2.model.UserApp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

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
	public void test_getAll() {
		List<UserApp> l = userAppService.getAll();
		System.err.println(l.size());

		for (int i = 0; i < l.size(); i++) {
			System.err.println(l.get(i).getId() + ":" + l.get(i).getApp_name());
		}
	}

}
