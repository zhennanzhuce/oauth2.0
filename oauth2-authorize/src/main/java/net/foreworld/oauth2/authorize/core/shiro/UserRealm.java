package net.foreworld.oauth2.authorize.core.shiro;

import net.foreworld.core.shiro.ShiroKit;
import net.foreworld.core.shiro.ShiroUser;
import net.foreworld.core.shiro.factory.IShiro;
import net.foreworld.oauth2.authorize.core.shiro.factory.ShiroFactroy;
import net.foreworld.oauth2.model.User;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
public class UserRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authToken) throws AuthenticationException {
		// String acc = (String) token.getPrincipal();
		IShiro<User> shiroFactory = ShiroFactroy.me();
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		User user = shiroFactory.user(token.getUsername());
		ShiroUser shiroUser = shiroFactory.shiroUser(user);

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(shiroUser,
				user.getUser_pass(), new Md5Hash(user.getSalt()), getName());
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
		md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.HASHALGORITHMNAME);
		md5CredentialsMatcher.setHashIterations(ShiroKit.HASHITERATIONS);
		super.setCredentialsMatcher(md5CredentialsMatcher);
	}

}
