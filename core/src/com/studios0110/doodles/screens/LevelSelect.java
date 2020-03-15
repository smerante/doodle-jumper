package com.studios0110.doodles.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.studios0110.doodles.ui.GameButton;

import java.util.ArrayList;

/**
 * Created by Sam Merante on 2019-02-25.
 */
public class LevelSelect implements NewScreenInterface {
    Texture levelSelectBG;
    ArrayList<GameButton> levelButtons;
    public static final int LEVELS = 2;
    public static int LEVEL_SELECTED = 0;
    public LevelSelect(){
        Splash.camera.zoom=1;
        Splash.camera.update();
        Gdx.input.setInputProcessor(mp); // set the default input processor to the multiplex processor from NewScreenInterface
        Gdx.input.setCatchBackKey(true);
        levelSelectBG = Splash.manager.get("screens/Levels.png");
        levelButtons = new ArrayList<GameButton>();
        for(int i=0 ; i<LEVELS; i++){
            GameButton temp = new GameButton((i+1) + "",new Vector2(320 + (i*(64+90)),730));
            levelButtons.add(temp);
            mp.addProcessor(new GestureDetector(temp));
        }
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
            batch.draw(levelSelectBG,0,0);
            for(int i=0; i<levelButtons.size();i++){
                levelButtons.get(i).draw(batch);
            }
        batch.end();
        update();
    }

    private void update(){
        for(int i=0 ; i<LEVELS; i++){
            if(levelButtons.get(i).clicked){
                levelButtons.get(i).resetButton();
                LevelSelect.LEVEL_SELECTED = (i+1);
                if(!Loading.alreadyLoadedGame) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Loading(Loading.GAME));
                }else{
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Play());
                }
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
