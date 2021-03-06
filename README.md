# Приложение Earnings counter
#### Стек:
* Java 9
* Spring 5 (Spring Integration)
* Spring Boot 2
* Swing
* Gradle

#### Описание:
* Приложение реализовано на стеке: Java 9, Swing, Spring 5 (Integration), Spring Boot 2, Gradle.
* Опрос сервиса курсов валют реализован на Spring Integration. 
* Текущий курс автоматически обновляется каждые 30 секунд.
* Курс за прошедшую дату запрашивается по требованию пользователя.
* Для обоих типов сообщений используются общие шлюз и набор каналов.
* Данные на форме обновляются автоматически при получении любых новых сообщений об изменении/запросе курса, т.е. если после подсчета прибыли/убытка и вывода данных на форму - изменится текущий курс, данные будут автоматически пересчитаны и отображены на форме

#### Примечания:
* Бесплатный доступ к АПИ fixer.io не позволяет выбрать в качестве базовой валюту отличную от EUR.
* Бесплатный АПИ имеет ограничение по количеству запросов в месяц на каждый сервис, если лимит будет исчерпан - следует заменить ключ на новый в конфигурационном файле (application.yml:integration.access.key).

#### Запуск:
    sdk use java 9.0.7-zulu
    gradle bootRun
