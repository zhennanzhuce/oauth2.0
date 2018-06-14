package net.foreworld.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

import tk.mybatis.spring.annotation.MapperScan;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@MapperScan(basePackages = "net.foreworld.oauth2.mapper")
@ImportResource(locations = { "classpath:spring-all.xml" })
@SpringBootApplication(scanBasePackages = { "net.foreworld.core",
		"net.foreworld.oauth2" })
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class,
		MongoDataAutoConfiguration.class })
public class RunOauth2AppClient implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(RunOauth2AppClient.class);

	public static void main(String[] args) {
		SpringApplication.run(RunOauth2AppClient.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("服务启动完成");
	}

}
