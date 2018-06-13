package net.foreworld.genc.test;

import java.util.List;

import javax.annotation.Resource;

import net.foreworld.genc.RunGencAppClient;
import net.foreworld.genc.model.User;
import net.foreworld.genc.service.UserService;
import net.foreworld.model.ResultMap;

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
@Import(RunGencAppClient.class)
public class UserServiceTest {

	@Resource
	UserService userService;

	@Test
	public void test_selectByExample() {
		List<User> list = userService.selectByExample(null);
		System.err.println(list.size());
	}

	@Test
	public void test_register() {
		User user = new User();
		user.setUser_name("testuser");

		ResultMap<User> map = userService.register(user);
		System.err.println(map.getSuccess());
		System.err.println(map.getData().getCreate_time());
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
		userService.resetPwd("1");
	}

}
