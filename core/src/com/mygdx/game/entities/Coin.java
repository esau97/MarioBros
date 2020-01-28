package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Utils;

public class Coin {
    public final Vector2 position;
    boolean active;
    public float top;
    public float bottom;
    public float left;
    public float right;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private final long startTime;

    public Coin(Vector2 position) {
        this.position = position;
        startTime = TimeUtils.nanoTime();
        active =false;
    }

    public void render(SpriteBatch batch) {

        Utils.drawTextureRegion(
                batch,
                Assets.instance.coinAssets.coin,
                position,
                Constants.COIN_CENTER
        );
    }

}
