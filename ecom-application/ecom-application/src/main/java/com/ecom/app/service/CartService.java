package com.ecom.app.service;

import com.ecom.app.dto.CartItemRequest;
import com.ecom.app.model.CartItem;
import com.ecom.app.model.Product;
import com.ecom.app.model.User;
import com.ecom.app.repository.CartItemRepository;
import com.ecom.app.repository.ProductRepository;
import com.ecom.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;


    public boolean  addToCart(Long userId, CartItemRequest request) {
        //looking for product
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        System.out.println("product id ");
        if(productOpt.isEmpty())
            return false;

        System.out.println("after product id ");

        Product product = productOpt.get();
        if(product.getStockQuantity() < request.getQuantity())

            return false;
        System.out.println("product quantity ");
        Optional<User>  userOpt = userRepository.findById((userId));
        if(userOpt.isEmpty())
            return false;
        System.out.println("userid check quantity ");

        User user = userOpt.get();

        CartItem existingCartitem = cartItemRepository.findByUserAndProduct(user , product);

        if(existingCartitem != null){
            //Update the quantity
            existingCartitem.setQuantity(existingCartitem.getQuantity() + request.getQuantity());
            existingCartitem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartitem.getQuantity())));
            cartItemRepository.save(existingCartitem);
        }else{
            //create a new cartitem
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;
    }
}
