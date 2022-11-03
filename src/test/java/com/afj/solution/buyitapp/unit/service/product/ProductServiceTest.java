package com.afj.solution.buyitapp.unit.service.product;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.afj.solution.buyitapp.model.Product;
import com.afj.solution.buyitapp.model.enums.Currency;
import com.afj.solution.buyitapp.payload.request.CreateProductRequest;
import com.afj.solution.buyitapp.repository.ProductRepository;
import com.afj.solution.buyitapp.service.product.ProductService;
import com.afj.solution.buyitapp.unit.BaseTest;

/**
 * @author Tomash Gombosh
 */
@DisplayName("Product service tests")
class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;


    @Autowired
    private ProductRepository productRepository;

    @Test
    @Disabled
    @DisplayName("Create product")
    void createProduct() {
        final CreateProductRequest createProductRequest = new CreateProductRequest(
                "Flowers",
                (float) 44.4,
                5,
                Currency.USD,
                "Beautiful flowers",
                categoryId,
                subCategoryId);
        final Product product = productService.save(createProductRequest, userId);
        assertThat(productRepository.findById(product.getId()).isPresent())
                .as("Product should be successfully saved")
                .isTrue();

    }
}
