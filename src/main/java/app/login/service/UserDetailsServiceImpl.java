package app.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.login.config.UserDetailsImpl;
import app.login.dao.AuthorityDao;
import app.login.dao.UserDao;
import app.login.entity.User;

/**
 * 認証情報を取得するインタフェースの実装クラス
 * 認証ユーザの認証・権限情報を取得
 * @author aoi
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AuthorityDao authorityDao;
	
	/**
	 * ユーザ名をもとに認証用のユーザエンティティを取得
	 * 更に権限情報を設定し、認証用ユーザオブジェクトを生成
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDao.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User" + username + "was not found");
		}
		
		// 権限
		user.setAuthList(authorityDao.findUserAuthorityList(user.getUserId()));

		UserDetails userDetails = new UserDetailsImpl(user);
		return userDetails;
				
	}

}
