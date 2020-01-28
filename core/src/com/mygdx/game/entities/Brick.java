package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Level;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

public class Brick {
    public float top;
    float bottom;
    float left;
    float right;
    ShapeRenderer shapeRenderer;
    Level level;

    public Brick(float left, float top, float width, float height,Level level) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
        this.shapeRenderer = new ShapeRenderer();
        this.level=level;
    }

    public void render(SpriteBatch batch) {
        float width = right - left;
        float height = top - bottom;

        Assets.instance.brickAssets.brickNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);
    }
}
