package com.mohammadg.flappysiavash.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Siavash {
    private static final int GRAVITY = -15;
    private static final int JUMP_UNITS = 250;
    private static final float BOUND_SCALE = 0.90f; //scale bounds width/height to provide fairer collision detection

    private static final int ANIMATION_FRAME_COUNT = 3; //number of frames in sprite sheet
    private static final float ANIMATION_CYCLE_TIME = 0.5f; //how long to show each frame

    private Vector2 position;
    private Vector2 velocity;

    private Texture siavashTexture;
    private Animation siavashAnimation;

    private Polygon bounds;
    private OrthographicCamera cam;

    private Sound flap;
    private Sound chirp;
    private Sound cry;

    public Siavash(OrthographicCamera cam, int x, int y) {
        this.cam = cam;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        siavashTexture = new Texture("images/siavash_spritesheet.png");
        siavashAnimation = new Animation(siavashTexture, ANIMATION_FRAME_COUNT, ANIMATION_CYCLE_TIME);

        //Setup bounds as polygon (rectangle offset by bound scale)
        int width = (int) (siavashAnimation.getFrame().getRegionWidth()*BOUND_SCALE);
        int height = (int) (siavashAnimation.getFrame().getRegionHeight()*BOUND_SCALE);

        bounds = new Polygon(new float[]{width*(1-BOUND_SCALE), height*(1-BOUND_SCALE),
            width, height*(1-BOUND_SCALE),
            width, height,
            width*(1-BOUND_SCALE), height
        });
        bounds.setOrigin(width/2, height/2);
        bounds.setPosition(x, y);

        //Sounds
        flap = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx_wing.ogg"));
        chirp = Gdx.audio.newSound(Gdx.files.internal("sounds/sia_chirp.ogg"));
        cry = Gdx.audio.newSound(Gdx.files.internal("sounds/sia_cry.ogg"));
    }

    public void update(float dt) {
        //Update sprite animation
        siavashAnimation.update(dt);

        //If above lowest point, apply gravity
        if (position.y > 0)
            velocity.add(0, GRAVITY);

        velocity.scl(dt); //scale
        position.add(0, velocity.y);

        if (position.y < 0)
            position.y = 0;
        else if (position.y > cam.viewportHeight - (siavashAnimation.getFrame().getRegionHeight()/2))
            position.y = cam.viewportHeight - (siavashAnimation.getFrame().getRegionHeight()/2);

        velocity.scl(1/dt); //reverse scale

        bounds.setPosition(position.x, position.y);
    }

    public void dispose() {
        siavashTexture.dispose();

        //Sounds
        flap.dispose();
        chirp.dispose();
        cry.dispose();
    }

    public void jump() {
        //play flap sound
        flap.play();

        //Update y velocity
        velocity.y = JUMP_UNITS;
    }

    public void justCrashed() {
        //Make velocity 0 to avoid increasing height as you rotate
        velocity.y = Math.min(0, velocity.y);
    }

    //Sounds
    public void playChirpSound() {
        playChirpSound(1.0f);
    }

    public void playChirpSound(float f) {
        chirp.play(f);
    }

    public void playCrySound() {
        playCrySound(1.0f);
    }

    public void playCrySound(float f) {
        cry.play(f);
    }

    //Getters
    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getTextureRegion() {
        return siavashAnimation.getFrame();
    }

    public Polygon getBounds() {
        return bounds;
    }
}
