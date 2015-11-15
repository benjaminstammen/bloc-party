package com.blocparty.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

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

//    private static InputListener tileListener = new InputListener(){
//        @Override
//        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//            ExpandingTile tile = (ExpandingTile)event.getTarget();
//            tile.deactivateTile();
//            return true;
//        }
//    };

    public ExpandingTile(float xPosition, float yPosition, int tileWidth, int tileHeight){
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;

        xOriginal = xPosition;
        yOriginal = yPosition;

        setPosition(xOriginal, yOriginal);

        active = false;

        setTouchable(Touchable.enabled);
//        addListener(tileListener);
    }

    public void activateTile(float expandTime){
        this.expandTime = expandTime;

        active = true;
        timeElapsed = 0;
        this.color = getColor();
    }

    public void deactivateTile(){
        active = false;
//        this.setWidth(0);
//        this.setHeight(0);
//        this.setBounds(getX(), getY(), getWidth(), getHeight());
        // TODO: this might be a good place to launch some effects
        spawnParticles();
    }

    // add the time elapsed to the total and adjust width and height
    public void update(float delta){
        timeElapsed += delta;
        this.setWidth(getScale() * tileWidth);
        this.setHeight(getScale() * tileHeight);
        this.setBounds(xOriginal - getWidth()/2, yOriginal - getHeight()/2, getWidth(), getHeight());
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
        shapeBatch.rect(this.getX()/* - getWidth()/2*/, this.getY()/* - getHeight()/2*/, getWidth(), getHeight());
    }

    private Color randomColor(){
        // TODO: create a random assortment of colors to assign
        return Color.BLACK;
    }

    private void spawnParticles(){
        // TODO: load and spawn particles as a part of the stage
    }
}
