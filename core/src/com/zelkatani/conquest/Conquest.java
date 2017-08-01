package com.zelkatani.conquest;

import com.badlogic.gdx.Game;
import com.zelkatani.conquest.multiplayer.Client;

public class Conquest extends Game {
    private Player player;
    private Client client;

    @Override
    public void create() {
        player = new Player(Assets.random());
        client = new Client(this, player);
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.SKIN.dispose();
        Assets.ATLAS.dispose();
        Assets.FOGGY.dispose();
    }
}
