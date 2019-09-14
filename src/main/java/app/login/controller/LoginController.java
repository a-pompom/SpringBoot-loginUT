package app.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログイン画面を管理するためのコントローラ
 * @author aoi
 *
 */
@Controller
public class LoginController {

	@RequestMapping("/")
	private String init() {
		return "login";
	}
	
}
