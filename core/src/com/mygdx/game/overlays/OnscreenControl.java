package com.mygdx.game.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Mario;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Utils;

public class OnscreenControl extends InputAdapter {
    public static final String TAG = OnscreenControl.class.getName();

    public final Viewport viewport;
    public Mario mario;
    private Vector2 moveLeftCenter;
    private Vector2 moveRightCenter;
    private Vector2 jumpCenter;
    private int moveLeftPointer;
    private int moveRightPointer;
    private int jumpPointer;

    public OnscreenControl() {
        this.viewport = new ExtendViewport(
                Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE,
                Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE);


        moveLeftCenter = new Vector2();
        moveRightCenter = new Vector2();
        jumpCenter = new Vector2();

        recalculateButtonPositions();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        if (viewportPosition.dst(jumpCenter) < Constants.BUTTON_RADIUS) {

            jumpPointer = pointer;
            mario.jumpButtonPressed = true;

        } else if (viewportPosition.dst(moveLeftCenter) < Constants.BUTTON_RADIUS) {

            moveLeftPointer = pointer;
            mario.leftButtonPressed = true;

        } else if (viewportPosition.dst(moveRightCenter) < Constants.BUTTON_RADIUS) {

            moveRightPointer = pointer;
            mario.rightButtonPressed = true;

        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    public void render(SpriteBatch batch) {

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        if (!Gdx.input.isTouched(jumpPointer)) {
            mario.jumpButtonPressed = false;
            jumpPointer = 0;
        }

        if (!Gdx.input.isTouched(moveLeftPointer)) {
            mario.leftButtonPressed = false;
            moveLeftPointer = 0;
        }

        if (!Gdx.input.isTouched(moveRightPointer)) {
            mario.rightButtonPressed = false;
            moveRightPointer = 0;
        }

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onscreenControlsAssets.moveLeft,
                moveLeftCenter,
                Constants.BUTTON_CENTER
        );

        Utils.drawTextureRegion(
                batch,
                Assets.instance.onscreenControlsAssets.moveRight,
                moveRightCenter,
                Constants.BUTTON_CENTER
        );


        Utils.drawTextureRegion(
                batch,
                Assets.instance.onscreenControlsAssets.jump,
                jumpCenter,
                Constants.BUTTON_CENTER
        );
    }

    public void recalculateButtonPositions() {
        moveLeftCenter.set(Constants.BUTTON_RADIUS * 3 / 4, Constants.BUTTON_RADIUS);
        moveRightCenter.set(Constants.BUTTON_RADIUS * 2 + 20, Constants.BUTTON_RADIUS * 3 / 4);
        jumpCenter.set(
                viewport.getWorldWidth() - Constants.BUTTON_RADIUS * 3 / 4 -20,
                Constants.BUTTON_RADIUS
        );
    }
}