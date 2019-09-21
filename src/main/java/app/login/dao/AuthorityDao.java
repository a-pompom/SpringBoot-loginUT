package app.login.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import app.login.entity.Authority;
import app.login.util.QueryBuilder;

/**
 * 権限情報を扱うためのDaoクラス
 * @author aoi
 *
 */
@Component
public class AuthorityDao extends BaseDao<Authority>{
	
	/**
	 * ユーザが持つ権限を取得
	 * @param userId 対象のユーザID Principalオブジェクトから取得
	 * @return 権限のリスト
	 */
	public List<Authority> findUserAuthorityList(Long userId) {
		
		QueryBuilder query = new QueryBuilder();
		
		query.append("select auth.authority_id, auth.authority ");
		query.append(" from ut_user_authority ua ");
		
		// ユーザと権限は一対多の関係ではあるが、Hibernateによって結合テーブルが作成されているので、
		// 結合テーブルをもとにユーザが持つ権限を取得する
		query.append(" inner join ut_authority auth ");
		query.append(" on ua.authority_id = auth.authority_id ");
		
		query.append(" where user_id = :userId").setParam("userId", userId);
		
		return findResultList(query.createQuery(Authority.class, getEm()));
	}
	
	/**
	 * 権限名から対応するエンティティを取得
	 * @param theAuthority 権限名
	 * @return 権限エンティティ
	 */
	public Authority findByAuthority(String theAuthority) {
		QueryBuilder query = new QueryBuilder();
		
		query.append("select authority_id, authority ");
		query.append(" from ut_authority ");
		
		query.append(" where authority = :theAuthority").setParam("theAuthority", theAuthority);
		
		return findSingle(query.createQuery(Authority.class, getEm()));
	}

}
