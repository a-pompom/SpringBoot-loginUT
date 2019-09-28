package app.login.test.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * アノテーションで与えられたユーザ情報をもとに実際に認証処理を行い、
 * テストコード用のSecurityContextを生成する
 * @author aoi
 *
 */
public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser>{
		
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		
		// ユーザ名・パスワードで認証するためのトークンを発行
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customUser.username(), customUser.password());		
		// ログイン処理
		Authentication auth = authenticationManager.authenticate(authToken);
		
		context.setAuthentication(auth);
		
		return context;
	}

}
