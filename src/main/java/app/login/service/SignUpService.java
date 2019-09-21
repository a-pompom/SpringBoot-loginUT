package app.login.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.login.common.LoginConst;
import app.login.dao.AuthorityDao;
import app.login.dao.UserDao;
import app.login.dto.UserDto;
import app.login.entity.Authority;
import app.login.entity.User;

/**
 * ユーザ登録画面を管理するためのサービス
 * @author aoi
 *
 */
@Service
public class SignUpService {
	
	/**
	 * ユーザDao
	 */
	@Autowired
	private UserDao userDao;
	
	/**
	 * 権限Dao
	 */
	@Autowired
	private AuthorityDao authDao;
	
	/**
	 * 新規ユーザをDBに登録
	 * @param dto
	 */
	@Transactional
	public UserDto registerUser(UserDto dto) {
		User responseUser = userDao.saveOrUpdate(createEntity(dto));
		
		return convertToDto(responseUser);
	}
	
	/**
	 * DTOをもとにユーザのエンティティを作成
	 * @param dto
	 * @return
	 */
	private User createEntity(UserDto dto) {
		
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword());
		
		user.setAuthList(new ArrayList<Authority>());
		
		// 今回はユーザ名のプレフィックスで権限を仮で設定
		if (dto.getUsername().startsWith("ADMIN_")) {
			// 権限オブジェクトを設定
			user.getAuthList().add(authDao.findByAuthority(LoginConst.AuthType.ADMIN.toString()));
			
			return user;
		}
		
		user.getAuthList().add(authDao.findByAuthority(LoginConst.AuthType.USER.toString()));
		
		return user;
	}
	
	/**
	 * UserエンティティをDTOへ詰め替える
	 * @param user
	 * @return ユーザDTO
	 */
	private UserDto convertToDto(User user) {
		UserDto responseDto = new UserDto();
		responseDto.setUserId(user.getUserId());
		
		responseDto.setUsername(user.getUsername());
		responseDto.setPassword(user.getPassword());
		
		responseDto.setAuthList(user.getAuthList());
		
		return responseDto;
	}

}
