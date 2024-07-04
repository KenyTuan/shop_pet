package com.test.tutipet.service.impl;

import com.test.tutipet.constants.MessageException;
import com.test.tutipet.converter.CartDtoConverter;
import com.test.tutipet.converter.ProductCartDtoConverter;
import com.test.tutipet.dtos.carts.CartRes;
import com.test.tutipet.dtos.productCarts.ProductCartReq;
import com.test.tutipet.entity.Cart;
import com.test.tutipet.entity.Product;
import com.test.tutipet.entity.ProductCart;
import com.test.tutipet.exception.BadRequestException;
import com.test.tutipet.exception.GenericAlreadyException;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.repository.CartRepository;
import com.test.tutipet.repository.ProductCartRepository;
import com.test.tutipet.repository.ProductRepository;
import com.test.tutipet.security.JwtUtil;
import com.test.tutipet.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final ProductCartRepository productCartRepository;

    private final ProductRepository productRepository;

    private final JwtUtil jwtUtil;

    @Override
    public CartRes getById(long id) {
        return null;
    }

    @Override
    public Cart getCartByToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new BadRequestException("400", "Invalid or missing Authorization header");
        }
        final String email = jwtUtil.extractUsername(token.substring(7));

        return cartRepository.findCartByUserEmail(email)
                .orElseThrow(()-> new NotFoundException(MessageException.NOT_FOUND_USER));
    }

    @Override
    public CartRes getProductCartsByToken(String token) {
        return CartDtoConverter.toResponse(getCartByToken(token));
    }

    @Transactional
    public CartRes addProductCartByToken(String token, ProductCartReq req) {
        final Cart cart = getCartByToken(token);

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

        final Product product = productRepository.findById(req.getProductId())
                .orElseThrow(()-> new NotFoundException("Product Not Found With Id" + req.getProductId()));


        ProductCart productCart = ProductCartDtoConverter.toEntity(req);
        productCart.setProduct(product);
        productCart.setCart(cart);

        cart.getProductCarts().add(productCart);

        productCartRepository.save(productCart);
        cartRepository.save(cart);

        return CartDtoConverter.toResponse(cart);
    }

    @Override
    @Transactional
    public CartRes addOrReplaceProductCartByToken(String token, ProductCartReq req) {

        Cart cart = getCartByToken(token);

        Set<ProductCart> productCarts = Objects.nonNull(cart) ? cart.getProductCarts() : null;

        AtomicBoolean existItem = new AtomicBoolean(false);

        productCarts
                .forEach(productCart -> {
                    if (productCart.getProduct().getId().equals(req.getProductId())) {
                        productCart.setQuantity(req.getQuantity());
                        productCartRepository.save(productCart);
                        existItem.set(true);
                    }
                });

        if (!existItem.get()) {
            final Product product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product Not Found With Id" + req.getProductId()));

            ProductCart productCart = ProductCartDtoConverter.toEntity(req);
            productCart.setProduct(product);
            productCart.setCart(cart);
            productCarts.add(productCart);
            productCartRepository.save(productCart);
        }

        cartRepository.save(cart);
        return CartDtoConverter.toResponse(cart);
    }

    @Override
    @Transactional
    public void updateCartsByProductId(Product product, long productId) {
        final List<ProductCart> productCarts = productCartRepository.findByProductId(productId);

        List<ProductCart> updatedProductCarts = productCarts.stream()
                .map(item -> updateProductCartData(item, product))
                .toList();

        productCartRepository.saveAll(updatedProductCarts);
    }

    @Override
    @Transactional
    public void deleteProductCartByToken(String token, long productId) {
        Cart cart = getCartByToken(token);

        ProductCart productCart = cart.getProductCarts()
                .stream()
                .filter(i -> i.getProduct().getId().equals(productId)).findFirst().get();
        cart.getProductCarts().removeIf(i -> i.getId().equals(productId));

        productCartRepository.delete(productCart);
        cartRepository.save(cart);
    }

    private ProductCart updateProductCartData(ProductCart productCart, Product product) {
        productCart.setProduct(product);
        return productCart;
    }

}
