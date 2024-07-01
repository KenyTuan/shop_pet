package com.test.tutipet.service;

import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.products.ProductReq;
import com.test.tutipet.dtos.products.ProductRes;

import java.util.List;

public interface ProductService{

    List<ProductRes> getAllProducts();

    PageRes<ProductRes> searchProducts(String keySearch, int page, int size, String sortBy, String sortDir);

    ProductRes getProductById(long id);

    ProductRes insertProduct(ProductReq req);

    ProductRes updateProduct(long id, ProductReq req);

    void deleteProduct(long id);

}
