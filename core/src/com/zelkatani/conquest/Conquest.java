package com.zelkatani.conquest;

import com.badlogic.gdx.Game;
import com.zelkatani.conquest.screens.MainMenu;

public class Conquest extends Game {
    MainMenu menu;

    @Override
    public void create() {
        menu = new MainMenu();
        setScreen(menu);
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.SKIN.dispose();
        Assets.ATLAS.dispose();
        Assets.FOGGY.dispose();
        Assets.LOGO.dispose();

        menu.dispose();
    }
}
