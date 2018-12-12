package com.antonch.earningscounter.config;

import com.antonch.earningscounter.ApplicationContextProvider;
import com.antonch.earningscounter.domain.CurrencyRateDataHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация компонентов широкого назначения
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
@Configuration
public class AppConfig {


    /**
     * Держатель контекста Spring
     */

    @Bean
    public ApplicationContextProvider contextProvider() {
        return new ApplicationContextProvider();
    }


    /**
     * Держатель данных о последних запрошенных курсах валют
     */

    @Bean
    public CurrencyRateDataHolder dataHolder() {
        return new CurrencyRateDataHolder();
    }

}
