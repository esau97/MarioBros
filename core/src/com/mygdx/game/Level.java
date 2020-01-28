package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Brick;
import com.mygdx.game.entities.BrickCoins;
import com.mygdx.game.entities.BrickMushroom;
import com.mygdx.game.entities.Coin;
import com.mygdx.game.entities.Flag;
import com.mygdx.game.entities.Goomba;
import com.mygdx.game.entities.Mario;
import com.mygdx.game.entities.Mushroom;
import com.mygdx.game.entities.Pipe;
import com.mygdx.game.entities.PlatformGround;
import com.mygdx.game.util.Constants;

public class Level {
    Mario mario;
    public Viewport viewport;
    Array<PlatformGround> platforms;
    Array<BrickCoins> bricksCoins;
    Array<Pipe> pipes;
    Array<BrickMushroom> brickMushrooms;
    public int score;
    Constants.Difficulty difficulty;
    Flag flag;
    Music flagPoleMusic;
    Music gameOverMusic;
    public int tiempo;
    long gameplayTime;
    public boolean gameOver;
    public int tiempoTotal;
    public boolean win;
    private DelayedRemovalArray<Coin> coinsArray;
    private DelayedRemovalArray<Brick> bricksArray;

    public DelayedRemovalArray<Brick> getBricksArray() {
        return bricksArray;
    }

    public void setBricksArray(DelayedRemovalArray<Brick> bricksArray) {
        this.bricksArray = bricksArray;
    }

    public DelayedRemovalArray<Mushroom> getMushArray() {
        return mushArray;
    }

    public void setMushArray(DelayedRemovalArray<Mushroom> mushArray) {
        this.mushArray = mushArray;
    }

    private DelayedRemovalArray<Mushroom> mushArray;

    public DelayedRemovalArray<Coin> getCoinsArray() {
        return coinsArray;
    }

    public void setCoinsArray(DelayedRemovalArray<Coin> coinsArray) {
        this.coinsArray = coinsArray;
    }

    public DelayedRemovalArray<Goomba> getGoombas() {
        return goombas;
    }

    public void setGoombas(DelayedRemovalArray<Goomba> goombas) {
        this.goombas = goombas;
    }

    private DelayedRemovalArray<Goomba> goombas;

    public Mario getMario() {
        return mario;
    }

    public void setMario(Mario mario) {
        this.mario = mario;
    }

    public Array<Pipe> getPipes() {
        return pipes;
    }

    public void setPipes(Array<Pipe> pipes) {
        this.pipes = pipes;
    }

    public Array<BrickCoins> getBricksCoins() {
        return bricksCoins;
    }

    public void setBricksCoins(Array<BrickCoins> bricksCoins) {
        this.bricksCoins = bricksCoins;
    }



    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Array<PlatformGround> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Array<PlatformGround> platforms) {
        this.platforms = platforms;
    }

    public long getGameplayTime() {
        return gameplayTime;
    }

    public void setGameplayTime(long gameplayTime) {
        this.gameplayTime = gameplayTime;
    }


    public Level(Viewport viewport, Constants.Difficulty difficulty) {
        this.viewport =viewport;
        this.difficulty = difficulty;
        gameplayTime = TimeUtils.nanoTime();
        //mario = new Mario(new Vector2(70,60),this);
        initLevel();
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public Array<BrickMushroom> getBrickMushrooms() {
        return brickMushrooms;
    }

    public void setBrickMushrooms(Array<BrickMushroom> brickMushrooms) {
        this.brickMushrooms = brickMushrooms;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public void initLevel(){
        flagPoleMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Flagpole.wav"));
        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("music/GameOver.wav"));
        gameOver = false;
        win = false;
        platforms = new Array<PlatformGround>();
        bricksCoins = new Array<BrickCoins>();
        pipes = new Array<Pipe>();
        brickMushrooms = new Array<BrickMushroom>();
        goombas = new DelayedRemovalArray<Goomba>();
        coinsArray = new DelayedRemovalArray<Coin>();
        bricksArray = new DelayedRemovalArray<Brick>();
        mushArray = new DelayedRemovalArray<Mushroom>();
        score=0;
        if(difficulty == Constants.Difficulty.EASY){
            tiempo = Constants.EASY_TIME;
            tiempoTotal = tiempo;
        }else if(difficulty == Constants.Difficulty.MEDIUM){
            tiempo = Constants.MEDIUM_TIME;
            tiempoTotal = tiempo;
        }else if(difficulty == Constants.Difficulty.HARD){
            tiempo = Constants.HARD_TIME;
            tiempoTotal = tiempo;
        }


        //bricksArray.add(new Brick(75,120,28,28,this));
        //mario = new Mario(new Vector2(70,60),this);
    }

    public void update(float delta) {
        if ( mario.getLives()<=0 || tiempoTotal==0){

            gameOver=true;

            //mario.setLives(0);
        }
        if( mario.position.x > flag.position.x +28){
            win=true;
            flagPoleMusic.play();
            mario.velocity.x=0;
            mario.velocity.y=0;
        }
        if(!gameOver){
            mario.update(delta,platforms,bricksArray,bricksCoins,pipes,brickMushrooms);
            for (int i = 0; i < goombas.size; i++) {
                Goomba goomba = goombas.get(i);
                goomba.update(delta,mario);
            }
        }

    }

    public void render(SpriteBatch batch) {
        for (PlatformGround platformGround : platforms) {
            platformGround.render(batch);
        }
        for (Brick brick : bricksArray){
            brick.render(batch);
        }
        for (BrickCoins brickCoins : bricksCoins){
            brickCoins.render(batch);
        }
        for (Pipe pipe : pipes){
            pipe.render(batch);
        }
        for (Goomba goomba : goombas) {
            goomba.render(batch);
        }
        for (Coin coin : coinsArray){
            coin.render(batch);
        }
        for (BrickMushroom brickMushroom: brickMushrooms){
            brickMushroom.render(batch);
        }
        for (Mushroom mushroom : mushArray){
            mushroom.render(batch);
        }
        flag.render(batch);
        mario.render(batch);
    }
    public void spawnCoin(Vector2 position) {
        coinsArray.add(new Coin(position));
    }
    public void spawnMush(Vector2 position) {
        mushArray.add(new  Mushroom(position));
    }
}
