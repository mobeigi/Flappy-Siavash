package com.mohammadg.flappysiavash.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.mohammadg.flappysiavash.FlappySiavashGame;
import com.mohammadg.flappysiavash.sprites.Cage;
import com.mohammadg.flappysiavash.sprites.Ground;
import com.mohammadg.flappysiavash.sprites.Siavash;

import java.util.ArrayList;

public class PlayState extends State {
    private static final int NUM_CAGES_PER_VIEWPORT = 3; //number of cages that cag show at once in 1 viewport
    private static final float BACKGROUND_DELTA_X = 0.5f; //how much to scroll background each update
    private static final float GROUND_DELTA_X = (float)Cage.MOVE_SPEED; //how much to scroll ground each update
    private static final float GAME_OVER_COOLDOWN_TIME = 1.0f;
    private static final float GAME_OVER_FLASH_ALPHA_START = 0.8f;
    private static final float GAME_OVER_FLASH_ALPHA_DECREMENT_STEP = 0.05f;

    public enum Stage {
        MAIN, //Actively playing
        CRASHING, //Collided with object and falling towards ground
        GAMEOVER_COOLDOWN, //Collided with ground and waiting
        GAMEOVER_POST, //Game is now over, show game over menu
    }

    private Texture background;
    private float backgroundDx;
    private Ground ground;
    private float groundDx;
    private Siavash siavash;
    private float gameOverFlashDt;
    private ArrayList<Cage> cages;
    private int score;
    private ShaderProgram fontShader;
    private BitmapFont eightBitWonder;
    private GlyphLayout glyphLayout;
    private Stage stage;

    private GameOverState gameOverState;
    private float gameOverCooldownCounter;


    protected PlayState(GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, FlappySiavashGame.WIDTH/2, FlappySiavashGame.HEIGHT/2);

