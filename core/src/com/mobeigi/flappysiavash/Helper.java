package com.mobeigi.flappysiavash;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Helper {

    public static void PrepareDrawText(SpriteBatch sb, BitmapFont bitmapFont, GlyphLayout glyphLayout, String text, Color color, float scale) {
        bitmapFont.setColor(color);
        bitmapFont.getData().setScale(scale);
        glyphLayout.setText(bitmapFont, text);
    }

    public static void DrawText(SpriteBatch sb, BitmapFont bitmapFont, GlyphLayout glyphLayout, ShaderProgram fontShader, float x, float y) {
        sb.setShader(fontShader);
        bitmapFont.draw(sb, glyphLayout, x, y);
        sb.setShader(null);
    }
}
