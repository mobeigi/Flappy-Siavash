package com.mohammadg.flappysiavash.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mohammadg.flappysiavash.FlappySiavashGame;
import com.mohammadg.flappysiavash.Helper;

public class GameOverState extends State {

    private Texture gameOver;
    private float gameOverMenuPopupDt = 0.0f;
    private Vector2 gameOverMenuDimensions;
    private Vector2 gameOverMenuPosition;
    private Texture playButton;
    private Rectangle playButtonBounds;
    private int finalScore = 0;
    private int highScore = 0;

    protected GameOverState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappySiavashGame.WIDTH/2, FlappySiavashGame.HEIGHT/2);
        gameOver = new Texture("gameover.png");
        gameOverMenuDimensions = new Vector2(gameOver.getWidth()*0.85f, gameOver.getHeight()*0.85f);
        this.playButton = new Texture("playbutton.png");
        this.playButtonBounds = new Rectangle(cam.viewportWidth/2 - playButton.getWidth()/2,
                cam.viewportHeight*0.35f - playButton.getHeight()/2,
                playButton.getWidth(), playButton.getHeight());

        this.gameOverMenuPosition = new Vector2(cam.viewportWidth/2 - gameOverMenuDimensions.x/2,
            cam.viewportHeight*0.65f - gameOverMenuDimensions.y/2);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0.0f);
            cam.unproject(tmp);
            if (playButtonBounds.contains(tmp.x, tmp.y)) {
                gsm.set(new PlayState(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        gameOverMenuPopupDt += dt;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        Color c = sb.getColor();

        float finalGameOverMenuPopupDt = (float) Math.min(1.0, gameOverMenuPopupDt);
        float finalGameOverMenuPopupDtSlow = (float) Math.min(1.0, gameOverMenuPopupDt*0.75); //for black to have slower fade in

        sb.setColor(c.r, c.g, c.b, finalGameOverMenuPopupDt);

        //Game over menu
        sb.draw(gameOver, gameOverMenuPosition.x, gameOverMenuPosition.y, gameOverMenuDimensions.x, gameOverMenuDimensions.y);

        //Play button
        sb.draw(playButton, playButtonBounds.x, playButtonBounds.y);


        //Display final/high scores
        Color white = new Color(1.0f, 1.0f, 1.0f, finalGameOverMenuPopupDt);
        Color black = new Color(0.0f, 0.0f, 0.0f, finalGameOverMenuPopupDtSlow);

        //Final Score
        String finalScoreStr = Integer.toString(finalScore).replace('0', 'O');

        Helper.PrepareDrawText(sb, gsm.assetManager.getEightBitWonder(), gsm.assetManager.getGlyphLayout(), finalScoreStr,
                black, 0.5f);
        Helper.DrawText(sb, gsm.assetManager.getEightBitWonder(), gsm.assetManager.getGlyphLayout(), gsm.assetManager.getFontShader(),
                (gameOverMenuPosition.x + gameOverMenuDimensions.x*0.88f) - gsm.assetManager.getGlyphLayout().width + 1, gameOverMenuPosition.y + gameOverMenuDimensions.y*0.47f - 1);

        Helper.PrepareDrawText(sb, gsm.assetManager.getEightBitWonder(), gsm.assetManager.getGlyphLayout(), finalScoreStr,
                white, 0.5f);
        Helper.DrawText(sb, gsm.assetManager.getEightBitWonder(), gsm.assetManager.getGlyphLayout(), gsm.assetManager.getFontShader(),
                (gameOverMenuPosition.x + gameOverMenuDimensions.x*0.88f) - gsm.assetManager.getGlyphLayout().width, gameOverMenuPosition.y + gameOverMenuDimensions.y*0.47f);

        //High Score
        String highScoreString = Integer.toString(highScore).replace('0', 'O');

        Helper.PrepareDrawText(sb, gsm.assetManager.getEightBitWonder(), gsm.assetManager.getGlyphLayout(), highScoreString,
                black, 0.5f);
        Helper.DrawText(sb, gsm.assetManager.getEightBitWonder(), gsm.assetManager.getGlyphLayout(), gsm.assetManager.getFontShader(),
                (gameOverMenuPosition.x + gameOverMenuDimensions.x*0.88f) - gsm.assetManager.getGlyphLayout().width + 1, gameOverMenuPosition.y + gameOverMenuDimensions.y*0.27f - 1);

        Helper.PrepareDrawText(sb, gsm.assetManager.getEightBitWonder(), gsm.assetManager.getGlyphLayout(), highScoreString,
                white, 0.5f);
        Helper.DrawText(sb, gsm.assetManager.getEightBitWonder(), gsm.assetManager.getGlyphLayout(), gsm.assetManager.getFontShader(),
                (gameOverMenuPosition.x + gameOverMenuDimensions.x*0.88f) - gsm.assetManager.getGlyphLayout().width, gameOverMenuPosition.y + gameOverMenuDimensions.y*0.27f);


        sb.setColor(c);
        sb.end();
    }

    @Override
    public void dispose() {
        gameOver.dispose();
        playButton.dispose();
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
