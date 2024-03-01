package ru.geekbraines.api.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbraines.api.product.data.Product;
import ru.geekbraines.api.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService  productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public List<Product> getProductById(@PathVariable Long Id){
        return List.of(productService.findById(Id).get());    // вернется коллекция, в которой только 1 объект
    }


    @GetMapping
    public List<Product> getAll(){
        return productService.getAll();
    }



    @GetMapping("/by_title")
    public Product findByTitle(@RequestParam String title){
        return productService.findByTitle(title).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/mostexpensive")
    public Product findMostExp (){
         return productService.findMostExpensive().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public List<Product> findByTitleCont(@RequestParam String title){
        return productService.findByTitleContaining(title);
      }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable Long Id){
        productService.deleteById(Id);
    }


    @GetMapping
    public Page<Product> getAllProductsByCostBetween(    // ДЗ - фильтры по выводу между макс. и мин. ценами
            @RequestParam(name="min_cost",defaultValue = "0") Integer minCost,
            @RequestParam(name="max_cost", required = false) Integer maxCost,
            @RequestParam(name="p", defaultValue = "1") Integer page) {

        if (maxCost == null)
            maxCost=Integer.MAX_VALUE;
        if (page<1)
            page=1;



        return productService.findAllByCostBetween(minCost,maxCost,page);

    }









}
