package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Utils;

public class Flag {
    public final Vector2 position;

    public Flag(Vector2 position) {
        this.position = position;
    }

    public void render(SpriteBatch batch) {
        Utils.drawTextureRegion(
                batch,
                Assets.instance.flagAssets.flag,
                position,
                Constants.FLAG_CENTER
        );
    }
}
