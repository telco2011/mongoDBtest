package tlc.mongodb.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class User {
	
	public static String COLLECTION = "users";

	public static String NICKNAME = "_id";
	public static String USERNAME = "username";
	public static String PASSWORD = "password";
	public static String SOCIALACCOUNTS = "socialAccounts";
	
	@Id
	private String nickname;
	
	private String username;
	private String password;
	private List<SocialAccount> socialAccounts;

	public List<SocialAccount> getSocialAccounts() {
		return socialAccounts;
	}

	public void setSocialAccounts(List<SocialAccount> socialAccounts) {
		this.socialAccounts = socialAccounts;
	}
	
	public void addSocialAccount(SocialAccount sa) {
		socialAccounts.add(sa);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(String username, String password, String nickname) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.socialAccounts = new ArrayList<SocialAccount>();
	}

	@Override
	public String toString() {
		String result = "Usuario [id=" + nickname + ", username= " + username + ", password=" + password;
		for (SocialAccount sa : socialAccounts) {
			result += ", social account=" + sa.getSocialType().toString();
		}
		return result + "]";
	}

}