        background = new Texture("bg.png");
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat); //set texture to repeat on x
        backgroundDx = 0.0f;

        ground = new Ground();
        ground.getGround().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat); //set texture to repeat on x
        groundDx = 0.0f;

        siavash = new Siavash(cam, (int)(cam.viewportWidth*0.20), (int)(cam.viewportHeight*0.75)); //starting position
        gameOverFlashDt = GAME_OVER_FLASH_ALPHA_START;

        cages = new ArrayList<Cage>();
        int basePos = (int)cam.viewportWidth; //Start at viewport width to create initial opening

        for (int i = 0; i < NUM_CAGES_PER_VIEWPORT; ++i) {
            Cage cage = new Cage(cam, basePos, (int) ground.getDimensions().y);
            cages.add(cage);
            basePos += cage.getTopCage().getWidth() + (cam.viewportWidth / NUM_CAGES_PER_VIEWPORT);
        }

        score = 0;

        fontShader = new ShaderProgram(Gdx.files.internal("8bitwonder.vert"), Gdx.files.internal("8bitwonder.frag"));
        if (!fontShader.isCompiled()) {
            Gdx.app.error("fontShader", "compilation failed:\n" + fontShader.getLog());
        }

        Texture eightBitWonderTexture = new Texture(Gdx.files.internal("8bitwonder.png"), true);
        eightBitWonderTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        eightBitWonder = new BitmapFont(Gdx.files.internal("8bitwonder.fnt"), new TextureRegion(eightBitWonderTexture), false);

        glyphLayout = new GlyphLayout();

        stage = Stage.MAIN;

        gameOverState = new GameOverState(gsm);

        gameOverCooldownCounter = 0.0f;
    }

    @Override
    protected void handleInput() {
        if (stage == Stage.MAIN) {
            if (Gdx.input.justTouched())
                siavash.jump();
        }
    }

    @Override
    public void update(float dt) {
        //Handle input
        handleInput();

        if (stage == Stage.MAIN) {
            //Perform all updates
            siavash.update(dt);

            //Cage logic
            for (Cage cage : cages) {
                cage.update(dt);

                //If cage has moved off the viewport, reposition it
                if (cage.getTopCagePos().x + cage.getTopCage().getWidth() < 0) {
                    cage.reposition(cam.viewportWidth);
                }

                //Check if we just cleared a cage (are past it)
                if (siavash.getPosition().x + (siavash.getTextureRegion().getRegionWidth()/2) == cage.getTopCagePos().x + cage.getTopCage().getWidth()) {
                    //Play victory sound and increment score
                    siavash.playChirpSound();
                    ++score;
                }
            }

            //Collision detection
            if (playerCollides()) {
                siavash.playCrySound();
                stage = Stage.CRASHING;
            }

            //Update scrolling background dx values
            backgroundDx += BACKGROUND_DELTA_X;
            groundDx += GROUND_DELTA_X;
        }
        else if (stage == Stage.CRASHING) {
            //Change dt for flash effect
            gameOverFlashDt -= GAME_OVER_FLASH_ALPHA_DECREMENT_STEP;

            if (playerCollidesGround()) {
                stage = Stage.GAMEOVER_COOLDOWN;
            }
            else {
                //Update siavash until gravity forces it to collide with ground
                siavash.update(dt);
            }
        }
        else if (stage == Stage.GAMEOVER_COOLDOWN) {
            //Change dt for flash effect (as stages overlap)
            gameOverFlashDt -= GAME_OVER_FLASH_ALPHA_DECREMENT_STEP;

            //Check if we should move onto post stage
            gameOverCooldownCounter += dt;
            if (gameOverCooldownCounter > GAME_OVER_COOLDOWN_TIME)
                stage = Stage.GAMEOVER_POST;
        }
        else if (stage == Stage.GAMEOVER_POST) {
            //todo
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        //Draw scrolling background
        sb.draw(background, cam.position.x - (cam.viewportWidth/2), 0,
                (int) (background.getWidth() + backgroundDx), background.getHeight(),
                background.getWidth(), background.getHeight());

        sb.draw(siavash.getTextureRegion(), siavash.getPosition().x, siavash.getPosition().y);

        for (Cage cage : cages) {
            sb.draw(cage.getTopCage(), cage.getTopCagePos().x, cage.getTopCagePos().y);
            sb.draw(cage.getBotCage(), cage.getBotCagePos().x, cage.getBotCagePos().y);
        }

        //Draw scrolling ground
        sb.draw(ground.getGround(), cam.position.x - (cam.viewportWidth/2), 0,
                (int) (ground.getGround().getWidth() + groundDx), ground.getGround().getHeight(),
                ground.getGround().getWidth(), ground.getGround().getHeight());


        //Draw score on screen using font shader
        if (stage == Stage.MAIN || stage == Stage.CRASHING) {
            sb.setShader(fontShader);

            //Replace number 0 in score with letter O as the 0 looks like an 8
            String scoreStr = Integer.toString(score).replace('0', 'O');

            //Draw score twice, white over black to simulate some bottom-right corner drop shadow
            eightBitWonder.getData().setScale(0.70f);
            eightBitWonder.setColor(Color.BLACK);
            glyphLayout.setText(eightBitWonder, scoreStr);
            eightBitWonder.draw(sb, glyphLayout, cam.viewportWidth / 2 - glyphLayout.width / 2, cam.viewportHeight * 0.95f);

            eightBitWonder.getData().setScale(0.65f);
            eightBitWonder.setColor(Color.WHITE);
            glyphLayout.setText(eightBitWonder, scoreStr);
            eightBitWonder.draw(sb, glyphLayout, cam.viewportWidth / 2 - glyphLayout.width / 2, cam.viewportHeight * 0.95f);

            sb.setShader(null);
        }

        //Draw white flash (fade out from white) on screen as we crash
        if (stage != Stage.MAIN && gameOverFlashDt >= 0) {
            Texture texture = new Texture("white1x1pixel.png");
            Color c = sb.getColor();
            sb.setColor(c.r, c.g, c.b, gameOverFlashDt);
            sb.draw(texture, 0, 0, (int) cam.viewportWidth, (int) cam.viewportHeight);
            sb.setColor(c);
        }
        sb.end();

        //todo

        if (stage == Stage.GAMEOVER_POST) {
            gameOverState.render(sb);
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        ground.dispose();
        siavash.dispose();

        for (Cage cage : cages)
            cage.dispose();

        gameOverState.dispose();
    }


    //Collision detection
    public boolean playerCollides() {
        return playerCollidesGround() || playerCollidesCage();
    }

    public boolean playerCollidesGround() {
        return ground.collides(siavash.getBounds());
    }

    public boolean playerCollidesCage() {
        for (Cage cage : cages) {
            if (cage.collides(siavash.getBounds()))
                return true;
        }

        return false;
    }

}
