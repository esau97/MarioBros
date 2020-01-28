package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    public MarioAssets marioAssets;
    public PlatformAssets platformAssets;
    public BrickAssets brickAssets;
    public BrickCoinsAssets brickCoinsAssets;
    private AssetManager assetManager;
    public PipeAssets pipeAssets;
    public GoombaAssets goombaAssets;
    public CoinAssets coinAssets;
    public FlagAssets flagAssets;
    public MushroomAssets mushroomAssets;
    public BrickMushroomAssets brickMushroomAssets;
    public SuperMarioAssets superMarioAssets;
    public OnscreenControlsAssets onscreenControlsAssets;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();
        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        marioAssets = new MarioAssets(atlas);
        platformAssets = new PlatformAssets(atlas);
        brickAssets = new BrickAssets(atlas);
        brickCoinsAssets = new BrickCoinsAssets(atlas);
        pipeAssets = new PipeAssets(atlas);
        goombaAssets = new GoombaAssets(atlas);
        coinAssets = new CoinAssets(atlas);
        brickMushroomAssets = new BrickMushroomAssets(atlas);
        mushroomAssets = new MushroomAssets(atlas);
        superMarioAssets = new SuperMarioAssets(atlas);
        flagAssets = new FlagAssets(atlas);
        onscreenControlsAssets = new OnscreenControlsAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class MarioAssets {

        public final TextureAtlas.AtlasRegion standingRight;
        public final TextureAtlas.AtlasRegion jumpingRight;
        public final TextureAtlas.AtlasRegion walkingRight;
        public final Animation walkingRightAnimation;


        public MarioAssets(TextureAtlas atlas) {
            standingRight = atlas.findRegion(Constants.STANDING_RIGHT);
            jumpingRight = atlas.findRegion(Constants.JUMPING_RIGHT);
            walkingRight = atlas.findRegion(Constants.WALKING_RIGHT_2);


            Array<TextureAtlas.AtlasRegion> walkingRightFrames = new Array<TextureAtlas.AtlasRegion>();
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_3));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_1));

            walkingRightAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingRightFrames, Animation.PlayMode.LOOP);
        }
    }
    public class SuperMarioAssets {

        public final TextureAtlas.AtlasRegion standingRight;
        public final TextureAtlas.AtlasRegion jumpingRight;
        public final TextureAtlas.AtlasRegion walkingRight;
        public final Animation walkingRightAnimation;


        public SuperMarioAssets(TextureAtlas atlas) {
            standingRight = atlas.findRegion(Constants.STANDING_RIGHT_SUPER);
            jumpingRight = atlas.findRegion(Constants.JUMPING_RIGHT_SUPER);
            walkingRight = atlas.findRegion(Constants.WALKING_RIGHT_2_SUPER);


            Array<TextureAtlas.AtlasRegion> walkingRightFrames = new Array<TextureAtlas.AtlasRegion>();
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2_SUPER));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_3_SUPER));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_1_SUPER));

            walkingRightAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingRightFrames, Animation.PlayMode.LOOP);
        }
    }
    public class PlatformAssets {


        public final NinePatch platformNinePatch;

        public PlatformAssets(TextureAtlas atlas) {

            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.PLATFORM_SPRITE);

            int edge = Constants.PLATFORM_EDGE;
            platformNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }
    public class BrickAssets {
        public final NinePatch brickNinePatch;

        public BrickAssets(TextureAtlas atlas) {

            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.PLATFORM_BRICK);

            int edge = Constants.PLATFORM_EDGE;
            brickNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }

    public class BrickCoinsAssets {
        public final NinePatch brickCoinsNinePatch;
        public final NinePatch brickCoinsTKNinePatch;

        public BrickCoinsAssets(TextureAtlas atlas) {

            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.PLATFORM_BRICK_COINS);
            TextureAtlas.AtlasRegion region2 = atlas.findRegion(Constants.PLATFORM_BRICK_COINSTK);

            int edge = Constants.PLATFORM_EDGE;
            brickCoinsNinePatch = new NinePatch(region, edge, edge, edge, edge);
            brickCoinsTKNinePatch = new NinePatch(region2, edge, edge, edge, edge);
        }
    }
    public class BrickMushroomAssets {
        public final NinePatch brickMushroomNinePatch;
        public final NinePatch brickMushroomTKNinePatch;

        public BrickMushroomAssets(TextureAtlas atlas) {

            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.PLATFORM_BRICK_COINS);
            TextureAtlas.AtlasRegion region2 = atlas.findRegion(Constants.PLATFORM_BRICK_COINSTK);

            int edge = Constants.PLATFORM_EDGE;
            brickMushroomNinePatch = new NinePatch(region, edge, edge, edge, edge);
            brickMushroomTKNinePatch = new NinePatch(region2, edge, edge, edge, edge);
        }
    }
    public class PipeAssets {
        public final NinePatch pipeNinePatch;

        public PipeAssets(TextureAtlas atlas) {

            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.PLATFORM_PIPE);

            int edge = Constants.PLATFORM_EDGE;
            pipeNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }
    public class GoombaAssets {
        public final TextureAtlas.AtlasRegion goomba1;
        public final TextureAtlas.AtlasRegion goomba2;
        public final Animation walkingGoombaAnimation;
        public GoombaAssets (TextureAtlas atlas){
            goomba1 = atlas.findRegion(Constants.GOOMBA_SPRITE1);
            goomba2 = atlas.findRegion(Constants.GOOMBA_SPRITE2);
            Array<TextureAtlas.AtlasRegion> walkingGoombaFrames = new Array<TextureAtlas.AtlasRegion>();
            walkingGoombaFrames.add(atlas.findRegion(Constants.GOOMBA_SPRITE1));
            walkingGoombaFrames.add(atlas.findRegion(Constants.GOOMBA_SPRITE2));

            walkingGoombaAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingGoombaFrames, Animation.PlayMode.LOOP);

        }
    }
    public class CoinAssets{
        public final TextureAtlas.AtlasRegion coin;

        public CoinAssets (TextureAtlas atlas){
            coin = atlas.findRegion(Constants.COIN_SPRITE);
        }
    }
    public class MushroomAssets{
        public final TextureAtlas.AtlasRegion mushroom;

        public MushroomAssets (TextureAtlas atlas){
            mushroom = atlas.findRegion(Constants.MUSHROOM_SPRITE);
        }
    }
    public class FlagAssets{
        public final TextureAtlas.AtlasRegion flag;

        public FlagAssets (TextureAtlas atlas){
            flag = atlas.findRegion(Constants.FLAG_SPRITE);
        }
    }
    public class OnscreenControlsAssets {

        public final TextureAtlas.AtlasRegion moveRight;
        public final TextureAtlas.AtlasRegion moveLeft;
        public final TextureAtlas.AtlasRegion jump;

        public OnscreenControlsAssets(TextureAtlas atlas) {
            moveRight = atlas.findRegion(Constants.MOVE_RIGHT_BUTTON);
            moveLeft = atlas.findRegion(Constants.MOVE_LEFT_BUTTON);
            jump = atlas.findRegion(Constants.JUMP_BUTTON);
        }


    }
}
