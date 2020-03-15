package com.studios0110.doodles.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


/**
 * Created by Sam Merante on 2019-02-23.
 */
public class Loading implements Screen {
    private boolean doneLoading;
    private AssetManager manager = Splash.manager;
    private ShapeRenderer shapeBatch = new ShapeRenderer();
    private SpriteBatch batch = new SpriteBatch();
    private OrthographicCamera camera = Splash.camera;
    private int screenW = Splash.screenW, screenH = Splash.screenH;
    private String loadingScreen;
    private Sprite splashScreen;
    public static boolean alreadyLoadedGame = false, alreadyLoadedLevelSelect = false;
    public static final String GAME = "game", LEVEL_SELECT="levelSelect";
    public Loading(String loadingScreen){
        this.loadingScreen = loadingScreen;
        doneLoading = false;
        splashScreen = new Sprite((Texture)Splash.manager.get("splash/splash2.png"));
        splashScreen.setSize(screenW,screenH);
        splashScreen.setPosition(0,0);
        if(loadingScreen.equalsIgnoreCase(GAME)){
            manager.load("screens/Game.png",Texture.class);
            manager.load("objects/circle_64.png",Texture.class);
            manager.load("objects/inkleft.png",Texture.class);
            manager.load("objects/floor_1800.png",Texture.class);
            manager.load("ui/DrawExample.png",Texture.class);
            manager.load("ui/Goal.png",Texture.class);
        }
        if(loadingScreen.equalsIgnoreCase(LEVEL_SELECT)){
            manager.load("screens/Levels.png",Texture.class);
            for(int i=0; i < LevelSelect.LEVELS; i ++){
                manager.load("ui/"+(i+1)+".png",Texture.class);
                manager.load("ui/"+(i+1)+"Pushed.png",Texture.class);
            }
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0,0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(manager.update())//if manager is done uploading then we can finish
            doneLoading = true;
        shapeBatch.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
            splashScreen.draw(batch);
        batch.end();

        shapeBatch.begin(ShapeRenderer.ShapeType.Filled);
        shapeBatch.setColor(Color.DARK_GRAY);
        shapeBatch.rect(screenW/2 - 120, 385, 200, 17);
        shapeBatch.setColor(Color.GREEN);
        shapeBatch.rect(screenW/2 - 120, 384, (manager.getProgress()*200), 20);
        shapeBatch.end();
        if(doneLoading){
            if(loadingScreen.equalsIgnoreCase(GAME)){
                Loading.alreadyLoadedGame = true;
                ((com.badlogic.gdx.Game)Gdx.app.getApplicationListener()).setScreen(new Play());
            }
            if(loadingScreen.equalsIgnoreCase(LEVEL_SELECT)){
                Loading.alreadyLoadedLevelSelect = true;
                ((com.badlogic.gdx.Game)Gdx.app.getApplicationListener()).setScreen(new LevelSelect());
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
