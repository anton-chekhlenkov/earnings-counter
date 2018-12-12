package com.antonch.earningscounter.view;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Кнопка с инициализацией обработчика
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
public class Button extends JButton {

    private ActionListener actionListener;


    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public void init() {
        this.addActionListener(actionListener);
    }

}