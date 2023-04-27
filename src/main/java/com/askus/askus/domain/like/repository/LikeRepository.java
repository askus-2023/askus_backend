package com.askus.askus.domain.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.like.domain.Like;
import com.askus.askus.domain.users.domain.Users;

public interface LikeRepository extends JpaRepository<Like, Long> {
	/**
	 * search like by given user and board
	 *
	 * @param users - users
	 * @param board - board
	 * @return - searched like
	 */
	Optional<Like> findByUsersAndBoard(Users users, Board board);
}
