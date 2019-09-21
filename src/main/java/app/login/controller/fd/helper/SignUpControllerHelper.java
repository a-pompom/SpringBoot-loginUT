package app.login.controller.fd.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.login.dto.UserDto;
import app.login.form.UserForm;

/**
 * 
 * FormオブジェクトとDTOの詰め替えを行うためのヘルパークラス
 * @author aoi
 *
 */
public class SignUpControllerHelper {
	
	/**
	 * DTOからフォームオブジェクトへ詰め替える
	 * ユーザ登録では常にフォームオブジェクトの初期値は空となるので、処理は行わない
	 * @param form
	 * @param dto
	 * @return 空のフォームオブジェクト
	 */
	public static UserForm dtoToForm(UserForm form, UserDto dto) {
		return form;
	}
	
	/**
	 * フォームからDTOへ詰め替える
	 * このとき、パスワードのハッシュ化を行う
	 * @param form
	 * @param dto
	 * @return DTO
	 */
	public static UserDto formToDto(UserForm form, UserDto dto) {
		
		dto.setUsername(form.getUsername());
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		dto.setPassword(encoder.encode(form.getRawPassword()));
		
		return dto;
	}

}
