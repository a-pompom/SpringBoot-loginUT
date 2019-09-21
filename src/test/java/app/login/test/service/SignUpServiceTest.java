package app.login.test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import app.login.common.LoginConst;
import app.login.dto.UserDto;
import app.login.main.LoginUtApplication;
import app.login.service.SignUpService;
import app.login.test.util.CsvDataSetLoader;

/**
 * ユーザ登録サービスのテストクラス
 * @author aoi
 *
 */
@ExtendWith(SpringExtension.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})
@SpringBootTest(classes = {LoginUtApplication.class})
@Transactional
public class SignUpServiceTest {
	
	@Autowired
	private SignUpService signUpService;
	
	private UserDto dto;
	
	@BeforeEach
	void setUp() {
		this.dto = new UserDto();
	}
	
	@Test
	@DatabaseSetup(value = "/service/signup/setUp/")
	void registerUserでADMINユーザが作成される() {
		dto.setUsername("ADMIN_user");
		dto.setPassword("password");
		
		UserDto actual = signUpService.registerUser(dto);
		
		// actualと同価のexpectedDTOを作成することもできるが、記述が冗長となるので、入力値と想定値のみで検証した
		assertThat(actual.getUsername(), is("ADMIN_user"));
		assertThat(actual.getPassword(), is("password"));
		assertThat(actual.getAuthList().get(0).getAuthority(), is(LoginConst.AuthType.ADMIN.toString()));
	}
	@Test
	@DatabaseSetup(value = "/service/signup/setUp/")
	void registerUserでUSERユーザが作成される() {
		dto.setUsername("user");
		dto.setPassword("password");
		
		UserDto actual = signUpService.registerUser(dto);
		
		assertThat(actual.getUsername(), is("user"));
		assertThat(actual.getPassword(), is("password"));
		assertThat(actual.getAuthList().get(0).getAuthority(), is(LoginConst.AuthType.USER.toString()));
	}

}
