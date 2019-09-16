package app.login.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.login.dao.UserDao;

/**
 * 入力されたユーザIDがユニークなものか検証する
 * @author aoi
 *
 */
@Constraint(validatedBy = UniqueUsername.UniqueUsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
	String message() default "入力されたユーザIDは既に使用されています";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	@Component
	public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
		
		@Autowired
		UserDao dao;
		
		@Override
		public void initialize(UniqueUsername uniqueUserID) {
		}
		
		/**
		 * 入力されたユーザIDがユニークなものか検証する
		 * ユニーク→true ユニークでない→false
		 */
		@Override
		public boolean isValid(String username, ConstraintValidatorContext cxt) {
			//DB上に存在しない場合はnullが返るため、nullチェックで検証
			if(dao.findByUsername(username) == null) {
				return true;
			};
			
			return false;
		}
	}
}