package app.login.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Daoの基本的なメソッドを実装したクラス
 * 各Daoクラスはこれを継承して利用する
 * @author aoi
 * @param <T>
 *
 */
public class BaseDao<T> {
	
	@PersistenceContext
	private EntityManager em;
			
	//コンストラクタ
	public BaseDao() {
	}
	
	public EntityManager getEm() {
		return this.em;
	}
	
	/**
	 * エンティティをDBへ登録する
	 * @param entity DBへ登録する対象となるエンティティ
	 */
	public T saveOrUpdate(T entity) {
		return em.merge(entity);
	}
	

}
