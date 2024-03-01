package ru.geekbraines.api.product.data;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="products2")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name="title",nullable = false,unique=true)
    private String title;
    @Column(name="cost")
    private Long cost;
    @Column(name="level")
    private Integer level;


    public Product() {
    }


    public Product(long l, String ggh, long l1, int i) {
    }
}
