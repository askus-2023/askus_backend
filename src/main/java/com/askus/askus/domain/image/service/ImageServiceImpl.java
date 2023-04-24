package com.askus.askus.domain.image.service;

import org.springframework.stereotype.Service;

import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.ProfileImage;
import com.askus.askus.domain.image.repository.ProfileImageRepository;
import com.askus.askus.domain.users.domain.Users;
import com.askus.askus.global.error.exception.KookleRuntimeException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

	private final ImageUploader imageUploader;
	private final ProfileImageRepository profileImageRepository;

	@Override
	public ProfileImage uploadProfileImage(Users users, Image image) {

		String profileImageUrl = image.uploadBy(imageUploader);
		ProfileImage profileImage = new ProfileImage(users, profileImageUrl);
		ProfileImage savedProfileImage = profileImageRepository.save(profileImage);

		return savedProfileImage;
	}

	@Override
	public void deleteProfileImage(Users users) {
		ProfileImage profileImage = profileImageRepository.findByUsers(users)
			.orElseThrow(() -> new KookleRuntimeException("profileImage not found"));
		profileImage.delete();
		profileImageRepository.save(profileImage);
	}
}
