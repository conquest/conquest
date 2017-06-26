package com.zelkatani.conquest;

import com.badlogic.gdx.Game;
import com.zelkatani.conquest.screens.MatchScreen;

public class Conquest extends Game {
    private Match match;
    private Level level;

    @Override
    public void create() {
        level = new Level();
        match = new Match(level.getTileArray());

        setScreen(new MatchScreen(match));
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.BITMAP_FONT.dispose();
    }
}
