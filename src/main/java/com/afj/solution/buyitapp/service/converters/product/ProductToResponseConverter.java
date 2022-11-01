package com.afj.solution.buyitapp.service.converters.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.exception.BadRequestException;
import com.afj.solution.buyitapp.model.Product;
import com.afj.solution.buyitapp.model.category.CategoryLocalization;
import com.afj.solution.buyitapp.payload.response.CategoryResponse;
import com.afj.solution.buyitapp.payload.response.CharacteristicResponse;
import com.afj.solution.buyitapp.payload.response.CreatedByResponse;
import com.afj.solution.buyitapp.payload.response.ImageResponse;
import com.afj.solution.buyitapp.payload.response.ProductResponse;
import com.afj.solution.buyitapp.payload.response.SubCategoryResponse;
import com.afj.solution.buyitapp.service.converters.Converter;

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
        final ImageResponse image = nonNull(product.getImage())
                ? new ImageResponse(imageResponse -> imageResponse.setName(product.getImage().getFileName()))
                : null;

        final CharacteristicResponse characteristic = nonNull(product.getCharacteristic())
                ? new CharacteristicResponse(product.getCharacteristic().getColor(),
                product.getCharacteristic().getSize(), product.getCharacteristic().getAdditionalParams())
                : null;

        final CategoryLocalization categoryLocalization = product
                .getCategory()
                .getLocalizations()
                .stream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Something went wrong with converter"));

        final CategoryLocalization subCategoryLocalization = product
                .getSubCategory()
                .getSubCategoryLocalizations()
                .stream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Something went wrong with converter"));

        final CreatedByResponse createdBy = new CreatedByResponse(product.getUser().getId(),
                product.getUser().getEmail(),
                product.getUser().getFirstName(),
                product.getUser().getLastName(),
                product.getUser().getPhoneNumber(),
                product.getUser().getHomeAddress());

        return new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                String.format("%s %s", product.getPrice(), product.getCurrency()),
                product.getQuantity(),
                image,
                characteristic,
                createdBy,
                new CategoryResponse(product.getCategory().getId(),
                        product.getCategory().getName(),
                        categoryLocalization.getName(),
                        categoryLocalization.getDescription(),
                        new SubCategoryResponse(product.getSubCategory().getId(),
                                subCategoryLocalization.getName(),
                                subCategoryLocalization.getDescription())));
    }
}
