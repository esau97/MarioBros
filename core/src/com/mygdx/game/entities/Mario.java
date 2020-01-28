package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Level;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Enums.WalkState;
import com.mygdx.game.util.Enums.JumpState;
import com.mygdx.game.util.Enums.Facing;
import com.mygdx.game.util.Enums;





public class Mario {
    public final static String TAG = Mario.class.getName();

    public Vector2 position;
    public Vector2 velocity;
    Vector2 spawnLocation;
    JumpState jumpState;
    long jumpStartTime;
    public Music powerup;
    Music breakBrick;
    Music bump;
    Music coinMusic;
    Music jumpMusic;
    Music die;
    Music squishMusic;
    Music warp;
    long walkStartTime;
    WalkState walkState;
    Vector2 lastFramePosition;
    ShapeRenderer shapeRenderer;
    public float tiempo;
    public int lives;
    public int score;
    public boolean jumpButtonPressed;
    public boolean leftButtonPressed;
    public boolean rightButtonPressed;

    Enums.MarioState marioState;

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    Facing facing;
    public Level level;

    public Mario(Vector2 spawnLocation) {
        this.spawnLocation=spawnLocation;
        position = new Vector2();
        lastFramePosition = new Vector2();
        velocity = new Vector2();
        init();

    }
    public Mario(Vector2 spawnLocation, Level level) {
        this.spawnLocation=spawnLocation;
        position = new Vector2();
        this.level=level;
        lastFramePosition = new Vector2();
        lives=Constants.INITIAL_LIVES;
        velocity = new Vector2();

        init();

    }

    public void init(){
        powerup = Gdx.audio.newMusic(Gdx.files.internal("music/Powerup.wav"));
        breakBrick = Gdx.audio.newMusic(Gdx.files.internal("music/Break.wav"));
        bump = Gdx.audio.newMusic(Gdx.files.internal("music/Bump.wav"));
        coinMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Coin.wav"));
        jumpMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Jump.wav"));
        squishMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Squish.wav"));
        warp = Gdx.audio.newMusic(Gdx.files.internal("music/Warp.wav"));

        if(marioState == marioState.DEATH){
            lives = lives-1;
            marioState = marioState.MARIO;
        }
        shapeRenderer = new ShapeRenderer();

        position.set(spawnLocation);

        lastFramePosition.set(spawnLocation);

        velocity.setZero();

        score = 0;

        jumpState = JumpState.FALLING;

        marioState = marioState.MARIO;

        facing = Facing.RIGHT;

        walkState = WalkState.STANDING;
    }

