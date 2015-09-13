package com.pulsardev.candycrush;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

public class SceneManager {
	//---------------------------------------------
	// SCENES
	//---------------------------------------------

	private BaseScene m_splash_scene;
	private BaseScene m_game_scene;

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
		SCENE_MENU,
		SCENE_GAME,
		SCENE_LOADING,
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
	public void setScene(BaseScene scene)
	{
		m_engine.setScene(scene);
		m_current_scene = scene;
		m_current_scene_type = scene.getSceneType();
	}
	public void loadGameScene(final Engine mEngine) {
		disposeSplashScene();
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
