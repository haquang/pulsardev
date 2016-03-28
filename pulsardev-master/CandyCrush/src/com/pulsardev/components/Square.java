package com.pulsardev.components;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.pulsardev.candycrush.BaseScene;
import com.pulsardev.config.Constant;

public class Square {

	private int row,col;
	Sprite m_sprite;
	public float pos_x;
	public float pos_y;
	public Square(int i, int j, float x, float y,BaseScene scene,TextureRegion region,VertexBufferObjectManager v_bom,float size){
        this.row = j+1;
        this.col = i+1; 
        pos_x = x + size/2;
        pos_y = y + size/2;
        m_sprite = new Sprite(pos_x, pos_y, Constant.WIDTH_SQUARE, Constant.HEIGHT_SQUARE, region, v_bom);
        m_sprite.setScale(size/Constant.WIDTH_SQUARE);
        scene.attachChild(m_sprite);
    }

    public Sprite getmSprite() {
        return m_sprite;
    }

    public void setmSprite(Sprite mSprite) {
        m_sprite = mSprite;
    } 
    public int getRow(){
    	return row;
    }
    public int getCol(){
    	return col;
    }
}
