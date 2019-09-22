package app.login.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 認証成功後に遷移する画面を管理するコントローラ
 * @author aoi
 *
 */
@Controller
public class TopAdminController {
	
	/**
	 * 初期処理 管理者用TOPページのビューを取得
	 * @return 管理者用TOPページのビュー
	 */
	@RequestMapping("/top_admin/init")
	private String init(Model model, @AuthenticationPrincipal UserDetails loginUser) {
		model.addAttribute("loginUsername", loginUser.getUsername());
		return "top_admin";
	}

}
