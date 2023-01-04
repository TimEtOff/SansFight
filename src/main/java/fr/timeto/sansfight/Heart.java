package fr.timeto.sansfight;

import fr.theshark34.swinger.Swinger;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static fr.timeto.sansfight.Game.*;
import static java.lang.Integer.valueOf;

public class Heart extends JLabel {
    private static BufferedImage heartImageSrc = Swinger.getResourceIgnorePath("/assets/sansfight/Animations/PlayerHeart/Default/000.png");
    private static BufferedImage heartImageSplitSrc = Swinger.getResourceIgnorePath("/assets/sansfight/Animations/PlayerHeart/Split/000.png");

    private static String playerName;
    private static int playerLevel;
    private static int maximumHp;
    private static int hp;
    private static int kr;
    private static int at;
    private static int df;
    private static Item.Weapon selectedWeapon;
    private static Item.Armor selectedArmor;

    private static int mode;
    private static int direction;

    public class HEART_MODE {
        public static final int RED_MODE = 0;
        public static final int BLUE_MODE = 1;
    }

    public class HEART_DIRECTION {
        public static final int EAST_DIRECTION = 0;
        public static final int SOUTH_DIRECTION = 1;
        public static final int WEST_DIRECTION = 2;
        public static final int NORTH_DIRECTION = 3;
    }

    public Heart(String name, int level, Item.Weapon weapon, Item.Armor armor) {
        super();

        this.setBounds(1, 1, 16, 16);
        this.setAlignmentX(SwingConstants.CENTER);
        this.setAlignmentY(SwingConstants.CENTER);

        mode = HEART_MODE.RED_MODE;
        direction = HEART_DIRECTION.SOUTH_DIRECTION;
        changeMode(mode);

        playerName = "Chara";
        playerLevel = 20;

        if (level == 20) {
            maximumHp = 99;
        } else {
            maximumHp = 4 * level + 16;
        }
        hp = maximumHp;
        kr = hp;
        at = -2 + (2*level);
        df = (level - 1) /4;
        playerName = name;
        playerLevel = level;

        selectedWeapon = weapon;
        selectedArmor = armor;

        System.out.println("[SansFight by TimEtOff on GitHub]");
        System.out.println("[SansFight] Player Name: " + playerName);
        System.out.println("[SansFight] Player Level: " + playerLevel);
        System.out.println("[SansFight] Max HP: " + maximumHp);
        System.out.println("[SansFight] AT: " + at);
        System.out.println("[SansFight] DF: " + df);
        System.out.println("[SansFight] Weapon: " + selectedWeapon.name + " (" + selectedWeapon.atk + "ATK)");
        System.out.println("[SansFight] Armor: " + selectedArmor.name + " (" + selectedArmor.df + "DF)");

    }

    private static BufferedImage getImageMode() {
        if (mode == HEART_MODE.RED_MODE) {
            return createColorImage(heartImageSrc, 0xFFFF0000);
        } else if (mode == HEART_MODE.BLUE_MODE) {
            return createColorImage(heartImageSrc, 0xFF0444f2);
        }
        return null;
    }

    private BufferedImage getImageDirection() {
        if (direction == HEART_DIRECTION.EAST_DIRECTION) {
            return rotateImage(heartImageSrc, 0);
        } else if (direction == HEART_DIRECTION.SOUTH_DIRECTION) {
            return rotateImage(heartImageSrc, 90);
        } else if (direction == HEART_DIRECTION.WEST_DIRECTION) {
            return rotateImage(heartImageSrc, 180);
        } else if (direction == HEART_DIRECTION.NORTH_DIRECTION) {
            return rotateImage(heartImageSrc, 270);
        }
        return null;
    }

    public void changeMode(int heartMode) {
        if (heartMode == HEART_MODE.RED_MODE) {
            mode = heartMode;
            this.setIcon(new ImageIcon(createColorImage(getImageDirection(), 0xFFFF0000)));

        } else if (heartMode == HEART_MODE.BLUE_MODE) {
            mode = heartMode;
            this.setIcon(new ImageIcon(createColorImage(getImageDirection(), 0xFF0444f2)));
        }

    }

    public void changeDirection(int heartDirection) {
        if (heartDirection == HEART_DIRECTION.EAST_DIRECTION) {
            direction = heartDirection;
            this.setIcon(new ImageIcon(rotateImage(getImageMode(), 0)));
        } else if (heartDirection == HEART_DIRECTION.SOUTH_DIRECTION) {
            direction = heartDirection;
            this.setIcon(new ImageIcon(rotateImage(getImageMode(), 90)));
        } else if (heartDirection == HEART_DIRECTION.WEST_DIRECTION) {
            direction = heartDirection;
            this.setIcon(new ImageIcon(rotateImage(getImageMode(), 180)));
        } else if (heartDirection == HEART_DIRECTION.NORTH_DIRECTION) {
            direction = heartDirection;
            this.setIcon(new ImageIcon(rotateImage(getImageMode(), 270)));
        }
    }

    private static boolean inKarma = false;

