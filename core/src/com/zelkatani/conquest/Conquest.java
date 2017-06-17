package com.zelkatani.conquest;

import com.badlogic.gdx.Game;
import com.zelkatani.conquest.screens.MatchScreen;

public class Conquest extends Game {

    Match match;

    @Override
    public void create() {
        match = new Match();

        setScreen(new MatchScreen(match));
    }
}
