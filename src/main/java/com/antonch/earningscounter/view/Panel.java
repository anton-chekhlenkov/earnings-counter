package com.antonch.earningscounter.view;


import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * Панель компонентов
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
public class Panel extends JPanel {

    private int axis;

    private List<Component> panelComponents = Collections.emptyList();


    public void setAxis(int axis) {
        this.axis = axis;
    }

    public void setPanelComponents(List panelComponents) {
        this.panelComponents = panelComponents;
    }

    public void init() {
        setLayout(new BoxLayout(this, axis));
        panelComponents.forEach(it -> add(it));
    }

}