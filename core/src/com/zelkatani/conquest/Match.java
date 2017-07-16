package com.zelkatani.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.zelkatani.conquest.entities.Tile;

public class Match implements Disposable {
    private ConquestCamera cam;
    private Stage stage;

    private Array<Tile> tiles;

    public Match(Array<Tile> tiles) {
        cam = new ConquestCamera(tiles);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam));

        this.tiles = tiles;

        Group tileGroup = new Group();
        Group labelGroup = new Group();
        for (Tile t : this.tiles) {
            tileGroup.addActor(t);
            labelGroup.addActor(t.getLabel());
        }

        stage.addActor(tileGroup);
        stage.addActor(labelGroup);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (Tile t : tiles) {
                    t.update();
                }
            }
        }, 10.5f, 10);
    }

    public void draw() {
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        for (Tile t : tiles) {
            t.dispose();
        }
    }

    public Array<Tile> getTiles() {
        return tiles;
    }

    public Stage getStage() {
        return stage;
    }

    public ConquestCamera getCam() {
        return cam;
    }
}
