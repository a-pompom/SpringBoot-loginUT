package app.login.test.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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
import app.login.test.util.WithMockCustomUser;

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
public class TopUserControllerTest {
	
	//mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DatabaseSetup(value = "/controller/top/setUp/")
	@WithMockCustomUser(username="top_user", password="password")
	void init処理でviewとしてユーザトップが渡される() throws Exception {
		this.mockMvc.perform(get("/top_user/init"))
			.andExpect(view().name("top_user"));
	}
	
	@Test
	@DatabaseSetup(value = "/controller/top/setUp/")
	@WithMockCustomUser(username="top_user", password="password")
	void init処理でログインユーザ名がモデルへ渡される() throws Exception {
		this.mockMvc.perform(get("/top_user/init"))
			.andExpect(model().attribute("loginUsername", "top_user"));
	}
	
	@Test
	@DatabaseSetup(value = "/controller/top/setUp/")
	@WithMockCustomUser(username="top_user", password="password")
	void ログアウト処理でログイン画面へ遷移する() throws Exception {
		this.mockMvc.perform(post("/logout")
			.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/?logout"));
	}

}
