package app.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopUserController {
	
	/**
	 * 初期処理 ユーザ用TOPページのビューを取得
	 * @return ユーザ用TOPページのビュー
	 */
	@RequestMapping("/top_user/init")
	private String init() {
		return "top_user";
	}

}