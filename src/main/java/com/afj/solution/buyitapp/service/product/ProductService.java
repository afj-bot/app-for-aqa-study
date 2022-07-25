package com.afj.solution.buyitapp.service.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.afj.solution.buyitapp.payload.response.ProductResponse;

/**
 * @author Tomash Gombosh
 */
public interface ProductService {
    Page<ProductResponse> getProducts(Pageable pageable);
}
