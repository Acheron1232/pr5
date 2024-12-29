Інструкція до програми для обробки даних та планування маршруту

Опис програми

Ця програма реалізує дві задачі за допомогою класу CompletableFuture у Java:

Отримання даних з кількох джерел та їх обробка:

Програма виконує асинхронний запит до трьох джерел даних.

Після завершення всіх запитів результати об'єднуються в один рядок.

Планування оптимального маршруту подорожі:

Програма оцінює варіанти транспорту (поїзд, автобус, літак) за вартістю та часом у дорозі.

Результати обробляються, щоб вибрати найкращий варіант подорожі.

Основні функції

Завдання 1: Отримання даних з кількох джерел

Метод fetchData симулює отримання даних із зазначеного джерела. Завдання виконуються паралельно за допомогою CompletableFuture.

Код:

CompletableFuture<String> source1 = CompletableFuture.supplyAsync(() -> fetchData("Source 1"));
CompletableFuture<String> source2 = CompletableFuture.supplyAsync(() -> fetchData("Source 2"));
CompletableFuture<String> source3 = CompletableFuture.supplyAsync(() -> fetchData("Source 3"));

CompletableFuture<Void> allTasks = CompletableFuture.allOf(source1, source2, source3);
CompletableFuture<String> combinedResult = allTasks.thenApply(v -> {
try {
return source1.get() + ", " + source2.get() + ", " + source3.get();
} catch (Exception e) {
throw new RuntimeException(e);
}
});

System.out.println("Combined data: " + combinedResult.get());

Завдання 2: Планування маршруту подорожі

Метод fetchRoute оцінює варіанти транспорту (поїзд, автобус, літак) за вартістю та тривалістю. Клас Route містить логіку розрахунку оцінки (score). Найкращий маршрут обирається серед усіх варіантів.

Код:

CompletableFuture<Route> train = CompletableFuture.supplyAsync(() -> fetchRoute("Train", 120, 5));
CompletableFuture<Route> bus = CompletableFuture.supplyAsync(() -> fetchRoute("Bus", 80, 8));
CompletableFuture<Route> flight = CompletableFuture.supplyAsync(() -> fetchRoute("Flight", 200, 2));

CompletableFuture<Route> bestRoute = CompletableFuture.allOf(train, bus, flight).thenApply(v -> {
Route trainRoute = train.join();
Route busRoute = bus.join();
Route flightRoute = flight.join();
return findBestRoute(trainRoute, busRoute, flightRoute);
});

System.out.println("Best route: " + bestRoute.get());

Як запустити

Завантажте вихідний код програми.

Впевніться, що у вас встановлено JDK 11 або новішої версії.

Скомпілюйте програму за допомогою команди:

javac DataProcessing.java TravelPlanning.java

Запустіть програми:

java DataProcessing
java TravelPlanning

Очікуваний результат

Для першої задачі програма виведе об'єднані результати трьох джерел даних.

Для другої задачі програма виведе інформацію про найкращий маршрут подорожі.