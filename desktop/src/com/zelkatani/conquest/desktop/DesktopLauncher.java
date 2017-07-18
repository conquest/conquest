package com.zelkatani.conquest.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zelkatani.conquest.Conquest;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.addIcon("icons/conquest-128.png", Files.FileType.Internal);
        config.addIcon("icons/conquest-32.png", Files.FileType.Internal);
        config.addIcon("icons/conquest-16.png", Files.FileType.Internal);

        config.resizable = false;
        config.width = 1280;
        config.height = 720;

        new LwjglApplication(new Conquest(), config);
    }
}
