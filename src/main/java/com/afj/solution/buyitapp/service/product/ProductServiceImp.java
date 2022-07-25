package com.afj.solution.buyitapp.service.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.afj.solution.buyitapp.model.Product;
import com.afj.solution.buyitapp.payload.request.CreateProductRequest;
import com.afj.solution.buyitapp.payload.response.ProductResponse;
import com.afj.solution.buyitapp.repository.ProductRepository;
import com.afj.solution.buyitapp.service.converters.ProductToResponseConverter;

/**
 * @author Tomash Gombosh
 */
@Service
@Slf4j
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final ProductToResponseConverter converter;

    @Autowired
    public ProductServiceImp(final ProductRepository productRepository,
                             final ProductToResponseConverter converter) {
        this.productRepository = productRepository;
        this.converter = converter;
    }

    @Override
    public Page<ProductResponse> getProducts(final Pageable pageable) {
        return productRepository.findAll(pageable).map(converter::convert);
    }

    @Override
    public Product save(final CreateProductRequest createProductRequest) {
        log.info("Create product {} from request", createProductRequest);
        final Product product = productRepository.save(new Product(p -> {
            p.setName(createProductRequest.getName());
            p.setPrice(createProductRequest.getPrice());
            p.setCurrency(createProductRequest.getCurrency());
            p.setDescription(createProductRequest.getDescription());
        }));
        log.info("Successfully create a product {}", product);
        return product;
    }
}
