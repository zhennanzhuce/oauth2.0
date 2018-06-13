package net.foreworld.oauth2.authorize.service.impl;

import java.util.Date;
import java.util.List;

import net.foreworld.model.ResultMap;
import net.foreworld.oauth2.authorize.mapper.UserMapper;
import net.foreworld.oauth2.authorize.service.UserService;
import net.foreworld.oauth2.model.User;
import net.foreworld.service.impl.BaseService;
import net.foreworld.util.RandomUtil;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import tk.mybatis.mapper.entity.Example;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseService<UserMapper, User> implements
		UserService {

	@Override
	public int save(User entity) {
		entity.setId(null);
		entity.setCreate_time(new Date());
		entity.setStatus(Status.START.value());
		return super.save(entity);
	}

	@Override
	public int updateNotNull(User entity) {
		entity.setCreate_time(null);
		return super.updateNotNull(entity);
	}

	@Value("${server.port}")
	String server_port;

	@Override
	public ResultMap<User> login(String user_name, String user_pass) {
		ResultMap<User> map = new ResultMap<User>();
		map.setSuccess(false);

		User u = getByName(user_name);

		if (null == u) {
			map.setMsg("用户名或密码输入错误");
			map.setCode("10001");
			return map;
		}

		if (1 != u.getStatus()) {
			map.setMsg("禁止登陆");
			map.setCode("10002");
			return map;
		}

		if (!u.getUser_pass()
				.equals(new SimpleHash("MD5", user_pass, new Md5Hash(u
						.getSalt()), 1024).toString())) {
			map.setMsg("用户名或密码输入错误");
			map.setCode("10003");
			return map;
		}

		map.setData(u);
		map.setSuccess(true);
		return map;
	}

	@Override
	public User getByName(String user_name) {
		User entity = new User();
		entity.setUser_name(user_name);
		return getByUser(entity);
	}

	@Override
	public User getByUser(User entity) {
		if (null == entity)
			return null;

		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();

		String user_name = entity.getUser_name();
		if (null != user_name) {
			criteria.andEqualTo("user_name", user_name);
		}

		List<User> list = selectByExample(example);
		Assert.notNull(list, "user list is null");
		return 1 == list.size() ? list.get(0) : null;
	}

	@Override
	public ResultMap<Void> changePwd(String id, String old_pass, String new_pass) {
		ResultMap<Void> map = new ResultMap<Void>();
		map.setSuccess(false);

		User user = getById(id);

		if (null == user) {
			map.setMsg("没有找到该用户");
			return map;
		}

		if (!user.getUser_pass().equals(
				new SimpleHash("MD5", old_pass, new Md5Hash(user.getSalt()),
						1024).toString())) {
			map.setMsg("原始密码输入错误");
			return map;
		}

		/* 创建新对象 */
		user = new User();
		user.setId(id);
		user.setSalt(RandomUtil.genRandomInt(4));
		user.setUser_pass(new SimpleHash("MD5", new_pass, new Md5Hash(user
				.getSalt()), 1024).toString());
		updateNotNull(user);

		map.setSuccess(true);
		return map;
	}

	@Value("${app.pwd.def:111111}")
	String app_pwd_def;

	@Override
	public void resetPwd(String id) {
		User user = new User();
		user.setId(id);
		user.setSalt(RandomUtil.genRandomInt(4));
		user.setUser_pass(new SimpleHash("MD5", app_pwd_def, new Md5Hash(user
				.getSalt()), 1024).toString());
		updateNotNull(user);
	}

}
