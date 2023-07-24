package com.ms.products.Services;

import com.ms.products.models.ProductModel;
import com.ms.products.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    public ProductModel saveProductService(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public Page<ProductModel> GetAllProductService(int page, int size) {
        Pageable paginationAndSort = PageRequest.of(page, size, Sort.by("value"));
        return productRepository.findAll(paginationAndSort);
    }

    public Optional<ProductModel> getOneProductService(UUID id) {
        return productRepository.findById(id);
    }

    public void deleteProductService(ProductModel productModel) {
        productRepository.delete(productModel);
    }
}
