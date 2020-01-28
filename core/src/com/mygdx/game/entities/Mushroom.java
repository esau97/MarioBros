package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Utils;

public class Mushroom {
    public final Vector2 position;


    public Mushroom(Vector2 position) {
        this.position = position;
    }

    public void render(SpriteBatch batch) {

        Utils.drawTextureRegion(
                batch,
                Assets.instance.mushroomAssets.mushroom,
                position,
                Constants.MUSHROOM_CENTER
        );
    }
}
