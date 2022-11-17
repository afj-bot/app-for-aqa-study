package com.afj.solution.buyitapp.service.converters.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.exception.BadRequestException;
import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.model.category.CategoryLocalization;
import com.afj.solution.buyitapp.model.category.SubCategory;
import com.afj.solution.buyitapp.model.product.Characteristic;
import com.afj.solution.buyitapp.model.product.Image;
import com.afj.solution.buyitapp.model.product.Product;
import com.afj.solution.buyitapp.model.product.Rating;
import com.afj.solution.buyitapp.payload.response.CategoryResponse;
import com.afj.solution.buyitapp.payload.response.CharacteristicResponse;
import com.afj.solution.buyitapp.payload.response.CreatedByResponse;
import com.afj.solution.buyitapp.payload.response.ImageResponse;
import com.afj.solution.buyitapp.payload.response.ProductResponse;
import com.afj.solution.buyitapp.payload.response.SubCategoryResponse;
import com.afj.solution.buyitapp.service.converters.Converter;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@Component
public class ProductToResponseConverter implements Converter<Product, ProductResponse> {

    @Override
    public ProductResponse convert(final Product product) {
        log.info("Convert the product({}) to response", product);
        final ImageResponse image = getImageResponse(product.getImage());
        final BigDecimal star = getStar(product.getRatings());
        final CharacteristicResponse characteristic = getCharacteristic(product.getCharacteristic());
        final CreatedByResponse createdBy = getCreatedByResponse(product.getUser());

        return new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                format("%s %s", product.getPrice(), product.getCurrency()),
                product.getQuantity(),
                star,
                image,
                characteristic,
                createdBy,
                geneRateCategoryResponse(product));
    }

    private BigDecimal getStar(final Set<Rating> ratings) {
        return BigDecimal.valueOf(ratings
                .parallelStream()
                .mapToInt(Rating::getStar)
                .average()
                .orElse(0.0)).setScale(1, RoundingMode.HALF_UP);
    }

    private CharacteristicResponse getCharacteristic(final Characteristic characteristic) {
        return nonNull(characteristic)
                ? new CharacteristicResponse(characteristic.getSize(),
                characteristic.getColor(), characteristic.getAdditionalParams())
                : null;
    }

    private ImageResponse getImageResponse(final Image image) {
        return nonNull(image)
                ? new ImageResponse(imageResponse -> imageResponse.setName(image.getFileName()))
                : null;
    }

    private CategoryLocalization getCategoryLocalized(final Category category) {
        return category
                .getLocalizations()
                .parallelStream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Something went wrong to convert product to product response"));
    }

    private CategoryLocalization getSubCategoryLocalized(final SubCategory category) {
        return category
                .getSubCategoryLocalizations()
                .parallelStream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Something went wrong to convert product to product response"));
    }

    private CategoryResponse geneRateCategoryResponse(final Product product) {
        final CategoryLocalization categoryLocalization = getCategoryLocalized(product.getCategory());

        final CategoryLocalization subCategoryLocalization = getSubCategoryLocalized(product.getSubCategory());

        return new CategoryResponse(product.getCategory().getId(),
                product.getCategory().getName(),
                categoryLocalization.getName(),
                categoryLocalization.getDescription(),
                new SubCategoryResponse(product.getSubCategory().getId(),
                        subCategoryLocalization.getName(),
                        subCategoryLocalization.getDescription()));
    }

    private CreatedByResponse getCreatedByResponse(final User user) {
        return new CreatedByResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getHomeAddress());
    }
}
