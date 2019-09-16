package app.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ログイン画面を管理するためのコントローラ
 * @author aoi
 *
 */
@Controller
public class LoginController {

	/**
	 * 初期表示処理
	 * @return ログイン画面のビュー
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String init() {
		return "login";
	}
	
}
