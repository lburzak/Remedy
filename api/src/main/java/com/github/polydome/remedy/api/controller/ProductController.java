package com.github.polydome.remedy.api.controller;

import com.github.polydome.remedy.api.model.Product;
import com.github.polydome.remedy.api.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("{id}")
    public Product getProduct(@PathVariable int id) {
        Product product = productRepository.findById(id);

        if (product == null)
            throw new EntityNotFoundException(String.format("Product identified by [id=%d] not found", id));

        return product;
    }
}
