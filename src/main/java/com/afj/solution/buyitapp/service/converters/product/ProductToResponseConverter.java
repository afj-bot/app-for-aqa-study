package com.afj.solution.buyitapp.service.converters.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.afj.solution.buyitapp.model.Product;
import com.afj.solution.buyitapp.payload.response.CharacteristicResponse;
import com.afj.solution.buyitapp.payload.response.CreatedByResponse;
import com.afj.solution.buyitapp.payload.response.ImageResponse;
import com.afj.solution.buyitapp.payload.response.ProductResponse;
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
        return new ProductResponse(productResponse -> {
            productResponse.setId(product.getId().toString());
            productResponse.setName(product.getName());
            productResponse.setPrice(String.format("%s %s", product.getPrice(), product.getCurrency()));
            productResponse.setDescription(product.getDescription());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setCreatedBy(new CreatedByResponse(createdByResponse -> {
                createdByResponse.setId(product.getUser().getId());
                createdByResponse.setEmail(product.getUser().getEmail());
                createdByResponse.setFirstName(product.getUser().getFirstName());
                createdByResponse.setLastName(product.getUser().getLastName());
                createdByResponse.setPhoneNumber(product.getUser().getPhoneNumber());
                createdByResponse.setHomeAddress(product.getUser().getHomeAddress());
            }));
            if (nonNull(product.getImage())) {
                productResponse.setImage(new ImageResponse(imageResponse ->
                        imageResponse.setName(product.getImage().getFileName())
                ));
            }
            if (nonNull(product.getCharacteristic())) {
                productResponse.setCharacteristic(new CharacteristicResponse(characteristicResponse -> {
                    characteristicResponse.setColor(product.getCharacteristic().getColor());
                    characteristicResponse.setSize(product.getCharacteristic().getSize());
                    characteristicResponse.setAdditionalParams(product.getCharacteristic().getAdditionalParams());
                }));
            }
        });
    }
}
