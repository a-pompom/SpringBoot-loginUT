package app.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ut_authority")
@Data
public class Authority extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "authority_id", unique = true, nullable = false)
	private Long authorityId;
	
	@Column(name = "authority", nullable = false, length = 255)
	private String authority;

}
