package fr.timeto.sansfight;

import fr.theshark34.swinger.Swinger;
import org.gagravarr.ogg.OggFile;
import org.gagravarr.ogg.OggPacket;
import org.gagravarr.ogg.OggStreamAudioData;
import org.gagravarr.ogg.OggStreamReader;
import org.gagravarr.ogg.tools.OggInfoTool;

import javax.sound.sampled.*;
import javax.swing.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static fr.timeto.sansfight.Game.*;

public class Animations {

    public static class SANS_BODY {
        public static final String HAND_UP = "HandUp";
        public static final String HAND_DOWN = "HandDown";
        public static final String HAND_RIGHT = "HandRight";
        public static final String HAND_LEFT = "HandLeft";

    }

    public static class SANS_HEAD {
        public static final String BLUE_EYE = "BlueEye";
        public static final String CLOSED_EYES = "ClosedEyes";
    }

    public static void returnSansComponentsToOriginalPlace() {
        sansSweat.setBounds(290, 99, 32, 9);
        sansHead.setBounds(290, 99, 32, 30);
        sansBody.setBounds(276, 100, 64, 70);
        sansTorso.setBounds(279, 123, 54, 25);
        sansLegs.setBounds(285, 148, 44, 23);
        sansBody.setVisible(false);
        sansSweat.setVisible(false);

    }

    static OggFile sound;

    static void soundTest() {
        File soundFile = new File(Animations.class.getResourceAsStream("/assets/sansfight/Files/BattleText.ogg").toString());
        try {
            sound = new OggFile(new FileInputStream(soundFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        OggStreamAudioData audioData;
        try {
            audioData = new OggStreamAudioData(sound.getPacketReader().getNextPacket());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            // Open an audio input stream.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void sansBody(String bodyAnim, boolean withComponentsReturn) {
        Thread t = new Thread(() -> {
            // soundTest();
            if (bodyAnim == SANS_BODY.HAND_UP) {
                sansBody.setBounds(276, 101, 64, 70);
                sansBody.setVisible(true);
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/000.png")));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/001.png")));
                sansHead.setBounds(290, 100, 32, 30);
                sansSweat.setBounds(290, 100, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/002.png")));
                sansHead.setBounds(290, 98, 32, 30);
                sansSweat.setBounds(290, 98, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/003.png")));
                sansHead.setBounds(290, 97, 32, 30);
                sansSweat.setBounds(290, 97, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/004.png")));
                sansHead.setBounds(290, 98, 32, 30);
                sansSweat.setBounds(290, 98, 32, 9);
            }

            else if (bodyAnim == SANS_BODY.HAND_DOWN) {
                sansBody.setBounds(276, 101, 64, 70);
                sansBody.setVisible(true);
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/000.png")));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/001.png")));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/002.png")));
                sansHead.setBounds(290, 100, 32, 30);
                sansSweat.setBounds(290, 100, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/003.png")));
                sansHead.setBounds(290, 101, 32, 30);
                sansSweat.setBounds(290, 101, 32, 9);
            }

            else if (bodyAnim == SANS_BODY.HAND_LEFT) {
                sansBody.setBounds(273, 123, 96, 48);
                sansBody.setVisible(true);
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/000.png")));
                sansHead.setBounds(291, 99, 32, 30);
                sansSweat.setBounds(291, 99, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/001.png")));
                sansHead.setBounds(288, 99, 32, 30);
                sansSweat.setBounds(288, 99, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/002.png")));
                sansHead.setBounds(286, 99, 32, 30);
                sansSweat.setBounds(286, 99, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/003.png")));
                sansHead.setBounds(289, 99, 32, 30);
                sansSweat.setBounds(289, 99, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/004.png")));
                sansHead.setBounds(290, 99, 32, 30);
                sansSweat.setBounds(290, 99, 32, 9);
            }

            else if (bodyAnim == SANS_BODY.HAND_RIGHT) {
                sansBody.setBounds(273, 123, 96, 48);
                sansBody.setVisible(true);
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/000.png")));
                sansHead.setBounds(290, 99, 32, 30);
                sansSweat.setBounds(290, 99, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/001.png")));
                sansHead.setBounds(289, 99, 32, 30);
                sansSweat.setBounds(289, 99, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/002.png")));
                sansHead.setBounds(286, 99, 32, 30);
                sansSweat.setBounds(286, 99, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/003.png")));
                sansHead.setBounds(288, 99, 32, 30);
                sansSweat.setBounds(288, 99, 32, 9);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sansBody.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/sansfight/Animations/SansBody/" + bodyAnim + "/004.png")));
                sansHead.setBounds(291, 99, 32, 30);
                sansSweat.setBounds(291, 99, 32, 9);
            }
            if (withComponentsReturn) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                returnSansComponentsToOriginalPlace();
            }
        });
        t.start();

    }

    public static void sansHead(String headAnim) {

    }
}
