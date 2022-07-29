package com.afj.solution.buyitapp.service.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.model.Product;
import com.afj.solution.buyitapp.payload.response.ImageResponse;
import com.afj.solution.buyitapp.payload.response.ProductResponse;

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
        return new ProductResponse(productResponse -> {
            productResponse.setId(product.getId().toString());
            productResponse.setName(product.getName());
            productResponse.setPrice(String.format("%s %s", product.getPrice(), product.getCurrency()));
            productResponse.setDescription(product.getDescription());
            productResponse.setQuantity(product.getQuantity());
            if (nonNull(product.getImage())) {
                productResponse.setImage(new ImageResponse(imageResponse -> {
                    imageResponse.setName(product.getImage().getFileName());
                }
                ));
            }
        });
    }
}
