package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static final Color BACKGROUND_COLOR = Color.SKY;
    public static final Color GAME_OVER_COLOR = Color.BLACK;
    public final static float WORLD_SIZE =480.0f;
    public static final float DIFFICULTY_WORLD_SIZE = 480.0f;
    public static final float KILL_PLANE = -100;

    public static final int INITIAL_LIVES = 3;

    public static final float DIFFICULTY_LABEL_SCALE = 1.5f;
    public static final String TEXTURE_ATLAS = "images/mario-sprites.pack.atlas";
    public static final String STANDING_RIGHT = "Mario";
    public static final String STANDING_RIGHT_SUPER = "SuperMario";
    public static final String VICTORY_MESSAGE = "You win";
    public static final String GAME_OVER_MESSAGE = "You lose";
    public static final float LEVEL_END_DURATION = 3;
    public static final String FONT_FILE = "font/header.fnt";
    public static final float ONSCREEN_CONTROLS_VIEWPORT_SIZE = 200;
    public static final float BUTTON_RADIUS = 24;
    public static final Vector2 BUTTON_CENTER = new Vector2(15, 15);

    public static final String MOVE_LEFT_BUTTON = "button-left";
    public static final String MOVE_RIGHT_BUTTON = "button-right";
    public static final String JUMP_BUTTON = "button-up";


    public static final String JUMPING_RIGHT = "Mario-Jump";
    public static final String WALKING_RIGHT_2 ="Mario-Walk2";
    public static final String WALKING_RIGHT_1 ="Mario-Walk1";
    public static final String WALKING_RIGHT_3 ="Mario-Walk3";
    public static final String JUMPING_RIGHT_SUPER = "SuperMario-Jump";
    public static final String WALKING_RIGHT_2_SUPER ="SuperMario-Walk2";
    public static final String WALKING_RIGHT_1_SUPER ="SuperMario-Walk1";
    public static final String WALKING_RIGHT_3_SUPER ="SuperMario-Walk3";
    public static final String PLATFORM_SPRITE = "sprite-1";
    public static final String PLATFORM_BRICK = "brick-2";
    public static final String PLATFORM_BRICK_COINS = "brick-coin-1";
    public static final String PLATFORM_BRICK_COINSTK = "brick-coin-tk";
    public static final String PLATFORM_BRICK_MUSH ="brick-coin-0";
    public static final String PLATFORM_FLAG ="flagx2";
    public static final String PLATFORM_PIPE = "tube-2";


    public static final int PLATFORM_EDGE = 8;
    public static final Vector2 MARIO_EYE_POSITION = new Vector2(16, 16);
    public static final float MARIO_EYE_HEIGHT = 16.0f;
    public static final String LEVEL_DIR = "levels";
    public static final String LEVEL_FILE_EXTENSION = "json";
    public static final String LEVEL_COMPOSITE = "composite";
    public static final String LEVEL_9PATCHES = "sImage9patchs";
    public static final String LEVEL_IMAGES = "sImages";
    public static final String LEVEL_ERROR_MESSAGE = "There was a problem loading the level.";
    public static final String LEVEL_IMAGENAME_KEY = "imageName";
    public static final String LEVEL_X_KEY = "x";
    public static final String LEVEL_Y_KEY = "y";
    public static final String LEVEL_WIDTH_KEY = "width";
    public static final String LEVEL_HEIGHT_KEY = "height";
    public static final String LEVEL_IDENTIFIER_KEY = "itemIdentifier";
    public static final String LEVEL_ENEMY_TAG = "Goomba";
    public static final String LEVEL_ENEMY_TAG2 = "Goomba2";


    public static final float WALK_LOOP_DURATION = 0.15f;

    public static final float MARIO_MOVE_SPEED = 140;

    public static final String LEVEL_EASY = "powerup-1.png";
    public static final String LEVEL_MEDIUM = "powerup-3.png";
    public static final String LEVEL_HARD="powerup-0.png";

    public static final int EASY_TIME =300;
    public static final int MEDIUM_TIME=250;
    public static final int HARD_TIME=200;

    public static final float JUMP_SPEED = 540;

    public static final float MAX_JUMP_DURATION = .15f;

    public static final float GRAVITY = 1000;
    public static final float MARIO_STANCE_WIDTH = 13.0f;
    public static final float MARIO_HEIGHT = 32f;

    public static final String MUSHROOM_SPRITE = "mushroom";
    public static final String FLAG_SPRITE = "flag2";
    public static final Vector2 FLAG_CENTER = new Vector2(31, 31);

    public static final String COIN_SPRITE = "coin-1";
    public static final Vector2 COIN_CENTER = new Vector2(14, 22);
    public static final Vector2 MUSHROOM_CENTER = new Vector2(14, 22);
    public static final int COIN_SCORE = 200;
    public static final int MUSH_SCORE = 100;
    public static final int GOOMBA_SCORE = 200;


    public static final String GOOMBA_SPRITE1="goomba-1";
    public static final String GOOMBA_SPRITE2="goomba-2";
    public static final Vector2 GOOMBA_CENTER = new Vector2(14, 22);

    public static final float GOOMBA_STANCE_WIDTH = 32f;
    public static final float GOOMBA_EYE_HEIGHT = 32f;
    public static final float GOOMBA_MOVEMENT_SPEED = 40;

    public enum Difficulty {
        EASY(EASY_TIME),
        MEDIUM(MEDIUM_TIME),
        HARD(HARD_TIME);

        float spawnRate;

        Difficulty(float timeRate) {
            this.spawnRate = timeRate;
        }


    }

    public static final float MUSH_WIDTH = (75f/2);
    public static final Vector2 EASY_CENTER = new Vector2((DIFFICULTY_WORLD_SIZE / 4)*1.2f+MUSH_WIDTH, (DIFFICULTY_WORLD_SIZE / 2)*0.5f+MUSH_WIDTH);
    public static final Vector2 MEDIUM_CENTER = new Vector2((DIFFICULTY_WORLD_SIZE / 2)*1.2f+MUSH_WIDTH, (DIFFICULTY_WORLD_SIZE / 2)*0.5f+MUSH_WIDTH);
    public static final Vector2 HARD_CENTER = new Vector2((DIFFICULTY_WORLD_SIZE * 3 / 4)*1.2f+MUSH_WIDTH, (DIFFICULTY_WORLD_SIZE / 2)*0.5f+MUSH_WIDTH);

    public static final float DIFFICULTY_BUBBLE_RADIUS = DIFFICULTY_WORLD_SIZE / 12;

    public static final float HUD_VIEWPORT_SIZE = 480;
    public static final float HUD_MARGIN = 20.0f;
    public static final String HUD_SCORE_LABEL = "Score: ";
    public static final String HUD_TIME_LABEL ="TIME";



}
