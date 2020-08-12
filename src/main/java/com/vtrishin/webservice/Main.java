package com.vtrishin.webservice;

//        Задание номер 3 - Cоздать web-service по работе с обьявлениями
//        Как пользователь сервиса я хочу:
//          1) Cоздавать объявление
//          3) Удалять
//          4) Искать обьявление по уникальному идентификатору
//          5) Выводить список всех объявлений по уникальному
//        идентификатору пользователя (все обявления одного пользователя)
//        Для работы с обьявлениями также нужен пользователь.
//***********************************************************************************
//        Сервис должен  быть разработан по архитектуре REST,
//        выдача данных и сохранение данных осуществляется в формате JSON
//***********************************************************************************
//        Веб - сервис имеет только backend часть,
//        следовательно для работы с ним необходимо пользоваться
//        дополнительными тулзами, чтобы отображать данные (Postman)
//        Пример http запроса для получения всех объявлений у одного пользователя
//        http://localhost:8080/api/customer/1/adverts , должен вернуть массив JSON
//
//        Архитектура приложения должна быть многослойной: (под слоем подрузамевается отдельный java class)
//        1 cлой - слой Rest контроллеров - которые непосредственно взаимодействует
//        с внешний миром - HTTP операции
//        2 слой -  слой сервисов -  там где расположена бизнес-логика
//        3 слой - слой доступа к базе данных - непросредственно логика
//        для чтения и записи в базу данных
//
//        Технологии для разработки приложения:
//        JAX-RS(rest контроллеры),
//        JDBC(работы с БД), MAVEN (сборка java приложения)
//        Деплой приложения: готовое приложение необходимо собрать в WAR архив
//        (это делает maven) и задеплоить на сервер приложений(TOMCAT, WILDFLY)
//
//        REST API данного сервиса должно быть задокументировано

import com.vtrishin.webservice.models.BaseModel;
import com.vtrishin.webservice.models.User;
import com.vtrishin.webservice.repositories.BaseTable;
import com.vtrishin.webservice.repositories.DatabaseAdvert;
import com.vtrishin.webservice.repositories.DatabaseUser;
import com.vtrishin.webservice.repositories.TableOperations;
import com.vtrishin.webservice.service.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public void main(String[] args) {
    }
}
