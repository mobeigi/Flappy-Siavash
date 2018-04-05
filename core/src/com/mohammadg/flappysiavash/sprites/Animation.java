package com.mohammadg.flappysiavash.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int curFrame;

    public Animation(Texture texture, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();
        TextureRegion textureRegion = new TextureRegion(texture);
        int frameWidth = textureRegion.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; ++i) {
            frames.add(new TextureRegion(textureRegion, i * frameWidth, 0 ,
                    frameWidth, textureRegion.getRegionHeight()));
        }
        this.maxFrameTime = cycleTime / frameCount;
        curFrame = 0;
    }

    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            ++curFrame;
            currentFrameTime = 0;
        }

        if (curFrame >= frames.size)
            curFrame = 0;
    }

    public TextureRegion getFrame() {
        return frames.get(curFrame);
    }
}
