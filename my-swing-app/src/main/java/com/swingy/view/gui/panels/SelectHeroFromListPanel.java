package com.swingy.view.gui;

import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

import com.swingy.model.Hero;
import com.swingy.controller.GameController;
import com.swingy.controller.Phases;



class SelectHeroFromListPanel extends JPanel {
    private Image background;

    private JComboBox<Hero> heroBox;
    private JButton submitButton;
    private  Hero chosenHero = null;

    private JPanel contentPanel;
    private JPanel buttonPanel;


    public SelectHeroFromListPanel(Action mainMenuAction, Action gamePanelAction, GameController controller) {
        // List<Hero> list = new ArrayList();
        // list.add(new Hero("Rinswind", "wizard", 1, 0, 3, 3, 4 ));
        // list.add(new Hero("Hercules", "warrior", 2, 0, 8, 3, 4 ));
        // list.add(new Hero("Kuzjjjjja", "barbarian", 0, 0, 3, 3, 4 ));

        setLayout(new GridBagLayout());

        contentPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel = new JPanel(new GridLayout(2, 1));

        // JLabel infoLabel = new JLabel();
        // if (list.size() > 0) {
        //     chosenHero = list.get(0);
        //     infoLabel.setText("EXP: " + chosenHero.getExp() + " | HP: " + chosenHero.getHp()
        //         +" | ATK: " + chosenHero.getAttack() + " | DEF: " + chosenHero.getDefence());
        // }

        // heroBox = new JComboBox<>(
        //     list.toArray(new Hero[0])
        // );

        // heroBox.setRenderer(new DefaultListCellRenderer() {
        //     @Override
        //     public Component getListCellRendererComponent(
        //             JList<?> list,
        //             Object value,
        //             int index,
        //             boolean isSelected,
        //             boolean cellHasFocus) {

        //         super.getListCellRendererComponent(
        //                 list, value, index, isSelected, cellHasFocus);

        //         if (value instanceof Hero hero) {
        //             setText(hero.getName() + " (" + hero.getType() + ")" + " | level: " + hero.getLevel());
        //         }

        //         return this;
        //     }
        // });
        // heroBox.addActionListener(e -> {
        //     chosenHero = (Hero) heroBox.getSelectedItem();

        //     if (chosenHero != null) {
        //         infoLabel.setText("EXP: " + chosenHero.getExp() + " | HP: " + chosenHero.getHp()
        //         +" | ATK: " + chosenHero.getAttack() + " | DEF: " + chosenHero.getDefence());
        //     }
        // });


        GridBagConstraints gbcWrap = new GridBagConstraints();
        gbcWrap.insets = new Insets(8, 8, 8, 8);
        gbcWrap.fill = GridBagConstraints.RELATIVE;

        // GridBagConstraints gbcCont = new GridBagConstraints();
        // gbcCont.insets = new Insets(8, 8, 8, 8);
        // gbcCont.fill = GridBagConstraints.HORIZONTAL;

        // // Hero
        // gbcCont.gridx = 0;
        // gbcCont.gridy = 0;

        // contentPanel.add(new JLabel("Chosen hero:"), gbcCont);


        // gbcCont.anchor = GridBagConstraints.CENTER;
        // gbcCont.gridx = 1;
        // contentPanel.add(heroBox, gbcCont);
        // gbcCont.gridx = 0;
        // gbcCont.gridy = 1;
        // contentPanel.add(infoLabel, gbcCont);
        // contentPanel.setOpaque(false);
        gbcWrap.gridy = 0;

        add(contentPanel, gbcWrap);

        // Button
        submitButton = new JButton("Start");

        submitButton.addActionListener(e -> {
            if (chosenHero != null) {
                controller.setGamePhase(Phases.GAMEPLAY);
                controller.startGame(chosenHero);
                gamePanelAction.actionPerformed(e);
            }
        });
        buttonPanel.add(submitButton);

        JButton backButton = new JButton(mainMenuAction);
        buttonPanel.add(backButton);
        gbcWrap.gridx = 0;
        gbcWrap.gridy = 1;
        add(buttonPanel, gbcWrap);
        // setLayout(new BorderLayout());

        // contentPanel = new JPanel();
        // contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        // contentPanel.setPreferredSize(new Dimension(300, 400));

        // JScrollPane scrollPane = new JScrollPane(contentPanel);
        // add(scrollPane, BorderLayout.CENTER);

        // buttonPanel = new JPanel();
        // buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Dimension buttonSize = new Dimension(240, 40);

        // JButton backButton = new JButton(mainMenuAction);
        // backButton.setMaximumSize(buttonSize);
        // backButton.setPreferredSize(buttonSize);
        // backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // buttonPanel.add(backButton);

        // add(buttonPanel, BorderLayout.SOUTH);
    }

