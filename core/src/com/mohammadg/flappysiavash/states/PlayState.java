package com.mohammadg.flappysiavash.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mohammadg.flappysiavash.FlappySiavashGame;
import com.mohammadg.flappysiavash.sprites.Siavash;

public class PlayState extends State {
    private Siavash siavash;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        siavash = new Siavash(50,300);
        cam.setToOrtho(false, FlappySiavashGame.WIDTH/2, FlappySiavashGame.HEIGHT/2);
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
        handleInput();
        siavash.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(siavash.getTexture(), siavash.getPosition().x, siavash.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {
        //siavash.dispose();
    }
}
