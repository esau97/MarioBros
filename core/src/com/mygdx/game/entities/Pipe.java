package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Level;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

public class Pipe {
    public float top;
    float bottom;
    float left;
    float right;
    Level level;
    ShapeRenderer shapeRenderer;

    public Pipe(float left, float top, float width, float height,Level level) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
        this.level=level;
        shapeRenderer= new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        float width = right - left;
        float height = top - bottom;
        Assets.instance.pipeAssets.pipeNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);
    }
}
