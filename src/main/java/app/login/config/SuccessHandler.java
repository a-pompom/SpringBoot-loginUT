package app.login.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * ログイン成功時の挙動を制御するためのハンドラ　
 * @author aoi
 *
 */
@Component
public class SuccessHandler implements AuthenticationSuccessHandler {
	
	/**
	 * 認証成功時に呼ばれる処理
	 * 権限に応じたTOP画面へ遷移
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		boolean isAdmin = false;
		
		UserDetails user = (UserDetails)authentication.getPrincipal();
		
		// 管理者権限を有しているか
		for (GrantedAuthority userAuthority : user.getAuthorities()) {
			if (userAuthority.getAuthority().equals("ADMIN")) {
				isAdmin = true;
			}
			
		}
		
		// 管理者TOPページ
		if (isAdmin) {
			response.sendRedirect("/top_admin/init");
			return;
		}
		
		// ユーザTOPページ
		response.sendRedirect("/top_user/init");
		return;
	}

}
