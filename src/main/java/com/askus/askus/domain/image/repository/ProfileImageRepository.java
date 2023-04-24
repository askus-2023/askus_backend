package com.askus.askus.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askus.askus.domain.image.domain.ProfileImage;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    Optional<String> findUrlByUsers(Long user_id);
}
