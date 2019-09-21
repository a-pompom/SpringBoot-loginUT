package app.login.form;

import app.login.annotation.AuthInputType;
import app.login.annotation.NotEmpty;
import app.login.annotation.UniqueUsername;
import lombok.Data;

/**
 * 画面のユーザ情報を扱うためのフォームオブジェクト
 * @author aoi
 *
 */
@Data
public class UserForm {
	
	@NotEmpty
	@AuthInputType
	@UniqueUsername
	private String username;
	
	@NotEmpty
	@AuthInputType
	private String rawPassword;
	
	private String cofirmPassword;

}
