package com.ms.products.Controllers;

import com.ms.products.Services.ProductService;
import com.ms.products.dtos.ProductDTO;
import com.ms.products.models.ProductModel;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "products")
@Slf4j
public class ProductController {

    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductDTO productDTO) {
        log.info("product successfully saved.");
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProductService(ProductModel.builder()
                .name(productDTO.getName())
                .value(productDTO.getValue())
                .build()));
    }

    @GetMapping("/")
    public ResponseEntity<Page<ProductModel>> GetAllProducts(@RequestParam(
            value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
        log.info("getting all products.");
        return ResponseEntity.status(HttpStatus.OK).body(productService.GetAllProductService(page, size));
    }
}
