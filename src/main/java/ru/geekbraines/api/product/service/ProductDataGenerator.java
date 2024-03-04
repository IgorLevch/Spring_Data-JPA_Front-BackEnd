package ru.geekbraines.api.product.service;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.geekbraines.api.product.data.Product;
import ru.geekbraines.api.product.repositories.ProductRepository;
@Component
public class ProductDataGenerator {

    @Autowired
    private ProductRepository productRepository;

    @EventListener(ApplicationReadyEvent.class)  // эта аннотация говорит Спрингу, что этот метод надо запустить, когда данное собитие в Контексте произойдет
    // и данное событие происходит , когда сервис запустился.
    // Когда пишется лог: : Started Application in 10.837 seconds (process running for 11.954) -- сразу запускается этот метод
    public void generateDataOnStartup(){  // метод будет вызываться в тот момент, когда приложение будет запускаться

        Faker faker = new Faker();

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setTitle(faker.name().title());
            product.setCost(faker.number().numberBetween(20L,780L));
            product.setLevel(faker.number().randomDigit());

            productRepository.save(product);
            // Спринг перехватывает вызов этого метода. Он знает, какая БД внутри себя.
            // И Спринг сам открывает сессию, выполняет запрос, коммитит его и закрывает сессию -- это то, что делает Спринг.Дата JPA

        }

    }




}
