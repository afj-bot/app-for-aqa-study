package com.afj.solution.buyitapp.service.product;

import java.io.IOException;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.afj.solution.buyitapp.exception.BadRequestException;
import com.afj.solution.buyitapp.exception.EntityNotFoundException;
import com.afj.solution.buyitapp.model.Image;
import com.afj.solution.buyitapp.model.Product;
import com.afj.solution.buyitapp.payload.request.CreateProductRequest;
import com.afj.solution.buyitapp.payload.response.ProductResponse;
import com.afj.solution.buyitapp.repository.ProductRepository;
import com.afj.solution.buyitapp.service.converters.ProductToResponseConverter;

import static java.util.Objects.nonNull;

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

    @Override
    public ProductResponse addImageToProduct(final UUID id, final MultipartFile file) throws IOException {
        final Product product = this.findById(id);
        final byte[] fileBytes = file.getBytes();
        final boolean isFileImage = nonNull(file.getOriginalFilename()) && file.getOriginalFilename().matches(".*(jpg|png|jpeg)");
        if (isFileImage) {
            log.info("Add image {} to the product {}", file.getOriginalFilename(), product.getId());
            product.setImage(new Image(i -> {
                i.setFileName(file.getOriginalFilename());
                i.setPicture(fileBytes);
            }));
            productRepository.save(product);
            log.info("Product {} saved successfully to database", product.getId());
            return converter.convert(product);
        }
        throw new BadRequestException(String.format("File extension is incorrect %s", file.getOriginalFilename()));
    }

    @Override
    public byte[] getImageByProductId(final UUID id) {
        log.info("Get image to the product {}", id);
        return this.findById(id).getImage().getPicture();
    }

    @Override
    public Product findById(final UUID id) {
        final Product product = this.productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, "id"));
        log.info("Find Product {} by id({})", product, id);
        return product;
    }
}
