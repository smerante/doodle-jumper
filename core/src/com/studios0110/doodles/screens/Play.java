package com.studios0110.doodles.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.studios0110.doodles.ui.Animator;
import com.studios0110.doodles.world.GameWorld;
import com.studios0110.doodles.world.ShapeDraw;

import java.util.ArrayList;

/**
 * Created by Sam Merante on 2019-02-23.
 */
public class Play implements NewScreenInterface, InputProcessor {

    private Texture gameScreen,floorTexture,inkLeftTexture;
    private Animation<TextureRegion> drawingHelp, goalAnimation;
    OrthographicCamera gameCamera, uiCamera;
    GameWorld gameWorld;
    Body floor, circle, goal ;
    private float inkLeft,lostInkFromShape, inkLoss, stateTime; //%
    private Sprite circleSprite;
    ShapeDraw shapeDraw;
    ArrayList<Vector2> drawingPoints;

    public Play(){
        Splash.camera.zoom=1;
        Splash.camera.update();
        Gdx.input.setInputProcessor(mp); // set the default input processor to the multiplex processor from NewScreenInterface
        Gdx.input.setCatchBackKey(true);
        mp.addProcessor(this);
        uiCamera = Splash.camera;
        gameCamera = new OrthographicCamera(Splash.screenW,Splash.screenH);
        gameCamera.setToOrtho(false, Splash.screenW,Splash.screenH);
        gameScreen = Splash.manager.get("screens/Game.png");
        floorTexture = Splash.manager.get("objects/floor_1800.png");
        inkLeftTexture = Splash.manager.get("objects/inkleft.png");
        circleSprite = new Sprite((Texture)Splash.manager.get("objects/circle_64.png"));
        drawingHelp = Animator.initAnimation("ui/DrawExample.png",8,6,25.0f);
        goalAnimation = Animator.initAnimation("ui/Goal.png",8,10,25.0f);
        circleSprite.setPosition(Splash.screenW/2 - 32, 900 -32);

        gameWorld = new GameWorld(-9.8f);
        shapeDraw = new ShapeDraw(gameWorld);
        floor = gameWorld.createBody(BodyDef.BodyType.StaticBody, new Vector2(50f, 550));
        goal = gameWorld.createBody(BodyDef.BodyType.StaticBody, new Vector2(1750,620));
        circle = gameWorld.createBody(BodyDef.BodyType.DynamicBody,new Vector2(circleSprite.getX() + circleSprite.getWidth()/2,circleSprite.getY() + circleSprite.getWidth()/2));

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius((32 / gameWorld.PIXELS_TO_METERS));
        gameWorld.createFixture(circle,0.05f,0.0f,2.25f,false,circleShape);
        gameWorld.createFixture(floor,1f,0,1f,false,gameWorld.createPolyRect(0,0,1800,120));
        gameWorld.createFixture(goal,0f,0,0f,true,gameWorld.createPolyRect(32,32,60,64));
        inkLeft = 100.0f;
        lostInkFromShape = 0.0f;
        drawingPoints = new ArrayList<Vector2>();
        inkLoss = 1.0f;
        stateTime = 0;

        gameWorld.getWorld().setContactListener(new ContactListener() {
            public void beginContact(Contact contact) {
                boolean reachedGoal = (contact.getFixtureB().getBody() == circle && contact.getFixtureA().getBody() == goal) || (contact.getFixtureA().getBody() == circle && contact.getFixtureB().getBody() == goal);
                if(reachedGoal){
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new Menu());
                }
            }

            public void endContact(Contact contact) {
                if(contact.getFixtureA().getBody() == circle || contact.getFixtureB().getBody() == circle){
                }
            }

            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
        System.out.println("LEVEL SELECTED " + LevelSelect.LEVEL_SELECTED);
    }

    @Override
    public void show() {}

    private void drawUI(){
        batch.setProjectionMatrix(Splash.camera.combined);
        shapes.setProjectionMatrix(Splash.camera.combined);

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion drawingHelpFrame = drawingHelp.getKeyFrame(stateTime,true);
        TextureRegion goalFrame = goalAnimation.getKeyFrame(stateTime,true);
        batch.begin();
        batch.draw(inkLeftTexture,50,980,0,0,(int)Math.max(inkLeftTexture.getWidth()*(inkLeft/100.0f),0),(inkLeftTexture.getHeight()));
        batch.draw(drawingHelpFrame,730,790);
        batch.draw(goalFrame,goal.getPosition().x*gameWorld.PIXELS_TO_METERS, goal.getPosition().y*gameWorld.PIXELS_TO_METERS);
        batch.end();
        shapes.begin();
        shapes.set(ShapeRenderer.ShapeType.Filled);
            shapes.setColor(Color.BLACK);
            for(int i=1; i < drawingPoints.size(); i++){
                shapes.rectLine(drawingPoints.get(i-1),drawingPoints.get(i),8f);
            }
            shapeDraw.draw(shapes);
        shapes.end();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(gameCamera.combined);
        shapes.setProjectionMatrix(gameCamera.combined);
        shapes.setAutoShapeType(true);

        batch.begin();
            batch.draw(gameScreen,0,0);
            batch.draw(floorTexture,50,550);
            circleSprite.draw(batch);
        batch.end();


//        gameWorld.renderDebug(batch);
        gameWorld.update();
        gameCamera.update();
        drawUI();
        update();
    }

    private void update(){
        circleSprite.setPosition(
                circle.getPosition().x*gameWorld.PIXELS_TO_METERS - circleSprite.getWidth()/2,
                circle.getPosition().y*gameWorld.PIXELS_TO_METERS - circleSprite.getWidth()/2);
        circleSprite.setRotation(circle.getAngle() * gameWorld.PIXELS_TO_METERS);
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

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.R){
           reset();
        }
        return false;
    }
    private void reset(){
        Splash.camera.zoom=1;
        Splash.camera.update();
        circleSprite.setPosition(Splash.screenW/2 - 32, 900 -32);
        gameWorld = new GameWorld(-9.8f);
        shapeDraw = new ShapeDraw(gameWorld);
        floor = gameWorld.createBody(BodyDef.BodyType.StaticBody, new Vector2(50f, 550));
        circle = gameWorld.createBody(BodyDef.BodyType.DynamicBody,new Vector2(circleSprite.getX() + circleSprite.getWidth()/2,circleSprite.getY() + circleSprite.getWidth()/2));
        goal = gameWorld.createBody(BodyDef.BodyType.StaticBody, new Vector2(1750,620));

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius((32 / gameWorld.PIXELS_TO_METERS));
        gameWorld.createFixture(circle,0.1f,0.1f,2.25f,false,circleShape);
        gameWorld.createFixture(floor,1f,0,1f,false,gameWorld.createPolyRect(0,0,1800,100));
        gameWorld.createFixture(goal,0f,0,0f,true,gameWorld.createPolyRect(32,32,60,64));
        inkLeft = 100.0f;
        lostInkFromShape = 0.0f;
        drawingPoints = new ArrayList<Vector2>();
        inkLoss = 2.5f;
        stateTime = 0;

        gameWorld.getWorld().setContactListener(new ContactListener() {
            public void beginContact(Contact contact) {
                boolean reachedGoal = (contact.getFixtureB().getBody() == circle && contact.getFixtureA().getBody() == goal) || (contact.getFixtureA().getBody() == circle && contact.getFixtureB().getBody() == goal);
                if(reachedGoal){
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new Menu());
                }
            }

            public void endContact(Contact contact) {
                if(contact.getFixtureA().getBody() == circle || contact.getFixtureB().getBody() == circle){
                }
            }

            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        drawingPoints.clear();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if(drawingPoints.size() > 5){
            final float[] vertecies = new float[drawingPoints.size()*2];
            int ithPoint=0;
            for(int i=0; i < drawingPoints.size(); i++){
                vertecies[ithPoint] = drawingPoints.get(i).x;
                ithPoint++;
                vertecies[ithPoint] = drawingPoints.get(i).y;
                ithPoint++;
            }
            lostInkFromShape=0.0f;
            shapeDraw.addDrawShape(vertecies);
            drawingPoints.clear();
        }else{
            if(lostInkFromShape > 0){
                inkLeft+= lostInkFromShape;
                lostInkFromShape=0.0f;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (inkLeft <= 0) return false;
        Vector3 touchPos = uiCamera.unproject(new Vector3(screenX, screenY, 0));
            if(drawingPoints.isEmpty()){
                drawingPoints.add(new Vector2(touchPos.x,touchPos.y));
            }else{
                float a = drawingPoints.get(drawingPoints.size()-1).x - touchPos.x;
                float b = drawingPoints.get(drawingPoints.size()-1).y - touchPos.y;
                float distance = (float)(Math.sqrt(a*a + b*b));
                if(distance >= 10){
                    inkLeft-=inkLoss*(distance/20.0f);
                    lostInkFromShape+=inkLoss*(distance/20.0f);
                    drawingPoints.add(new Vector2(touchPos.x,touchPos.y));
                }
            }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
