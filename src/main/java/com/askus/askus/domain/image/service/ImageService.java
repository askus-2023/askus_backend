package com.askus.askus.domain.image.service;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.image.domain.BoardImage;
import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.ProfileImage;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.dto.SignUpRequest;

public interface ImageService {

	BoardImage uploadThumbnailImage(Board board, Image image);

	BoardImage uploadRepresentativeImage(Board board, Image image);

	ProfileImage uploadProfileImage(Users users, SignUpRequest request);
}
