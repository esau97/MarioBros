package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.util.Constants;


import static com.mygdx.game.util.Constants.DIFFICULTY_WORLD_SIZE;
import static com.mygdx.game.util.Constants.EASY_CENTER;
import static com.mygdx.game.util.Constants.HARD_CENTER;
import static com.mygdx.game.util.Constants.LEVEL_EASY;
import static com.mygdx.game.util.Constants.MEDIUM_CENTER;
import static com.mygdx.game.util.Constants.WORLD_SIZE;

public class InitialScreen extends InputAdapter implements Screen {

    public static final String TAG = InitialScreen.class.getName();

    MarioBrosGame game;
    ShapeRenderer renderer;
    SpriteBatch batch;

    ExtendViewport viewport;
    Vector2 worldTouch;

    BitmapFont font;
    Texture initialLogo;
    Texture level_easy;
    Texture level_medium;
    Texture level_hard;

    public InitialScreen(MarioBrosGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();


        viewport = new ExtendViewport(DIFFICULTY_WORLD_SIZE,DIFFICULTY_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);
        initialLogo = new Texture("inicio.jpg");
        level_easy = new Texture(Constants.LEVEL_EASY);
        level_medium = new Texture(Constants.LEVEL_MEDIUM);
        level_hard = new Texture(Constants.LEVEL_HARD);
        worldTouch = new Vector2(0,0);
        font = new BitmapFont();
        font.getData().setScale(Constants.DIFFICULTY_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        batch.draw(initialLogo,0,0,viewport.getWorldWidth(),viewport.getWorldHeight());
        batch.draw(level_easy,Constants.EASY_CENTER.x-Constants.MUSH_WIDTH, Constants.EASY_CENTER.y-Constants.MUSH_WIDTH, 75,75);
        batch.draw(level_medium,MEDIUM_CENTER.x-Constants.MUSH_WIDTH,MEDIUM_CENTER.y-Constants.MUSH_WIDTH,75,75);
        batch.draw(level_hard,HARD_CENTER.x-Constants.MUSH_WIDTH,HARD_CENTER.y-Constants.MUSH_WIDTH,75,75);

        batch.end();


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
        renderer.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (worldTouch.dst(Constants.EASY_CENTER) < Constants.DIFFICULTY_BUBBLE_RADIUS) {
            game.showGameplayScreen(Constants.Difficulty.EASY);
        }

        if (worldTouch.dst(Constants.MEDIUM_CENTER) < 480/9) {
            game.showGameplayScreen(Constants.Difficulty.MEDIUM);
        }

        if (worldTouch.dst(Constants.HARD_CENTER) < 75/2) {
            game.showGameplayScreen(Constants.Difficulty.HARD);
        }

        return true;
    }
}
