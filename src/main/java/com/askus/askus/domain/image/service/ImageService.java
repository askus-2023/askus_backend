package com.askus.askus.domain.image.service;

import com.askus.askus.domain.image.domain.Image;
import com.askus.askus.domain.image.domain.ProfileImage;
import com.askus.askus.domain.users.domain.Users;

public interface ImageService {

	ProfileImage uploadProfileImage(Users users, Image image);

	void deleteProfileImage(Users users);
}
