package com.askus.askus.domain.reply.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.reply.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	/**
	 * search replies by given board
	 *
	 * @param board - board
	 * @return - searched reply list
	 */
	List<Reply> findAllByBoardAndDeletedAtNull(Board board);
}
