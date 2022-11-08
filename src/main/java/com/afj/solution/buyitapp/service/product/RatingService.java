package com.afj.solution.buyitapp.service.product;

import java.util.UUID;

import com.afj.solution.buyitapp.payload.request.AddRatingRequest;

/**
 * @author Tomash Gombosh
 */
public interface RatingService {

    void addRating(AddRatingRequest addRatingRequest, UUID userId);

    double getTotalRating(UUID id);
}
