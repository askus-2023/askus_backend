package com.askus.askus.domain.image.service;

import java.util.Map;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.dto.BoardAddRequest;
import com.askus.askus.domain.image.domain.ImageType;
import com.askus.askus.domain.image.domain.ProfileImage;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.dto.SignUpRequest;

public interface ImageService {
	Map<ImageType, Object> uploadBoardImage(Board board, BoardAddRequest request);

	ProfileImage uploadProfileImage(Users users, SignUpRequest request);
}
