package com.test.tutipet.controller;


import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.promotions.CreatePromotionReq;
import com.test.tutipet.dtos.promotions.PromotionRes;
import com.test.tutipet.dtos.promotions.UpdatePromotionReq;
import com.test.tutipet.enums.EnableStatus;
import com.test.tutipet.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ApiEndpoints.PREFIX)
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping(ApiEndpoints.PROMOTION_V1)
    public List<PromotionRes> getAllPromotion() {
        return promotionService.getAllPromotion();
    }

    @GetMapping(ApiEndpoints.PROMOTION_V1 + "/search")
    public PageRes<PromotionRes> getAllPromotionPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "fullName") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ){
        return promotionService.getAllPromotionPage(page,size,sortBy,sortDir);
    }

    @GetMapping(ApiEndpoints.PROMOTION_V1 + "/{id}")
    public PromotionRes getPromotionById(@PathVariable long id) {
        return promotionService.getPromotionById(id);
    }

    @PostMapping(ApiEndpoints.PROMOTION_V1)
    @ResponseStatus(HttpStatus.CREATED)
    public PromotionRes createPromotion(@RequestBody @Valid CreatePromotionReq promotion) {
        return promotionService.createPromotion(promotion);
    }

    @PutMapping(ApiEndpoints.PROMOTION_V1 + "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PromotionRes updatePromotion(@RequestBody @Valid UpdatePromotionReq promotion,
                                        @PathVariable long id) {
        return promotionService.updatePromotion(promotion, id);
    }

    @DeleteMapping(ApiEndpoints.PROMOTION_V1 + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePromotion(@PathVariable long id) {
        promotionService.deletePromotion(id);
    }

    @PatchMapping(ApiEndpoints.PROMOTION_V1 + "/{code}/validate")
    public void validatePromotion(@PathVariable String code,
                                  @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        promotionService.validatePromotionByCode(code, token);
    }

    @PatchMapping(ApiEndpoints.PROMOTION_V1 + "/{id}")
    public PromotionRes updatePromotionStatus(
            @PathVariable long id,
            @RequestBody EnableStatus enableStatus) {
        return promotionService.updatePromotionStatus(id, enableStatus);
    }

}
