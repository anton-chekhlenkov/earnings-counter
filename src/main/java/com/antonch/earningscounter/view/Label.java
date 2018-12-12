package com.antonch.earningscounter.view;

import javax.swing.*;

/**
 * Нередактируемое текстовое поле для информационных
 * сообщений и заголовков полей
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
public class Label extends JTextField {

    public Label(String text) {
        super(text);
        setEditable(false);
    }

}