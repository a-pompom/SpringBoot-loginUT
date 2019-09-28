package app.login.form;

import app.login.annotation.AuthInputType;
import app.login.annotation.UniqueUsername;
import lombok.Data;

/**
 * 画面のユーザ情報を扱うためのフォームオブジェクト
 * @author aoi
 *
 */
@Data
public class UserForm {
	
	@AuthInputType
	@UniqueUsername
	private String username;
	
	@AuthInputType
	private String rawPassword;

}
