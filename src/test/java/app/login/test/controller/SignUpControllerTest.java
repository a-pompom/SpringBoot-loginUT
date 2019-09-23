package app.login.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class SignUpControllerTest {
	
	//mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
	}
	
	@Test
	void init処理でviewとしてsignupが渡される() throws Exception {
		this.mockMvc.perform(get("/signup/init"))
			.andExpect(status().isOk())
			.andExpect(view().name("signup"));
	}
	
	@Test
	void 新規登録処理で管理者ユーザが作成される() throws Exception {
		this.mockMvc.perform(post("/signup/register")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("username", "ADMIN_user")
			.param("rawPassword", "password")
			.with(SecurityMockMvcRequestPostProcessors.csrf()))
		
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/top_admin/init"));
	}
	@Test
	void 新規登録処理で利用者ユーザが作成される() throws Exception {
		this.mockMvc.perform(post("/signup/register")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("username", "top_user")
			.param("rawPassword", "password")
			.with(SecurityMockMvcRequestPostProcessors.csrf()))
		
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/top_user/init"));
	}
	
	@Test
	void 空文字をPOSTするとNotEmptyエラーが発生() throws Exception {
		this.mockMvc.perform(post("/signup/register")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("username", "")
			.param("rawPassword", "")
			.with(SecurityMockMvcRequestPostProcessors.csrf()))
		
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("userForm", "username", "AuthInputType"))
			.andExpect(model().attributeHasFieldErrorCode("userForm", "rawPassword", "AuthInputType"));
			
		
			
	}
	
	@Test
	void 半角英数ハイフンアンダースコア以外に属する全角文字列をPOSTするとAuthInpuTypeエラーが発生() throws Exception {
		this.mockMvc.perform(post("/signup/register")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("username", "無効文字列")
			.param("rawPassword", "無効文字列")
			.with(SecurityMockMvcRequestPostProcessors.csrf()))
		
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("userForm", "username", "AuthInputType"))
			.andExpect(model().attributeHasFieldErrorCode("userForm", "rawPassword", "AuthInputType"));
	}
	@Test
	void 半角英数ハイフンアンダースコア以外に属する記号群をPOSTするとAuthInpuTypeエラーが発生() throws Exception {
		this.mockMvc.perform(post("/signup/register")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("username", "<script>alert(1)</script>")
			.param("rawPassword", ";delete from ut_user")
			.with(SecurityMockMvcRequestPostProcessors.csrf()))
		
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("userForm", "username", "AuthInputType"))
			.andExpect(model().attributeHasFieldErrorCode("userForm", "rawPassword", "AuthInputType"));
	}
	
	@DatabaseSetup(value = "/controller/signup/setUp/")
	@Test
	void DBに既に存在するユーザ名でPOSTするとUniqueUsernameエラーが発生() throws Exception {
		this.mockMvc.perform(post("/signup/register")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("username", "test_user")
			.param("rawPassword", "password")
			.with(SecurityMockMvcRequestPostProcessors.csrf()))
		
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("userForm", "username", "UniqueUsername"));
			
	}
	
}
