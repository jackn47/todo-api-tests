# TODO API Test Automation

## Описание

Этот проект предназначен для автоматизированного тестирования **REST API** и **WebSocket** взаимодействия приложения todo-app. 
Тесты реализованы на Java с использованием фреймворка **JUnit 5**, библиотеки **RestAssured**, и **Allure** для генерации отчётов.  
Также реализована CI/CD-интеграция через **GitHub Actions**, включая автоматическую публикацию Allure-отчета на GitHub Pages.

## Структура проекта

```
todo-api-tests/
├── .github/workflows/         # CI/CD пайплайны (GitHub Actions)
├── src/
│   ├── main/
│   │   └── java/org/example/model/       # Модели данных (DTO)
│   └── test/
│       └── java/org/example/tests/       # Тесты API и WebSocket
│           ├── service/                  # API и WebSocket клиенты
│           └── utils/                    # Генераторы тестовых данных
├── pom.xml                     # Maven зависимости и конфигурации
├── allure-results/            # Результаты прогона тестов (генерируется)
├── allure-report/      # Allure-отчет (генерируется)
└── README.md
```

## Технологии

- **Java 17**
- **JUnit 5**
- **RestAssured**
- **WebSocket API (Tyrus Client)**
- **Maven**
- **Allure**
- **GitHub Actions**
- **Docker**

## Запуск локально

### Предварительные требования

- Установлены: `Java 17`, `Maven`, `Docker`
- Запущено тестируемое приложение:

```bash
docker pull jackn47/todo-app:latest
docker run -d -p 8080:4242 --name todo-app jackn47/todo-app:latest
```

### Запуск тестов

```bash
mvn clean test
```

### Генерация отчета Allure (локально)

```bash
mvn allure:report
mvn allure:serve
```

## CI/CD

При каждом пуше в ветку `main`:

1. Приложение разворачивается в Docker-контейнере.
2. Выполняется запуск тестов.
3. Генерируется Allure-отчёт.
4. Отчёт автоматически публикуется в ветку `gh-pages`.

## Тестовое покрытие

- API: создание, чтение, обновление, удаление задач (CRUD)
- WebSocket: приём и отправка сообщений при обновлении задач
- Проверка кода ответа, схемы ответа, состояния задач

## Разработка и структура

- Каждый тип тестов вынесен в отдельный класс
- Общие API-клиенты и генераторы данных — в отдельных пакетах
- WebSocket клиент реализован как сервис