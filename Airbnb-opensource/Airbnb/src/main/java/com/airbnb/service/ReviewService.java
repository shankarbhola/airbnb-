package com.airbnb.service;

import com.airbnb.entity.User;
import com.airbnb.payload.ReviewDTO;

public interface ReviewService {
    public ReviewDTO addReview(ReviewDTO reviewDTO, long propertyId, User user);
    public ReviewDTO updateReview(ReviewDTO reviewDTO, long reviewId, User user);
}
