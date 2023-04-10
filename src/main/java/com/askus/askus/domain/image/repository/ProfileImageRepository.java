package com.askus.askus.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askus.askus.domain.image.domain.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
