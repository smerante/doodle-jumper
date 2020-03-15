package com.studios0110.doodles.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Sam Merante on 2019-02-23.
 */
public class GameWorld {
    World world;
    Box2DDebugRenderer debugRenderer;

    Matrix4 debugMatrix;
    public final float PIXELS_TO_METERS = 64; //Divide by pixels for physics, multiply back for graphics translations

    public GameWorld(float gravity){
        world = new World(new Vector2(0, gravity),true);
        debugRenderer = new Box2DDebugRenderer();
    }

    public boolean isLocked(){
        return world.isLocked();
    }

    public PolygonShape createPolyRect(float x, float y, float w, float h){
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(
                new float[]{
                        x/PIXELS_TO_METERS, y/PIXELS_TO_METERS,
                        x/PIXELS_TO_METERS,(y+h)/PIXELS_TO_METERS,
                        (x+w)/PIXELS_TO_METERS,(y+h)/PIXELS_TO_METERS,
                        (x+w)/PIXELS_TO_METERS,y/PIXELS_TO_METERS
                }
        );
        return polygonShape;
    }

    public World getWorld(){
        return this.world;
    }
    public PolygonShape createPolyShape(float[] verticies){
        PolygonShape polygonShape = new PolygonShape();
        for(int i=0; i < verticies.length; i++){
            verticies[i] = verticies[i]/PIXELS_TO_METERS;
        }
        polygonShape.set(verticies);
        return polygonShape;
    }

    public Body createBody(BodyDef.BodyType type, Vector2 pos){
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.type = type;
        bodyDefinition.position.set( pos.x / PIXELS_TO_METERS, pos.y / PIXELS_TO_METERS);
        return world.createBody(bodyDefinition);
    }

    public void createFixture(Body body, float friction, float restitution, float density, boolean isSensor, Shape shape){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.density = density;
        fixtureDef.isSensor = isSensor;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void update(){
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    public void renderDebug(SpriteBatch batch){
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);
        debugRenderer.render(world, debugMatrix);
    }

}
