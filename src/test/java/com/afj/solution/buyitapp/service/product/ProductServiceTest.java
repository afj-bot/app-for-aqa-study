package com.afj.solution.buyitapp.service.product;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.afj.solution.buyitapp.model.Product;
import com.afj.solution.buyitapp.model.enums.Currency;
import com.afj.solution.buyitapp.payload.request.CreateProductRequest;
import com.afj.solution.buyitapp.repository.CategoryRepository;
import com.afj.solution.buyitapp.repository.ProductRepository;
import com.afj.solution.buyitapp.repository.SubcategoryRepository;
import com.afj.solution.buyitapp.service.BaseTest;

/**
 * @author Tomash Gombosh
 */
@DisplayName("Product service tests")
class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Disabled
    @DisplayName("Create product")
    void createProduct() {
        final UUID categoryId = categoryRepository.findAll().get(0).getId();
        final UUID subcategoryId = subcategoryRepository.findByCategoryId(categoryId).get().getId();
        final CreateProductRequest createProductRequest = new CreateProductRequest(
                "Flowers",
                (float) 44.4,
                5,
                Currency.USD,
                "Beautiful flowers",
                categoryId,
                subcategoryId);
        final Product product = productService.save(createProductRequest, userId);
        assertThat(productRepository.findById(product.getId()).isPresent())
                .as("Product should be successfully saved")
                .isTrue();

    }
}
