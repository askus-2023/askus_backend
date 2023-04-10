package com.askus.askus.domain.like.service;

import com.askus.askus.domain.like.dto.LikeAddAndDeleteRequest;
import com.askus.askus.domain.like.dto.LikeAddAndDeleteResponse;

public interface LikeService {
	LikeAddAndDeleteResponse addLike(long usersId, LikeAddAndDeleteRequest request);

	LikeAddAndDeleteResponse deleteLike(long usersId, LikeAddAndDeleteRequest request);
}
