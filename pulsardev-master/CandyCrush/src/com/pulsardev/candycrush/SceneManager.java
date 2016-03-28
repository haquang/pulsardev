package com.pulsardev.candycrush;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.pulsardev.config.Constant;

import android.util.Log;

public class SceneManager {
	//---------------------------------------------
	// SCENES
	//---------------------------------------------

	private BaseScene m_splash_scene;
	private BaseScene m_game_scene;
	private BaseScene m_manual_scene;

	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------

	private static final SceneManager INSTANCE = new SceneManager();

	private SceneType m_current_scene_type = SceneType.SCENE_SPLASH;

	private BaseScene m_current_scene;

	private Engine m_engine = ResourcesManager.getInstance().m_engine;


	public enum SceneType
	{
		SCENE_SPLASH,
		SCENE_GAME,
		SCENE_MANUAL
	}

	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------

	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback){
		ResourcesManager.getInstance().loadSplashScreen();
		m_splash_scene = new SplashScene();
		m_current_scene = m_splash_scene;
		pOnCreateSceneCallback.onCreateSceneFinished(m_splash_scene);
	}

	private void disposeSplashScene()
	{
		ResourcesManager.getInstance().unloadSplashScreen();
		m_splash_scene.disposeScene();
		m_splash_scene = null;
	}
	
	private void disposeManualScene()
	{
		ResourcesManager.getInstance().unloadManualScreenResources();
		m_manual_scene.disposeScene();
		m_manual_scene = null;
	}
	
	private void disposeGameScene(){
		ResourcesManager.getInstance().unloadGameTextures();
		m_game_scene.disposeScene();
		m_game_scene = null;
	}
	
	public void setScene(BaseScene scene)
	{
		m_engine.setScene(scene);
		m_current_scene = scene;
		m_current_scene_type = scene.getSceneType();
	}
	public void loadGameScene(final Engine mEngine) {
		if (SceneType.SCENE_SPLASH == getCurrentSceneType())
			disposeSplashScene();
		else if (SceneType.SCENE_MANUAL == getCurrentSceneType()){
			disposeManualScene();
		} else {
			return;
		}
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
		{
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourcesManager.getInstance().loadGameResources();
				m_game_scene = new GameScene();
				setScene(m_game_scene);
			}
		}));
	}
	
	public void loadManualScene(final Engine mEngine){
		disposeGameScene();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
		{
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourcesManager.getInstance().loadManualScreenResources();
				m_manual_scene = new ManualScene();
				setScene(m_manual_scene);
			}
		}));
	}
	
	public void setScene(SceneType sceneType)
	{
		switch (sceneType)
		{
		case SCENE_GAME:
			setScene(m_game_scene);
			break;
		case SCENE_SPLASH:
			setScene(m_splash_scene);
			break;
		case SCENE_MANUAL:
			setScene(m_manual_scene);
			break;
		default:
			break;
		}
	}

	//---------------------------------------------
	// GETTERS AND SETTERS
	//---------------------------------------------

	public static SceneManager getInstance()
	{
		return INSTANCE;
	}

	public SceneType getCurrentSceneType()
	{
		return m_current_scene_type;
	}

	public BaseScene getCurrentScene()
	{
		return m_current_scene;
	}
}
