package com.ms.products.Services;

import com.ms.products.models.ProductModel;
import com.ms.products.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Transactional
    public ProductModel saveProductService(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public Page<ProductModel> GetAllProductService(int page, int size) {
        Pageable paginationAndSort = PageRequest.of(page, size, Sort.by("value"));
        return productRepository.findAll(paginationAndSort);
    }
}
