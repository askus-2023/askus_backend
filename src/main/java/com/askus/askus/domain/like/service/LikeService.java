package com.askus.askus.domain.like.service;

import com.askus.askus.domain.like.dto.LikeRequest;
import com.askus.askus.domain.like.dto.LikeResponse;

public interface LikeService {
	LikeResponse addLike(long usersId, LikeRequest request);

	LikeResponse deleteLike(long usersId, LikeRequest request);
}
