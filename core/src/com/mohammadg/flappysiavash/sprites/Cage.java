package com.mohammadg.flappysiavash.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Cage {
    public static final int MOVE_SPEED = 1; //speed which cages move towards player (to the left)
    private static final int CAGE_HEIGHT_PADDING = 35; //minimum space between bot/top of viewport and end of cage
    private static final int CAGE_GAP = 100; //gap between top/bot cages
    public static final int BASE_POSITION = 100; //starting position of very first cage

    private Texture topCage, botCage;
    private Vector2 topCagePos, botCagePos;
    private Rectangle topCageBounds, botCageBounds;
    private Random rand;
    private int lowestOpening = 0;
    private OrthographicCamera cam;

    public Cage(OrthographicCamera cam, float x, int lowestOpening) {
        this.cam = cam;
        topCage = new Texture("topcage.png");
        botCage = new Texture("bottomcage.png");

        rand = new Random(System.currentTimeMillis());
        topCagePos = new Vector2();
        botCagePos = new Vector2();
        topCageBounds = new Rectangle();
        botCageBounds = new Rectangle();

        this.lowestOpening = lowestOpening;

        this.reposition(x);
    }

    public void update(float dt) {
       //Move x axis back
       topCagePos.x -= MOVE_SPEED;
       botCagePos.x -= MOVE_SPEED;

       //Update bounds
       topCageBounds.x = topCagePos.x;
       botCageBounds.x = botCagePos.x;
    }

    public void dispose() {
        topCage.dispose();
        botCage.dispose();
    }

    public void reposition(float x) {
        //Position
        this.topCagePos.set(BASE_POSITION + x, lowestOpening + CAGE_GAP + CAGE_HEIGHT_PADDING +
            rand.nextInt((int) (cam.viewportHeight - lowestOpening - CAGE_GAP - 2*CAGE_HEIGHT_PADDING)));
        this.botCagePos.set(BASE_POSITION + x, topCagePos.y - botCage.getHeight() - CAGE_GAP);

        //Set bounds
        this.topCageBounds.set(topCagePos.x, topCagePos.y, topCage.getWidth(), topCage.getHeight());
        this.botCageBounds.set(botCagePos.x, botCagePos.y, botCage.getWidth(), botCage.getHeight());
    }

    public boolean collides(Rectangle playerBounds) {
        return playerBounds.overlaps(this.topCageBounds) || playerBounds.overlaps(this.botCageBounds);
    }

    //Getters
    public Texture getTopCage() {
        return topCage;
    }

    public Texture getBotCage() {
        return botCage;
    }

    public Vector2 getTopCagePos() {
        return topCagePos;
    }

    public Vector2 getBotCagePos() {
        return botCagePos;
    }
}
