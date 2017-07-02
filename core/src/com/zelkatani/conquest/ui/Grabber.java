package com.zelkatani.conquest.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.zelkatani.conquest.entities.Tile;

public class Grabber extends InputAdapter {
    private Array<Tile> tiles;
    private Rectangle rect;
    private Camera cam;

    private float touchX, touchY, mouseX, mouseY;

    public Grabber(Array<Tile> tiles, Camera cam) {
        this.tiles = tiles;
        rect = new Rectangle();
        this.cam = cam;
    }

    public void draw(ShapeRenderer renderer) {
        if (rect.width < 25 || rect.height < 25) return;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.setProjectionMatrix(cam.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(new Color(0x228b2255));
        renderer.rect(rect.x, rect.y, rect.width, rect.height);

        renderer.set(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.BLACK);
        renderer.rect(rect.x, rect.y, rect.width, rect.height);
        renderer.end();
    }

    public void grab() {
        float width = touchX - mouseX, height = mouseY - touchY;
        float offX = width < 0 ? width : 0, offY = height < 0 ? height : 0;

        Vector3 vector3 = new Vector3(mouseX,  mouseY, 0);
        cam.unproject(vector3);

        rect.set(vector3.x + offX, vector3.y + offY, Math.abs(width), Math.abs(height));

        if (rect.width < 25 || rect.height < 25) return;
        for (Tile tile : tiles) {
            tile.setHovered(rect.contains(tile.getCenter()));
        }
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        touchX = screenX;
        touchY = screenY;

        mouseX = screenX;
        mouseY = screenY;

        for (Tile tile : tiles) {
            tile.setHovered(false);
            tile.setSelected(false);
        }
        return false;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        mouseX = screenX;
        mouseY = screenY;

        touchX = screenX;
        touchY = screenY;

        for (Tile tile : tiles) {
            tile.setSelected(rect.contains(tile.getCenter()));
        }
        return false;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        mouseX = screenX;
        mouseY = screenY;

        for (Tile tile : tiles) {
            tile.setHovered(rect.contains(tile.getCenter()));
        }
        return false;
    }
}
