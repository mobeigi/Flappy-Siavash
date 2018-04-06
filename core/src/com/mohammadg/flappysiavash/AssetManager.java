package com.mohammadg.flappysiavash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class AssetManager {

    private BitmapFont verdana;
    private BitmapFont eightBitWonder;
    private GlyphLayout glyphLayout;
    private ShaderProgram fontShader;

    public AssetManager() {
        Texture eightBitWonderTexture = new Texture(Gdx.files.internal("fonts/8bitwonder.png"), true);
        eightBitWonderTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        eightBitWonder = new BitmapFont(Gdx.files.internal("fonts/8bitwonder.fnt"), new TextureRegion(eightBitWonderTexture), false);

        Texture verdanaTexture = new Texture(Gdx.files.internal("fonts/verdana.png"), true);
        verdanaTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        verdana = new BitmapFont(Gdx.files.internal("fonts/verdana.fnt"), new TextureRegion(verdanaTexture), false);

        glyphLayout = new GlyphLayout();

        fontShader = new ShaderProgram(Gdx.files.internal("shaders/fontshader.vert"), Gdx.files.internal("shaders/fontshader.frag"));
        if (!fontShader.isCompiled()) {
            Gdx.app.error("fontShader", "compilation failed:\n" + fontShader.getLog());
        }
    }

    public BitmapFont getEightBitWonder() {
        return eightBitWonder;
    }

    public BitmapFont getVerdana() {
        return verdana;
    }

    public GlyphLayout getGlyphLayout() {
        return glyphLayout;
    }

    public ShaderProgram getFontShader() {
        return fontShader;
    }

}
