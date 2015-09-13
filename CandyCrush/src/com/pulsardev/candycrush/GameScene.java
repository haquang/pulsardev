package com.pulsardev.candycrush;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.math.Vector2;
import com.pulsardev.candycrush.GameScene;
import com.pulsardev.candycrush.SceneManager.SceneType;
import com.pulsardev.components.NumItem;
import com.pulsardev.components.Square;
import com.pulsardev.config.Constant;
import com.pulsardev.util.Util;

import android.util.Log;
import android.widget.Toast;


public class GameScene extends BaseScene {
	private HUD gameHUD;
	private Text t_score_text;
	private int i_score = 0;
	private PhysicsWorld m_physical_world;

	private ButtonSprite btn_exit;
	private ButtonSprite btn_music;
	private ButtonSprite btn_play;
	private ButtonSprite btn_restart;
	private ButtonSprite btn_settings;
	private ButtonSprite btn_sound_on;
	private ButtonSprite btn_sound_off;
	private ButtonSprite btn_pause;


	private ArrayList<NumItem> m_list_num;
	private ArrayList<Integer> m_game_state;
	ArrayList<Integer> subArrayRow;
	ArrayList<Integer> subArrayCol;
	private int grid_size;
	private int square_size;
	private float shift_row = 0;
	private float shift_col = 0;
	int zero_position;
	boolean cross_down = false;
	boolean cross_up = false;
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		createPhysics();
		createSquareGrid();
		//	createNumberItem();
		createButton();
		createHUD();
	}
	private void createBackground(){
		setBackground(new Background(Color.BLACK));
	}

	private void createButton(){
		// Button Exit
		btn_exit = new ButtonSprite(GameActivity.getCameraWidth() - shift_col/3 , GameActivity.getCameraHeight()/5, m_resource_manager.m_button_region[Constant.BTN_EXIT], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					this.setScale(1.0f);
				} else if (pTouchEvent.isActionUp()) {
					this.setScale(0.75f);
				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		registerTouchArea(btn_exit);
		btn_exit.setScale(0.75f);
		attachChild(btn_exit);

		// Button Music
		btn_music = new ButtonSprite(GameActivity.getCameraWidth() - shift_col/3 , 2 * GameActivity.getCameraHeight()/5, m_resource_manager.m_button_region[Constant.BTN_MUSIC], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					this.setScale(1.0f);
				} else if (pTouchEvent.isActionUp()) {
					this.setScale(0.75f);
				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		btn_music.setScale(0.75f);
		registerTouchArea(btn_music);
		attachChild(btn_music);

		// Button sound on

		btn_sound_on = new ButtonSprite(GameActivity.getCameraWidth() - shift_col/3 , 3 * GameActivity.getCameraHeight()/5, m_resource_manager.m_button_region[Constant.BTN_SOUND_ON], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					this.setVisible(false);
					btn_sound_off.setVisible(true);
				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		btn_sound_on.setScale(0.75f);
		registerTouchArea(btn_sound_on);
		attachChild(btn_sound_on);
		// Button sound off
		btn_sound_off = new ButtonSprite(GameActivity.getCameraWidth() - shift_col/3 , 3 * GameActivity.getCameraHeight()/5, m_resource_manager.m_button_region[Constant.BTN_SOUND_OFF], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					this.setVisible(false);
					btn_sound_on.setVisible(true);
				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		btn_sound_off.setScale(0.75f);
		btn_sound_off.setVisible(false);
		registerTouchArea(btn_sound_off);
		attachChild(btn_sound_off);
		// Button Settings
		btn_settings = new ButtonSprite(GameActivity.getCameraWidth() - shift_col/3 , 4 * GameActivity.getCameraHeight()/5, m_resource_manager.m_button_region[Constant.BTN_SETTINGS], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					this.setScale(1.0f);
				} else if (pTouchEvent.isActionUp()) {
					this.setScale(0.75f);
				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		btn_settings.setScale(0.75f);
		registerTouchArea(btn_settings);
		attachChild(btn_settings);
		// Button Play
		btn_play = new ButtonSprite(shift_col/3 , 2*GameActivity.getCameraHeight()/5, m_resource_manager.m_button_region[Constant.BTN_PLAY], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					this.setVisible(false);
					btn_pause.setVisible(true);
				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		btn_play.setScale(0.75f);
		registerTouchArea(btn_play);

		attachChild(btn_play);

		btn_pause = new ButtonSprite(shift_col/3 , 2*GameActivity.getCameraHeight()/5, m_resource_manager.m_button_region[Constant.BTN_PAUSE], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					this.setVisible(false);
					btn_play.setVisible(true);
				} 
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		btn_pause.setScale(0.75f);
		registerTouchArea(btn_pause);
		btn_pause.setVisible(false);
		attachChild(btn_pause);

		// Button restart
		btn_restart = new ButtonSprite(shift_col/3 , GameActivity.getCameraHeight()/5, m_resource_manager.m_button_region[Constant.BTN_RESTART], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					restart();
				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		btn_restart.setScale(0.75f);
		registerTouchArea(btn_restart);
		attachChild(btn_restart);
	}

	public void createSquareGrid(){
		int pos;
		grid_size = Constant.GRID_SIZE_DEFAULT;
		square_size = GameActivity.getCameraHeight()/grid_size;
		shift_row = square_size/2;
		shift_col = (GameActivity.getCameraWidth() - grid_size * square_size)/2 + square_size/2;
		new ArrayList<Square>();
		m_list_num = new ArrayList<NumItem>();
		m_game_state = randomMatrixGenerator(Constant.MAX_ITEM_NUM);
		subArrayRow = new ArrayList<Integer>();
		subArrayCol = new ArrayList<Integer>();

		for (int i = 0;i< grid_size;i++)
			for (int j = 0;j< grid_size;j++){
				pos = i*grid_size+j;
				if (m_game_state.get(pos) != 0){
					NumItem btn_sprite = new NumItem(j*square_size, i*square_size, m_resource_manager.m_number_region[m_game_state.get(pos) - 1], m_vbom) {
						@Override
						public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX,
								float pTouchAreaLocalY) {
							if (pTouchEvent.isActionDown()){
								onClickNumber(this);
							}

							return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
						}
					};
					btn_sprite.setScale(square_size/btn_sprite.getHeight());
					btn_sprite.setshift(shift_col, shift_row);
					btn_sprite.setRow(i+1);
					btn_sprite.setCol(j+1);
					btn_sprite.setSize(square_size);
					registerTouchArea(btn_sprite);
					attachChild(btn_sprite);
					m_list_num.add(btn_sprite);
				} else {
					NumItem btn_sprite = new NumItem(j*square_size, i*square_size, m_resource_manager.m_square_region, m_vbom);
					btn_sprite.setScale(square_size/btn_sprite.getHeight());
					btn_sprite.setshift(shift_col, shift_row);
					btn_sprite.setRow(i+1);
					btn_sprite.setCol(j+1);
					btn_sprite.setSize(square_size);
					attachChild(btn_sprite);
					m_list_num.add(btn_sprite);
					zero_position = m_list_num.size() - 1;
				}
			}
	}

	public ArrayList<Integer> randomMatrixGenerator(int N){
		ArrayList<Integer> matrix = new ArrayList<Integer>();
		ArrayList<Integer> list = new ArrayList<>(N);
		for (int i = 1; i <= N; i++){
			list.add(i);
		}
		for (int count = 0; count < N; count++){
			matrix.add(list.remove((int)(Math.random() * list.size())));
		}
		matrix.add(0); 
		return matrix;
	}

	public void restart(){
		m_game_state.clear();
		for (int i = 0;i<m_list_num.size();i++)
			m_list_num.get(i).detachSelf();
		m_list_num.clear();
		createSquareGrid();
	}

	public void onClickNumber(NumItem sprite){
		if (isMoveable(sprite)){
			int index = m_list_num.indexOf(sprite);

			//		for (int i = 0;i<m_list_num.size();i++)
			//			m_list_num.get(i).resetPosition();
			toogle(sprite, m_list_num.get(zero_position));
			//		Collections.swap(m_list_num, index, zero_position);
			Log.i("CandyCrush", String.valueOf(m_game_state));
			checkState();
			updateState();
		} else {
			Log.i("CandyCrush", "Item not moveable");
		}
	}

	public void updateState(){
		int pos;
		if (!subArrayRow.isEmpty()){
			pos = subArrayRow.remove(0);
			removeRow(pos);
		}
		if (!subArrayCol.isEmpty()){
			pos = subArrayCol.remove(0);
			removeCol(pos);
		}
		if (cross_down)
			Log.i("CandyCrush", "CROSS_DOWN");
		for (int i=0;i<grid_size ;i++)
			for (int j=0;j<grid_size ;j++){
			if ((i==j) && cross_up){
				removeItem(i, j);
			} else if (((i+j) == (grid_size - 1)) && cross_down){
				removeItem(i, j);
			}
		}
	}

	public void toogle(NumItem sprite,NumItem target){
		int r,c;
		r = sprite.getRow();
		c = sprite.getCol();
		sprite.setRow(target.getRow());
		sprite.setCol(target.getCol());
		target.setCol(c);
		target.setRow(r);
		target.resetPosition();
		sprite.resetPosition();	
	}

	public Boolean isMoveable(NumItem item) {
		int touch_row = item.getRow();
		int touch_col = item.getCol();
		int touch_pos = (touch_row-1)*grid_size + touch_col - 1;
		int zero_pos = m_game_state.indexOf(0);
		int zero_row = zero_pos / grid_size + 1;
		int zero_col = zero_pos % grid_size + 1;
		for (int i = touch_row - 1;i <= touch_row + 1;i++)
			for (int j = touch_col - 1;j <= touch_col + 1;j++)
			{
				if ((i == zero_row) && (j == zero_col) && ((i == touch_row) || (j == touch_col))){
					// Update game state
					m_game_state.set(zero_pos, m_game_state.get(touch_pos));
					m_game_state.set(touch_pos, 0);					
					return true;
				}
			}
		return false;
	}
	public void removeRow(int i){
		for (int j = 0;j<grid_size;j++)
			removeItem(i,j);
	}

	public void removeCol(int j){
		for (int i = 0;i<grid_size;i++)
			removeItem(i,j);
	}

	public void removeItem(int row,int col){
		for (int i = 0;i < m_list_num.size();i++){
			Log.i("CandyCrush", "remove: [ " + String.valueOf(row) + ":" + String.valueOf(col)+ "]\n"); 
			if ((row == m_list_num.get(i).getRow()-1) && (col == m_list_num.get(i).getCol()-1)){
				m_list_num.get(i).detachSelf();
				m_list_num.remove(i);
			}
		}

	}


	private void checkState(){
		subArrayRow = Util.checkRow(m_game_state, grid_size);
		subArrayCol = Util.checkCol(m_game_state, grid_size);
		cross_down = Util.checkCrossDown(m_game_state, grid_size);
		cross_up = Util.checkCrossUp(m_game_state, grid_size);
	}
	private void createHUD(){
		gameHUD = new HUD();
		t_score_text = new Text(shift_row/2 , 6 * GameActivity.getCameraHeight()/7, m_resource_manager.font, "Score: 0123456789", new TextOptions(org.andengine.util.adt.align.HorizontalAlign.LEFT), m_vbom);
		t_score_text.setSkewCenter(0, 0);    
		t_score_text.setText("Score: 0");

		gameHUD.attachChild(t_score_text);

		m_camera.setHUD(gameHUD);
	}
	private void addScore(int i){
		i_score += i;
		t_score_text.setText("Score: " + i_score);
	}
	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		//	m_list_num.clear();
		//	m_list_square.clear();
		//	m_resource_manager.unloadGameTextures();
		System.exit(0);
	}
	private void createPhysics()
	{
		m_physical_world = new FixedStepPhysicsWorld(60, new Vector2(0, -17), false); 
		registerUpdateHandler(m_physical_world);
	}
	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene()
	{
		m_camera.setHUD(null);
		m_camera.setCenter(GameActivity.getCameraWidth()/2, GameActivity.getCameraHeight()/2);
	}


}
