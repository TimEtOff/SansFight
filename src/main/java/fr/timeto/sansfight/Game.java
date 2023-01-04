package fr.timeto.sansfight;

import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.AbstractBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static fr.timeto.sansfight.Animations.sansDialogue;
import static fr.timeto.sansfight.Heart.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Game extends JPanel implements NativeKeyListener, SwingerEventListener {

    static JFrame frame;

    public static Heart heart;

    public static final String MENU_ARG = "-menu";
    public static final String FIGHT_ARG = "-fight";

    public static STexturedButton uiFightButton = new STexturedButton(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIFight/Default/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIFight/Highlight/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIFight/Default/000.png"));
    public static STexturedButton uiActButton = new STexturedButton(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIAct/Default/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIAct/Highlight/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIAct/Default/000.png"));
    public static STexturedButton uiItemsButton = new STexturedButton(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIItem/Default/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIItem/Highlight/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIItem/Default/000.png"));
    public static STexturedButton uiMercyButton = new STexturedButton(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIMercy/Default/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIMercy/Highlight/000.png"), Swinger.getResourceIgnorePath("/assets/sansfight/Animations/UIMercy/Default/000.png"));
    public static JLabel playerNameLabel = new JLabel("", SwingConstants.LEFT);
    public static JLabel levelLabel = new JLabel("", SwingConstants.RIGHT);
    public static JPanel bulletBoard;
    public static JTextArea bulletBoardTextArea = new JTextArea(){
        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            bulletBoard.setBounds(x +3, y +3, width -6, height -6);
        }

        @Override
        public void setBounds(Rectangle r) {
            super.setBounds(r);
            bulletBoard.setBounds((int) (r.getX() +3), (int) (r.getY() +3), (int) (r.getWidth() -6), (int) (r.getHeight() -6));
        }
    };
    public static SColoredBar krBar = new SColoredBar(new Color(191, 0, 0), new Color(255, 0, 255));
    public static SColoredBar hpBar = new SColoredBar(Swinger.getTransparentWhite(0), new Color(255, 255, 0));
    public static JLabel hpLabel = new JLabel("HP/MAXHP", SwingConstants.RIGHT);
    public static JLabel hpIcon = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/HP/Default/000.png")));
    public static JLabel krIcon = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/KR/Default/000.png")));

    public static JLabel sansBody = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/HandUp/000.png")), SwingConstants.CENTER);
    public static JLabel sansSweat = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansSweat/Sweat1/000.png")), SwingConstants.CENTER);
    public static JLabel sansHead = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansHead/Default/000.png")), SwingConstants.CENTER);
    public static JLabel sansTorso = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansTorso/Default/000.png")), SwingConstants.CENTER);
    public static JLabel sansLegs = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansLegs/Standing/000.png")), SwingConstants.CENTER);
    public static JLabel sansSpeechBubble = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SpeechBubble/Default/000.png")), SwingConstants.CENTER);
    public static JTextArea sansSpeechTextArea = new JTextArea(10, 5);

    public static final Item.Weapon RealKnife = new Item.Weapon("Real Knife", "RealKnf", "Here we are!", "It's about time.", 99);
    public static final Item.Armor TheLocket = new Item.Armor("The Locket", "ThLockt", "You can feel it beating.", "Right where it belongs.", 99);

    public static void launch(String[] args) {
        String secondArg = null;
        try {
            secondArg = args[1];
        } catch (Exception ignored) {
        }

        String thirdArg = null;
        try {
            thirdArg = args[2];
        } catch (Exception ignored) {
        }

        heart = new Heart(secondArg, Integer.parseInt(thirdArg), RealKnife, TheLocket);
        bulletBoard = new BulletBoardPanel();

        frame = new JFrame();
        frame.setTitle("Sans Fight");
        frame.setSize(640, 480);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Swinger.getResourceIgnorePath("/assets/sansfight/Files/icon-256.png"));
        frame.setContentPane(new Game(args));

        frame.setVisible(true);

        GlobalKeyListener.launchKeyListener();

    }

    private void initComponents() {
        this.add(heart);

        uiFightButton.setBounds(45, 385);
        uiFightButton.addEventListener(this);
        this.add(uiFightButton);
        uiFightButton.setVisible(false);

        uiActButton.setBounds(uiFightButton.getX() + 142, uiFightButton.getY());
        uiActButton.addEventListener(this);
        this.add(uiActButton);
        uiActButton.setVisible(false);

        uiItemsButton.setBounds(uiActButton.getX() + 142, uiFightButton.getY());
        uiItemsButton.addEventListener(this);
        this.add(uiItemsButton);
        uiItemsButton.setVisible(false);

        uiMercyButton.setBounds(uiItemsButton.getX() + 142, uiFightButton.getY());
        uiMercyButton.addEventListener(this);
        this.add(uiMercyButton);
        uiMercyButton.setVisible(false);

        playerNameLabel.setBounds(82, 361, 140, 20);
        playerNameLabel.setFont(determinationSansFont.deriveFont(22f));
        playerNameLabel.setForeground(Color.WHITE);
        playerNameLabel.setOpaque(false);
        this.add(playerNameLabel);
        playerNameLabel.setVisible(false);

        levelLabel.setBounds(82, 361, 140, 20);
        levelLabel.setFont(determinationSansFont.deriveFont(22f));
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setOpaque(false);
        this.add(levelLabel);
        levelLabel.setVisible(false);

        this.add(bulletBoard);
        bulletBoard.setVisible(false);

        bulletBoardTextArea.setBounds(uiFightButton.getX(), 215, uiMercyButton.getX() + 70, 140);
        bulletBoardTextArea.setBorder(new RectangleBorder(Color.WHITE, new Insets(4, 4, 4, 4), new Insets(15, 15, 15, 15)));
        bulletBoardTextArea.setEditable(false);
        bulletBoardTextArea.setForeground(Color.WHITE);
        bulletBoardTextArea.setFont(determinationSansFont.deriveFont(20f));
        bulletBoardTextArea.setOpaque(false);
        bulletBoardTextArea.setText("*  Tu vas passer un sale quart d'heure..." + System.getProperty("line.separator") + "     Ou pas ?");
        this.add(bulletBoardTextArea);
        bulletBoardTextArea.setVisible(false);

        hpBar.setBounds(267, 360, 130, 17);
        this.add(hpBar);
        hpBar.setVisible(false);

        krBar.setBounds(hpBar.getX(), hpBar.getY(), 130, 17);
        this.add(krBar);
        krBar.setVisible(false);

        hpLabel.setBounds(370, 361, 130, 20);
        hpLabel.setFont(determinationSansFont.deriveFont(22f));
        hpLabel.setForeground(Color.WHITE);
        hpLabel.setOpaque(false);
        this.add(hpLabel);
        hpLabel.setVisible(false);

        hpIcon.setBounds(330, 361, 23, 10);
        this.add(hpIcon);
        hpIcon.setVisible(false);

        krIcon.setBounds(330, hpIcon.getY(), 23, 10);
        this.add(krIcon);
        krIcon.setVisible(false);

        sansSpeechTextArea.setBounds(373, 88, 230, 100);
        sansSpeechTextArea.setFont(undertaleSansFont.deriveFont(18f));
        sansSpeechTextArea.setForeground(Color.BLACK);
        sansSpeechTextArea.setOpaque(false);
        this.add(sansSpeechTextArea);
        sansSpeechTextArea.setVisible(false);

        sansSpeechBubble.setBounds(340, 77, 237, 104);
        this.add(sansSpeechBubble);
        sansSpeechBubble.setVisible(false);

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

            playerNameLabel.setText(heart.getPlayerName().toUpperCase());
            playerNameLabel.setVisible(true);
            levelLabel.setText("LV " + heart.getPlayerLevel());
            levelLabel.setVisible(true);
            krBar.setVisible(true);
            krBar.setValue(heart.getKr());
            krBar.setMaximum(heart.getMaxHp());
            hpBar.setVisible(true);
            hpBar.setValue(heart.getHp());
            hpBar.setMaximum(heart.getMaxHp());
            hpLabel.setVisible(true);
            hpLabel.setText(heart.getHp() + "/" + heart.getMaxHp());
            hpIcon.setVisible(true);
            krIcon.setVisible(true);

            bulletBoardTextArea.setVisible(true);
            bulletBoard.setVisible(true);

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

    public static Font determinationSansFont;
    public static Font determinationMonoFont;
    public static Font hachicroFont;
    public static Font undertaleSansFont;

    private static final String FONT_PATH_DETERMINATIONSANS = "assets/sansfight/Fonts/DTM-Sans.ttf";
    private static final String FONT_PATH_DETERMINATIONMONO = "assets/sansfight/Fonts/DTM-Mono.ttf";
    private static final String FONT_PATH_HACHICRO = "assets/sansfight/Fonts/hachicro.TTF";
    private static final String FONT_PATH_UNDERTALESANS = "assets/sansfight/Fonts/UndertaleSans.ttf";

    public static void initFonts() {
        determinationSansFont = CustomFont(FONT_PATH_DETERMINATIONSANS);
        determinationMonoFont = CustomFont(FONT_PATH_DETERMINATIONMONO);
        hachicroFont = CustomFont(FONT_PATH_HACHICRO);
        undertaleSansFont = CustomFont(FONT_PATH_UNDERTALESANS);

    }

    @Override
    public void onEvent(SwingerEvent e) {
        if (e.getSource() == uiFightButton) {
            Thread t = new Thread(() -> {
                Animations.sansHead(Animations.SANS_HEAD.WINK);
                Animations.sansBody(Animations.SANS_BODY.HAND_UP, false);
                heart.damage(5);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                sansSweat.setVisible(true);
                sansSweat.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansSweat/Sweat1/000.png")));
                Animations.sansBody(Animations.SANS_BODY.HAND_DOWN, false);
                heart.damage(5);
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
                Animations.sansHead(Animations.SANS_HEAD.BLUE_EYE);
                Animations.sansBody(Animations.SANS_BODY.HAND_RIGHT, false);
                heart.krDamage(heart.getHp(), 10);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                Animations.sansBody(Animations.SANS_BODY.HAND_LEFT, false);
                heart.krDamage(heart.getHp(), 10);
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
            heart.heal(50, "Your mother");
            Animations.sansHead(Animations.SANS_HEAD.TIRED1);
        } else if (e.getSource() == uiMercyButton) {
            sansDialogue("I'm the baaaaad tiiime... Da tulu tululululu");
            Animations.sansHead(Animations.SANS_HEAD.LOOK_LEFT);
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
