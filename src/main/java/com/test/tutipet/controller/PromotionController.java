package com.test.tutipet.controller;


import com.test.tutipet.constants.ApiEndpoints;
import com.test.tutipet.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ApiEndpoints.PREFIX)
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;
}