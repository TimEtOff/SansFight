package fr.timeto.sansfight;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.AbstractBorder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static fr.timeto.sansfight.Heart.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Game extends JPanel implements KeyListener , SwingerEventListener {

    static JFrame frame;

    public static final String MENU_ARG = "-menu";
    public static final String FIGHT_ARG = "-fight";

    public static STexturedButton uiFightButton = new STexturedButton(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIFight/Default/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIFight/Highlight/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIFight/Default/000.png"));
    public static STexturedButton uiActButton = new STexturedButton(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIAct/Default/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIAct/Highlight/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIAct/Default/000.png"));
    public static STexturedButton uiItemsButton = new STexturedButton(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIItem/Default/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIItem/Highlight/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIItem/Default/000.png"));
    public static STexturedButton uiMercyButton = new STexturedButton(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIMercy/Default/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIMercy/Highlight/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIMercy/Default/000.png"));
    public static JLabel playerNameLabel = new JLabel("", SwingConstants.LEFT);
    public static JLabel levelLabel = new JLabel("", SwingConstants.RIGHT);
    public static JTextArea cadre = new JTextArea();
    public static SColoredBar deprecationBar = new SColoredBar(new Color(191, 0, 0), new Color(255, 0, 255));
    public static SColoredBar lifeBar = new SColoredBar(Swinger.getTransparentWhite(0), new Color(255, 255, 0));
    public static JLabel lifeLabel = new JLabel(hp + "/" + maximumHp, SwingConstants.RIGHT);

    public static JLabel sansBody = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/HandUp/000.png")), SwingConstants.CENTER);
    public static JLabel sansSweat = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansSweat/Sweat1/000.png")), SwingConstants.CENTER);
    public static JLabel sansHead = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansHead/Default/000.png")), SwingConstants.CENTER);
    public static JLabel sansTorso = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansTorso/Default/000.png")), SwingConstants.CENTER);
    public static JLabel sansLegs = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansLegs/Standing/000.png")), SwingConstants.CENTER);

    public static void launch(String[] args) {
        playerName = "Chara";
        playerLevel = 20;

        String secondArg = null;
        try {
            secondArg = args[1];
            playerName = secondArg;
        } catch (Exception ignored) {
        }

        String thirdArg = null;
        try {
            thirdArg = args[2];
            playerLevel = Integer.parseInt(thirdArg);
        } catch (Exception ignored) {
        }

        if (playerLevel == 20) {
            maximumHp = 99;
        } else {
            maximumHp = 4 * playerLevel + 16;
        }
        hp = maximumHp;
        deprecationHp = hp;

        frame = new JFrame();
        frame.setTitle("Sans Fight");
        frame.setSize(640, 480);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Swinger.getResourceIgnorePath("/assets/sansfight/Files/icon-256.png"));
        frame.setContentPane(new Game(args));

        frame.setVisible(true);

    }

    private void initComponents() {
        uiFightButton.setBounds(82, 380);
        uiFightButton.addEventListener(this);
        this.add(uiFightButton);
        uiFightButton.setVisible(false);

        uiActButton.setBounds(202, 380);
        uiActButton.addEventListener(this);
        this.add(uiActButton);
        uiActButton.setVisible(false);

        uiItemsButton.setBounds(322, 380);
        uiItemsButton.addEventListener(this);
        this.add(uiItemsButton);
        uiItemsButton.setVisible(false);

        uiMercyButton.setBounds(442, 380);
        uiMercyButton.addEventListener(this);
        this.add(uiMercyButton);
        uiMercyButton.setVisible(false);

        playerNameLabel.setBounds(82, 358, 130, 20);
        playerNameLabel.setFont(bitOperatorFont.deriveFont(25f));
        playerNameLabel.setForeground(Color.WHITE);
        playerNameLabel.setOpaque(false);
        this.add(playerNameLabel);
        playerNameLabel.setVisible(false);

        levelLabel.setBounds(82, 358, 130, 20);
        levelLabel.setFont(bitOperatorFont.deriveFont(25f));
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setOpaque(false);
        this.add(levelLabel);
        levelLabel.setVisible(false);

        cadre.setBounds(82, 227, 470, 130);
        cadre.setBorder(new RectangleBorder(Color.WHITE, new Insets(5, 5, 5, 5), new Insets(10, 15, 10, 15)));
        cadre.setEditable(false);
        cadre.setForeground(Color.WHITE);
        cadre.setFont(bitOperatorFont.deriveFont(23f));
        cadre.setOpaque(false);
        cadre.setText("*  Tu vas passer un sale quart d'heure..." + System.getProperty("line.separator") + "     Ou pas ?");
        this.add(cadre);
        cadre.setVisible(false);

        lifeBar.setBounds(250, 360, 130, 17);
        this.add(lifeBar);
        lifeBar.setVisible(false);

        deprecationBar.setBounds(250, 360, 130, 17);
        this.add(deprecationBar);
        deprecationBar.setVisible(false);

        lifeLabel.setBounds(330, 358, 130, 20);
        lifeLabel.setFont(bitOperatorFont.deriveFont(25f));
        lifeLabel.setForeground(Color.WHITE);
        lifeLabel.setOpaque(false);
        this.add(lifeLabel);
        lifeLabel.setVisible(false);

        sansSweat.setBounds(290, 99, 32, 9);
        this.add(sansSweat);
        sansSweat.setVisible(false);

        sansHead.setBounds(290, 99, 32, 30);
        this.add(sansHead);
        sansHead.setVisible(false);

        sansBody.setBounds(276, 100, 64, 70);
        this.add(sansBody);
        sansBody.setVisible(false);

        sansTorso.setBounds(279, 123, 54, 25);
        this.add(sansTorso);
        sansTorso.setVisible(false);

        sansLegs.setBounds(285, 148, 44, 23);
        this.add(sansLegs);
        sansLegs.setVisible(false);

    }

    public static void menuScene() {

    }

    public static void fightScene(boolean enabled) {
        if (enabled) {
            uiFightButton.setVisible(true);
            uiActButton.setVisible(true);
            uiItemsButton.setVisible(true);
            uiMercyButton.setVisible(true);

            playerNameLabel.setText(playerName.toUpperCase());
            playerNameLabel.setVisible(true);
            levelLabel.setText("LV " + playerLevel);
            levelLabel.setVisible(true);
            deprecationBar.setVisible(true);
            deprecationBar.setValue(deprecationHp);
            deprecationBar.setMaximum(maximumHp);
            lifeBar.setVisible(true);
            lifeBar.setValue(hp);
            lifeBar.setMaximum(maximumHp);
            lifeLabel.setVisible(true);
            lifeLabel.setText(hp + "/" + maximumHp);

            cadre.setVisible(true);

            sansHead.setVisible(true);
            sansTorso.setVisible(true);
            sansLegs.setVisible(true);
        }

    }

    public Game(String [] args) {
        this.setLayout(null);
        initFonts();
        initComponents();

        String firstArg = null;
        try {
            firstArg = args[0];
        } catch (Exception ignored) {}

        if (Objects.equals(firstArg, MENU_ARG)) {
            menuScene();

        } else if (Objects.equals(firstArg, FIGHT_ARG)) {
            fightScene(true);

        } else {
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void paintComponent(Graphics g) {
        BufferedImage background = Swinger.getResourceIgnorePath("/assets/sansfight/Textures/BG.jpg");

        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_KP_UP) {


        }

    }

    private static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = CustomFonts.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    private static Font CustomFont(String path) {
        Font customFont = loadFont(path, 24f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);
        return customFont;

    }
    private static Font loadFont(String path, float size){
        try {
            Font myFont = Font.createFont(Font.TRUETYPE_FONT, getFileFromResourceAsStream(path));
            return myFont.deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static Font bitOperatorFont;
    public static Font undertaleSansFont;

    private static final String FONT_PATH_8BITOPERATOR = "assets/sansfight/Fonts/8bitoperator_jve.ttf";
    private static final String FONT_PATH_UNDERTALESANS = "assets/sansfight/Fonts/UndertaleSans.ttf";

    public static void initFonts() {
        bitOperatorFont = CustomFont(FONT_PATH_8BITOPERATOR);
        undertaleSansFont = CustomFont(FONT_PATH_UNDERTALESANS);

    }

    @Override
    public void onEvent(SwingerEvent e) {
        if (e.getSource() == uiFightButton) {
            Thread t = new Thread(() -> {
                Animations.sansBody(Animations.SANS_BODY.HAND_UP, false);
                damage(5);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                sansSweat.setVisible(true);
                sansSweat.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansSweat/Sweat1/000.png")));
                Animations.sansBody(Animations.SANS_BODY.HAND_DOWN, false);
                damage(5);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                sansSweat.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansSweat/Sweat2/000.png")));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                sansBody.setVisible(false);
                sansHead.setBounds(290, 99, 32, 30);
                sansSweat.setBounds(290, 99, 32, 9);
                sansSweat.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansSweat/Sweat1/000.png")));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                sansSweat.setVisible(false);

            });
            t.start();

        } else if (e.getSource() == uiActButton) {
            Thread t = new Thread(() -> {
                Animations.sansBody(Animations.SANS_BODY.HAND_RIGHT, false);
                deprecationDamage(hp, 10);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                Animations.sansBody(Animations.SANS_BODY.HAND_LEFT, false);
                deprecationDamage(hp, 10);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                sansHead.setBounds(290, 99, 32, 30);
                sansSweat.setBounds(290, 99, 32, 9);
                sansBody.setVisible(false);
            });
            t.start();
        } else if (e.getSource() == uiItemsButton) {
            heal(50, "Your mother");
        }

    }
}

class RectangleBorder extends AbstractBorder {
    protected Insets thickness;

    protected Color lineColor;

    protected Insets gap;

    public RectangleBorder(Color color) {
        this(color, new Insets(1, 1, 1, 1));
    }

    public RectangleBorder(Color color, Insets thickness) {
        this(color, thickness, thickness);
    }

    public RectangleBorder(Color color, Insets thickness, Insets gap) {
        lineColor = color;
        this.thickness = thickness;
        this.gap = gap;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Color oldColor = g.getColor();


        g.setColor(lineColor);
        // top
        for (int i = 0; i < thickness.top; i++) {
            g.drawLine(x, y + i, x + width, y + i);
        }
        // bottom
        for (int i = 0; i < thickness.bottom; i++) {
            g.drawLine(x, y + height - i - 1, x + width, y + height - i - 1);
        }
        // right
        for (int i = 0; i < thickness.right; i++) {
            g.drawLine(x + width - i - 1, y, x + width - i - 1, y + height);
        }
        // left
        for (int i = 0; i < thickness.left; i++) {
            g.drawLine(x + i, y, x + i, y + height);
        }
        g.setColor(oldColor);
    }

    /**
     * Returns the insets of the border.
     *
     * @param c
     *          the component for which this border insets value applies
     */
    public Insets getBorderInsets(Component c) {
        return gap;
    }

    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = gap.left;
        insets.top = gap.top;
        insets.right = gap.right;
        insets.bottom = gap.bottom;
        return insets;
    }

    /**
     * Returns the color of the border.
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Returns the thickness of the border.
     */
    public Insets getThickness() {
        return thickness;
    }

    /**
     * Returns whether the border is opaque.
     */
    public boolean isBorderOpaque() {
        return false;
    }

    public Insets getGap() {
        return gap;
    }

}
