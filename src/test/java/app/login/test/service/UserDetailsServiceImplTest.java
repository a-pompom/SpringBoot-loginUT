package app.login.test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import app.login.main.LoginUtApplication;
import app.login.test.util.CsvDataSetLoader;

/**
 * ユーザ認証サービスのテストクラス
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
public class UserDetailsServiceImplTest {
	
	@Autowired
	private UserDetailsService sut;
	
	@Test
	@DatabaseSetup(value = "/service/userDetails/setUp/")
	void loadUserByUsernameでユーザが取得できる() {
		
		UserDetails actual = sut.loadUserByUsername("user1");
		
		assertThat(actual.getUsername(), is("user1"));
		assertThat(actual.getPassword(), is("$2a$10$LK1q5kWjEtRWWA.flTGylOUXU4w0xMatlN6h8n6ADNiEeel6.PHF6"));
		
		// ここでは権限の種類については検証対象外となるので、なんらかの権限が取得できているかを検証するにとどめておく
		assertThat(actual.getAuthorities().size(), is(1));
	}
	
	@Test
	@DatabaseSetup(value = "/service/userDetails/setUp/")
	void 存在しないユーザ名でloadUserByUsernameを実行するとUsernameNotFoundExceptionが投げられる() {
		
		assertThrows(UsernameNotFoundException.class, () -> {
			sut.loadUserByUsername("nobody");
		});
	}
}
