package com.pulsardev.components;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.pulsardev.config.Constant;

public class NumItem extends ButtonSprite {
	
	int _row;
	int _col;
	float _x;
	float _y;
	float _size;
	static float shift_row = 0;
	static float shift_col = 0;
	public NumItem(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {

		super(shift_col + pX, shift_row + pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		_x = pX;
		_y = pY;
	}
	public void setSize(float size){
		_size = size;
	}
	public void resetPosition() {
		super.setPosition(shift_col+ (_col - 1) * _size, shift_row + (_row - 1)* _size);
	}
	
	public void setRow(int row){
		_row = row;
	}
	public void setCol(int col){
		_col = col;
	}
	
	public int getRow(){
		return _row;
	}
	public int getCol(){
		return _col;
	}
	public void setshift(float col,float row){
		shift_row = row;
		shift_col = col;
		super.setPosition(shift_col + _x, shift_row + _y);
	}
	

	
}
