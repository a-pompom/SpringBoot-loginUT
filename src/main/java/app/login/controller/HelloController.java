package app.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 認証成功後に遷移する画面を管理するコントローラ
 * @author aoi
 *
 */
@Controller
public class HelloController {
	
	@RequestMapping("/hello")
	private String init() {
		return "hello";
	}

}
