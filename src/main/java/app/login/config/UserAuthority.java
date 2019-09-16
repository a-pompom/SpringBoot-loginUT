package app.login.config;

import org.springframework.security.core.GrantedAuthority;

import app.login.entity.Authority;
import lombok.Data;

/**
 * 権限情報を扱うインタフェースの実装クラス
 * 権限エンティティを保持する　
 * @author aoi
 *
 */
@Data
public class UserAuthority implements GrantedAuthority {
	
	private Authority authority;
	
	public UserAuthority(Authority authority) {
		this.authority = authority;
	}
	
	@Override
	public String getAuthority() {
		return this.authority.getAuthority();
	}

}
