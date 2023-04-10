package com.askus.askus.domain.image.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.image.domain.BoardImage;
import com.askus.askus.domain.image.domain.ImageType;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
	List<BoardImage> findAllByBoardAndImageTypeAndDeletedAtNull(Board board, ImageType type);
}
