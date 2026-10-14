# Лабораторна робота №2 — Аналізатор логів серверів

Дисципліна: **Технології розробки на платформі Java**.  
Тема: **Збірка проєкту за допомогою Gradle та робота з Java Collections Framework**.

## Варіант

**V = N mod 3 = 3 mod 3 = 0** (якщо ваш номер для розрахунку — 3; за потреби виправте N).

## Опис проєкту

`LogAnalyzer` у пакеті `ua.kpi.comsys.collections` читає рядки серверних логів у форматі Common Log Format (CLF), ігнорує некоректні рядки і використовує лише стандартні колекції Java.

- `Set<String> getUniqueIps(List<String> logLines)` — повертає множину унікальних IP.
- `List<String> getTopIps(List<String> logLines, int topN)` — повертає до `topN` IP, відсортованих за спаданням кількості входжень; за однакової частоти — лексикографічно за зростанням.
- Якщо `logLines == null`, обидва методи кидають `IllegalArgumentException("logLines cannot be null")`.
- Якщо `topN <= 0`, `getTopIps` кидає `IllegalArgumentException("topN must be greater than 0")`.

## Вимоги

- JDK 27.
- Доступ до інтернету під час першого запуску для завантаження Gradle та JUnit 5.

## Запуск тестів

У терміналі в корені проєкту на macOS / Linux:

```bash
./gradlew test
./gradlew build
```

На Windows:

```powershell
.\gradlew.bat test
.\gradlew.bat build
```

**Примітка:** `gradlew` / `gradlew.bat` у цьому навчальному архіві — невеликі bootstrap-скрипти, які завантажують Gradle 9.8.1 під час першого запуску. Це **не офіційний Gradle Wrapper JAR**. Після відкриття проєкту в IntelliJ IDEA можна використовувати вбудований Gradle; для повністю стандартного Wrapper запустіть `gradle wrapper --gradle-version 9.8.1` на комп'ютері з установленим Gradle та закомітьте створені файли.

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

## GitHub

Завантажуйте в репозиторій **вміст папки проєкту**, щоб `README.md`, `build.gradle.kts` і `src` були на верхньому рівні. Не завантажуйте `build/` і `.gradle/`.
