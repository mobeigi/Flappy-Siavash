package com.mohammadg.flappysiavash.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Siavash {
    private static final int GRAVITY = -15;
    private static final int JUMP_UNITS = 250;

    private Vector2 position;
    private Vector2 velocity;
    private Texture siavash;
    private Rectangle bounds;

    public Siavash(int x, int y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        siavash = new Texture("siavash.png");
        bounds = new Rectangle(x, y, siavash.getWidth(), siavash.getHeight());
    }

    public void update(float dt) {
        if (position.y > 0)
            velocity.add(0, GRAVITY);

        velocity.scl(dt); //scale
        position.add(0, velocity.y);

        if (position.y < 0)
            position.y = 0;

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
