package com.ms.products.Controllers;

import com.ms.products.Services.ProductService;
import com.ms.products.Services.exception.AppNotFoundException;
import com.ms.products.dtos.ProductDTO;
import com.ms.products.models.ProductModel;
import com.ms.products.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "products")
@Slf4j
public class ProductController {


    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductDTO productDTO) {
        log.info("product successfully saved.");
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProductService(ProductModel.builder()
                .name(productDTO.getName())
                .value(productDTO.getValue())
                .build()));
    }

    @GetMapping
    public ResponseEntity<Page<ProductModel>> GetAllProducts
            (@RequestParam(value = "page", defaultValue = "0") int page,
             @RequestParam(value = "size", defaultValue = "5") int size) {
        log.info("getting all products.");
        return ResponseEntity.status(HttpStatus.OK).body(productService.GetAllProductService(page, size));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> GetProductById(@PathVariable(value = "id")UUID id) {
        Optional<ProductModel> object = productService.getOneProductService(id);
        if (object.isPresent()) {
            log.info("product successfully found.");
            return ResponseEntity.status(HttpStatus.OK).body(object);
        } else {
            log.info("product not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("object not found.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable(value = "id") UUID id,
                                                      @RequestBody @Valid ProductDTO productDTO) {

        ProductModel objectOptionalProduct = findById(id);
        objectOptionalProduct.update(productDTO);
        log.info("product successfully updated");
        return ResponseEntity.status(HttpStatus.OK).body(productService.saveProductService(objectOptionalProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        ProductModel productModel = findById(id);
        log.info("product successfully deleted.");
        productService.deleteProductService(productModel);
        return ResponseEntity.status(HttpStatus.OK).body("Product Successfully Deleted.");
    }

    public ProductModel findById(UUID id) {
        return productRepository.findById(id).orElseThrow(AppNotFoundException::new);
    }
}
