package com.afj.solution.buyitapp.service.product;

import java.util.UUID;

/**
 * @author Tomash Gombosh
 */
public interface RatingService {

    void addRating(int stars, UUID id, UUID userId);

    double getTotalRating(UUID id);
}
