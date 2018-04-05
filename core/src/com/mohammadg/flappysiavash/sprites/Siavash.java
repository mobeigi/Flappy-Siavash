package com.mohammadg.flappysiavash.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Siavash {
    private static final int GRAVITY = -15;
    private static final int JUMP_UNITS = 250;
    private static final float BOUND_SCALE = 0.75f; //scale bounds width/height to provide fairer collision detection

    private static final int ANIMATION_FRAME_COUNT = 3; //number of frames in sprite sheet
    private static final float ANIMATION_CYCLE_TIME = 0.5f; //how long to show each frame

    private Vector2 position;
    private Vector2 velocity;

    private Texture siavashTexture;
    private Animation siavashAnimation;
    private Rectangle bounds;
    private OrthographicCamera cam;

    private Sound flap;
    private Sound chirp;
    private Sound cry;

    public Siavash(OrthographicCamera cam, int x, int y) {
        this.cam = cam;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        siavashTexture = new Texture("siavash_spritesheet.png");
        siavashAnimation = new Animation(siavashTexture, ANIMATION_FRAME_COUNT, ANIMATION_CYCLE_TIME);

        bounds = new Rectangle(x + (siavashAnimation.getFrame().getRegionWidth()*BOUND_SCALE)/2,
                y + (siavashAnimation.getFrame().getRegionWidth()*BOUND_SCALE)/2,
                siavashAnimation.getFrame().getRegionWidth()*BOUND_SCALE,
                siavashAnimation.getFrame().getRegionHeight()*BOUND_SCALE);

        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        chirp = Gdx.audio.newSound(Gdx.files.internal("sia_chirp.ogg"));
        cry = Gdx.audio.newSound(Gdx.files.internal("sia_cry.ogg"));
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
        else if (position.y > cam.viewportHeight - (siavashTexture.getHeight()/2))
            position.y = cam.viewportHeight - (siavashTexture.getHeight()/2);

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

    //Sounds
    public void playChirpSound() {
        chirp.play(1.0f);
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

    public Rectangle getBounds() {
        return bounds;
    }
}
