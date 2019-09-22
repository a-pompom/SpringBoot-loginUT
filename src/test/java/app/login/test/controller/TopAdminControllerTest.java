package app.login.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
public class TopAdminControllerTest {
	
	//mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DatabaseSetup(value = "/controller/top/setUp/")
	@WithMockCustomUser(username="admin_user", password="password")
	void init処理でviewとしてユーザトップが渡される() throws Exception {
		this.mockMvc.perform(get("/top_admin/init"))
			.andExpect(view().name("top_admin"));
	}
	
	@Test
	@DatabaseSetup(value = "/controller/top/setUp/")
	@WithMockCustomUser(username="admin_user", password="password")
	void init処理でログインユーザ名がモデルへ渡される() throws Exception {
		this.mockMvc.perform(get("/top_admin/init"))
			.andExpect(model().attribute("loginUsername", "admin_user"));
	}

}
