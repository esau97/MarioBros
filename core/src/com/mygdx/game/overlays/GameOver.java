package com.mygdx.game.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.util.Constants;

public class GameOver {

    public final static String TAG = GameOver.class.getName();
    public final Viewport viewport;
    final BitmapFont font;

    public GameOver(Viewport viewport) {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        font = new BitmapFont(Gdx.files.internal(Constants.FONT_FILE));
        font.getData().setScale(1);
    }



    public void render(SpriteBatch batch) {

        viewport.apply();
        Gdx.gl.glClearColor(
                Constants.GAME_OVER_COLOR.r,
                Constants.GAME_OVER_COLOR.g,
                Constants.GAME_OVER_COLOR.b,
                Constants.GAME_OVER_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }
}

