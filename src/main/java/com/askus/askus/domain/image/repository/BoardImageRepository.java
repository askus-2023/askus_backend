package com.askus.askus.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askus.askus.domain.image.domain.BoardImage;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
}
