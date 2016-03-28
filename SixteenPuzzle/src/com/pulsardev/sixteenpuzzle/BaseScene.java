package com.pulsardev.sixteenpuzzle;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import com.pulsardev.sixteenpuzzle.SceneManager.SceneType;

import android.app.Activity;

public abstract class BaseScene extends Scene {
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------

	protected Engine m_engine;
	protected BaseGameActivity m_activity;
	protected ResourcesManager m_resource_manager;
	protected VertexBufferObjectManager m_vbom;
	protected Camera m_camera;

	//---------------------------------------------
	// CONSTRUCTOR
	//---------------------------------------------

	public BaseScene()
	{
		this.m_resource_manager = ResourcesManager.getInstance();
		this.m_engine = m_resource_manager.m_engine;
		this.m_activity = m_resource_manager.m_activity;
		this.m_vbom = m_resource_manager.m_vbom;
		this.m_camera = m_resource_manager.m_camera;
		createScene();
	}

	//---------------------------------------------
	// ABSTRACTION
	//---------------------------------------------

	public abstract void createScene();

	public abstract void onBackKeyPressed();

	public abstract SceneType getSceneType();

	public abstract void disposeScene();
}
