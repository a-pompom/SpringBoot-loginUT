package app.login.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopUserController{
	
	/**
	 * 初期処理 ユーザ用TOPページのビューを取得
	 * @return ユーザ用TOPページのビュー
	 */
	@RequestMapping("/top_user/init")
	private String init(Model model, @AuthenticationPrincipal UserDetails loginUser) {
		model.addAttribute("loginUsername", loginUser.getUsername());
		return "top_user";
	}

}