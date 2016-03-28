package com.pulsardev.sixteenpuzzle;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.pulsardev.sixteenpuzzle.SceneManager.SceneType;

public class SplashScene extends BaseScene {

	private Sprite m_splash;
	private GameActivity m_activity = ResourcesManager.getInstance().m_activity;
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		m_splash = new Sprite(0, 0, m_resource_manager.m_splash_region, m_vbom)
		{
		    @Override
		    protected void preDraw(GLState pGLState, Camera pCamera) 
		    {
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		};
		m_splash.setScale(1.5f);
	    m_splash.setPosition(GameActivity.getCameraWidth() * 0.5f, GameActivity.getCameraHeight() * 0.5f);;
		attachChild(m_splash);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		m_splash.detachSelf();
		m_splash.dispose();
		this.detachSelf();
		this.dispose();
	}

}
