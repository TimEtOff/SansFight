package fr.timeto.sansfight;

import fr.theshark34.swinger.Swinger;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static fr.timeto.sansfight.Game.*;

public class Heart {
    public static String playerName;
    public static int playerLevel;
    public static int maximumHp;
    public static int hp;
    public static int deprecationHp;

    private static boolean inDeprecation = false;

    public static void damage(int nbOfHp) {
        if (!inDeprecation) {
            hp -= nbOfHp;
            lifeBar.setValue(hp);
            lifeLabel.setText(hp + "/" + maximumHp);
            deprecationHp = hp;
            deprecationBar.setValue(deprecationHp);
            verifyHealth();
        }

    }

    public static void deprecationDamage(int nbOfHpBefore, int nbOfDeprecatedHp) {
        if (!inDeprecation) {
            inDeprecation = true;
            deprecationHp = nbOfHpBefore;
            hp = hp - nbOfDeprecatedHp;
            lifeLabel.setForeground(new Color(255, 0, 255));
            lifeBar.setValue(hp);
            Thread t = new Thread(() -> {
                while ((deprecationHp != hp) && verifyHealth()) {
                    lifeLabel.setText(deprecationHp + "/" + maximumHp);
                    deprecationHp -= 1;
                    deprecationBar.setValue(deprecationHp);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
                inDeprecation = false;
                lifeLabel.setText(hp + "/" + maximumHp);
                lifeLabel.setForeground(Color.WHITE);
            });
            t.start();
        }
    }

    public static void heal(int nbOfHp, String foodName) {
        hp += nbOfHp;
        lifeBar.setValue(hp);
        lifeLabel.setText(hp + "/" + maximumHp);
        deprecationHp = hp;
        deprecationBar.setValue(deprecationHp);
        cadre.setText("* You ate " + foodName.toUpperCase() + System.getProperty("line.separator") + "    You gained " + nbOfHp + "HP!");
        verifyHealth();

    }

    private static boolean verifyHealth() {
        if (deprecationHp <= 0) {
            death();
            return false;
        } else if (hp >= maximumHp) {
            hp = maximumHp;
            inDeprecation = false;
            lifeLabel.setText(hp + "/" + maximumHp);
            lifeLabel.setForeground(Color.WHITE);
            return false;
        }
        return true;

    }

    public static void death() {
        hp = 0;
        lifeBar.setValue(0);
        lifeLabel.setText(hp + "/" + maximumHp);
        deprecationHp = hp;
        cadre.setText("* DEATH");

        JPanel panel = new JPanel(null) {
            @Override
            public void paintComponent(Graphics g) {
                BufferedImage background = Swinger.getResourceIgnorePath("/assets/sansfight/Textures/BG.jpg");

                super.paintComponent(g);
                g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

            }
        };

        frame.setContentPane(panel);

        JLabel l = new JLabel();
        l.setBounds(1000, 1000, 1, 1);
        panel.add(l);
        l.setVisible(false);

    }
}
