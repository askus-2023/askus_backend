package com.askus.askus.domain.reply.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.common.BaseEntity;
import com.askus.askus.domain.users.domain.Users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Reply entity
 *
 * @Mapping - mapped with Users
 * @Mapping - mapped with Board
 * @Function - create
 * @Function - update
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reply")
@Entity
public class Reply extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id")
	private Users users;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	@Column(columnDefinition = "TEXT")
	private String content;

	public Reply(Users users, Board board, String content) {
		this.users = users;
		this.board = board;
		this.content = content;
	}

	public void update(String content) {
		this.content = content;
	}
}
