package ru.geekbraines.api.product.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbraines.api.product.data.Product;

public class ProductsSpecifications {

    // это специальный класс, который мы генерим для создания фильтров (Specification в Spring Boot)
    // Спецификация - это некий критерий поиска


    // это метод, который подготовит некое правило ( что  level должен быть больше , либо равен чему-то):
    public static Specification<Product> levelGreaterOrEqualsThan(Integer level)            //мы говорим, что уровень продукта должен быть больше
    // либо равен чему-то
    {
        return (Specification<Product>) (root,criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("level"),level);
        //criteriaBuilder  - это объект, который позволяет строить различные критерии (можем выполнять те же операции, что и в СКьюЭль)
        // greaterThanOrEqualTo  --> мы говорим, что что-то должно быть больше , либо равно чему-то
        // root - это корневой объект и это объект того типа, дчя которого мы строим спецификацию (в нашем случае - ссылка на Product).
        // ИТОГО , в нашем методе мы говорим, что : поле level Продукта было больше ,либо равно тому level, который мы отдадим в метод на вход
    }

    public static Specification<Product> levelLessOrEqualThan(Integer level)    {
        return (Specification<Product>) (root,criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("level"),level);
        // вторая проверка-- level  меньше чем что-то
    }


    public static Specification<Product> titleLike(String titlePart)    {
        return (Specification<Product>) (root,criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"),String.format("%%%s%%",  titlePart));
        // третья проверка-- ищем по названию
        // что означает куча процентов:
        // %s  - (какой-то элемент, какой-то токен, на место которого что-то подставляется)  означает, что мы на место  String.format подставим titlePart (т.е. то, что мы отдадим на вход)
        // %% (первые и последние) --- это экранированный процент:  означает, что мы ни  «спереди , ни сзади » нашего titlePart не забыли
        // приписать никакие буквы
        // ИТОГО , конструкция:
        // return (Specification<Product>) (root,criteriaQuery, criteriaBuilder) ->
        //                criteriaBuilder.like(root.get("title"),String.format("%%%s%%",  titlePart))
        // означает буквально  следующее:  select p from Product p where p.title like %Bo%     (если даем «Bo» на вход)
    }



}
