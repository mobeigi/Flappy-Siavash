package com.mohammadg.flappysiavash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class AssetManager {
    private BitmapFont eightBitWonder;
    private GlyphLayout glyphLayout;
    private ShaderProgram fontShader;

    public AssetManager() {
        Texture eightBitWonderTexture = new Texture(Gdx.files.internal("8bitwonder.png"), true);
        eightBitWonderTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        eightBitWonder = new BitmapFont(Gdx.files.internal("8bitwonder.fnt"), new TextureRegion(eightBitWonderTexture), false);
        glyphLayout = new GlyphLayout();

        fontShader = new ShaderProgram(Gdx.files.internal("8bitwonder.vert"), Gdx.files.internal("8bitwonder.frag"));
        if (!fontShader.isCompiled()) {
            Gdx.app.error("fontShader", "compilation failed:\n" + fontShader.getLog());
        }
    }

    public BitmapFont getEightBitWonder() {
        return eightBitWonder;
    }

    public GlyphLayout getGlyphLayout() {
        return glyphLayout;
    }

    public ShaderProgram getFontShader() {
        return fontShader;
    }

}
