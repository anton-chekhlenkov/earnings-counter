package com.antonch.earningscounter.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

/**
 * Главный экран приложения
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
@Slf4j
@Component
public class MainFrame extends JFrame {

    private static final Dimension FRAME_DIMENSION = new Dimension(400, 100);

    @Autowired
    public JPanel mainPanel;


    public MainFrame() {
        super();
    }

    @Override
    @Value("${main.frame.title}")
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @PostConstruct
    public void init() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setMaximumSize(FRAME_DIMENSION);
                setMinimumSize(FRAME_DIMENSION);
                add(mainPanel);

                pack();
                setVisible(true);
                log.debug("Singleton bean 'MainFrame' was successfully initialized.");
            }
        });
    }

}
