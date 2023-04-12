package com.askus.askus.domain.image.service;

import java.util.ArrayList;
import java.util.List;

import com.askus.askus.domain.users.dto.UsersRequest;
import org.springframework.stereotype.Service;

import com.askus.askus.domain.board.domain.Board;
import com.askus.askus.domain.image.domain.BoardImage;
import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.ImageType;
import com.askus.askus.domain.image.domain.ProfileImage;
import com.askus.askus.domain.image.repository.BoardImageRepository;
import com.askus.askus.domain.image.repository.ProfileImageRepository;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.global.error.exception.KookleRuntimeException;

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
	public ProfileImage uploadProfileImage(Users users, UsersRequest.SignUp request) {

		String profileImageUrl = request.getProfileImage().uploadBy(imageUploader);
		ProfileImage profileImage = new ProfileImage(users, profileImageUrl);
		ProfileImage savedProfileImage = profileImageRepository.save(profileImage);

		return savedProfileImage;
	}

	@Override
	public void deleteThumbnailImage(Board board) {
		BoardImage thumbnailImage = boardImageRepository.findByBoardAndImageTypeAndDeletedAtNull(board,
				ImageType.THUMBNAIL)
			.orElseThrow(() -> new KookleRuntimeException("Thumbnail Image Not Found"));
		thumbnailImage.delete();
		boardImageRepository.save(thumbnailImage);
	}

	@Override
	public void deleteRepresentativeImages(Board board) {
		List<BoardImage> deletedRepresentativeImages = new ArrayList<>();
		List<BoardImage> representativeImages = boardImageRepository.findAllByBoardAndImageTypeAndDeletedAtNull(board,
			ImageType.REPRESENTATIVE);
		for (BoardImage image : representativeImages) {
			image.delete();
			deletedRepresentativeImages.add(image);
		}
		boardImageRepository.saveAll(deletedRepresentativeImages);
	}
}
