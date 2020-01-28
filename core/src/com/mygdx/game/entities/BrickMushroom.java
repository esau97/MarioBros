package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Level;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Enums;

public class BrickMushroom {
    public float top;
    public float bottom;
    public float left;
    public float right;
    public Enums.MushState mushState;
    ShapeRenderer shapeRenderer;
    Level level;

    public BrickMushroom(float left, float top, float width, float height, Level level) {
        this.top = top;
        this.level = level;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
        shapeRenderer = new ShapeRenderer();
        mushState = Enums.MushState.SHOW;
    }

    public void render(SpriteBatch batch) {
        float width = right - left;
        float height = top - bottom;
        if (mushState == Enums.MushState.SHOW){
            Assets.instance.brickMushroomAssets.brickMushroomNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);
        }else {
            Assets.instance.brickMushroomAssets.brickMushroomTKNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);
        }
    }
}
