package com.antonch.earningscounter.config;

import com.antonch.earningscounter.ApplicationContextProvider;
import com.antonch.earningscounter.view.Button;
import com.antonch.earningscounter.view.Label;
import com.antonch.earningscounter.view.Panel;
import com.github.lgooddatepicker.components.DatePicker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Конфигурация Spring для графического интерфейса
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
@Slf4j
@Configuration
public class ViewConfig {

    /**
     * Константы
     */

    private static final Double ZERO = 0.0;

    private static final Double DEF_VALUE = 42.0;

    private static final String EMPTY_STRING = "";

    private static final String EMPTY_TEXT_AREA_STRING = "\n\n\n\n\n";


    /**
     * Элементы главного экрана
     */

    @Bean(initMethod = "init")
    public Panel mainPanel() {
        Panel panel = new Panel();
        panel.setAxis(1);
        panel.setPanelComponents(
                List.of(
                        dateLabel(),
                        datePicker(),
                        amountLabel(),
                        amountInput(),
                        buttonPanel(),
                        resultLabel(),
                        resultValue()
                ));
        return panel;
    }

    @Bean(initMethod = "init")
    public Panel buttonPanel() {
        Panel panel = new Panel();
        panel.setAxis(0);
        panel.setPanelComponents(
                List.of(
                        recalculationButton(),
                        clearButton()
                )
        );
        return panel;
    }


    /**
     * Текстовые поля
     */

    @Bean
    public JTextComponent dateLabel() {
        return new Label("Date");
    }

    @Bean
    public JTextComponent amountLabel() {
        return new Label("Amount EUR");
    }

    @Bean
    public JTextComponent resultLabel() {
        return new Label("Result");
    }

    @Bean
    public JTextComponent resultValue() {
        JTextArea textArea = new JTextArea(EMPTY_TEXT_AREA_STRING);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        return textArea;
    }


    /**
     * Поля ввода
     */

    @Bean
    public JTextComponent amountInput() {
        JFormattedTextField input = new JFormattedTextField(DEF_VALUE);
        input.setFormatterFactory(new AbstractFormatterFactory() {
            @Override
            public AbstractFormatter getFormatter(JFormattedTextField tf) {
                NumberFormat format = DecimalFormat.getInstance();
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(2);
                format.setRoundingMode(RoundingMode.HALF_UP);
                InternationalFormatter formatter = new InternationalFormatter(format);
                formatter.setAllowsInvalid(false);
                return formatter;
            }
        });
        return input;
    }


    /**
     * Кнопки
     */

    @Bean(initMethod = "init")
    public Button recalculationButton() {
        Button button = new Button();
        button.setText("Recalculate");
        button.setActionListener(recalculateListener());
        return button;
    }

    @Bean(initMethod = "init")
    public Button clearButton() {
        Button button = new Button();
        button.setText("Clear");
        button.setActionListener(clearListener());
        return button;
    }


    /**
     * Другие компоненты
     */

    @Bean
    public DatePicker datePicker() {
        // TODO: Should allow only past
        return new DatePicker();
    }


    /**
     * Слушатели
     */

    @Bean
    public ActionListener clearListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amountInput().setText(ZERO.toString());
                resultValue().setText(EMPTY_TEXT_AREA_STRING);
                datePicker().clear();
                ApplicationContextProvider.getIntegrationController().clean();
            }
        };
    }

    @Bean
    public ActionListener recalculateListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultValue().setText(EMPTY_TEXT_AREA_STRING);
                ApplicationContextProvider.getIntegrationController().requestCalculation();
            }
        };
    }

}