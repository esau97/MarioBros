package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.util.Assets;

public class PlatformGround {
    public float top;
    float bottom;
    float left;
    float right;

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public PlatformGround(float left, float top, float width, float height) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
    }

    public void render(SpriteBatch batch) {
        float width = right - left;
        float height = top - bottom;
        Assets.instance.platformAssets.platformNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);
    }
}