    public void update(float delta, Array<PlatformGround> platforms, Array<Brick> bricks, Array<BrickCoins> bricksCoins, Array<Pipe> pipes, Array <BrickMushroom> brickMushrooms){
        lastFramePosition.set(position);
        velocity.y -= delta * Constants.GRAVITY;
        tiempo +=delta;
        position.mulAdd(velocity, delta);
        float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
        TextureRegion region = Assets.instance.marioAssets.standingRight;
        if (marioState== Enums.MarioState.SUPERMARIO){
            region = Assets.instance.superMarioAssets.standingRight;
        }else {
            region = Assets.instance.marioAssets.standingRight;
        }

        Rectangle marioRectangle = new Rectangle(
                position.x- Constants.MARIO_EYE_POSITION.x,
                position.y - Constants.MARIO_EYE_POSITION.y,
                region.getRegionWidth(),
                region.getRegionHeight());
        if (marioState == marioState.DEATH){
            init();
        }

        if (position.y < Constants.KILL_PLANE){
            init();
            lives--;
        }
        if (jumpState != JumpState.JUMPING && marioState!=marioState.DEATH) {
            jumpState = JumpState.FALLING;


            for (PlatformGround platformGround : platforms) {
                if (landedOnPlatform(platformGround)) {
                    jumpMusic.stop();
                    jumpState = JumpState.GROUNDED;
                    velocity.y = 0;
                    position.y = platformGround.top +Constants.MARIO_EYE_HEIGHT;
                }
            }
            for (BrickMushroom brickMushroom: brickMushrooms){
                Rectangle brickMushroomBounds = new Rectangle(
                        brickMushroom.left ,
                        brickMushroom.top,
                        brickMushroom.right-brickMushroom.left,
                        brickMushroom.top-brickMushroom.bottom
                );
                if(landedOnBrickMushroom(brickMushroom)){
                    jumpState = jumpState.GROUNDED;
                    velocity.y=0;
                    position.y = brickMushroom.top + Constants.MARIO_EYE_HEIGHT;
                }


                if (marioRectangle.overlaps(brickMushroomBounds)) {
                    if (position.x < brickMushroom.left && position.y < brickMushroom.top) {
                        position.x= brickMushroom.left-Constants.MARIO_STANCE_WIDTH;

                    } else if (position.x > brickMushroom.right && position.y < brickMushroom.top){
                        position.x= brickMushroom.right+Constants.MARIO_STANCE_WIDTH;

                    }if(position.y + Constants.MARIO_EYE_HEIGHT> brickMushroom.bottom && position.y< brickMushroom.top
                            && position.x>brickMushroom.left && position.x<brickMushroom.right && velocity.y!=0){
                        jumpState = jumpState.FALLING;
                        velocity.y=0;
                        position.y = brickMushroom.bottom - Constants.MARIO_EYE_HEIGHT;
                        if(brickMushroom.mushState == Enums.MushState.SHOW){
                            level.spawnMush(new Vector2(brickMushroom.left+16,brickMushroom.top+25));
                            brickMushroom.mushState= Enums.MushState.HIDE;
                        }

                    }
                }
            }
            for (BrickCoins brickCoins : bricksCoins){
                Rectangle brickCoinsBounds = new Rectangle(
                        brickCoins.left ,
                        brickCoins.top,
                        brickCoins.right-brickCoins.left,
                        brickCoins.top-brickCoins.bottom
                );
                if(landedOnBrickCoins(brickCoins)){

                    jumpState = jumpState.GROUNDED;

                    velocity.y=0;
                    position.y = brickCoins.top + Constants.MARIO_EYE_HEIGHT;
                }

                if (marioRectangle.overlaps(brickCoinsBounds)) {
                    if (position.x < brickCoins.left && position.y<brickCoins.top) {
                        position.x= brickCoins.left-Constants.MARIO_STANCE_WIDTH;
                    } else if (position.x > brickCoins.right && position.y<brickCoins.top){
                        position.x= brickCoins.right+Constants.MARIO_STANCE_WIDTH;
                    }if(position.y + region.getRegionHeight()> brickCoins.bottom && position.y< brickCoins.top
                            && position.x>brickCoins.left && position.x<brickCoins.right && velocity.y!=0){
                        if(brickCoins.coinState == Enums.CoinState.SHOW && velocity.y>0){
                            level.spawnCoin(new Vector2(brickCoins.left+20,brickCoins.top+32));
                            brickCoins.coinState= Enums.CoinState.HIDE;

                        }
                        bump.play();
                        jumpState = jumpState.FALLING;
                        velocity.y=0;
                        position.y = brickCoins.bottom - region.getRegionHeight();
                    }
                }
            }
            for (Pipe pipe : pipes){
                if (landedOnPipe(pipe)){
                    jumpState = jumpState.GROUNDED;
                    velocity.y=0;
                    position.y = pipe.top + Constants.MARIO_EYE_HEIGHT;
                }
                if(position.x+ Constants.MARIO_STANCE_WIDTH >pipe.left && position.x<pipe.right && facing==Facing.RIGHT && position.y<pipe.top){
                    position.x=pipe.left-Constants.MARIO_STANCE_WIDTH;
                }else if(position.x>pipe.left && position.x-Constants.MARIO_STANCE_WIDTH-2<pipe.right && facing==Facing.LEFT && position.y<=pipe.top){
                    position.x=pipe.right+Constants.MARIO_STANCE_WIDTH+2;
                }
            }

            DelayedRemovalArray<Coin> coinsArray = level.getCoinsArray();
            coinsArray.begin();
            for (int i = 0; i < coinsArray.size; i++) {
                Coin c = coinsArray.get(i);
                Rectangle coinsBounds = new Rectangle(
                        c.position.x ,
                        c.position.y,
                        Assets.instance.coinAssets.coin.getRegionWidth(),
                        Assets.instance.coinAssets.coin.getRegionHeight()
                );

                if (marioRectangle.overlaps(coinsBounds)) {
                    coinMusic.play();
                    level.score += Constants.COIN_SCORE;
                    coinsArray.removeIndex(i);
                }
            }
            coinsArray.end();

            DelayedRemovalArray<Brick> bricksArray = level.getBricksArray();
            bricksArray.begin();
            for (int i = 0; i < bricksArray.size; i++) {
                Brick c = bricksArray.get(i);


                if(landedOnBrick(c)){
                    jumpState = jumpState.GROUNDED;
                    velocity.y=0;
                    position.y = c.top + Constants.MARIO_EYE_HEIGHT;
                }else if (position.y + Constants.MARIO_EYE_HEIGHT> c.bottom && position.y< c.top
                        && position.x>c.left && position.x<c.right && velocity.y!=0){
                    if(marioState == Enums.MarioState.SUPERMARIO && velocity.y>0){
                        breakBrick.play();
                        bricksArray.removeIndex(i);
                    }else {
                        bump.play();
                    }
                    jumpState = jumpState.FALLING;
                    velocity.y=0;
                    position.y=c.bottom-Constants.MARIO_EYE_HEIGHT;

               }
            }
            bricksArray.end();

            DelayedRemovalArray<Mushroom> mushArray = level.getMushArray();
            mushArray.begin();
            for (int i = 0; i < mushArray.size; i++) {
                Mushroom c = mushArray.get(i);
                Rectangle mushBounds = new Rectangle(
                        c.position.x ,
                        c.position.y,
                        Assets.instance.mushroomAssets.mushroom.getRegionWidth(),
                        Assets.instance.mushroomAssets.mushroom.getRegionHeight()
                );

                if (marioRectangle.overlaps(mushBounds)) {
                    powerup.play();
                    level.score += Constants.MUSH_SCORE;
                    mushArray.removeIndex(i);
                    marioState = Enums.MarioState.SUPERMARIO;
                }
            }
            mushArray.end();
            DelayedRemovalArray<Goomba> goombaArray = level.getGoombas();
            goombaArray.begin();
            for (int i = 0; i < goombaArray.size; i++) {
                Rectangle enemyBounds = new Rectangle(
                        goombaArray.get(i).position.x - Constants.GOOMBA_CENTER.x,
                        goombaArray.get(i).position.y - Constants.GOOMBA_CENTER.y,
                        Constants.GOOMBA_STANCE_WIDTH,
                        Constants.GOOMBA_EYE_HEIGHT
                );

                if (marioRectangle.overlaps(enemyBounds) && marioState!=marioState.DEATH ) {
                    if(goombaArray.get(i).landedOnGoomba()){
                        squishMusic.play();
                        goombaArray.removeIndex(i);
                        level.score += Constants.GOOMBA_SCORE;
                        break;
                    }
                    if(marioState == Enums.MarioState.MARIO){
                        if (lives > 0) {
                            marioState = Enums.MarioState.DEATH;
                            init();
                        }else{
                            level.gameOver=true;
                        }

                        break;
                    }else if(marioState == Enums.MarioState.SUPERMARIO){
                        if (position.x < goombaArray.get(i).position.x) {
                            warp.play();
                            marioState = Enums.MarioState.MARIO;
                            if(goombaArray.get(i).direction == Enums.Direction.LEFT){
                                position.x = goombaArray.get(i).position.x+Constants.GOOMBA_STANCE_WIDTH*2;
                            }else{
                                position.x = goombaArray.get(i).position.x-50;
                            }
                        }else {
                            marioState = Enums.MarioState.MARIO;
                            if(goombaArray.get(i).direction == Enums.Direction.LEFT){
                                position.x = goombaArray.get(i).position.x+50;
                            }else{
                                position.x = goombaArray.get(i).position.x-Constants.GOOMBA_STANCE_WIDTH*2;
                            }
                        }
                    }

                }
            }
            goombaArray.end();
        }

        if(!level.win){
            if (Gdx.input.isKeyJustPressed(Input.Keys.Z) || jumpButtonPressed) {
                jumpMusic.play();
                switch (jumpState) {
                    case GROUNDED:
                        startJump();
                        break;
                    case JUMPING:
                        continueJump();
                }
                jumpButtonPressed=false;
            } else {
                endJump();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || leftButtonPressed) {
                moveLeft(delta);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || rightButtonPressed) {
                moveRight(delta);
            }else{
                walkState = WalkState.STANDING;
            }
        }

    }
    private void startJump() {
        jumpState = JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();
        continueJump();
    }

