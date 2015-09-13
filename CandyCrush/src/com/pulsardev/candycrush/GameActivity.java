package com.pulsardev.candycrush;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import com.pulsardev.config.Constant;

import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;

public class GameActivity extends BaseGameActivity {
	private Camera m_camera;
	private ResourcesManager m_resource_manager;
	private static int _camera_height = Constant.CAMERA_HEIGHT;
	private static int _camera_width = Constant.CAMERA_WIDTH;
	private static int _screen_height;
	private static int _screen_width;
	
	String fileFolderForRatio = "";

	public static int getCameraHeight()
	{
		return _camera_height;
	}

	public static int getCameraWidth()
	{
		return _camera_width;
	}
	public static int getScreenHeight()
	{
		return _screen_height;
	}

	public static int getScreenWidth()
	{
		return _screen_width;
	}
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		// TODO Auto-generated method stub
		return new LimitedFPSEngine(pEngineOptions, 60);
	}
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		Point screenSize = getScreenSizeByRatio();
		_camera_width = screenSize.x;
		_camera_height = screenSize.y;
		
		DisplayMetrics display = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(display);
		_screen_height = display.heightPixels;
		_screen_width = display.widthPixels;
		
		m_camera = new Camera(0.0F, 0.0F, _camera_width, _camera_height);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), this.m_camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}
	public Point getScreenSizeByRatio()
	{
		final Display display = getWindowManager().getDefaultDisplay();

		double sw = display.getWidth();
		double sh = display.getHeight();
		double ratio = sw / sh;
		if (sw < sh) ratio = sh/sw;

		Point screenPoints = new Point();
		if (ratio > 1.3 && ratio < 1.4)
		{

			fileFolderForRatio = "1024x768";
			screenPoints.x = 1024;
			screenPoints.y = 768;

		} else
		{

			fileFolderForRatio = "1280x768";
			screenPoints.x = 1280;
			screenPoints.y = 768;

		}

		Debug.e("RATIO", fileFolderForRatio);

		return screenPoints;
	}
	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
		// TODO Auto-generated method stub
		ResourcesManager.prepareManager(mEngine, this, m_camera, getVertexBufferObjectManager());
		m_resource_manager = ResourcesManager.getInstance();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		// TODO Auto-generated method stub
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {
		// TODO Auto-generated method stub
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
		{
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{
				mEngine.unregisterUpdateHandler(pTimerHandler);
				SceneManager.getInstance().loadGameScene(m_resource_manager.m_engine);
			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.exit(0);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		}
		return false; 
	}
}
