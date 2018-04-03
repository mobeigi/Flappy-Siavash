package com.mohammadg.flappysiavash.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Cage {
    private static final int MOVE_SPEED = 1; //speed which cages move towards player (to the left)
    private static final int FLUCTUATION = 130;
    private static final int CAGE_GAP = 100; //gap between top/bot cages
    private static final int LOWEST_OPENING = 120; //lowest position possible for opening
    public static final int BASE_POSITION = 100; //starting position of very first cage

    private Texture topCage, botCage;
    private Vector2 topCagePos, botCagePos;
    private Rectangle topCageBounds, botCageBounds;
    private Random rand;

    public Cage(float x) {
        topCage = new Texture("topcage.png");
        botCage = new Texture("bottomcage.png");

        rand = new Random();
        topCagePos = new Vector2(0,0);
        botCagePos = new Vector2(0,0);
        topCageBounds = new Rectangle();
        botCageBounds = new Rectangle();

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
        this.topCagePos.set(BASE_POSITION + x, rand.nextInt(FLUCTUATION) + CAGE_GAP + LOWEST_OPENING);
        this.botCagePos.set(BASE_POSITION + x, topCagePos.y - CAGE_GAP - botCage.getHeight());

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
