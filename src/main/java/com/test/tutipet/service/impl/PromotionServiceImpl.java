package com.test.tutipet.service.impl;

import com.test.tutipet.constants.MessageException;
import com.test.tutipet.converter.PromotionDtoConverter;
import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.promotions.CreatePromotionReq;
import com.test.tutipet.dtos.promotions.PromotionRes;
import com.test.tutipet.dtos.promotions.UpdatePromotionReq;
import com.test.tutipet.entity.Order;
import com.test.tutipet.entity.Product;
import com.test.tutipet.entity.Promotion;
import com.test.tutipet.enums.EnableStatus;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.enums.PromotionTarget;
import com.test.tutipet.exception.BadRequestException;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.repository.OrderRepository;
import com.test.tutipet.repository.ProductRepository;
import com.test.tutipet.repository.PromotionRepository;
import com.test.tutipet.security.JwtUtil;
import com.test.tutipet.service.PromotionService;
import com.test.tutipet.utils.PromotionUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final JwtUtil jwtUtil;
    private final OrderRepository orderRepository;

    @Override
    public List<PromotionRes> getAllPromotions() {
        return PromotionDtoConverter.toResponseList(promotionRepository.findAllActivePromotions());
    }

    @Override
    public List<PromotionRes> getLiveAndUpcomingPromotions() {
        final ZonedDateTime now = ZonedDateTime.now();
        final ZonedDateTime startTime = now.plusDays(15);

        return PromotionDtoConverter.toResponseList(promotionRepository
                .findAllActivePromotionsByLiveAndUpcoming(now, startTime));
    }

    @Override
    public PageRes<PromotionRes> getAllPromotionPage(String keySearch, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Promotion> promotions = promotionRepository.findByNameContainingAndObjectStatus(keySearch,ObjectStatus.ACTIVE,pageable);

        List<PromotionRes> promotionResList = promotions
                .stream()
                .map(PromotionDtoConverter::toResponse)
                .toList();

        return new PageRes<>(
                promotionResList,
                promotions.getNumber(),
                promotions.getSize(),
                promotions.getTotalElements(),
                promotions.getTotalPages(),
                promotions.isLast()
        );
    }

    @Override
    public PromotionRes getPromotionById(Long id) {
        final Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_PROMOTION));
        return PromotionDtoConverter.toResponse(promotion);
    }

    @Override
    public void validatePromotionByCode(String code, String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new BadRequestException("Invalid or missing Authorization header");
        }
        final String email = jwtUtil.extractUsername(token.substring(7));

        if (promotionRepository.existsByCodeAndUserEmail(code,email)) {
            throw new NotFoundException(MessageException.NOT_FOUND_PROMOTION);
        }

    }

    @Override
    public PromotionRes createPromotion(CreatePromotionReq req) {
        final Promotion promotion = PromotionDtoConverter.toEntity(req);

        if(promotion.getTarget().equals(PromotionTarget.PRODUCT)){
            Set<Product> products = productRepository.findAllById(req.getProductIds())
                    .stream()
                    .peek((item) -> item.setPromotions(new HashSet<>(Set.of(promotion))))
                    .collect(Collectors.toSet());
            promotion.setProducts(products);

        }else {
            final String code = generateUniqueCode();

            promotion.setProducts(new HashSet<>());
            promotion.setCode(code);
        }
        promotionRepository.save(promotion);
        return PromotionDtoConverter.toResponse(promotion);
    }

    @Override
    public PromotionRes updatePromotion(UpdatePromotionReq req, long id) {
        final Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_PROMOTION));

        final Promotion promotionToUpdate = PromotionDtoConverter.toEntity(req);

        updateDataDeletePromotion(promotion);

        if(promotion.getTarget().equals(PromotionTarget.PRODUCT)){
            Set<Product> products = new HashSet<>(productRepository.findAllById(req.getProductIds()));
            promotionToUpdate.setProducts(products);

        }else{
            promotionToUpdate.setCode(promotion.getCode());
        }

        Set<Order> orders = promotion.getOrders().stream().map(
                (item)-> orderRepository.findById(item.getId())
                        .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_ORDER))
        ).collect(Collectors.toSet());

        promotionToUpdate.setOrders(orders);

        promotionRepository.save(promotionToUpdate);
        return PromotionDtoConverter.toResponse(promotionToUpdate);
    }

    @Override
    public PromotionRes updatePromotionStatus(Long id, EnableStatus enableStatus) {
        final Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_PROMOTION));

        promotion.setEnableStatus(enableStatus);

        promotionRepository.save(promotion);


        return PromotionDtoConverter.toResponse(promotion);
    }

    @Override
    public void deletePromotion(Long id) {
        final Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_PROMOTION));

        updateDataDeletePromotion(promotion);
    }

    private void updateDataDeletePromotion(Promotion promotion) {
        promotion.setObjectStatus(ObjectStatus.DELETED);
        promotionRepository.save(promotion);
    }

    private String generateUniqueCode() {
        String code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        while (promotionRepository.existsByCode(code)) {
            code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        return code;
    }
}
