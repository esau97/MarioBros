package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Enums;

public class BrickCoins {
    public float top;
    public float bottom;
    public float left;
    public float right;
    public Enums.CoinState coinState;

    public BrickCoins(float left, float top, float width, float height) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
        coinState = Enums.CoinState.SHOW;
    }

    public void render(SpriteBatch batch) {
        float width = right - left;
        float height = top - bottom;
        if (coinState == Enums.CoinState.SHOW){
            Assets.instance.brickCoinsAssets.brickCoinsNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);
        }else {
            Assets.instance.brickCoinsAssets.brickCoinsTKNinePatch.draw(batch, left - 1, bottom - 1, width + 2, height + 2);
        }

    }
}
