package com.askus.askus.domain.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askus.askus.domain.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
	Optional<Board> findByIdAndDeletedAtNull(long boardId);

	List<Board> findAllById(long[] boardIds);
}
