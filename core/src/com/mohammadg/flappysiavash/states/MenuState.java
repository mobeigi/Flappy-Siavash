package com.mohammadg.flappysiavash.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mohammadg.flappysiavash.FlappySiavashGame;

public class MenuState extends State {

    private Texture background;
    private Texture playButton;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        this.background = new Texture("bg.png");
        this.playButton = new Texture("playbutton.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, FlappySiavashGame.WIDTH, FlappySiavashGame.HEIGHT);
        sb.draw(playButton, (FlappySiavashGame.WIDTH/2) - (playButton.getWidth()/2),
                            (FlappySiavashGame.HEIGHT/2) - (playButton.getHeight()/2));
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }

}
