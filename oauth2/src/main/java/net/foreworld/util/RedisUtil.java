package net.foreworld.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@PropertySource("classpath:redis.properties")
@Component
@Scope
public class RedisUtil {

	@Value("${db.redis.maxIdle}")
	int db_redis_maxIdle;

	@Value("${db.redis.maxWaitMillis}")
	int db_redis_maxWaitMillis;

	@Value("${db.redis.testOnBorrow:true}")
	boolean db_redis_testOnBorrow;

	@Value("${db.redis.host:127.0.0.1}")
	String db_redis_host;

	@Value("${db.redis.port:6379}")
	int db_redis_port;

	@Value("${db.redis.timeout}")
	int db_redis_timeout;

	@Value("${db.redis.pass}")
	String db_redis_pass;

	JedisPool jedisPool = null;

	private void initPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(db_redis_maxIdle);
			config.setMaxWaitMillis(db_redis_maxWaitMillis);
			config.setTestOnBorrow(db_redis_testOnBorrow);
			// timeout 最大延迟时间
			jedisPool = new JedisPool(config, db_redis_host, db_redis_port,
					db_redis_timeout, db_redis_pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return
	 */
	public Jedis getJedis() {
		if (null == jedisPool) {
			initPool();
		}
		try {
			return jedisPool.getResource();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
