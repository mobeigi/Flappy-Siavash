package com.mohammadg.flappysiavash.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ground {
    private Texture ground;
    private Vector2 dimensions;
    private Rectangle bounds;

    public  Ground() {
        this(0,0);
    }

    public Ground(int x, int y) {
        ground = new Texture("ground.png");

        dimensions = new Vector2(ground.getWidth(), ground.getHeight());
        bounds = new Rectangle(x, y, ground.getWidth(), ground.getHeight());
    }

    public void dispose() {
        ground.dispose();
    }

    public boolean collides(Rectangle playerBounds) {
        return playerBounds.overlaps(this.bounds);
    }
    //Getter
    public Texture getGround() {
        return ground;
    }

    public Vector2 getDimensions() {
        return dimensions;
    }

}
