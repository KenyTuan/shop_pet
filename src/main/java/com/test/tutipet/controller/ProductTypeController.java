package com.test.tutipet.controller;

import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.converter.ProductTypeDtoConverter;
import com.test.tutipet.dtos.productTypes.ProductTypeRes;
import com.test.tutipet.service.ProductTypeService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ApiEndpoints.PREFIX)
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @GetMapping(ApiEndpoints.PRO_TYPE_V1)
    public List<ProductTypeRes> getAllProductTypes() {
        return productTypeService
                .getAllProductTypes()
                .stream()
                .map(ProductTypeDtoConverter::toResponse)
                .toList();
    }

    @GetMapping(ApiEndpoints.PRO_TYPE_V1+"/{id}")
    public ProductTypeRes getProductTypeById(
            @PathVariable @NotNull int id
    ) {
        return ProductTypeDtoConverter.toResponse(productTypeService.getProductTypeById(id));
    }



}
