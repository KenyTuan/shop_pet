package com.test.tutipet.service;

import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.products.CreateProductReq;
import com.test.tutipet.dtos.products.ProductRes;
import com.test.tutipet.dtos.products.UpdateProductReq;
import com.test.tutipet.enums.EnableStatus;

import java.util.List;

public interface ProductService{

    List<ProductRes> getAllProducts();

    PageRes<ProductRes> searchProducts(String keySearch, int page, int size, String sortBy, String sortDir);

    ProductRes getProductById(long id);

    ProductRes insertProduct(CreateProductReq req);

    ProductRes updateEnableProduct(long id, UpdateProductReq req);

    ProductRes updateEnableProduct(long productId, EnableStatus enableStatus);

    void deleteProduct(long id);

}
