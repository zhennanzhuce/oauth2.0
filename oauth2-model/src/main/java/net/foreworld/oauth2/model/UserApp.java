package net.foreworld.oauth2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.foreworld.model.Model;

/**
 *
 * @author huangxin <3203317@qq.com>
 *
 */
@Table(name = "s_user_app")
public class UserApp extends Model<Model<UserApp>> {

	private static final long serialVersionUID = -8378080971689684386L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	private String app_name;

	private String app_desc;

	private String user_id;

	private String seckey;

	private String redirect_uri;

	private Integer status;

	private Date create_time;

	private String website;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public String getApp_desc() {
		return app_desc;
	}

	public void setApp_desc(String app_desc) {
		this.app_desc = app_desc;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getSeckey() {
		return seckey;
	}

	public void setSeckey(String seckey) {
		this.seckey = seckey;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}
