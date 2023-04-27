package com.askus.askus.domain.board.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.askus.askus.domain.board.dto.BoardRequest;
import com.askus.askus.domain.common.BaseEntity;
import com.askus.askus.domain.image.domain.RepresentativeImage;
import com.askus.askus.domain.image.domain.ThumbnailImage;
import com.askus.askus.domain.users.domain.Users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Board entity
 *
 * @Mapping - mapped with Users, ThumbnailImage, RepresentativeImages
 * @Function - create, update, reset images, add images, like count, reply count, delete like count, reply count
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
@Entity
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
	@Column(columnDefinition = "TEXT")
	private String content;
	@Column
	private String tag;
	@OneToOne(mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private ThumbnailImage thumbnailImage;
	@OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<RepresentativeImage> representativeImages = new ArrayList<>();
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

	public void update(BoardRequest.Post request) {
		this.title = request.getTitle();
		this.foodName = request.getFoodName();
		this.category = request.getCategory();
		this.ingredients = request.getIngredients();
		this.content = request.getContent();
		this.tag = request.getTag();
	}

	public void resetThumbnailImage() {
		this.thumbnailImage = null;
	}

	public void setThumbnailImage(ThumbnailImage thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}

	public void resetRepresentativeImages() {
		this.representativeImages.clear();
	}

	public void addRepresentativeImage(RepresentativeImage representativeImage) {
		this.representativeImages.add(representativeImage);
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
