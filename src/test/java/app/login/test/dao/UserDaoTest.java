package app.login.test.dao;

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
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import app.login.dao.UserDao;
import app.login.entity.User;
import app.login.main.LoginUtApplication;
import app.login.test.util.CsvDataSetLoader;

/**
 * ユーザ登録Daoのテストクラス
 * 権限設定はサービスレイヤーで行われ、かつ権限情報自体はSpringSecurityのセッションで保持されるので、
 * Daoレイヤーでは、ユーザ名・パスワードが正しく設定・取得できるかのみを検証する
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
public class UserDaoTest {
	
	@Autowired
	private UserDao userDao;
	
	@BeforeEach
	void setUp() {
	}

	@Test
	@DatabaseSetup(value = "/signup/setUp/")
	@ExpectedDatabase(value = "/signup/create/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void saveOrUpdateで新規ユーザが登録される() {
		User entity = new User();
		
		entity.setUsername("new-user");
		entity.setPassword("new-password");
		
		userDao.saveOrUpdate(entity);
	}
	
	@Test
	@DatabaseSetup(value = "/signup/setUp/")
	void findByUsernameでユーザ名からレコードが取得できる() {
		User actual = userDao.findByUsername("user1");
		
		assertThat(actual.getUsername(), is("user1"));
	}
}
