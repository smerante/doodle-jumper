package com.studios0110.doodles.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.studios0110.doodles.ui.GameButton;

/**
 * Created by Sam Merante on 2019-02-23.
 */
public class Menu implements NewScreenInterface {

    private float width = Splash.screenW, height = Splash.screenH;
    private Texture menuScreen;
    private GameButton playButton;
    public Menu(){
        Splash.camera.zoom=1;
        Splash.camera.update();
        Gdx.input.setInputProcessor(mp); // set the default input processor to the multiplex processor from NewScreenInterface
        Gdx.input.setCatchBackKey(true);
        menuScreen = Splash.manager.get("screens/Menu.png");
        playButton = new GameButton("Play",new Vector2(750f,410f));
        mp.addProcessor(new GestureDetector(playButton));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(Splash.camera.combined);
        shapes.setProjectionMatrix(Splash.camera.combined);
        shapes.setAutoShapeType(true);
        batch.begin();
            batch.draw(menuScreen,0,0);
            playButton.draw(batch);
        batch.end();
        update();
    }

    private void update(){
        if(playButton.clicked){
            playButton.resetButton();
            if(!Loading.alreadyLoadedLevelSelect){
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Loading(Loading.LEVEL_SELECT));
            }else{
                ((Game)Gdx.app.getApplicationListener()).setScreen(new LevelSelect());
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
