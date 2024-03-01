package ru.geekbraines.api.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.geekbraines.api.product.data.Product;
import ru.geekbraines.api.product.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public Optional<Product> findById(Long Id){
        return productRepository.findById(Id);
    }

    public List<Product>  getAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findByTitle(String title){
        return productRepository.findByTitle(title);
    }

    public  Optional<Product> findMostExpensive(){
        return productRepository.findMostExpensive();
    }

    public List<Product> findByTitleContaining (String title){
        return productRepository.findByTitleContaining(title);
    }
    public void deleteById(Long Id){
        productRepository.deleteById(Id);
    }

    public Page<Product> findAllByCostBetween(Integer minCost, Integer maxCost, Integer page){
        return productRepository.findAllByCostBetween(minCost,  maxCost,  page);
    }



}
