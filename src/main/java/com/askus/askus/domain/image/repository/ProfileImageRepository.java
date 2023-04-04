package com.askus.askus.domain.image.repository;

import com.askus.askus.domain.image.domain.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
