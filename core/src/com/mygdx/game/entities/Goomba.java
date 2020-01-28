package com.mygdx.game.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Level;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;

import com.mygdx.game.util.Enums.Direction;

public class Goomba {
    private final PlatformGround platform;

    public Direction direction;
    Vector2 lastFramePosition;
    public Vector2 position;
    ShapeRenderer shapeRenderer;
    Level level;
    final long startTime;
    Mario mario;


    public Goomba(PlatformGround platform,Level level) {
        this.platform = platform;
        this.level = level;
        shapeRenderer = new ShapeRenderer();
        lastFramePosition = new Vector2();
        position = new Vector2(platform.left, platform.top + Constants.GOOMBA_CENTER.y);
        direction=Direction.RIGHT;
        startTime = TimeUtils.nanoTime();
    }

    public void update(float delta,Mario mario) {
        this.mario=mario;
        switch (direction) {
            case LEFT:
                position.x -= Constants.GOOMBA_MOVEMENT_SPEED * delta;
                break;
            case RIGHT:
                position.x += Constants.GOOMBA_MOVEMENT_SPEED * delta;
        }
        if (position.x-16< platform.left) {
            position.x = platform.left+16;
            direction = Direction.RIGHT;
        } else if (position.x +16> platform.right) {
            position.x = platform.right-16;
            direction = Direction.LEFT;
        }

        
    }
    boolean landedOnGoomba() {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (mario.lastFramePosition.y - Constants.MARIO_EYE_HEIGHT >= position.y &&
                position.y - Constants.MARIO_EYE_HEIGHT < position.y ) {

            float leftFoot = mario.position.x - Constants.MARIO_STANCE_WIDTH / 2;
            float rightFoot = mario.position.x + Constants.MARIO_STANCE_WIDTH / 2;

            leftFootIn = (position.x < leftFoot && position.x+Constants.GOOMBA_STANCE_WIDTH > leftFoot);
            rightFootIn = (position.x < rightFoot && position.x+Constants.GOOMBA_STANCE_WIDTH > rightFoot);

            straddle = (position.x > leftFoot && position.x+Constants.GOOMBA_STANCE_WIDTH < rightFoot);
        }

        return leftFootIn || rightFootIn || straddle;
    }

    public void render(SpriteBatch batch) {
        float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
        TextureRegion region = Assets.instance.goombaAssets.goomba1;
        region = (TextureRegion) Assets.instance.goombaAssets.walkingGoombaAnimation.getKeyFrame(walkTimeSeconds);
        batch.draw(
                region.getTexture(),
                position.x- Constants.GOOMBA_CENTER.x,
                position.y - Constants.GOOMBA_CENTER.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }
}
