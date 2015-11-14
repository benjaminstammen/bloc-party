package com.blocparty.game.screens;



import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blocparty.game.utilities.GameClickListener;

public class GameplayScreen implements Screen {

    Stage stage;
    Skin skin;

    SpriteBatch batch;
    ShapeRenderer shapeBatch;

    private final static int BTN_PANEL_LEFT_MARGIN = 200;
    private final static int BTN_PANEL_BOTTOM = 200;

    private int width;
    private int height;
    private int boxWidth;
    private int boxHeight;


    public GameplayScreen() {
        makeItFit();
    }

    @Override
    public void resize(int width, int height) {
        makeItFit();
    }

    @Override
    public void render(float arg0) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        shapeBatch.begin(ShapeType.Line);

        shapeBatch.setColor(Color.BLACK);
        //shapeBatch.rect(0, 0, 100, 50);

        int xDist = 0;
        int yDist = 0;
        int count = 0;
        while (xDist < width) {
            //x, y, x2, y2
            //shapeBatch.line(xDist, yDist, );
            yDist = 0;
            while (yDist < height) {
                //shapeBatch.line(xDist, yDist, xDist, yDist + boxHeight);
                shapeBatch.rect(xDist, yDist, boxWidth, boxHeight);
                yDist += boxHeight;
                count++;
            }
            xDist += boxWidth;
        }
        System.out.println(count);


        shapeBatch.end();



//		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//		stage.draw();
    }

    private void makeItFit() {


        batch = new SpriteBatch();
        shapeBatch = new ShapeRenderer();

        //hud = GameScreenHUD.create(round);


        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        boxWidth = width / 4;
        boxHeight = height / 2;





        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GameClickListener());
        Gdx.input.setInputProcessor(multiplexer);



//		stage = new Stage();
//		Gdx.input.setInputProcessor(stage);
//		skin = new Skin(Gdx.files.internal("data/uiskin.json"));

    }



    //===========================================

    @Override
    public void dispose() {
    }
    @Override
    public void hide() {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void show() {
    }
}

