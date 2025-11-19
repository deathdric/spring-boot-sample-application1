package org.deathdric.application1.service;

import lombok.RequiredArgsConstructor;
import org.deathdric.application1.data.dao.ProductDao;
import org.deathdric.application1.data.model.ReadProduct;
import org.deathdric.application1.web.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    public List<ProductDto> findAllProducts() {
        return productDao.findAllProduts().stream().map(this::mapToProductDto).toList();
    }

    private ProductDto mapToProductDto(ReadProduct product) {
        return ProductDto.builder()
                .productId(product.id())
                .name(product.name())
                .description(product.description())
                .price(product.price())
                .build();
    }
}
