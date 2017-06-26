package com.zelkatani.conquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.zelkatani.conquest.entities.Tile;

public class Match implements Disposable {
    private Camera cam;
    private Stage stage;

    private Array<Tile> tileArray;

    public Match(Array<Tile> tileArray) {
        cam = new Camera();
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam));

        this.tileArray = tileArray;
        for (Tile t : this.tileArray) {
            stage.addActor(t);
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (Tile t : tileArray) {
                    t.update();
                }
            }
        }, 10.5f, 10);
    }

    public void draw() {
        cam.update();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        for (Tile t : tileArray) {
            t.dispose();
        }
    }
}
