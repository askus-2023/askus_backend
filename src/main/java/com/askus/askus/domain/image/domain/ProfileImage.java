package com.askus.askus.domain.image.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.askus.askus.domain.common.BaseEntity;
import com.askus.askus.domain.users.domain.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "profile_image")
@NoArgsConstructor
public class ProfileImage extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users users;

	@Column
	private String url;

	public ProfileImage(Users users, String url) {
		this.users = users;
		this.url = url;
	}
}
