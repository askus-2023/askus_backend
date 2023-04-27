package com.askus.askus.domain.users.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.askus.askus.domain.users.dto.UsersRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.askus.askus.domain.common.BaseEntity;
import com.askus.askus.domain.image.domain.ProfileImage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Users entity
 *
 * @Mapping - mapped with ProfileImage
 * @Function - create, update email, nickname, password, set profileImage, encode password
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class Users extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String nickname;
	@OneToOne(mappedBy = "users", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private ProfileImage profileImage;

	public Users(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}

	public void setProfileImage(ProfileImage profileImage) {
		this.profileImage = profileImage;
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
	}

	public void update(UsersRequest.Patch request) {
		this.email = request.getEmail();
		this.nickname = request.getNickname();
	}

	public void updatePassword(String password) {
		this.password = password;
	}
}

