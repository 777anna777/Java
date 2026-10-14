# Лабораторна робота №2 — Аналізатор логів серверів

Дисципліна: **Технології розробки на платформі Java**.  
Тема: **Збірка проєкту за допомогою Gradle та робота з Java Collections Framework**.

## Варіант

**V = 3 mod 3 = 0**

## Опис проєкту

`LogAnalyzer` у пакеті `ua.kpi.comsys.collections` читає рядки серверних логів у форматі Common Log Format (CLF), ігнорує некоректні рядки і використовує лише стандартні колекції Java.

- `Set<String> getUniqueIps(List<String> logLines)` — повертає множину унікальних IP.
- `List<String> getTopIps(List<String> logLines, int topN)` — повертає до `topN` IP, відсортованих за спаданням кількості входжень; за однакової частоти — лексикографічно за зростанням.
- Якщо `logLines == null`, обидва методи кидають `IllegalArgumentException("logLines cannot be null")`.
- Якщо `topN <= 0`, `getTopIps` кидає `IllegalArgumentException("topN must be greater than 0")`.

## Запуск тестів

У IntelliJ IDEA: **File → Open** → папка проєкту → дочекайтеся синхронізації Gradle → запустіть `LogAnalyzerTest`.

## Структура

```text
lab2-log-analyzer-java27/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle/libs.versions.toml
├── gradlew, gradlew.bat
├── src/main/java/ua/kpi/comsys/collections/LogAnalyzer.java
├── src/test/java/ua/kpi/comsys/collections/LogAnalyzerTest.java
└── README.md
```