    public void damage(int nbOfHp) {
        if (!inKarma) {
            hp -= nbOfHp /*- getDef(false)*/ ;
            hpBar.setValue(hp);
            hpLabel.setText(hp + "/" + maximumHp);
            kr = hp;
            krBar.setValue(kr);
            verifyHealth();
        }

    }

    public void krDamage(int nbOfHpBefore, int nbOfKr) {
        if (!inKarma) {
            inKarma = true;
            kr = nbOfHpBefore;
            hp = hp - nbOfKr;
            hpLabel.setForeground(new Color(255, 0, 255));
            hpBar.setValue(hp);
            Thread t = new Thread(() -> {
                while ((kr != hp) && verifyHealth()) {
                    hpLabel.setText(kr + "/" + maximumHp);
                    kr -= 1;
                    krBar.setValue(kr);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
                inKarma = false;
                hpLabel.setText(hp + "/" + maximumHp);
                hpLabel.setForeground(Color.WHITE);
            });
            t.start();
        }
    }

    public void heal(int nbOfHp, String foodName) {
        hp += nbOfHp;
        hpBar.setValue(hp);
        hpLabel.setText(hp + "/" + maximumHp);
        kr = hp;
        krBar.setValue(kr);
        bulletBoardTextArea.setText("* You ate " + foodName.toUpperCase() + System.getProperty("line.separator") + "    You gained " + nbOfHp + "HP!");
        verifyHealth();

    }

    private boolean verifyHealth() {
        if (kr <= 0) {
            death();
            return false;
        } else if (hp >= maximumHp) {
            hp = maximumHp;
            inKarma = false;
            hpLabel.setText(hp + "/" + maximumHp);
            hpLabel.setForeground(Color.WHITE);
            return false;
        }
        return true;

    }

    public void death() {
        hp = 0;
        hpBar.setValue(0);
        hpLabel.setText(hp + "/" + maximumHp);
        kr = hp;
        bulletBoardTextArea.setText("* DEATH");

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

        panel.add(this);

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.setBounds(this.getX() -2, this.getY(), 20, 16);
            this.setIcon(new ImageIcon(rotateImage(heartImageSplitSrc, 90)));
        });
        t.start();

    }

    public String getPlayerName() {return playerName;}
    public static int getPlayerLevel() {return playerLevel;}
    public static int getHp() {return hp;}
    public static int getKr() {return kr;}
    public static int getMaxHp() {return maximumHp;}
    public static int getAtk(boolean withWeapon) {
        if (withWeapon) {
            return maximumHp + selectedWeapon.atk;
        }
        return maximumHp;
    }

    public static int getDef(boolean withArmor) {
        if (withArmor) {
            return df + selectedArmor.df;
        }
        return df;
    }

    public void move(int direction) {
        Thread t = new Thread(() -> {
            if (mode == HEART_MODE.RED_MODE) {
                if (direction == HEART_DIRECTION.EAST_DIRECTION) {
                    if (this.getLocation().x + this.getWidth() + 2 <= bulletBoard.getLocation().x + bulletBoard.getWidth()) {
                        this.setLocation(this.getX() + 2, this.getY());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        this.setLocation(this.getX() + 2, this.getY());
                    }
                } else if (direction == HEART_DIRECTION.SOUTH_DIRECTION) {
                    if (this.getLocation().y + this.getHeight() + 2 <= bulletBoard.getLocation().y + bulletBoard.getHeight()) {
                        this.setLocation(this.getX(), this.getY() +2);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        this.setLocation(this.getX(), this.getY() +2);
                    }
                } else if (direction == HEART_DIRECTION.WEST_DIRECTION) { // x doit être compris entre le x d
                    if (this.getLocation().x - 2 >= bulletBoard.getLocation().x) {
                        this.setLocation(this.getX() - 2, this.getY());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        this.setLocation(this.getX() - 2, this.getY());
                    }
                } else if (direction == HEART_DIRECTION.NORTH_DIRECTION) {
                    if (this.getLocation().y - 2 >= bulletBoard.getLocation().y) {
                        this.setLocation(this.getX(), this.getY() - 2);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        this.setLocation(this.getX(), this.getY() - 2);
                    }
                }
            }
        });
        t.start();
    }

    public static BufferedImage createColorImage(BufferedImage originalImage, int mask) {
        BufferedImage colorImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int pixel = originalImage.getRGB(x, y) & mask;
                colorImage.setRGB(x, y, pixel);
            }
        }

        return colorImage;
    }

    public BufferedImage rotateImage(BufferedImage buffImage, double angle) {
        double radian = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radian));
        double cos = Math.abs(Math.cos(radian));

        int width = buffImage.getWidth();
        int height = buffImage.getHeight();

        int nWidth = (int) Math.floor((double) width * cos + (double) height * sin);
        int nHeight = (int) Math.floor((double) height * cos + (double) width * sin);

        BufferedImage rotatedImage = new BufferedImage(
                nWidth, nHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = rotatedImage.createGraphics();

        graphics.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        graphics.translate((nWidth - width) / 2, (nHeight - height) / 2);
        // rotation around the center point
        graphics.rotate(radian, (double) (width / 2), (double) (height / 2));
        graphics.drawImage(buffImage, 0, 0, null);
        graphics.dispose();

        return rotatedImage;
    }
}
