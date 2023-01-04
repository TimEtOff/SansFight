package fr.timeto.sansfight;

import fr.theshark34.swinger.Swinger;

import javax.swing.*;
import java.awt.*;
import java.awt.im.InputContext;
import java.awt.image.BufferedImage;

public class BulletBoardPanel extends JPanel {

    public BulletBoardPanel() {
        Game.heart.setLocation(300, 250);
        this.add(Game.heart);
    }

    @Override
    public void paintComponent(Graphics g) {
        BufferedImage background = Swinger.getResourceIgnorePath("/assets/sansfight/Textures/BG.jpg");

        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

    }
}
