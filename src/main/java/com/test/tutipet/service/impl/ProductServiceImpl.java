package com.test.tutipet.service.impl;

import com.test.tutipet.converter.ProductDtoConverter;
import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.products.ProductReq;
import com.test.tutipet.dtos.products.ProductRes;
import com.test.tutipet.entity.Product;
import com.test.tutipet.entity.ProductType;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.exception.ResourceNotFoundException;
import com.test.tutipet.repository.ProductRepo;
import com.test.tutipet.repository.ProductTypeRepo;
import com.test.tutipet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final ProductTypeRepo productTypeRepo;

    @Override
    public PageRes<ProductRes> getProducts(String keySearch, int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = productRepo
                .findByNameContainingAndObjectStatus(keySearch, ObjectStatus.ACTIVE ,pageable);

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
    public ProductRes getById(long id) {

        final Product product = productRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found With Id" + id));
        return ProductDtoConverter.toResponse(
                product
        );
    }

    @Override
    public ProductRes insertProduct(ProductReq req) {

        final ProductType type = productTypeRepo.findById(req.getType_id())
                .orElseThrow(()-> new ResourceNotFoundException("Product Type Not Found With Id" + req.getType_id()));

        final Product product = ProductDtoConverter.toEntity(req);

        product.setProductType(type);

        return ProductDtoConverter.toResponse(productRepo.save(product)) ;
    }

    @Override
    public ProductRes updateProduct(long id, ProductReq req) {

        final Product product = productRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found With Id" + id));

        changeObjStatus(product);

        final Product updatedProduct = ProductDtoConverter.toEntity(req);

        final ProductType type = productTypeRepo.findById(req.getType_id())
                .orElseThrow(() -> new ResourceNotFoundException("Product Type Not Found With Id" + req.getType_id()));

        updatedProduct.setProductType(type);

        return ProductDtoConverter.toResponse(productRepo.save(updatedProduct));
    }

    @Override
    public void deleteProduct(long id) {
        final Product product = productRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

        changeObjStatus(product);
    }

    private void changeObjStatus(Product product){
        product.setObjectStatus(ObjectStatus.DELETED);
        productRepo.save(product);
    }

}
