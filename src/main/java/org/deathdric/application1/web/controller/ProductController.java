package org.deathdric.application1.web.controller;

import lombok.RequiredArgsConstructor;
import org.deathdric.application1.service.ProductService;
import org.deathdric.application1.web.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/products")
    public List<ProductDto> findAllProducts() {
        return productService.findAllProducts();
    }
}
