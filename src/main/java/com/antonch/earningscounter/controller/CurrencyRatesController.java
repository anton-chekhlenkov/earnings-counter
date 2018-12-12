package com.antonch.earningscounter.controller;

import com.antonch.earningscounter.MessageFactory;
import com.antonch.earningscounter.config.IntegrationConfig.IntegrationService;
import com.antonch.earningscounter.domain.CurrencyRateData;
import com.antonch.earningscounter.domain.CurrencyRateDataHolder;
import com.antonch.earningscounter.domain.ErrorData;
import com.github.lgooddatepicker.components.DatePicker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.swing.text.JTextComponent;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Контроллер для отправки запросов данных
 * о курсах валют в интеграционный канал
 * и обработки полученных данных с отображением
 * в графическом интерфейсе
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
@Slf4j
@Component
public class CurrencyRatesController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    JTextComponent amountInput;

    @Autowired
    DatePicker datePicker;

    @Autowired
    CurrencyRateDataHolder dataHolder;

    @Autowired
    IntegrationService integrationService;

    @Autowired
    JTextComponent resultValue;


    public void clean() {
        dataHolder.clean();
    }

    public void requestCalculation() {
        dataHolder.clean();
        String amountStr = amountInput.getText();
        if (amountStr != null) {
            Double amount = parseAmount(amountStr);
            dataHolder.setAmount(amount);
        } else {
            showError("Target amount is empty!");
        }

        LocalDate date = datePicker.getDate();
        if (date == null) {
            showError("Target date is empty!");
        } else if (LocalDate.now().isAfter(date)) {
            requestRateOn(date);
        } else {
            showError("Target date should be before current!");
        }
    }

    public void processRateData(CurrencyRateData rateData) {
        if (rateData.isSuccess()) {
            dataHolder.updateRate(rateData);
            resultValue.setText(dataHolder.getCalcResult());
        } else {
            ErrorData error = rateData.getError();
            if (error != null) {
                showError(String.format("Unsuccess service response: \n(%s) %s", error.getCode(), error.getInfo()));
            } else {
                showError("Unexpected service response!");
            }
        }
    }

    private void requestRateOn(LocalDate date) {
        String dateStr = date.format(formatter);
        Message message = MessageFactory.newMessage(dateStr);

        new Thread(new Runnable() {
            @Override
            public void run() {
                integrationService.requestRate(message);
            }
        }).start();
    }

    private Double parseAmount(String value) {
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            return format.parse(value).doubleValue();
        } catch (Exception e) {
            showError("Amount parsing error: " + e.getMessage());
        }
        return null;
    }

    private void showError(String message) {
        log.error(message);
        resultValue.setText(message + "\n\n\n\n\n");
    }

}
