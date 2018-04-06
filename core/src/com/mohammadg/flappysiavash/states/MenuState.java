package com.mohammadg.flappysiavash.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mohammadg.flappysiavash.FlappySiavashGame;

public class MenuState extends State {

    private Texture background;
    private Texture logo;
    private Rectangle logoBounds;
    private Texture playButton;
    private Rectangle playButtonBounds;
    private Texture siavashFace;
    private Rectangle siavashFaceBounds;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, FlappySiavashGame.WIDTH/2, FlappySiavashGame.HEIGHT/2);

        this.background = new Texture("bg.png");
        this.logo = new Texture("logo.png");
        this.logoBounds = new Rectangle((cam.viewportWidth/2) - ((logo.getWidth()*0.4f)/2),
                cam.viewportHeight*0.55f - (logo.getHeight()*0.4f)/2,
                logo.getWidth()*0.4f, logo.getHeight()*0.4f);

        this.playButton = new Texture("playbutton.png");
        this.playButtonBounds = new Rectangle(cam.viewportWidth/2 - playButton.getWidth()/2,
                cam.viewportHeight*0.35f - playButton.getHeight()/2,
                playButton.getWidth(), playButton.getHeight());

        this.siavashFace = new Texture("siavash_face_circle.png");
        this.siavashFace.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.siavashFaceBounds = new Rectangle((cam.viewportWidth/2) - ((siavashFace.getWidth()*0.4f)/2),
                logoBounds.getY() + logoBounds.getHeight(),
                siavashFace.getWidth()*0.4f, siavashFace.getHeight()*0.4f);
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
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth/2), 0,
                background.getWidth(), background.getHeight());

        sb.draw(siavashFace, siavashFaceBounds.x, siavashFaceBounds.y, siavashFaceBounds.getWidth(), siavashFaceBounds.getHeight());

        sb.draw(logo, logoBounds.x, logoBounds.y, logoBounds.getWidth(), logoBounds.getHeight());

        sb.draw(playButton, playButtonBounds.x, playButtonBounds.y);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        logo.dispose();
        playButton.dispose();
    }

}
