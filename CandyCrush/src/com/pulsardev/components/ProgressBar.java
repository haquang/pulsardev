package com.pulsardev.components;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.pulsardev.candycrush.GameScene;
import com.pulsardev.config.Constant;

import android.util.Log;

public class ProgressBar {
	
    boolean isPause = false;
    boolean isStop = false;
    Rectangle m_rectangle;
    
    int _pX;
    int _pY;
    
    int total_time = -1;
    int current_time = 0;
    float rect_width = 0;
    float rect_height = 0;
    int _width;
    
    public static GameScene _scene;
	Sprite m_sprite;
	TextureRegion m_texture_region;
	VertexBufferObjectManager m_vbom;
	Thread _thread;
	public ProgressBar(int x,int y,TextureRegion region,VertexBufferObjectManager vbom,int width){
		m_texture_region = region;
		m_vbom = vbom;
		_pX = x;
		_pY = y;
		_width = width;  
		m_sprite = new Sprite(_pX, _pY, m_texture_region, m_vbom);
		m_sprite.setScale(_width/m_sprite.getWidth());

		rect_width = _width;
		rect_height = m_sprite.getHeight() * m_sprite.getScaleY();
		m_rectangle = new Rectangle(_pX, _pY, rect_width, rect_height, m_vbom);
		m_rectangle.setColor(1f, 0.01f, 0.02f);
		_scene.attachChild(m_rectangle);
		_scene.attachChild(m_sprite);
		_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop && current_time > 0) {
                    if (!isPause) {
                        try {
                            Thread.sleep(1000);
                            current_time--;
                            updateProgressBar();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                
                m_rectangle.setVisible(false);
                if (current_time <= 0)
                    _scene.timeOut();
            }
        });
	}
	
	public void setTotalTime(int t){
		total_time = t;
        current_time = total_time;
        updateProgressBar();
        if (!_thread.isAlive())
			 _thread.start();
	}
	public void reset(){
		current_time = total_time;
		updateProgressBar();
	}
	public int getCurrentTime(){
		return current_time;
	}
	
	public void stop(){
		isStop = true;
		
	}
	public void interrupt(){
		 if (_thread.isAlive())
			 _thread.destroy();
	}
	public void pause() {
		isPause = true;
	}
	
	public void resume(){
		isPause = false;
	}
	
	public void start() {

        if (total_time < 0)
            return;
        m_rectangle.setVisible(true);
        current_time = total_time;
        updateProgressBar();
        if (!_thread.isAlive()){
        	_thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!isStop && current_time > 0) {
                        if (!isPause) {
                            try {
                                Thread.sleep(1000);
                                current_time--;
                                updateProgressBar();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }                  
                    m_rectangle.setVisible(false);
                    if (current_time <= 0)
                        _scene.timeOut();
                }
            });
        	_thread.start();
        } 
    }
 
    public void updateProgressBar() {
   // 	Log.i(Constant.TAG, "TIME: " +String.valueOf(current_time));
        if (current_time > 0) {
            rect_width = _width * current_time/total_time;
            m_rectangle.setWidth(rect_width);
            m_rectangle.setPosition(_pX - (_width - rect_width)/2, _pY);
        }
    }
	
}
