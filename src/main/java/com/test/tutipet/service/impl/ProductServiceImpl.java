package com.test.tutipet.service.impl;

import com.test.tutipet.constants.MessageException;
import com.test.tutipet.converter.ProductDtoConverter;
import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.products.CreateProductReq;
import com.test.tutipet.dtos.products.ProductRes;
import com.test.tutipet.dtos.products.UpdateProductReq;
import com.test.tutipet.entity.Product;
import com.test.tutipet.entity.ProductType;
import com.test.tutipet.enums.EnableStatus;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.repository.ProductRepository;
import com.test.tutipet.repository.ProductTypeRepository;
import com.test.tutipet.service.CartService;
import com.test.tutipet.service.ProductService;
import com.test.tutipet.utils.PromotionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductTypeRepository productTypeRepository;

    private final CartService cartService;

    @Override
    public List<ProductRes> getAllProducts() {
        return ProductDtoConverter.toResponseList(productRepository.findAllActiveProducts());
    }

    @Override
    public PageRes<ProductRes> searchProducts(String keySearch, int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = productRepository
                .findPagedByNameContainingAndObjectStatus(keySearch, ObjectStatus.ACTIVE ,pageable);

        List<ProductRes> productList = products
                .stream()
                .map(ProductDtoConverter::toResponse)
                .toList();


        return new PageRes<>(
                productList,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.isLast()
        );
    }

    @Override
    public ProductRes getProductById(long id) {

        final Product product = productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Product Not Found With Id" + id));
        return ProductDtoConverter.toResponse(
                product
        );
    }

    @Override
    @Transactional
    public ProductRes insertProduct(CreateProductReq req) {

        final ProductType type = productTypeRepository.findById(req.getTypeId())
                .orElseThrow(()-> new NotFoundException(MessageException.NOT_FOUND_PRODUCT_TYPE));

        final Product product = ProductDtoConverter.toEntity(req);

        product.setProductType(type);

        productRepository.save(product);
        return ProductDtoConverter.toResponse(product) ;
    }

    @Override
    @Transactional
    public ProductRes updateEnableProduct(long id, UpdateProductReq req) {

        final Product product = productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(MessageException.NOT_FOUND_PRODUCT));

        updateDeleteProductData(product);

        final Product updatedProduct = ProductDtoConverter.toEntity(req);

        final ProductType type = productTypeRepository.findById(req.getTypeId())
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_PRODUCT_TYPE));

        updatedProduct.setProductType(type);
        updatedProduct.setPromotions(PromotionUtils.getCurrentListPromotion(product.getPromotions()) );
        Product savedProduct = productRepository.save(updatedProduct);

        cartService.updateCartsByProductId(savedProduct, product.getId());

        return ProductDtoConverter.toResponse(savedProduct);
    }

    @Override
    public ProductRes updateEnableProduct(long productId, EnableStatus enableStatus) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(()-> new NotFoundException(MessageException.NOT_FOUND_PRODUCT));

        product.setStatus(enableStatus);

        productRepository.save(product);

        return ProductDtoConverter.toResponse(product);
    }

    @Override
    @Transactional
    public void deleteProduct(long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(MessageException.NOT_FOUND_PRODUCT));

        updateDeleteProductData(product);
    }

    @Override
    public ProductRes getProductByName(String name) {
        final Product product = productRepository.findByNameContaining(name)
                .orElseThrow(()-> new NotFoundException(MessageException.NOT_FOUND_PRODUCT));
        return ProductDtoConverter.toResponse(product);
    }

    private void updateDeleteProductData(Product product){
        product.setObjectStatus(ObjectStatus.DELETED);
        productRepository.save(product);
    }

}
