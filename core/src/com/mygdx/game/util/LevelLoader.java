package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Level;
import com.mygdx.game.entities.Brick;
import com.mygdx.game.entities.BrickCoins;
import com.mygdx.game.entities.BrickMushroom;
import com.mygdx.game.entities.Flag;
import com.mygdx.game.entities.Goomba;
import com.mygdx.game.entities.Mario;
import com.mygdx.game.entities.Pipe;
import com.mygdx.game.entities.PlatformGround;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.Comparator;



public class LevelLoader {

    public static final String TAG = LevelLoader.class.toString();

    public static Level load(String levelName, Viewport viewport, Constants.Difficulty difficulty) {

        Level level = new Level(viewport,difficulty);

        String path = Constants.LEVEL_DIR + File.separator + levelName + "." + Constants.LEVEL_FILE_EXTENSION;

        FileHandle file = Gdx.files.internal(path);
        JSONParser parser = new JSONParser();

        try {

            JSONObject rootJsonObject = (JSONObject) parser.parse(file.reader());
            JSONObject composite = (JSONObject) rootJsonObject.get(Constants.LEVEL_COMPOSITE);
            JSONArray platforms = (JSONArray) composite.get(Constants.LEVEL_9PATCHES);
            loadPlatforms(platforms, level);
            JSONArray nonPlatformObjects = (JSONArray) composite.get(Constants.LEVEL_IMAGES);
            loadNonPlatformEntities(level,nonPlatformObjects );


        } catch (Exception ex) {

            Gdx.app.error(TAG, ex.getMessage());

            Gdx.app.error(TAG, Constants.LEVEL_ERROR_MESSAGE);
        }

        return level;
    }
    private static float safeGetFloat(JSONObject object, String key){
        Number number = (Number) object.get(key);
        return (number == null) ? 0 : number.floatValue();
    }

    private static void loadPlatforms(JSONArray array, Level level) {

        Array<PlatformGround> platformArray = new Array<PlatformGround>();
        Array<Brick> brickArray = new Array<Brick>();
        Array<BrickCoins> brickCoinsArray = new Array<BrickCoins>();
        Array<Pipe> pipeArray = new Array<Pipe>();
        Array<BrickMushroom> brickMushrooms = new Array<BrickMushroom>();


        for (Object object : array) {
            final JSONObject platformObject = (JSONObject) object;

            final float x = safeGetFloat(platformObject, Constants.LEVEL_X_KEY);

            final float y = safeGetFloat(platformObject, Constants.LEVEL_Y_KEY);

            final float width = ((Number) platformObject.get(Constants.LEVEL_WIDTH_KEY)).floatValue();

            final float height = ((Number) platformObject.get(Constants.LEVEL_HEIGHT_KEY)).floatValue();

            if(platformObject.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.PLATFORM_BRICK)){
                final Brick brick = new Brick (x, y + height, width, height,level);
                brickArray.add(brick);
            }else if (platformObject.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.PLATFORM_SPRITE)){
                final PlatformGround platform = new PlatformGround(x, y + height, width, height);
                platformArray.add(platform);
                final String identifier = (String) platformObject.get(Constants.LEVEL_IDENTIFIER_KEY);


                if (identifier != null) {
                    if(identifier.equals(Constants.LEVEL_ENEMY_TAG)){
                        final Goomba goomba = new Goomba(platform,level);
                        level.getGoombas().add(goomba);
                    }else if(identifier.equals(Constants.LEVEL_ENEMY_TAG2)){
                        final Goomba goomba = new Goomba(platform,level);
                        float posicion=platform.getLeft()+40;
                        platform.setLeft(posicion);
                        final Goomba goomba2 = new Goomba(platform,level);
                        level.getGoombas().add(goomba);
                        level.getGoombas().add(goomba2);
                    }

                }
            }else if (platformObject.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.PLATFORM_BRICK_COINS)){
                final BrickCoins brickCoins = new BrickCoins(x, y + height, width, height);
                brickCoinsArray.add(brickCoins);
            }else if (platformObject.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.PLATFORM_PIPE)){
                final Pipe pipe = new Pipe(x, y + height, width, height,level);
                pipeArray.add(pipe);
            }else if (platformObject.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.PLATFORM_BRICK_MUSH)){
                final BrickMushroom brickMushroom = new BrickMushroom(x, y + height, width, height,level);
                brickMushrooms.add(brickMushroom);
            }


        }

        platformArray.sort(new Comparator<PlatformGround>() {
            @Override
            public int compare(PlatformGround o1, PlatformGround o2) {
                if (o1.top < o2.top) {
                    return 1;
                } else if (o1.top > o2.top) {
                    return -1;
                }
                return 0;
            }
        });
        level.getBrickMushrooms().addAll(brickMushrooms);
        level.getPipes().addAll(pipeArray);
        level.getBricksCoins().addAll(brickCoinsArray);
        level.getBricksArray().addAll(brickArray);
        level.getPlatforms().addAll(platformArray);
    }
    private static void loadNonPlatformEntities(Level level, JSONArray nonPlatformObjects) {
        for (Object o : nonPlatformObjects) {


            JSONObject item = (JSONObject) o;
            Vector2 lowerLeftCorner = new Vector2();

            final float x = safeGetFloat(item, Constants.LEVEL_X_KEY);
            final float y = safeGetFloat(item, Constants.LEVEL_Y_KEY);

            lowerLeftCorner = new Vector2(x, y);
            if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.STANDING_RIGHT)) {
                final Vector2 marioPosition = lowerLeftCorner.add(Constants.MARIO_EYE_POSITION);
                level.setMario(new Mario(marioPosition, level));
            }

            else if (item.get(Constants.LEVEL_IMAGENAME_KEY).equals(Constants.FLAG_SPRITE)) {
                final Vector2 flagPosition = lowerLeftCorner.add(Constants.FLAG_CENTER);
                level.setFlag(new Flag(flagPosition));
            }
        }
    }

}