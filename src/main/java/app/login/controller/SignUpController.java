package app.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ユーザ登録画面を管理するためのコントローラー
 * @author aoi
 *
 */
@Controller
public class SignUpController {
	
	@RequestMapping("signup")
	private String init() {
		return "signup";
	}

}
