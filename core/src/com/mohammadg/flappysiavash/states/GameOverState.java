package com.mohammadg.flappysiavash.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mohammadg.flappysiavash.FlappySiavashGame;

public class GameOverState extends State {

    private Texture gameOver;

    protected GameOverState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappySiavashGame.WIDTH/2, FlappySiavashGame.HEIGHT/2);
        gameOver = new Texture("gameover.png");
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(gameOver, cam.viewportWidth/2 - gameOver.getWidth()/2, cam.viewportHeight*0.70f,
                gameOver.getWidth(), gameOver.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        gameOver.dispose();
    }
}
