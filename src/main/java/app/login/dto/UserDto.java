package app.login.dto;

import java.util.List;

import app.login.entity.Authority;
import lombok.Data;

/**
 * ユーザDTO
 * @author aoi
 *
 */
@Data
public class UserDto {
	
	/**
	 * ユーザID
	 */
	private Long userId;
	
	/**
	 * ユーザ名 ユニーク
	 */
	private String username;
	
	private String password;
	
	private List<Authority> authList;
}
