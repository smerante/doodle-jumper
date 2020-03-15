package com.studios0110.doodles.screens;

/*
Sam Merante: Splash loading screen, loads assets
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.Game;

public class Splash implements Screen
{

//    public static final String PREF_NAME = "com.studios0110.cliffJumper.hs",HIGH_SCORE = "HIGH_SCORE";
    private ShapeRenderer shapeBatch = new ShapeRenderer();
    private SpriteBatch batch = new SpriteBatch();
    private boolean[] done;
    private Stage stage;
    public static AssetManager manager = new AssetManager();
    public static OrthographicCamera camera = new OrthographicCamera();
    public static int screenW = 1920, screenH = 1080;
    private void loadSplashScreens()
    {
        Image splashScreen = new Image(new Texture("splash/splash1.png"));
        Image splashScreen2 = new Image(new Texture("splash/splash2.png"));
        splashScreen.setWidth(Gdx.graphics.getWidth());
        splashScreen.setHeight(Gdx.graphics.getHeight());
        splashScreen2.setWidth(Gdx.graphics.getWidth());
        splashScreen2.setHeight(Gdx.graphics.getHeight());
        float delayTime = 0.1f;
        splashScreen.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.fadeIn(3f* delayTime),
                Actions.delay(3f* delayTime),
                Actions.run
                        (
                                new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        done[0] = true;
                                    }
                                }
                        )
        ));

        splashScreen2.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.delay(3f* delayTime),
                Actions.fadeIn(1.5f* delayTime),
                Actions.fadeOut(1.5f* delayTime)
        ));
        stage = new Stage();
        stage.addActor(splashScreen);
        stage.addActor(splashScreen2);

    }

    @Override
    public void show() {
        camera.setToOrtho(false,screenW,screenH);
        done = new boolean[2];
        done[0] = false;
        done[1] = false;
        loadSplashScreens();
//        Preferences prefs = Gdx.app.getPreferences(Splash.PREF_NAME);
//        if(!prefs.contains(Splash.HIGH_SCORE)){
//            prefs.putInteger(Splash.HIGH_SCORE,0);
//        }
//        prefs.flush();
       //AssestManager
        manager.load("splash/splash1.png",Texture.class);
        manager.load("splash/splash2.png",Texture.class);
        manager.load("screens/Menu.png",Texture.class);
        manager.load("ui/Play.png",Texture.class);
        manager.load("ui/PlayPushed.png",Texture.class);
        //AssestManager


    }
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0,0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(manager.update())//if manager is done uploading then we can finish
            done[1] = true;
        shapeBatch.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();
        shapeBatch.begin(ShapeType.Filled);
        shapeBatch.setColor(Color.DARK_GRAY);
        shapeBatch.rect(screenW/2 - 120, 385, 200, 17);
        shapeBatch.setColor(Color.GREEN);
        shapeBatch.rect(screenW/2 - 120, 384, (manager.getProgress()*200), 20);
        shapeBatch.end();
        if(done[0] && done[1]){
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Menu());
        }


    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

}