    private void continueJump() {
        if (jumpState == JumpState.JUMPING) {
            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);

            if (jumpDuration < Constants.MAX_JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED;
            } else {
                endJump();
            }
        }
    }

    private void endJump() {

        if (jumpState == JumpState.JUMPING) {
            jumpState = JumpState.FALLING;
        }
    }

    private void moveLeft(float delta) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        facing = Facing.LEFT;
        walkState = WalkState.WALKING;
        position.x -= delta * Constants.MARIO_MOVE_SPEED;
    }

    private void moveRight(float delta) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        facing = Facing.RIGHT;
        walkState = WalkState.WALKING;
        position.x += delta * Constants.MARIO_MOVE_SPEED;
    }

    public void render(SpriteBatch batch) {

        TextureRegion region = Assets.instance.marioAssets.standingRight;
        if (marioState== Enums.MarioState.SUPERMARIO){
            region = Assets.instance.superMarioAssets.standingRight;
        }else {
            region = Assets.instance.marioAssets.standingRight;
        }
        boolean flip = false;
        if (marioState== Enums.MarioState.SUPERMARIO){
            if (facing == Facing.RIGHT && jumpState != JumpState.GROUNDED) {
                region = Assets.instance.superMarioAssets.jumpingRight;
                flip = false;
            } else if (facing == Facing.RIGHT && walkState == WalkState.STANDING) {
                region = Assets.instance.superMarioAssets.standingRight;
                flip = false;
            } else if (facing == Facing.RIGHT && walkState == WalkState.WALKING) {

                float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
                flip = false;
                region = (TextureRegion) Assets.instance.superMarioAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
            } else if (facing == Facing.LEFT && jumpState != JumpState.GROUNDED) {
                region = Assets.instance.superMarioAssets.jumpingRight;
                flip = true;
            } else if (facing == Facing.LEFT && walkState == WalkState.STANDING) {
                region = Assets.instance.superMarioAssets.standingRight;
                flip = true;
            } else if (facing == Facing.LEFT && walkState == WalkState.WALKING) {
                flip = true;
                float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);

                region = (TextureRegion) Assets.instance.superMarioAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
            }
        }else{
            if (facing == Facing.RIGHT && jumpState != JumpState.GROUNDED) {
                region = Assets.instance.marioAssets.jumpingRight;
                flip = false;
            } else if (facing == Facing.RIGHT && walkState == WalkState.STANDING) {
                region = Assets.instance.marioAssets.standingRight;
                flip = false;
            } else if (facing == Facing.RIGHT && walkState == WalkState.WALKING) {

                float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
                flip = false;
                region = (TextureRegion) Assets.instance.marioAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
            } else if (facing == Facing.LEFT && jumpState != JumpState.GROUNDED) {
                region = Assets.instance.marioAssets.jumpingRight;
                flip = true;
            } else if (facing == Facing.LEFT && walkState == WalkState.STANDING) {
                region = Assets.instance.marioAssets.standingRight;
                flip = true;
            } else if (facing == Facing.LEFT && walkState == WalkState.WALKING) {
                flip = true;
                float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
                region = (TextureRegion) Assets.instance.marioAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
            }
        }
        batch.draw(
                region.getTexture(),
                position.x- Constants.MARIO_EYE_POSITION.x,
                position.y - Constants.MARIO_EYE_POSITION.y,
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
                flip,
                false);

    }

    boolean landedOnPlatform(PlatformGround platformGround) {

        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.MARIO_EYE_HEIGHT >= platformGround.top &&
                position.y - Constants.MARIO_EYE_HEIGHT < platformGround.top) {

            float leftFoot = position.x - Constants.MARIO_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.MARIO_STANCE_WIDTH / 2;

            leftFootIn = (platformGround.left < leftFoot && platformGround.right > leftFoot);
            rightFootIn = (platformGround.left < rightFoot && platformGround.right > rightFoot);

            straddle = (platformGround.left > leftFoot && platformGround.right < rightFoot);
        }

        return leftFootIn || rightFootIn || straddle;
    }
    boolean landedOnBrick(Brick brick) {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.MARIO_EYE_HEIGHT >= brick.top &&
                position.y - Constants.MARIO_EYE_HEIGHT < brick.top) {

            float leftFoot = position.x - Constants.MARIO_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.MARIO_STANCE_WIDTH / 2;

            leftFootIn = (brick.left < leftFoot && brick.right > leftFoot);
            rightFootIn = (brick.left < rightFoot && brick.right > rightFoot);

            straddle = (brick.left > leftFoot && brick.right < rightFoot);
        }

        return leftFootIn || rightFootIn || straddle;
    }
    boolean landedOnBrickCoins(BrickCoins brickCoins) {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.MARIO_EYE_HEIGHT >= brickCoins.top &&
                position.y - Constants.MARIO_EYE_HEIGHT < brickCoins.top) {

            float leftFoot = position.x - Constants.MARIO_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.MARIO_STANCE_WIDTH / 2;

            leftFootIn = (brickCoins.left < leftFoot && brickCoins.right > leftFoot);
            rightFootIn = (brickCoins.left < rightFoot && brickCoins.right > rightFoot);

            straddle = (brickCoins.left > leftFoot && brickCoins.right < rightFoot);
        }

        return leftFootIn || rightFootIn || straddle;
    }
    boolean landedOnBrickMushroom(BrickMushroom brickMushrooms) {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.MARIO_EYE_HEIGHT >= brickMushrooms.top &&
                position.y - Constants.MARIO_EYE_HEIGHT < brickMushrooms.top) {

            float leftFoot = position.x - Constants.MARIO_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.MARIO_STANCE_WIDTH / 2;

            leftFootIn = (brickMushrooms.left < leftFoot && brickMushrooms.right > leftFoot);
            rightFootIn = (brickMushrooms.left < rightFoot && brickMushrooms.right > rightFoot);

            straddle = (brickMushrooms.left > leftFoot && brickMushrooms.right < rightFoot);
        }

        return leftFootIn || rightFootIn || straddle;
    }
    boolean landedOnPipe(Pipe pipe) {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.MARIO_EYE_HEIGHT >= pipe.top &&
                position.y - Constants.MARIO_EYE_HEIGHT < pipe.top) {

            float leftFoot = position.x - Constants.MARIO_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.MARIO_STANCE_WIDTH / 2;

            leftFootIn = (pipe.left < leftFoot && pipe.right > leftFoot);
            rightFootIn = (pipe.left < rightFoot && pipe.right > rightFoot);

            straddle = (pipe.left > leftFoot && pipe.right < rightFoot);
        }

        return leftFootIn || rightFootIn || straddle;
    }



}
