package fr.timeto.sansfight;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import static fr.timeto.sansfight.Game.frame;
import static fr.timeto.sansfight.Game.heart;

public class GlobalKeyListener implements NativeKeyListener {
    public void nativeKeyPressed(NativeKeyEvent e) {
    //    System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (!frame.isActive()) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_UP) {
            heart.move(Heart.HEART_DIRECTION.NORTH_DIRECTION);
        } else if (e.getKeyCode() == NativeKeyEvent.VC_DOWN) {
            heart.move(Heart.HEART_DIRECTION.SOUTH_DIRECTION);
        } else if (e.getKeyCode() == NativeKeyEvent.VC_RIGHT) {
            heart.move(Heart.HEART_DIRECTION.EAST_DIRECTION);
        } else if (e.getKeyCode() == NativeKeyEvent.VC_LEFT) {
            heart.move(Heart.HEART_DIRECTION.WEST_DIRECTION);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    //    System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    //    System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public static void launchKeyListener() {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }
}