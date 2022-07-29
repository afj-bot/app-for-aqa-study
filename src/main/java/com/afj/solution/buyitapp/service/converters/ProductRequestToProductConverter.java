package com.afj.solution.buyitapp.service.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.model.Product;
import com.afj.solution.buyitapp.payload.request.CreateProductRequest;

/**
 * @author Tomash Gombosh
 */
@Component
@Slf4j
public class ProductRequestToProductConverter implements Converter<CreateProductRequest, Product> {
    @Override
    public Product convert(final CreateProductRequest createProductRequest) {
        log.info("Convert the product request({}) to product", createProductRequest);
        return new Product(p -> {
            p.setName(createProductRequest.getName());
            p.setPrice(createProductRequest.getPrice());
            p.setCurrency(createProductRequest.getCurrency());
            p.setDescription(createProductRequest.getDescription());
            p.setQuantity(createProductRequest.getQuantity());
        });
    }
}
