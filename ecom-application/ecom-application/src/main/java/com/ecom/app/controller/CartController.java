package com.ecom.app.controller;

import com.ecom.app.dto.CartItemRequest;
import com.ecom.app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/cart")
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-user-ID") Long userId,
            @RequestBody CartItemRequest request){

       if(!cartService.addToCart(userId , request)) {

           return ResponseEntity.badRequest().body("Product not found or user not found ");
       }
       return ResponseEntity.status(HttpStatus.CREATED).build();


    }
}
