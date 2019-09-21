package app.login.dao;

import org.springframework.stereotype.Component;

import app.login.entity.User;
import app.login.util.QueryBuilder;

/**
 * ユーザエンティティを管理するためのDaoクラス
 * @author aoi
 *
 */
@Component
public class UserDao extends BaseDao<User>{
	
	/**
	 * ユーザ名をもとに特定のユーザを検索
	 * ログイン・ユニークチェックで利用
	 * @param username 検索対象のユーザ名文字列
	 * @return User Entity→DBに存在 null→DBに存在しない
	 */
	public User findByUsername(String username) {
		
		QueryBuilder query = new QueryBuilder();
		
		query.append("select user_id, username, password ");
		query.append(" from ut_user ");
		query.append(" where username = :username").setParam("username", username);
		
		return findSingle(query.createQuery(User.class, getEm()));
	}
	
}
