package com.antonch.earningscounter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Инициализация контейнера Spring и запуск
 * приложения
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.headless(false).run(args);
    }

}