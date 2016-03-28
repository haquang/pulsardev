package com.pulsardev.components;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import com.pulsardev.config.Constant;
import com.pulsardev.sixteenpuzzle.BaseScene;
import com.pulsardev.sixteenpuzzle.GameScene;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

public class NumSprite{

	// Position in Matrix
	int _row;
	int _col;
	public static boolean istouchable = true;
	// Display Position
	int _pX;
	int _pY;

	// Resource ID
	int _resource_ID;

	// Sprite to display
	Sprite m_sprite;

	float _angle = 0;
	public static boolean _rotate_finish = false;
	// Size
	public static int _size;
	// Texture region
	public static TextureRegion[] m_texture_region;
	// VertexBufferObjectManager
	public static VertexBufferObjectManager m_vbom;

	// GameScene
	public static GameScene m_scene;

	// Game State
	public static Matrix2D m_game_state;
	
	// GameActivity
	public static BaseGameActivity m_activity;
	// Touch processing

	Point touchDown = new Point(0,0);
	Point touchUp = new Point(0,0);
	
	public NumSprite(int pX, int pY, int resource_ID){
		_resource_ID = resource_ID;
		_pX = pX;
		_pY = pY;
		m_sprite = new ButtonSprite(_pX,_pY,m_texture_region[_resource_ID],m_vbom){

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				touchProcess(pSceneTouchEvent, (int) pTouchAreaLocalX, (int) pTouchAreaLocalY);
				return true;
			}  
		};
		m_sprite.setScale(_size/m_texture_region[_resource_ID].getHeight());
		m_scene.attachChild(m_sprite);
		m_scene.registerTouchArea(m_sprite);
	}
	
	

	public void touchProcess(TouchEvent event, int pX, int pY){
		MotionEvent motionEvent = event.getMotionEvent();
		switch (event.getAction()){
		case TouchEvent.ACTION_DOWN:
//			if (istouchable){
//				if (checkMoveAble()){
//					m_scene.onTouchMovable(this);
//				} 
//			}
			m_scene.touchDown.x = (int) motionEvent.getX();
			m_scene.touchDown.y = (int) motionEvent.getY();
			m_scene.touchedSprite = this;
			break;
		case TouchEvent.ACTION_UP:
			m_scene.touchUp.x = (int) motionEvent.getX();
			m_scene.touchUp.y = (int) motionEvent.getY();
			m_scene.onTouchMovable(this);
			break;
		default:
			break;
		}
	}

	public void setPosition(Point p){
		this._pX = p.x;
		this._pY = p.y;
		m_sprite.setPosition(_pX, _pY);
	}

	public void setMatrixPosition(int i,int j){
		_row = i;
		_col = j;
	}

	public Point getMatrixPosition(){
		return new Point(_row,_col);
	}

	public int getResourceID(){
		return _resource_ID;
	}
	public int getPx(){
		return _pX;
	}
	public int getPy(){
		return _pY;
	}
	public int getRow(){
		return _row;
	}
	public int getCol(){
		return _col;
	}
	
	public Sprite getSprite(){
		return m_sprite;
	}
	private boolean checkMoveAble() {
		
	//	m_game_state.showMatrix2D();
//		Point p0 = m_game_state.getZeroXY();
//		if ((_row == p0.x) && (_col == p0.y))
//			return false;
//		if (((Math.abs(_row - p0.x) <= 1) && (_col == p0.y)) || ((Math.abs(_col - p0.y) <= 1) && (_row == p0.x)))
//			return true;
//		else  
//			return false;
		return false;

	}	
	
	public void rotateSprite(){
		_angle = 0;
		_rotate_finish = false;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (_angle < 90){
					try {
						Thread.sleep(10);
						_angle += 5;
						m_sprite.setRotation(_angle);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				m_sprite.setVisible(false);
			}
		}).start();
	}
	public boolean getRotationState(){
		return _rotate_finish;
	}
	public void destroy(){
		m_activity.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (m_sprite != null){
					m_scene.unregisterTouchArea(m_sprite);
					//m_scene.detachChild(m_sprite);
					m_sprite.detachSelf();
					if (!m_sprite.isDisposed())
						m_sprite.dispose();
				}
			}
		});	
	}

}
