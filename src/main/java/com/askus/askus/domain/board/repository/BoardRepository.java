package com.askus.askus.domain.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.image.domain.RepresentativeImage;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
	@Query("SELECT b.representativeImages "
		+ "FROM Board b "
		+ "WHERE b.id = :boardId")
	List<RepresentativeImage> findRepresentativeImagesByBoardId(@Param("boardId") long boardId);
}
