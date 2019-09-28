package app.login.test.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * 認証ユーザ情報をテストクラスへ設定するためのアノテーション
 * パラメータとして与えられたユーザ名・パスワードで認証処理を行う
 * @author aoi
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
	
	String username();
	String password();

}
