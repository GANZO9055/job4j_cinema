## Проект Кинотеатр - job4j_cinema

## Описание:
В данном проекте реализуется сервис по покупке билетов.
Сервис позволяет:
1. Производить просмотр фильмов и киносеансов
2. При выборе фильма можно приобрести билет, если пользователь зарегистрирован
3. В случае если пользователь не зарегистрирован, пользователя перебросит на страницу регистрации 

Данные сохраняются в БД

## Используемые технологии:
- Java 17
- Spring Boot
- Thymeleaf
- Bootstrap
- H2 Database
- PostgreSQL
- Sql2o

## Окружение:
- Java 17
- PostgreSQL 16
- Maven 3.8

## Запуск проекта

1. Создайте базу данных
``` sql
CREATE DATABASE cinema
```

2. Клонируйте репозиторий
``` bash
git clone https://github.com/GANZO9055/job4j_cinema
cd job4j_cinema
```

3. Соберите проект с помощью Maven:
``` bash
mvn clean install 
```

4. Запустите приложение:
``` bash
mvn spring-boot:run
```

После запуска, проект будет доступен по адресу: [http://localhost:8080](http://localhost:8080)

Взаимодействие с приложением:

1. Главная

![Главная](image/main.png)

2. Кинотека

![Кинотека](image/films.png)

3. Покупка билета на фильм 

![Покупка билета на фильм](image/buy_ticket.png)

4. Вход

![Вход](image/input.png)

5. Регистрация

![Регистрация](image/register.png)

## Контакты

Telegram: @konstantin9055
