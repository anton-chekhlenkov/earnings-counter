package com.antonch.earningscounter;

import com.antonch.earningscounter.controller.CurrencyRatesController;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Доступ к контексту Spring из любого
 * места приложения
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ctx = context;
    }

    public static CurrencyRatesController getIntegrationController() {
        return ctx.getBean(CurrencyRatesController.class);
    }

}
