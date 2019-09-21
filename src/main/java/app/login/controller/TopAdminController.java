package app.login.controller;

import org.springframework.stereotype.Controller;
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
	private String init() {
		return "top_admin";
	}

}
