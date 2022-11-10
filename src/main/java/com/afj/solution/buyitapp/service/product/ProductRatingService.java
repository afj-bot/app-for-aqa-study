package com.afj.solution.buyitapp.service.product;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.exception.BadRequestException;
import com.afj.solution.buyitapp.model.product.Product;
import com.afj.solution.buyitapp.model.product.Rating;
import com.afj.solution.buyitapp.service.localize.TranslatorService;
import com.afj.solution.buyitapp.service.user.UserService;
import com.afj.solution.buyitapp.service.user.UserServiceImpl;

/**
 * @author Tomash Gombosh
 */
@Service
@Slf4j
public class ProductRatingService implements RatingService {

    private final ProductService productService;

    private final UserService userService;

    private final TranslatorService translatorService;

    @Autowired
    public ProductRatingService(final ProductServiceImp productService,
                                final UserServiceImpl userService,
                                final TranslatorService translatorService) {
        this.productService = productService;
        this.userService = userService;
        this.translatorService = translatorService;
    }


    @Override
    public void addRating(final int stars,
                          final UUID productId,
                          final UUID userId) {
        userService.findById(userId);
        final Product product = productService.findById(productId);
        if (product
                .getRatings()
                .stream()
                .noneMatch(r -> r.getUserId().equals(userId))) {
            product.getRatings().add(new Rating(ratingLambda -> {
                ratingLambda.setProduct(product);
                ratingLambda.setStar(stars);
                ratingLambda.setUserId(userId);
            }
            ));
            log.info("Add rating {} to the product {}", stars, productId);
            productService.save(product);
        } else {
            throw new BadRequestException(translatorService.toLocale("error.product.rating.already.added"));
        }
    }

    @Override
    public double getTotalRating(final UUID productId) {
        return productService.findById(productId)
                .getRatings()
                .stream()
                .mapToInt(Rating::getStar)
                .average()
                .orElse(0.0);
    }
}
