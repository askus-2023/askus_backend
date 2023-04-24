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
	private String foodName;
	@Column
	@Enumerated(EnumType.STRING)
	private Category category;
	@Column
	private String ingredients;
	@Column
	private String content;
	@Column
	private String tag;
	@Column
	private Long likeCount = 0L;
	@Column
	private Long replyCount = 0L;

	public Board(Users users, String title, String foodName, Category category, String ingredients, String content,
		String tag) {
		this.users = users;
		this.title = title;
		this.foodName = foodName;
		this.category = category;
		this.ingredients = ingredients;
		this.content = content;
		this.tag = tag;
	}

	public void update(String title, String foodName, Category category, String ingredients, String content, String tag) {
		this.title = title;
		this.foodName = foodName;
		this.category = category;
		this.ingredients = ingredients;
		this.content = content;
		this.tag = tag;
	}

	public void addLikeCount() {
		this.likeCount++;
	}

	public void deleteLikeCount() {
		this.likeCount--;
	}

	public void addReplyCount() {
		this.replyCount++;
	}

	public void deleteReplyCount() {
		this.replyCount--;
	}
}
