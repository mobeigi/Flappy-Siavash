package com.mohammadg.flappysiavash.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Siavash {
    private static final int GRAVITY = -15;

    private Vector2 position;
    private Vector2 velocity;
    private Texture siavash;

    public Siavash(int x, int y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        siavash = new Texture("siavash.png");
    }

    public void update(float dt) {
        velocity.add(0, GRAVITY);

        velocity.scl(dt); //scale
        position.add(0, velocity.y);
        velocity.scl(1/dt); //reverse scale
    }


    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return siavash;
    }

}
