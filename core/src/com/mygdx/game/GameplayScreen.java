package com.mygdx.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.overlays.GameOver;
import com.mygdx.game.overlays.MarioHud;
import com.mygdx.game.overlays.OnscreenControl;
import com.mygdx.game.overlays.Win;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.ChaseCamera;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.LevelLoader;
import com.mygdx.game.util.Utils;


public class GameplayScreen extends ScreenAdapter {


    MarioBrosGame game;
    GameOver gameOver;
    Win win;
    public int contLevel;
    SpriteBatch batch;
    private ChaseCamera chaseCamera;
    ShapeRenderer renderer;
    public ExtendViewport viewport;
    Level level;
    Music music;
    Music powerup;
    Constants.Difficulty difficulty;
    OnscreenControl onscreenControl;
    MarioHud marioHud;
    long levelEndOverlayStartTime;


    public GameplayScreen(MarioBrosGame game, Constants.Difficulty difficulty) {
        this.game= game;
        this.difficulty=difficulty;
    }


    @Override
    public void show() {


        music = Gdx.audio.newMusic(Gdx.files.internal("music/GameMusic.mp3"));

        music.play();
        music.setVolume(0.3f);

        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();

        viewport = new ExtendViewport(Constants.DIFFICULTY_WORLD_SIZE, Constants.DIFFICULTY_WORLD_SIZE);
        level = LevelLoader.load("level1", viewport,difficulty);


        marioHud=new MarioHud();
        chaseCamera = new ChaseCamera(viewport.getCamera(),level.mario);
        gameOver = new GameOver(viewport);
        win = new Win();
        onscreenControl = new OnscreenControl();
        onscreenControl.mario = level.mario;

        if (onMobile()) {
            Gdx.input.setInputProcessor(onscreenControl);
        }

        contLevel=1;
    }
    private boolean onMobile() {
        return Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS;
    }

    @Override
    public void render(float delta) {
        level.update(delta);
        chaseCamera.update();
        viewport.apply();

        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        level.render(batch);

        marioHud.render(batch, level.getMario().getLives(), level.score, level.mario.tiempo,level.tiempo,level);
        if (onMobile()){
            onscreenControl.render(batch);
        }

        renderLevelEndOverlays(batch);
        batch.end();
    }
    private void renderLevelEndOverlays(SpriteBatch batch) {
        if (level.gameOver) {
            endMusic();
            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                gameOver.render(batch);
            }

            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                levelFailed();
            }
        } else if (level.win) {

            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                win.render(batch);
            }

            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                if(contLevel!=2){
                    contLevel++;
                    levelComplete();
                }else{
                    endMusic();
                    finish();
                }

            }
        }
    }

    public void levelComplete() {
        startNewLevel();
    }
    private void startNewLevel() {

        LevelLoader levelLoader=new LevelLoader();
        level=levelLoader.load("level2", viewport, difficulty);
        onscreenControl.mario = level.mario;

        chaseCamera.camera = level.viewport.getCamera();
        chaseCamera.target = level.getMario();
        //onscreenControls.mario = level.getMario();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    public void finish(){
        game.showInitialScreen();
    }

    @Override
    public void resize(int width, int height) {
        marioHud.viewport.update(width, height, true);
        viewport.update(width, height, true);
        onscreenControl.viewport.update(width, height, true);
        onscreenControl.recalculateButtonPositions();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    public void endMusic (){
        music.stop();
    }

    @Override
    public void hide() {
        renderer.dispose();
    }

    @Override
    public void dispose() {
        music.dispose();
        powerup.dispose();
    }
    public void levelFailed(){
        game.showInitialScreen();
    }
}