   public void updateHeroList(List<Hero> list) {
        // System.out.println("Heroes count: " + list.size());

        contentPanel.removeAll();
         JLabel infoLabel = new JLabel();
        if (list.size() > 0) {
            chosenHero = list.get(0);
            infoLabel.setText("EXP: " + chosenHero.getExperience() + " | HP: " + chosenHero.getHitPoints()
                +" | ATK: " + chosenHero.getAttack() + " | DEF: " + chosenHero.getDefense());
        }

        heroBox = new JComboBox<>(
            list.toArray(new Hero[0])
        );

        heroBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                if (value instanceof Hero) {
                    Hero hero = (Hero) value;
                    setText(hero.getName() + " (" + hero.getArchetype() + ")" + " | level: " + hero.getLevel());
                }

                return this;
            }
        });
        heroBox.addActionListener(e -> {
            chosenHero = (Hero) heroBox.getSelectedItem();

            if (chosenHero != null) {
                infoLabel.setText("EXP: " + chosenHero.getExperience() + " | HP: " + chosenHero.getHitPoints()
                +" | ATK: " + chosenHero.getAttack() + " | DEF: " + chosenHero.getDefense());
            }
        });

        GridBagConstraints gbcCont = new GridBagConstraints();
        gbcCont.insets = new Insets(8, 8, 8, 8);
        gbcCont.fill = GridBagConstraints.HORIZONTAL;

        // Hero
        gbcCont.gridx = 0;
        gbcCont.gridy = 0;

        contentPanel.add(new JLabel("Chosen hero:"), gbcCont);


        gbcCont.anchor = GridBagConstraints.CENTER;
        gbcCont.gridx = 1;
        contentPanel.add(heroBox, gbcCont);
        gbcCont.gridx = 0;
        gbcCont.gridy = 1;
        contentPanel.add(infoLabel, gbcCont);
        contentPanel.setOpaque(false);


        // contentPanel.add(new JLabel("Heroes:"));
        // contentPanel.add(Box.createVerticalStrut(10));

        // if (heroes.size() == 0) {
        //     JLabel label = new JLabel("No data to show");
        //     label.setAlignmentX(Component.LEFT_ALIGNMENT);
        //     contentPanel.add(label);
        //     contentPanel.add(Box.createVerticalStrut(5));
        // }
        // for (Hero hero : heroes) {
        //     JLabel label = new JLabel("Hero " + hero.getName());
        //     label.setAlignmentX(Component.LEFT_ALIGNMENT);
        //     contentPanel.add(label);
        //     contentPanel.add(Box.createVerticalStrut(5));
        // }

        contentPanel.revalidate();
        contentPanel.repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        RadialGradientPaint paint = new RadialGradientPaint(
                getWidth() / 2f,
                getHeight() / 2f,
                getWidth() / 2f,
                new float[]{0f, 1f},
                new Color[]{
                        new Color(105, 255, 255, 0),
                        new Color(0, 0, 0, 180)
                });

        g2.setPaint(paint);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();
    }
}
