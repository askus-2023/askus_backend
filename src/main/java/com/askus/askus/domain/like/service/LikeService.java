package com.askus.askus.domain.like.service;

import com.askus.askus.domain.like.dto.LikeRequest;
import com.askus.askus.domain.like.dto.LikeResponse;

public interface LikeService {

	/**
	 * add like by given request(LikeRequest)
	 *
	 * @param usersId - current usersId
	 * @param request - LikeRequest
	 * @return - updated board data(LikeResponse)
	 */
	LikeResponse addLike(long usersId, LikeRequest request);

	/**
	 * delete like by given request(LikeRequest)
	 *
	 * @param usersId - current usersId
	 * @param request - LikeRequest
	 * @return - updated board data(LikeResponse)
	 */
	LikeResponse deleteLike(long usersId, LikeRequest request);
}
