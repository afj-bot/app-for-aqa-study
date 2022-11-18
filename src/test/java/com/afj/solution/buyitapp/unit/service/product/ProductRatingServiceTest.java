package com.afj.solution.buyitapp.unit.service.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.afj.solution.buyitapp.model.product.Rating;
import com.afj.solution.buyitapp.repository.ProductRepository;
import com.afj.solution.buyitapp.service.product.ProductRatingService;
import com.afj.solution.buyitapp.unit.BaseTest;

@DisplayName("Product Rating Service Test")
class ProductRatingServiceTest extends BaseTest {

    @Autowired
    private ProductRatingService service;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Add Rating to the product")
    void addRatingToTheProduct() {
        final int productStar = 2;
        service.addRating(productStar, productId, userId);
        final Rating rating = productRepository.findById(productId)
                .orElseThrow(() -> new AssertionError("No such product id"))
                .getRatings()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("No such ratings for product id"));
        assertThat(rating.getStar())
                .isEqualTo(productStar);
    }
}
