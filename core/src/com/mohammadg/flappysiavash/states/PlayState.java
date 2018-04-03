package com.mohammadg.flappysiavash.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mohammadg.flappysiavash.FlappySiavashGame;
import com.mohammadg.flappysiavash.sprites.Cage;
import com.mohammadg.flappysiavash.sprites.Siavash;

import java.util.ArrayList;

public class PlayState extends State {
    private static final int NUM_CAGES_PER_VIEWPORT = 3; //number of cages that cag show at once in 1 viewport

    private Texture background;
    private Siavash siavash;
    private ArrayList<Cage> cages;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappySiavashGame.WIDTH/2, FlappySiavashGame.HEIGHT/2);

        background = new Texture("bg.png");
        siavash = new Siavash(50,300);

        cages = new ArrayList<Cage>();
        int basePos = (int)cam.viewportWidth; //Start at viewport width to create initial opening

        for (int i = 0; i < NUM_CAGES_PER_VIEWPORT; ++i) {
            Cage cage = new Cage(basePos);
            cages.add(cage);
            basePos += cage.getTopCage().getWidth() + (cam.viewportWidth / NUM_CAGES_PER_VIEWPORT);
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            siavash.jump();
    }

    @Override
    public void update(float dt) {
        handleInput();
        siavash.update(dt);

        for (Cage cage : cages) {
            cage.update(dt);

            //If cage has moved off the viewport, reposition it
            if (cage.getTopCagePos().x + cage.getTopCage().getWidth() < 0) {
                cage.reposition(cam.viewportWidth);
            }

            //Collision detection
            if (cage.collides(siavash.getBounds())) {
                gsm.set(new PlayState(gsm));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth/2), 0);
        sb.draw(siavash.getTexture(), siavash.getPosition().x, siavash.getPosition().y);

        for (Cage cage : cages) {
            sb.draw(cage.getTopCage(), cage.getTopCagePos().x, cage.getTopCagePos().y);
            sb.draw(cage.getBotCage(), cage.getBotCagePos().x, cage.getBotCagePos().y);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        siavash.dispose();

        for (Cage cage : cages)
            cage.dispose();

        background.dispose();
    }
}
