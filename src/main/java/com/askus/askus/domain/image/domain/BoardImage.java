package com.askus.askus.domain.image.domain;

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

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "board_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardImage extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;
	@Column
	@Enumerated(EnumType.STRING)
	private ImageType imageType;
	@Column
	private String url;

	public BoardImage(Board board, ImageType imageType, String url) {
		this.board = board;
		this.imageType = imageType;
		this.url = url;
	}
}
