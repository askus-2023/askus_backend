package com.askus.askus.domain.board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.askus.askus.domain.common.BaseEntity;
import com.askus.askus.domain.users.domain.Users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id")
	private Users users;
	@Column
	private String title;
	@Column
	@Enumerated(EnumType.STRING)
	private Category category;
	@Column
	private String ingredients;
	@Column
	private String content;
	@Column
	private String tag;

	public Board(Users users, String title, Category category, String ingredients, String content, String tag) {
		this.users = users;
		this.title = title;
		this.category = category;
		this.ingredients = ingredients;
		this.content = content;
		this.tag = tag;
	}
}
