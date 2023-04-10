package com.askus.askus.domain.image.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.board.dto.BoardAddRequest;
import com.askus.askus.domain.image.domain.BoardImage;
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
	public Map<ImageType, Object> uploadBoardImage(Board board, BoardAddRequest request) {
		HashMap<ImageType, Object> map = new HashMap<>();

		String thumbnailImageUrl = request.getThumbnailImage().uploadBy(imageUploader);
		String representativeImageUrl = request.getRepresentativeImage().uploadBy(imageUploader);

		BoardImage thumbnailImage = new BoardImage(board, ImageType.THUMBNAIL, thumbnailImageUrl);
		BoardImage representativeImage = new BoardImage(board, ImageType.REPRESENTATIVE, representativeImageUrl);

		BoardImage savedThumbnailImage = boardImageRepository.save(thumbnailImage);
		BoardImage savedRepresentativeImage = boardImageRepository.save(representativeImage);

		map.put(ImageType.THUMBNAIL, savedThumbnailImage);
		map.put(ImageType.REPRESENTATIVE, savedRepresentativeImage);

		return map;
	}

	@Override
	public ProfileImage uploadProfileImage(Users users, SignUpRequest request) {

		String profileImageUrl = request.getProfileImage().uploadBy(imageUploader);
		ProfileImage profileImage = new ProfileImage(users, profileImageUrl);
		ProfileImage savedProfileImage = profileImageRepository.save(profileImage);

		return savedProfileImage;
	}
}
