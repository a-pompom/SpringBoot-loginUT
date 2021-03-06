package app.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.login.service.UserDetailsServiceImpl;

/**
 * 認証・認可処理を扱うための設定クラス
 * @author aoi
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	// 認証ユーザサービス
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	// ログイン成功時のハンドラ
	@Autowired
	private SuccessHandler successHandler;
	
	// パスワードのエンコード処理を行うBean
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		return bCryptPasswordEncoder;
	}
	
	/**
	 * ユーザ登録で手動ログインを行うための認証管理オブジェクト
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	/**
	 * 静的リソースの設定
	 * 静的リソースへのアクセスは許可しておく
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/images/**",
				"/css/**",
				"/javascript/**"
				);
	}
	
	/**
	 * 認証・認可の設定
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 認可
		http
			.authorizeRequests()
				.antMatchers("/signup/*").permitAll() //ユーザ登録画面は誰でも参照可
				// トップ画面はユーザ権限によって表示を分岐
				.antMatchers("/top_admin/**").hasAuthority("ADMIN")
				.antMatchers("/top_user/**").hasAuthority("USER")
				
				.anyRequest().authenticated()
			.and()
			// 認証
			.formLogin()
				.loginPage("/")
				.loginProcessingUrl("/sign_in")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(successHandler)
				.failureUrl("/?error")
				.permitAll()
			.and()
			// ログアウト
			.logout()
				.logoutUrl("/logout")
				.clearAuthentication(true)
				.logoutSuccessUrl("/?logout")
				.invalidateHttpSession(true)
				.permitAll();
	}
	
	/**
	 * 認証処理
	 */
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}
