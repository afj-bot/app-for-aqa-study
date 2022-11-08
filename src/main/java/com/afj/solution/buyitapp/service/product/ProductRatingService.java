package com.afj.solution.buyitapp.service.product;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.model.product.Product;
import com.afj.solution.buyitapp.model.product.Rating;
import com.afj.solution.buyitapp.payload.request.AddRatingRequest;
import com.afj.solution.buyitapp.repository.RatingRepository;
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

    private final RatingRepository repository;

    @Autowired
    public ProductRatingService(final ProductServiceImp productService,
                                final UserServiceImpl userService,
                                final RatingRepository repository) {
        this.productService = productService;
        this.userService = userService;
        this.repository = repository;
    }


    @Override
    public void addRating(final AddRatingRequest addRatingRequest,
                          final UUID userId) {
        userService.findById(userId);
        final Product product = productService.findById(addRatingRequest.id());
        product.getRatings().add(new Rating(ratingLambda -> {
            ratingLambda.setProduct(product);
            ratingLambda.setStar(addRatingRequest.star());
            ratingLambda.setUserId(userId);
        }
        ));
        log.info("Add rating {} to the product {}", addRatingRequest, product.getId());
        productService.save(product);
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
