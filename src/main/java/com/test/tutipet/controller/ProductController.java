package com.test.tutipet.controller;

import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.products.ProductReq;
import com.test.tutipet.dtos.products.ProductRes;
import com.test.tutipet.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ApiEndpoints.PREFIX)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(ApiEndpoints.PRODUCT_V1)
    public List<ProductRes> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(ApiEndpoints.PRODUCT_V1 + "/search")
    public PageRes<ProductRes> searchProducts(
            @RequestParam(value = "keySearch", defaultValue = "") String keySearch,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {

        return productService.searchProducts(keySearch,page, size,sortBy,sortDir);
    }


    @GetMapping(ApiEndpoints.PRODUCT_V1 + "/{id}")
    public ProductRes getProductById(@PathVariable(value = "id") long id) {
        return productService.getProductById(id);
    }

    @PostMapping(ApiEndpoints.PRODUCT_V1)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductRes postProduct(@RequestBody @Valid ProductReq product) {
        return productService.insertProduct(product);
    }

    @DeleteMapping(ApiEndpoints.PRODUCT_V1 + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(value = "id") long id) {
        productService.deleteProduct(id);
    }



}
