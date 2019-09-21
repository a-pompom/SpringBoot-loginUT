package app.login.test.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

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

import app.login.dao.AuthorityDao;
import app.login.entity.Authority;
import app.login.main.LoginUtApplication;
import app.login.test.util.CsvDataSetLoader;

/**
 * 権限Daoのテストクラス
 * 権限そのもの、ユーザの持つ権限リストの取得を検証する
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
public class AuthorityDaoTest{
	
	@Autowired
	private AuthorityDao authorityDao;
	
	@BeforeEach
	void setUp() {
	}
	
	@Test
	@DatabaseSetup(value = "/auth/authority/")
	void findUserAuthorityListでユーザの権限が取得できる() {
		// Setupで作成されたADMIN権限を持つユーザを取得
		List<Authority> actual = authorityDao.findUserAuthorityList(1L);
		
		// ADMINのみを権限として持つか
		assertThat(actual.size(), is(1));
	}
	
	@Test
	@DatabaseSetup(value = "/auth/setUp/")
	void findByAuthorityで権限が取得できる() {
		Authority actual = authorityDao.findByAuthority("ADMIN");
		
		assertThat(actual.getAuthority(), is("ADMIN"));
	}

}
