package net.foreworld.oauth2.authorize.test;

import java.util.List;

import javax.annotation.Resource;

import net.foreworld.model.ResultMap;
import net.foreworld.oauth2.authorize.RunOauth2AppClient;
import net.foreworld.oauth2.authorize.service.UserService;
import net.foreworld.oauth2.model.User;

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
public class UserServiceTest {

	@Resource
	UserService userService;

	@Test
	public void test_selectByExample() {
		List<User> l = userService.selectByExample(null);
		System.err.println(l.size());

		for (int i = 0; i < l.size(); i++) {
			System.err
					.println(l.get(i).getId() + ":" + l.get(i).getUser_name());
		}
	}

	@Test
	public void test_getByName() {
		User user = userService.getByName("hx");
		System.err.println(user.getCreate_time());
	}

	@Test
	public void test_changePwd() {
		ResultMap<Void> map = userService.changePwd("1", "123456", "123456");
		System.err.println(map.getSuccess());
		System.err.println(map.getMsg());
	}

	@Test
	public void test_resetPwd() {
		userService.resetPwd("9c012a33aa8b4ecc8aaf20ea149a6f25");
	}

	@Test
	public void test_login() {
		ResultMap<User> map = userService.login("mega", "123456");

		if (!map.getSuccess()) {
			System.err.println(map.getCode() + ":" + map.getMsg());
			return;
		}

		User u = map.getData();

		System.err.println(u.getId() + ":" + u.getUser_name() + ":"
				+ u.getSalt());
	}

}
