package com.studios0110.doodles.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.studios0110.doodles.screens.Splash;

/**
 * Created by Sam Merante on 2019-02-24.
 */
public class Animator {
    public static Animation initAnimation(String sheet, int cols, int rows, float framesPerSecond){
        Texture spriteSheet = Splash.manager.get(sheet);
        TextureRegion[][] temp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/cols, spriteSheet.getHeight()/rows);
        TextureRegion[] tempAnim = new TextureRegion[cols*rows];
        int index = 0;
        for(int i = 0; i < rows; i ++) {
            for (int j = 0; j < cols; j++) {
                tempAnim[index++] = temp[i][j];
            }
        }
        return new Animation(1.0f/framesPerSecond,tempAnim);
    }
}
