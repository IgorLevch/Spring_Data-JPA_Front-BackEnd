package ru.geekbraines.api.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbraines.api.product.data.Product;
import ru.geekbraines.api.product.dto.ProductDto;
import ru.geekbraines.api.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
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


   /* @GetMapping                   как выглядел этот метод до пагинации и фильтрации
    public List<Product> getAll(){
        return productService.getAll();
    }*/

    // Добавляем пагинацию и фильтрацию через некий единый метод:
    @GetMapping
    public Page<ProductDto> getAll(
            @RequestParam(name = "p",defaultValue = "1") Integer page,
            @RequestParam(name = "min_level", defaultValue = "0") Integer minLevel, // можно тоже сделать required = false
            @RequestParam(name = "max_level", required = false) Integer maxLevel,    // поле явл-ся необязательным. если его никто не указал (required = false)
            @RequestParam(name = "title_part", required = false) String titlePart

    ){    // будем получать всех студентов, но возможно придет некоторое кол-во параметров (добавляем @RequestParam)
      if (page<1){
          page=1;   //чтобы никто не мог указать отрицательное значение
      }


        return productService.find(minLevel, maxLevel, titlePart, page).map(
                s-> new ProductDto(s)

        );  // page - это номер страницы
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

    @DeleteMapping("/{id}")   // после того , как сделали аннотацию @DeleteMapping  -  глагол delete из енд-пойнта убираем
    public void delete(@PathVariable Long Id){
        productService.deleteById(Id);
    }


  /*  @GetMapping
    public Page<Product> getAllProductsByCostBetween(    // ДЗ - фильтры по выводу между макс. и мин. ценами
            @RequestParam(name="min_cost",defaultValue = "0") Integer minCost,
            @RequestParam(name="max_cost", required = false) Integer maxCost,
            @RequestParam(name="p", defaultValue = "1") Integer page) {

        if (maxCost == null)
            maxCost=Integer.MAX_VALUE;
        if (page<1)
            page=1;



        return productService.findAllByCostBetween(minCost,maxCost,page);

    }*/


    @PostMapping
    public Product saveNewProduct(@RequestBody Product product ){
        product.setId(null); // если нам на пост пришлют новый продукт с айдишником, то мы перезапишем старый продукт ( а такого быть не должно)
        // поэтому у входящего продукта на всяк. случай Id зануляем.
        return productService.save(product);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product ){
        //  а в @PutMapping присылают с Id уже и мы просто его просто перезаписываем
        return productService.save(product);
    }





}
