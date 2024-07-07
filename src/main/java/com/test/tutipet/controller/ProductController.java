package com.test.tutipet.controller;

import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.products.CreateProductReq;
import com.test.tutipet.dtos.products.ProductRes;
import com.test.tutipet.dtos.products.UpdateProductReq;
import com.test.tutipet.enums.EnableStatus;
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
    public ProductRes postProduct(@RequestBody @Valid CreateProductReq product) {
        return productService.insertProduct(product);
    }

    @PutMapping(ApiEndpoints.PRODUCT_V1 + "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductRes updateProduct(
            @PathVariable long id,
            @RequestBody @Valid UpdateProductReq product) {
        return productService.updateEnableProduct(id,product);
    }

    @PatchMapping(ApiEndpoints.PRODUCT_V1 + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductRes updateEnableProduct(
            @PathVariable long id,
            @RequestBody @Valid EnableStatus enableStatus) {
        return productService.updateEnableProduct(id,enableStatus);
    }

    @DeleteMapping(ApiEndpoints.PRODUCT_V1 + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(value = "id") long id) {
        productService.deleteProduct(id);
    }

}
