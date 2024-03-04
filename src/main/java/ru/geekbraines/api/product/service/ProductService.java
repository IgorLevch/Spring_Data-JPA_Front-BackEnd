package ru.geekbraines.api.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbraines.api.product.data.Product;
import ru.geekbraines.api.product.repositories.ProductRepository;
import ru.geekbraines.api.product.repositories.specifications.ProductsSpecifications;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    // После добавления JpaSpecificationExecutor<Product> -- это наши методы поиска по критериям
    // из Specification . (Это поддержка наших Спецификаций).
    // И теперь все возможные методы поиска и сортировки сводятся к 1-му методу:
    public Page<Product> find(Integer minLevel, Integer maxLevel, String titlePart, Integer page) {
        // Page - это список тот же объектов,только с доп. информацией:
        // в объекте типа Page хранится список объектов, номер страницы, сколько всего страниц есть , есть ли следующая стр-ца
        // Page - это продвинутый List : помимо списка объектов будет информация о том, откуда мы их достали.

        // и мы в этом методе собираем спецификацию
        // (у нас есть много маленьких правил - мы хотим создать итоговое правило):
        Specification<Product> spec = Specification.where(null);
        // это то же самое, что: select s from Product s where true (вытаскивает все)
        //where(null) - означает, что это спецификация,
        // которая ничего не проверяет и мы ее теперь по кусочкам собираем :
        if (minLevel != null) {
            spec = spec.and(ProductsSpecifications.levelGreaterOrEqualsThan(minLevel));    // мы добавляем к нашей спецификации следующую проверку(если задали minLevel)
            // тут мы добавили еще одну проверку:
            // select s from Product s where true AND  s.level > minLevel 
        }
        if (maxLevel != null) {
            spec = spec.and(ProductsSpecifications.levelLessOrEqualThan(maxLevel));
            // если мы добавим еще и это, то получится:
            //  select s from Product s where true AND  s.level > minLevel  AND s.level <maxLevel
        }
        if (titlePart !=null){
            spec = spec.and(ProductsSpecifications.titleLike(titlePart));
            //  select s from Product s where true AND  s.level > minLevel  AND s.level <maxLevel AND s.title LIKE %partTitle%
        }
        return productRepository.findAll(spec, PageRequest.of(page -1,5));

                // метод findAll поддерживает объект типа Pageable (PageRequest в частности):
                // мы отдаем PageRequest . Говорим, какая нас страница интересуе (page -1: индексация страниц идет с 0, не с 1)
                //  и размер страницы.   И в зависимости от того, что мы в Pageable отдали, нам   Spring вернет объект типа страница


                // Pageable - это поддержка пагинации
                // PageRequest.of() -- отдаем номер страницы и размер страницы
                // page -1 -- потому что с фронта будут страницы приходить с индексацией с 1
                // 5 - размер (по 5 продуктов на стр-цу)
                //  после размера можно добавить  сортировку через запятую( Sort.by - отсортировать по такому-то столбцу)
    }

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

   /* public Page<Product> findAllByCostBetween(Integer minCost, Integer maxCost, Integer page){
        return productRepository.findAllByCostBetween(minCost,  maxCost,  page);
    }*/

    public Product save(Product product){
       return  productRepository.save(product);
    }

}
