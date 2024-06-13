package com.airbnb.controller;

import com.airbnb.entity.User;
import com.airbnb.payload.ReviewDTO;
import com.airbnb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO, @RequestParam long propertyId, @AuthenticationPrincipal User user){

        ReviewDTO dto = reviewService.addReview(reviewDTO,propertyId, user);
        if (dto==null){
            return new ResponseEntity<>("Review Exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dto, HttpStatus.CREATED);


    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@RequestBody ReviewDTO reviewDTO, @PathVariable long reviewId, @AuthenticationPrincipal User user){

        ReviewDTO dto = reviewService.updateReview(reviewDTO,reviewId, user);
        if (dto==null){
            return new ResponseEntity<>("Review not found or user not authorized", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.CREATED);


    }
}
