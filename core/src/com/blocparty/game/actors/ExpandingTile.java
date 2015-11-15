package com.blocparty.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.blocparty.game.utilities.Constants;

import java.util.List;
import java.util.Random;

/**
 * Created by sfotm on 11/14/15.
 */
public class ExpandingTile extends Actor {

    private float timeElapsed;
    private float expandTime;

    private int tileHeight;
    private int tileWidth;

    private float xOriginal;
    private float yOriginal;

    private boolean active;
    private Color color;

    Random rand;

    public ExpandingTile(float xPosition, float yPosition, int tileWidth, int tileHeight){
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;

        xOriginal = xPosition;
        yOriginal = yPosition;

        setPosition(xOriginal, yOriginal);

        active = false;

        setTouchable(Touchable.enabled);

        rand = new Random();
//        addListener(tileListener);
    }

    public void activateTile(float expandTime){
        this.expandTime = expandTime;

        active = true;
        timeElapsed = 0;
        color = randomColor();
    }

    public ParticleEffect deactivateTile(){
        active = false;
//        this.setWidth(0);
//        this.setHeight(0);
//        this.setBounds(getX(), getY(), getWidth(), getHeight());
        // TODO: this might be a good place to launch some effects
        return spawnParticles();
    }

    // add the time elapsed to the total and adjust width and height
    public void update(float delta){
        timeElapsed += delta;
        this.setWidth(getScale() * tileWidth);
        this.setHeight(getScale() * tileHeight);
        this.setBounds(xOriginal - getWidth() / 2, yOriginal - getHeight() / 2, getWidth(), getHeight());
    }

    public float getScale(){
        return (float)Math.sqrt(Math.min(timeElapsed / expandTime, 1.0f));
    }

    public boolean isLoser(){
        return timeElapsed > expandTime;
    }

    public boolean isActive() {
        return active;
    }

    public void draw(ShapeRenderer shapeBatch){
        //shapeBatch.circle(this.getX(), this.getY(), getScale() * (tileWidth / 2));
        shapeBatch.setColor(Color.DARK_GRAY);
        shapeBatch.rect(this.getX() + 30 * getScale(), this.getY() - 30 * getScale(), getWidth(), getHeight());
        shapeBatch.setColor(color);
        shapeBatch.rect(this.getX(), this.getY(), getWidth(), getHeight());
    }

    private Color randomColor(){
        return Constants.COLOR_LIST.get(rand.nextInt(Constants.COLOR_LIST.size()));
    }

    private ParticleEffect spawnParticles(){
        // TODO: load and spawn particles as a part of the stage
        ParticleEffect pe = new ParticleEffect();
        pe.load(Gdx.files.internal("pixelBurst.party"), Gdx.files.internal(""));
        ParticleEmitter emitter = pe.getEmitters().first();
        emitter.setPosition(xOriginal, yOriginal);
        emitter.getTint().setColors(new float[]{color.r, color.g, color.b});
        emitter.getScale().setHigh(getScale() * 150, getScale() * 200);
        pe.start();
        return pe;
    }
}
