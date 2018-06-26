package net.foreworld.oauth2.sec.config;

import javax.annotation.Resource;

import net.foreworld.oauth2.sec.service.SecUserService;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http //
		.csrf().disable() //
				.authorizeRequests() //
				.antMatchers("/").permitAll() //
				.anyRequest().authenticated() //
				.and() //
				.httpBasic();
	}

	@Value("${spring.mvc.static-path-pattern}")
	String static_path_pattern;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(static_path_pattern);
	}

	@Resource
	SecUserService secUserService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(secUserService).passwordEncoder(
				new PasswordEncoder() {

					@Override
					public boolean matches(CharSequence rawPassword,
							String encodedPassword) {

						String[] passwords = encodedPassword.split(",");

						String old_pass = new SimpleHash("MD5", rawPassword,
								new Md5Hash(passwords[1]), 1024).toString();

						return old_pass.equals(passwords[0]);
					}

					@Override
					public String encode(CharSequence rawPassword) {
						return null;
					}
				});
	}

	public static void main(String[] args) {
		String passS = "a,b";

		String[] strs = passS.split(",");
		System.err.println(strs[0]);
		System.err.println(strs[1]);
	}

}