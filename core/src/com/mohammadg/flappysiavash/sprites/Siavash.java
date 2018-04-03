package com.mohammadg.flappysiavash.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Siavash {
    private static final int GRAVITY = -15;
    private static final int JUMP_UNITS = 250;
    private static final float BOUND_SCALE = 0.75f; //scale bounds width/height to provide fairer collision detection

    private Vector2 position;
    private Vector2 velocity;
    private Texture siavash;
    private Rectangle bounds;
    private OrthographicCamera cam;

    public Siavash(OrthographicCamera cam, int x, int y) {
        this.cam = cam;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        siavash = new Texture("siavash.png");
        bounds = new Rectangle(x, y, siavash.getWidth()*BOUND_SCALE, siavash.getHeight()*BOUND_SCALE);
    }

    public void update(float dt) {
        //If above lowest point, apply gravity
        if (position.y > 0)
            velocity.add(0, GRAVITY);

        velocity.scl(dt); //scale
        position.add(0, velocity.y);

        if (position.y < 0)
            position.y = 0;
        else if (position.y > cam.viewportHeight - (siavash.getHeight()/2))
            position.y = cam.viewportHeight - (siavash.getHeight()/2);

        velocity.scl(1/dt); //reverse scale

        bounds.setPosition(position.x, position.y);
    }

    public void dispose() {
        siavash.dispose();
    }


    public void jump() {
        velocity.y = JUMP_UNITS;
    }

    //Getters
    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return siavash;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
