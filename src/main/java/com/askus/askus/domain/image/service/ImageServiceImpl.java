package com.askus.askus.domain.image.service;

import org.springframework.stereotype.Service;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.image.domain.BoardImage;
import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.ImageType;
import com.askus.askus.domain.image.domain.ProfileImage;
import com.askus.askus.domain.image.repository.BoardImageRepository;
import com.askus.askus.domain.image.repository.ProfileImageRepository;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.domain.users.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

	private final ImageUploader imageUploader;
	private final BoardImageRepository boardImageRepository;
	private final ProfileImageRepository profileImageRepository;

	@Override
	public BoardImage uploadThumbnailImage(Board board, Image image) {
		String url = image.uploadBy(imageUploader);
		BoardImage boardImage = new BoardImage(board, ImageType.THUMBNAIL, url);
		return boardImageRepository.save(boardImage);
	}

	@Override
	public BoardImage uploadRepresentativeImage(Board board, Image image) {
		String url = image.uploadBy(imageUploader);
		BoardImage boardImage = new BoardImage(board, ImageType.REPRESENTATIVE, url);
		return boardImageRepository.save(boardImage);
	}

	@Override
	public ProfileImage uploadProfileImage(Users users, SignUpRequest request) {

		String profileImageUrl = request.getProfileImage().uploadBy(imageUploader);
		ProfileImage profileImage = new ProfileImage(users, profileImageUrl);
		ProfileImage savedProfileImage = profileImageRepository.save(profileImage);

		return savedProfileImage;
	}
}
