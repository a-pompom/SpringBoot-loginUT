package app.login.test.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import app.login.main.LoginUtApplication;
import app.login.test.util.CsvDataSetLoader;

@ExtendWith(SpringExtension.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class,
	  WithSecurityContextTestExecutionListener.class
	})
@AutoConfigureMockMvc
@SpringBootTest(classes = {LoginUtApplication.class})
@Transactional
public class LoginControllerTest {
	
	//mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DatabaseSetup(value = "/controller/top/setUp/")
	void DB上に存在する利用者ユーザでログインできる() throws Exception {
		this.mockMvc.perform(formLogin("/sign_in")
				.user("top_user")
				.password("password"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/top_user/init"));
	}
	@Test
	@DatabaseSetup(value = "/controller/top/setUp/")
	void DB上に存在する管理者ユーザでログインできる() throws Exception {
		this.mockMvc.perform(formLogin("/sign_in")
				.user("admin_user")
				.password("password"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/top_admin/init"));
	}
	
	@Test
	@DatabaseSetup(value = "/controller/top/setUp/")
	void DB上に存在するユーザでパスワードを間違えると失敗画面へリダイレクトされる() throws Exception {
		this.mockMvc.perform(formLogin("/sign_in")
				.user("top_user")
				.password("wrongpassword"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/?error"));
	}
	
	@Test
	@DatabaseSetup(value = "/controller/top/setUp/")
	void DB上に存在しないユーザでログインするとエラーURLへリダイレクトされる() throws Exception {
		this.mockMvc.perform(formLogin("/sign_in")
				.user("nobody")
				.password("password"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/?error"));
	}

}
