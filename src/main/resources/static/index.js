

angular.module('app', []).controller('indexController', function($scope,$http){
/*app - название как и в файле индекс.хтмл*/
/*'app', []  -  []  - обязательны. Означает, что создаем новое приложение без каких-либо зависимостей дополнительных  */
/*'indexController', function($scope,$http){  --  'indexController' - это то, как называется, а function - это внутренность контроллера*/

/*$scope,$http  - добавляются 2 стандартные зависимости
$scope - это некий контекст, куда можно складывать данные и обмениваться этими данными с фронтом
т.е. например, если я положу в scope переменную: $scope.a=10; (это мы положили в контекст)
В index.html где-нибудь в начале body можно отпечатать букву {{a}}*/
/*(все, чем хотим обмениваться с html, складываем в $scope)*/

/*$http - модуль для того, тчобы отправлять POST, GET запросы ( взаимодействие с бекендом)*/

/*все, что внутри function(){} - это реализация контролера*/

    const contextPath = 'http://localhost:8080/api/v1/products';
    /*это константа - мы создали, чтобы знать корень нашего приложения*/

    console.log(123);
    // это выводим в лог, чтобы понять , загружается что-то или нет.


        /*метод loadProduct ниже переписываем, с введением фильтров:*/
  /*  $scope.loadProducts = function() {

    $http.get(contextPath)
    // http.get - означает, что я хочу послать get запрос
        .then(function(response){ // а это ответ на наш запрос какой должен быть
        console.log(response.data)
        // выводим в лог то, что попадет в этот список всех продуктов
            console.log(response);
            $scope.ProductList =response.data;  // data - это стандартное поле в ответе: это тот JSON, который отдал бекэнд
        });
    };*/

    /*    переписанный метод loadProduct с введением параметров (ДОмашка)с пагинацией:*/

    $scope.loadProducts = function(pageIndex = 1) {

    $http ({
        method:'GET',
        url:contextPath,    /*а тут PathVariable*/
        params: {       /*это RequestParam  --  используем что-то одно */
            title_part: $scope.filter ? $scope.filter.title_part: null,           /*проверка: если title_part задан, то отправляем его. иначе отправляем null */
            min_level: $scope.filter ? $scope.filter.min_level : null,  /*к юрл для поиска всех продуктов подвязывается фильтр по мин-й цене. null - это проверка, если фильтр не задан, мы не отправим его с юрл-ом */
            max_level: $scope.filter ? $scope.filter.max_level : null,  /*к юрл для поиска всех продуктов подвязывается фильтр по макс    -й цене. null - это проверка, если фильтр не задан, мы не отправим его с юрл-ом */
            p : page                                                         /* и номер страницы для пагинации*/
        }
    }) .then(function (response) {
        $scope.ProductsList = response.data.content;
        /*$scope.PageArray = scope.generatePages(1, $scope.ProductsPage.totalPages); Эту строку вроде не нужно */
    });

    };





    $scope.deleteProduct = function (productId) {                       /*писал сам*/
        $http.delete(contextPath + productId)
            .then(function (response) {
                $scope.loadProducts();
                 console.log(response.data)
                 // тоже проверяем работу метода
            });

    }

    $scope.findProduct = function(productId){      /* писал сам - вывод результатов поиска по id: уточнить -- вернется коллекция, в которой только 1 объект */
        $http.get(contextPath + productId)           /*27.31 на видео - разбор такого метода*/
           .then(function (response){
           $scope.ProductList = response.data
           });
    }

     $scope.findProductByTitle = function(productTitle){      /* писал сам - вывод результатов поиска: уточнить  */
            $http.get(contextPath + productTitle)
               .then(function (response){
               $scope.productId = response.data
               });
        }


      $scope.createProductJson = function() {
      console.log($scope.newProductJson);
      $http.post(contextPath, $scope.newProductJson)
        .then(function (response) {
        $scope.loadProducts();
        });

        }







    // $scope - это связочка. Если мы что-то положим тут (в джава Скрипте) в $scope, то мы это увидим в ХТМЛ
    // scope - это коробка для обмена данными между ХТМЛ и ДжиЭс
    // это просто пример как раз scope:
    $scope.hh =     'uhahah';
    $scope.loadProducts();
    // функцию мы вызываем либо с хтмл файла, либо прямо в коде говорим:  $scope.loadProducts();
});