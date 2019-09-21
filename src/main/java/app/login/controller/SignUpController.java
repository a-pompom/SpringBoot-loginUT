package app.login.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.login.common.LoginConst;
import app.login.controller.fd.helper.SignUpControllerHelper;
import app.login.dto.UserDto;
import app.login.form.UserForm;
import app.login.service.SignUpService;

/**
 * ユーザ登録画面を管理するためのコントローラー
 * @author aoi
 *
 */
@Controller
public class SignUpController {
	
	/**
	 * ユーザ登録サービス
	 */
	@Autowired
	SignUpService signUpService;
	
	/**
	 * ユーザ登録画面で入力された情報で認証を行うための機能
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/**
	 * 初期処理 ユーザ登録画面のビューを取得する
	 * @return ユーザ登録画面のビュー
	 */
	@RequestMapping(value = "/signup/init", method = RequestMethod.GET)
	private String init() {
		return "signup";
	}
	
	/**
	 * ユーザ登録処理 新規ユーザをDBに登録後、権限ごとのTOP画面へリダイレクト
	 * @param request リクエスト
	 * @param form フォームオブジェクト
	 * @param result バリデーション結果格納オブジェクト
	 * @return TOP画面のビュー
	 */
	@RequestMapping(value = "/signup/register")
	private ModelAndView register(HttpServletRequest request, @Valid UserForm form, BindingResult result) {
		
		/**
		 * 以下について検証
		 * ユーザ名: [空でない, ユニーク, 半角英数及び一部記号で構成]
		 * パスワード: [空でない, ユニーク, 半角英数及び一部記号で構成]
		 */
		if (result.hasErrors()) {
			return new ModelAndView("signup");
		}
		
		UserDto dto = SignUpControllerHelper.formToDto(form, new UserDto()); 
		
		signUpService.registerUser(dto);
		
		// DTOのパスワードはハッシュ化されているので、Formオブジェクトを利用して認証を行う
		return authWithAuthManager(request, form);
		
	}
	
	/**
	 * フォームをもとに新規登録されたユーザ情報でログイン処理を直接行う
	 * @param request 
	 * @param form フォームオブジェクト
	 * @return 権限ごとのTOP画面
	 */
	private ModelAndView authWithAuthManager(HttpServletRequest request, UserForm form) {
		
		// ユーザ名・パスワードで認証するためのトークンを発行
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(form.getUsername(), form.getRawPassword());
		
		// セッション情報を設定
		authToken.setDetails(new WebAuthenticationDetails(request));
		
		// ログイン処理
		Authentication auth = authenticationManager.authenticate(authToken);
		// ログイン後に渡されるセッション情報を手動で設定
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		// 認証オブジェクトの権限情報から遷移先を決定
		for (GrantedAuthority userAuth : auth.getAuthorities()) {
			if (userAuth.getAuthority().equals(LoginConst.AuthType.ADMIN.toString())) {
				return new ModelAndView("redirect:/top_admin/init");
			}
		}
		
		return new ModelAndView("redirect:/top_user/init");
	}
	
	@ModelAttribute("userForm")
	UserForm userForm() {
		return new UserForm();
	}

}
