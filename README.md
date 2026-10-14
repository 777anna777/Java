# Order Management — Java DOP (Lab 1, V=0)

Лабораторна робота №1 з дисципліни «Технології розробки на платформі Java».

## Варіант

**V = N mod 3 = 0** (номер залікової книжки `N` підставити самостійно).

## Опис

Система управління замовленнями на основі Data-Oriented Programming (DOP):
- `sealed interface OrderStatus` обмежує типи статусів;
- `record Pending`, `Paid`, `Shipped`, `Cancelled` представляють незмінні дані;
- компактні конструктори перевіряють правильність вхідних даних;
- `OrderProcessor.process(OrderStatus)` використовує вичерпний `switch expression` із pattern matching **без `default`**;
- вихідні повідомлення формуються за допомогою Java Text Blocks.

Усі класи знаходяться в пакеті `ua.kpi.comsys.dop`.

## Вимоги

- JDK 21+
- Maven 3.9+ (або Maven, вбудований в IntelliJ IDEA)

## Запуск тестів

```bash
mvn test
```

Альтернативно відкрийте папку проєкту в IntelliJ IDEA та запустіть `OrderProcessorTest`.

## Структура

```text
src/main/java/ua/kpi/comsys/dop/
  OrderStatus.java
  Pending.java
  Paid.java
  Shipped.java
  Cancelled.java
  OrderProcessor.java
src/test/java/ua/kpi/comsys/dop/
  OrderProcessorTest.java
pom.xml
README.md
```

## Тестування

Тести JUnit 5 перевіряють чотири гілки `switch`, null-статус, невалідні значення `Paid`, `Shipped`, `Cancelled` і точний текст результатів.

## Здача

Завантажте проєкт у **публічний GitHub-репозиторій**. Додайте в Google Classroom посилання на репозиторій.
