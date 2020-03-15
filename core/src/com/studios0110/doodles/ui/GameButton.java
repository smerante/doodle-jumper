package com.studios0110.doodles.ui;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.studios0110.doodles.screens.Splash;

/*
 ********************************************* Class Description***********************************************
	Custom Button to take a texture(clicked/normal) for an image, and to recieve input from gestures
 ********************************************* Class Description***********************************************
 */
public class GameButton implements GestureListener{
    public Texture button,buttonPushed;
    public Vector2 location;
    int width ,height;
    public boolean touched,clicked,holding,visible;
    Vector3 touchPos;

    public GameButton(String textureName, Vector2 l){
        this.location = l;
        button = Splash.manager.get("ui/"+textureName+".png");
        buttonPushed = Splash.manager.get("ui/"+textureName+"Pushed.png");
        width = buttonPushed.getWidth();
        height = buttonPushed.getHeight();
        touchPos = new Vector3(-50,-50,0);
        visible = true;
    }

    public void draw(SpriteBatch batch){
        if(visible){
            if(touched)
                batch.draw(buttonPushed,location.x,location.y);
            else
                batch.draw(button,location.x,location.y);
            //System.out.println(clicked);
        }
    }

    public void resetButton(){
        touched = false;
        clicked = false;
        holding = false;
        touchPos = new Vector3(-50,-50,0);
    }



    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        if(visible){
            touchPos.set(x,y,0);
            Splash.camera.unproject(touchPos);
            if(touchPos.x < location.x + width && touchPos.x > location.x && touchPos.y > location.y && touchPos.y < location.y + height){
                touched=true;
                holding=true;
            }
            else{
                touched=false;
                holding=false;
            }
        }
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if(visible){
            touchPos.set(x,y,0);
            Splash.camera.unproject(touchPos);
            if(touchPos.x < location.x + width && touchPos.x > location.x && touchPos.y > location.y && touchPos.y < location.y + height){
                clicked=true;
            }
            else{
                touched=false;clicked=false;
            }
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        if(visible){
            touchPos.set(x,y,0);
            Splash.camera.unproject(touchPos);
            holding = touchPos.x < location.x + width && touchPos.x > location.x && touchPos.y > location.y && touchPos.y < location.y + height;
        }
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if(visible){
            if(touchPos.x < location.x + width && touchPos.x > location.x && touchPos.y > location.y && touchPos.y < location.y + height)
            {
                clicked=true;
            }else{
                touched=false;clicked=false;
            }
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if(visible){
            if(touchPos.x < location.x + width && touchPos.x > location.x && touchPos.y > location.y && touchPos.y < location.y + height)
            { //if you were previously touching the button you can pan accross it
                touchPos.set(x,y,0);
                Splash.camera.unproject(touchPos);
                if(touchPos.x < location.x + width && touchPos.x > location.x && touchPos.y > location.y && touchPos.y < location.y + height){
                    touched=true;
                    holding=true;
                }
                else{
                    touched=false;
                    holding=false;
                }
            }
        }

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        if(visible){
            if(touchPos.x < location.x + width && touchPos.x > location.x && touchPos.y > location.y && touchPos.y < location.y + height)
            {
                touchPos.set(x,y,0);
                Splash.camera.unproject(touchPos);
                if(touchPos.x < location.x + width && touchPos.x > location.x && touchPos.y > location.y && touchPos.y < location.y + height){
                    clicked=true;
                }
                else{
                    touched=false;clicked=false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
                         Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

}
