package ru.geekbraines.api.product.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbraines.api.product.data.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // JpaSpecificationExecutor<Product> -- добавляем для того, чтобы добавить наши методы поиска по критериям
    // из Specification . Это поддержка наших Спецификаций.



    Optional<Product> findByTitle(String title);

    @Query("select c from Product c where c.cost = (select max(c2.cost) from Product c2)")
    Optional<Product> findMostExpensive();

    List<Product> findByTitleContaining (String title);


    /* @Query("здесь надо написать запрос HQL о нахожденири всех значений между самым большим и самым маленьким")
    public Page<Product> findAllByCostBetween(Integer minCost, Integer maxCost, Integer page);*/





}
