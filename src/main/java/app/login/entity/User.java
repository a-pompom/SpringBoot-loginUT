package app.login.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ut_user")
@Data
public class User extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private Long userId;
	
	@Column(name = "username", nullable = false, length = 255)
	private String username;
	
	@Column(name = "password", nullable = false, length = 255)
	private String password;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ut_user_authority", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private List<Authority> authList;

}
