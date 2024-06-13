package com.airbnb.service;

import com.airbnb.exception.ResourceNotFound;
import com.airbnb.entity.Property;
import com.airbnb.entity.Review;
import com.airbnb.entity.User;
import com.airbnb.payload.ReviewDTO;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {


    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;
    private ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewDTO addReview(ReviewDTO reviewDTO, long propertyId, User user) {
        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setUser(user);
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new ResourceNotFound("Property Not Found"));
        review.setProperty(property);
        Review checkReviewExist = reviewRepository.checkReviewExist(property, user);
        if (checkReviewExist == null) {
            Review savedReview = reviewRepository.save(review);
            ReviewDTO dto = modelMapper.map(savedReview, ReviewDTO.class);
            return dto;
        }

        return null;
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO reviewDTO, long reviewId, User user) {
        Optional<Review> checkReview = reviewRepository.findById(reviewId);
        if (checkReview.isPresent()) {
            Review review = checkReview.get();
            review.setContent(reviewDTO.getContent());
            review.setRating(reviewDTO.getRating());
            Review updatedReview = reviewRepository.save(review);
            ReviewDTO dto = modelMapper.map(updatedReview, ReviewDTO.class);
            return dto;
        }

        return null;
    }
}
