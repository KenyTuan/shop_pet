package com.test.tutipet.service.impl;

import com.test.tutipet.converter.CartDtoConverter;
import com.test.tutipet.converter.ProductCartDtoConverter;
import com.test.tutipet.dtos.carts.CartRes;
import com.test.tutipet.dtos.productCarts.ProductCartReq;
import com.test.tutipet.dtos.productCarts.ProductCartRes;
import com.test.tutipet.entity.Cart;
import com.test.tutipet.entity.Product;
import com.test.tutipet.entity.ProductCart;
import com.test.tutipet.exception.GenericAlreadyException;
import com.test.tutipet.exception.ResourceNotFoundException;
import com.test.tutipet.repository.CartRepo;
import com.test.tutipet.repository.ProductCartRepo;
import com.test.tutipet.repository.ProductRepo;
import com.test.tutipet.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;

    private final ProductCartRepo productCartRepo;

    private final ProductRepo productRepo;

    @Override
    public CartRes getById(long id) {
        return null;
    }

    @Override
    public Cart getCartByUserId(long userId) {

        return cartRepo.findByUserId(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User Not Found With Id" + userId));
    }

    @Override
    public CartRes getProductCartsByUserId(long userId) {
        return CartDtoConverter.toResponse(getCartByUserId(userId));
    }

    @Override
    @Transactional
    public CartRes addProductCartByUserId(long userId, ProductCartReq req) {
        final Cart cart = getCartByUserId(userId);

        final long count = cart
                .getProductCarts()
                .stream()
                .filter(i-> i.getProduct().getId().equals(req.getProductId()))
                .count();

        if (count > 0 ){
            throw new GenericAlreadyException(
              "Product Cart Already Exist With Id " + req.getProductId()
            );

        }

        final Product product = productRepo.findById(req.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found With Id" + req.getProductId()));


        ProductCart productCart = ProductCartDtoConverter.toEntity(req);
        productCart.setProduct(product);
        productCart.setCart(cart);

        cart.getProductCarts().add(productCart);

        productCartRepo.save(productCart);
        cartRepo.save(cart);

        return CartDtoConverter.toResponse(cart);
    }

    @Override
    @Transactional
    public CartRes addOrReplaceProductCartByUserId(long userId, ProductCartReq req) {

        Cart cart = getCartByUserId(userId);

        Set<ProductCart> productCarts = Objects.nonNull(cart) ? cart.getProductCarts() : null;

        AtomicBoolean existItem = new AtomicBoolean(false);

        productCarts
                .forEach(productCart -> {
                    if (productCart.getId().equals(req.getProductId())) {
                        productCart.setQuantity(req.getQuantity());
                        productCart.setTotalProduct(req.getTotalProduct());
                        productCartRepo.save(productCart);
                        existItem.set(true);
                    }
                });

        if (!existItem.get()) {
            final Product product = productRepo.findById(req.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product Not Found With Id" + req.getProductId()));


            ProductCart productCart = ProductCartDtoConverter.toEntity(req);
            productCart.setProduct(product);
            productCart.setCart(cart);
            productCarts.add(productCart);
            productCartRepo.save(productCart);
        }


        cartRepo.save(cart);

        return CartDtoConverter.toResponse(cart);
    }

    @Override
    @Transactional
    public void deleteProductCartFromCart(long userId, long productId) {
        Cart cart = getCartByUserId(userId);
        ProductCart productCart = cart.getProductCarts()
                .stream()
                .filter(i -> i.getProduct().getId().equals(productId)).findFirst().get();
        cart.getProductCarts().removeIf(i -> i.getId().equals(productId));

        productCartRepo.delete(productCart);
        cartRepo.save(cart);
    }


}
