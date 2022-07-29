package com.afj.solution.buyitapp.service.product;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
import com.afj.solution.buyitapp.service.converters.ProductRequestToProductConverter;
import com.afj.solution.buyitapp.service.converters.ProductToResponseConverter;

import static java.util.Objects.nonNull;

/**
 * @author Tomash Gombosh
 */
@Service
@Slf4j
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final ProductToResponseConverter productToResponseConverter;
    private final ProductRequestToProductConverter productRequestToProductConverter;

    @Autowired
    public ProductServiceImp(final ProductRepository productRepository,
                             final ProductToResponseConverter productToResponseConverter,
                             final ProductRequestToProductConverter productRequestToProductConverter) {
        this.productRepository = productRepository;
        this.productToResponseConverter = productToResponseConverter;
        this.productRequestToProductConverter = productRequestToProductConverter;
    }

    @Override
    public Page<ProductResponse> getProducts(final Pageable pageable) {
        return productRepository.findAll(pageable).map(productToResponseConverter::convert);
    }

    @Override
    public Product save(final CreateProductRequest createProductRequest) {
        log.info("Create product {} from request", createProductRequest);
        final Product product = this.save(productRequestToProductConverter.convert(createProductRequest));
        log.info("Successfully create a product {}", product);
        return product;
    }

    @Override
    public Product save(final Product product) {
        log.info("Save the product with name {} and id {}", product.getName(), product.getId());
        return productRepository.save(product);
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
            return productToResponseConverter.convert(product);
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

    @Override
    public List<Product> productsWithEmptyQuantity(final List<UUID> productIds) {
        return productRepository
                .findAllById(productIds)
                .stream()
                .filter(p -> p.getQuantity() <= 0)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Product> getProductsById(final List<UUID> productIds) {
        log.info("Get products by id {}", productIds);
        return new HashSet<>(productRepository.findAllById(productIds));
    }

    @Override
    public void decreaseProductQuantity(final UUID productId, final int quantity) {
        final Product product = this.findById(productId);
        product.setQuantity(product.getQuantity() - quantity);
        this.save(product);
        log.info("Decrease the product {}-{} quantity to {}",
                product.getId(), product.getName(), product.getQuantity() - quantity);
    }

    @Override
    public void increaseProductQuantity(final UUID productId, final int quantity) {
        final Product product = this.findById(productId);
        product.setQuantity(product.getQuantity() + quantity);
        this.save(product);
        log.info("Decrease the product {}-{} quantity to {}",
                product.getId(), product.getName(), product.getQuantity());
    }
}
