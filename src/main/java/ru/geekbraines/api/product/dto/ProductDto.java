package ru.geekbraines.api.product.dto;

import jakarta.persistence.*;
import lombok.Data;
import ru.geekbraines.api.product.data.Product;


@Data
public class ProductDto {

    // ДТО-шки - объекты для передачи данных
    // Dto - это Data Transfert Object (объект для передачи данных )



    private Long Id;

    private String title;

    private Long cost;

    private Integer level;

   // private String secretKey; // данные, которые мы не хотим отдавать фронту
    // здесь оставляем те поля, которые хотелось бы передать клиенту




    public ProductDto() {
    }


    public ProductDto(long l, String ggh, long l1, int i) {
    }

    public ProductDto(Product product){
        this.Id= product.getId();
        this.cost = product.getCost();
        this.level= product.getLevel();
        this.title= product.getTitle();

    }     // и в контроллере в методе  getAll отдаем Product Dto, а не Product
            // в delete - не Dto. В update - Dto


}
