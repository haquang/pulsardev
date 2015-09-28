package com.pulsardev.fast15puzzle;

import java.util.ArrayList;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

import com.pulsardev.config.Constant;
import com.pulsardev.fast15puzzle.SceneManager.SceneType;

import android.util.Log;

public class ManualScene extends BaseScene implements IOnMenuItemClickListener,IOnSceneTouchListener {

	ArrayList<Sprite> manual_sprite;
	private MenuScene m_menu_child_scene;
	private int current_sprite = 0;
	private final int MENU_RETURN = 0;
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackGround();
		createButtonChild();
		setOnSceneTouchListener(this);
	}
	private void createButtonChild() {
		// TODO Auto-generated method stub
		m_menu_child_scene = new MenuScene(m_camera);
		m_menu_child_scene.setPosition(0,0);

		final IMenuItem returnMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETURN, m_resource_manager.m_return_button_region,m_vbom), 1.2f, 1);

		m_menu_child_scene.addMenuItem(returnMenuItem);

		m_menu_child_scene.buildAnimations();
		m_menu_child_scene.setBackgroundEnabled(false);

		returnMenuItem.setScale(2.0f);
		returnMenuItem.setPosition(GameActivity.getCameraWidth() - returnMenuItem.getWidth(), returnMenuItem.getHeight() * 2);
		
		m_menu_child_scene.setOnMenuItemClickListener(this);

		setChildScene(m_menu_child_scene);
	}
	void createBackGround(){
		manual_sprite = new ArrayList<Sprite>();
		for (int i = 0; i< Constant.MANUAL_MAX;i++){
			Sprite sprite = new Sprite(0, 0, m_resource_manager.m_manual_region[i], m_vbom);
			sprite.setVisible(false);
			sprite.setPosition(GameActivity.getCameraWidth()/2, GameActivity.getCameraHeight()/2);
			sprite.setScale(GameActivity.getCameraHeight()/sprite.getHeight());
			attachChild(sprite);
			manual_sprite.add(sprite);
		}

		manual_sprite.get(current_sprite).setVisible(true);
	}


	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		SceneManager.getInstance().loadGameScene(m_engine);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_MANUAL;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		this.detachSelf();
		this.dispose();
	}
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX,
			float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		switch(pMenuItem.getID())
		{
		case MENU_RETURN:
			SceneManager.getInstance().loadGameScene(m_engine);
			return true;
		default:
			return false;
		}
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene,final TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		Log.i(Constant.TAG, "SCREEN TOUCH");
		if (pSceneTouchEvent.isActionDown()){
			
			manual_sprite.get(current_sprite).setVisible(false);
			current_sprite++;
			if (current_sprite == Constant.MANUAL_MAX)
				current_sprite = 0;	
			manual_sprite.get(current_sprite).setVisible(true);
		}
		return false;
	}

}
