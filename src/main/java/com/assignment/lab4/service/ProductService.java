package com.assignment.lab4.service;

import com.assignment.lab4.entity.Product;
import com.assignment.lab4.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private final ProductRepository productRepository;


    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProducts(Integer id) {
        return productRepository.findById(id).orElseThrow(()-> new RuntimeException(""));
    }


}
