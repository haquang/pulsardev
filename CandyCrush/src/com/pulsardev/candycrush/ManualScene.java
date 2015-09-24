package com.pulsardev.candycrush;

import java.util.ArrayList;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.adt.color.Color;

import com.pulsardev.candycrush.SceneManager.SceneType;
import com.pulsardev.config.Constant;

public class ManualScene extends BaseScene implements IOnMenuItemClickListener{

	ArrayList<Sprite> manual_sprite;
	private MenuScene m_menu_child_scene;
	private int current_sprite = 0;
	private final int MENU_NEXT = 0;
	private final int MENU_BACK = 1;
	private final int MENU_RETURN = 2;
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackGround();
		createButtonChild();
	}
	private void createButtonChild() {
		// TODO Auto-generated method stub
		m_menu_child_scene = new MenuScene(m_camera);
		m_menu_child_scene.setPosition(0,0);

		final IMenuItem nextMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_NEXT, m_resource_manager.m_next_button_region, m_vbom), 1.2f, 1);
		final IMenuItem backMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_BACK, m_resource_manager.m_back_button_region,m_vbom), 1.2f, 1);
		final IMenuItem returnMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETURN, m_resource_manager.m_return_button_region,m_vbom), 1.2f, 1);

		m_menu_child_scene.addMenuItem(nextMenuItem);
		m_menu_child_scene.addMenuItem(backMenuItem);
		m_menu_child_scene.addMenuItem(returnMenuItem);

		m_menu_child_scene.buildAnimations();
		m_menu_child_scene.setBackgroundEnabled(false);

		nextMenuItem.setPosition(nextMenuItem.getWidth()/2, nextMenuItem.getHeight());
		backMenuItem.setPosition(GameActivity.getCameraWidth() / 2, nextMenuItem.getHeight());
		returnMenuItem.setPosition(GameActivity.getCameraWidth() - returnMenuItem.getWidth()/2, nextMenuItem.getHeight());

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
		case MENU_NEXT:
			manual_sprite.get(current_sprite).setVisible(false);
			current_sprite++;
			if (current_sprite == Constant.MANUAL_MAX)
				current_sprite = 0;	
			manual_sprite.get(current_sprite).setVisible(true);
			return true;
		case MENU_BACK:
			manual_sprite.get(current_sprite).setVisible(false);
			
			if (current_sprite == 0)
				current_sprite = Constant.MANUAL_MAX - 1;
			else 
				current_sprite--;
			manual_sprite.get(current_sprite).setVisible(true);
			return true;
		case MENU_RETURN:
			SceneManager.getInstance().loadGameScene(m_engine);
			return true;
		default:
			return false;
		}
	}

}
