# Order Management — Java DOP (Lab 1, V=0)

Лабораторна робота №1 з дисципліни «Технології розробки на платформі Java».

**V = 3 mod 3 = 0** 

## Опис

Система управління замовленнями на основі Data-Oriented Programming (DOP):
- `sealed interface OrderStatus` обмежує типи статусів;
- `record Pending`, `Paid`, `Shipped`, `Cancelled` представляють незмінні дані;
- компактні конструктори перевіряють правильність вхідних даних;
- `OrderProcessor.process(OrderStatus)` використовує вичерпний `switch expression` із pattern matching без `default`;
- вихідні повідомлення формуються за допомогою Java Text Blocks.

Усі класи знаходяться в пакеті `ua.kpi.comsys.dop`.

## Запуск тестів

Відкрийте папку проєкту в IntelliJ IDEA та запустіть `OrderProcessorTest`.

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

