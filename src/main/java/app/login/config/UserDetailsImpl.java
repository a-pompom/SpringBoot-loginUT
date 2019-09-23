package app.login.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import app.login.entity.Authority;
import app.login.entity.User;

/**
 * 認証ユーザ情報の実装クラス
 * @author aoi
 *
 */
public class UserDetailsImpl implements UserDetails{
	
	private String username;
	private String password;
	private List<Authority> authList;
	
	public UserDetailsImpl(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authList = user.getAuthList();
	}

	/**
	 * 権限情報を取得
	 * 権限はAuthorityエンティティを格納したUserAuthorityオブジェクトに格納して渡される
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
		
		for (Authority auth : authList) {
			grantedAuthorityList.add(new UserAuthority(auth));
		}
		
		return grantedAuthorityList;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	// アカウントが期限切れとなっていないかを返す
	// 今回のシステムでは特にアカウントの寿命等を設けないので、常にtrueを返す　
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// アカウントがロックされていないかを返す
	// 今回は公開するようなシステムでもないので、ロックはしないものとして、常にtrueを返す
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// パスワードが期限切れとなっていないかを返す
	// パスワードは基本的には不変とするので、今回は常にtrueを返す
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// ユーザが利用可能かを返す
	// 今回は全ユーザを利用可能とする
	@Override
	public boolean isEnabled() {
		return true;
	}

}
