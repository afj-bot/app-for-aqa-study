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
import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.model.category.Category;
import com.afj.solution.buyitapp.model.category.SubCategory;
import com.afj.solution.buyitapp.model.product.Image;
import com.afj.solution.buyitapp.model.product.Product;
import com.afj.solution.buyitapp.payload.request.CreateProductRequest;
import com.afj.solution.buyitapp.payload.request.UpdateCharacteristicRequest;
import com.afj.solution.buyitapp.payload.response.ProductResponse;
import com.afj.solution.buyitapp.repository.ProductRepository;
import com.afj.solution.buyitapp.service.category.CategoryService;
import com.afj.solution.buyitapp.service.category.SubCategoryService;
import com.afj.solution.buyitapp.service.converters.product.ProductRequestToProductConverter;
import com.afj.solution.buyitapp.service.converters.product.ProductToResponseConverter;
import com.afj.solution.buyitapp.service.converters.product.UpdateCharacteristicRequestToCharacteristicConverter;
import com.afj.solution.buyitapp.service.localize.TranslatorService;
import com.afj.solution.buyitapp.service.user.UserServiceImpl;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

/**
 * @author Tomash Gombosh
 */
@Service
@Slf4j
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final UserServiceImpl userService;
    private final ProductToResponseConverter productToResponseConverter;
    private final ProductRequestToProductConverter productRequestToProductConverter;
    private final UpdateCharacteristicRequestToCharacteristicConverter converter;
    private final TranslatorService translator;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    @Autowired
    public ProductServiceImp(final ProductRepository productRepository,
                             final UserServiceImpl userService,
                             final ProductToResponseConverter productToResponseConverter,
                             final ProductRequestToProductConverter productRequestToProductConverter,
                             final UpdateCharacteristicRequestToCharacteristicConverter converter,
                             final TranslatorService translator,
                             final CategoryService categoryService,
                             final SubCategoryService subCategoryService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.productToResponseConverter = productToResponseConverter;
        this.productRequestToProductConverter = productRequestToProductConverter;
        this.converter = converter;
        this.translator = translator;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
    }

    @Override
    public Page<ProductResponse> getProducts(final Pageable pageable, final String language) {
        return productRepository.findAll(pageable)
                .map(p -> {
                    p.setCategory(categoryService.getLocalizedCategory(p.getCategory(), language));
                    return p;
                })
                .map(productToResponseConverter::convert);
    }

    @Override
    public Product save(final CreateProductRequest createProductRequest, final UUID userId) {
        log.info("Create product {} from request", createProductRequest);
        final Product convertedProduct = productRequestToProductConverter.convert(createProductRequest);
        final User createdByUser = userService.findById(userId);
        final Category category = categoryService.findById(createProductRequest.getCategoryId());
        final SubCategory subCategory = subCategoryService.findById(createProductRequest.getSubCategoryId());
        convertedProduct.setUser(createdByUser);
        convertedProduct.setCategory(category);
        convertedProduct.setSubCategory(subCategory);
        final Product product = this.save(convertedProduct);
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
            this.save(product);
            log.info("Product {} saved successfully to database", product.getId());
            return productToResponseConverter.convert(product);
        }
        throw new BadRequestException(format(translator
                .toLocale("error.file.unsupported"), file.getOriginalFilename()));
    }

    @Override
    public ProductResponse updateCharacteristicToProduct(final UUID id, final UpdateCharacteristicRequest request) {
        if (request.getColor().isEmpty() && request.getSize().isEmpty() && request.getAdditionalParams().isEmpty()) {
            throw new BadRequestException(translator.toLocale("error.model.empty-provided"));
        }
        log.info("Add characteristic {} to the product {}", request, id);
        final Product product = this.findById(id);
        product.setCharacteristic(converter.convert(request));
        this.save(product);
        return productToResponseConverter.convert(product);
    }

    @Override
    public byte[] getImageByProductId(final UUID id) {
        log.info("Get image to the product {}", id);
        final Image image = this.findById(id).getImage();
        if (nonNull(image)) {
            return image.getPicture();
        }
        throw new BadRequestException(format(translator.toLocale("error.product.image.not-found"), id));
    }

    @Override
    public Product findById(final UUID id) {
        final Product product = this.productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        format(translator
                                .toLocale("error.product.not-found"), id)));
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

    @Override
    public Page<ProductResponse> getMyProducts(final Pageable pageable, final UUID userId, final String title, final String description) {
        final User user = userService.findById(userId);
        return productRepository.findAllByUserAndNameAndDescription(pageable, user, title, description)
                .map(productToResponseConverter::convert);
    }

    @Override
    public ProductResponse getProductById(final UUID id) {
        return productToResponseConverter.convert(this.findById(id));
    }
}
