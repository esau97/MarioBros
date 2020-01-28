package com.mygdx.game.overlays;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Level;
import com.mygdx.game.util.Assets;
import com.mygdx.game.util.Constants;
import com.mygdx.game.util.Utils;


public class MarioHud {
    public final Viewport viewport;
    final BitmapFont font;

    public MarioHud() {
        this.viewport = new ExtendViewport(Constants.HUD_VIEWPORT_SIZE, Constants.HUD_VIEWPORT_SIZE);
        font = new BitmapFont();
        font.getData().setScale(1);
    }

    public void render(SpriteBatch batch, int lives, int score, float tiempo, int tiempoTotal, Level level) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        final String hudString =
                Constants.HUD_SCORE_LABEL + score + "\n";

        font.draw(batch, hudString, Constants.HUD_MARGIN, viewport.getWorldHeight() - Constants.HUD_MARGIN);
        level.tiempoTotal=tiempoTotal-Math.round(tiempo);
        final String hudStringTime = Constants.HUD_TIME_LABEL+ "\n " +level.tiempoTotal;

        font.draw(batch, hudStringTime, viewport.getWorldWidth() - Constants.HUD_MARGIN*4, viewport.getWorldHeight() -Constants.HUD_MARGIN*4);

        final TextureRegion standingRight = Assets.instance.marioAssets.standingRight;
        for (int i = 1; i <= lives; i++) {
            final Vector2 drawPosition = new Vector2(
                    viewport.getWorldWidth() - i * (Constants.HUD_MARGIN / 2 + standingRight.getRegionWidth()),
                    viewport.getWorldHeight() - Constants.HUD_MARGIN - standingRight.getRegionHeight()
            );
            Utils.drawTextureRegion(
                    batch,
                    standingRight,
                    drawPosition
            );
        }

    }
}
