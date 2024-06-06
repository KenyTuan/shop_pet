package com.test.tutipet.service;

import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.products.ProductReq;
import com.test.tutipet.dtos.products.ProductRes;

public interface ProductService{

    PageRes<ProductRes> getProducts(String keySearch, int page, int size, String sortBy, String sortDir);

    ProductRes getById(long id);

    ProductRes insertProduct(ProductReq req);

    ProductRes updateProduct(long id, ProductReq req);

    void deleteProduct(long id);

}
