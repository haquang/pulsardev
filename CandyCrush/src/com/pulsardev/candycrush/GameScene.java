package com.pulsardev.candycrush;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;
import com.pulsardev.candycrush.GameScene;
import com.pulsardev.candycrush.SceneManager.SceneType;
import com.pulsardev.components.Matrix2D;
import com.pulsardev.components.NumSprite;
import com.pulsardev.components.ProgressBar;
import com.pulsardev.components.Score;
import com.pulsardev.config.Constant;
import com.pulsardev.dialog.DialogExit;
import com.pulsardev.dialog.DialogGameOver;
import com.pulsardev.dialog.DialogLevel;
import com.pulsardev.dialog.DialogPause;
import com.pulsardev.util.Util;

import android.graphics.Point;
import android.util.Log;


public class GameScene extends BaseScene {
	private HUD gameHUD;
	private Text t_score_text;
	private Score _score; 
	private ButtonSprite btn_exit;
	private ButtonSprite btn_play;
	private ButtonSprite btn_restart;
	private ButtonSprite btn_settings;
	private ButtonSprite btn_sound_on;
	private ButtonSprite btn_sound_off;
	private ButtonSprite btn_pause;
	private ProgressBar m_progress_bar;

	HashMap<Point, NumSprite> _map;
	int shift_row = 0;
	int shift_col = 0;
	int game_zone_height;
	int square_size;
	int grid_size = Constant.GRID_SIZE_DEFAULT;
	int _level = 1;
	boolean sound_on = true;
	Matrix2D m_game_state;
	ArrayList<Integer> archiveRow;
	ArrayList<Integer> archiveCol;
	ArrayList<Integer> removeList;
	boolean cross_down = false;
	boolean cross_up = false;
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		initialize();
		initSize();
		createButton();
		createHUD();
		createGame();
		createProgressBar();
	}
	private void createProgressBar() {
		// TODO Auto-generated method stub
		m_progress_bar = new ProgressBar(GameActivity.getCameraWidth()/2 , (int) (0.95*GameActivity.getCameraHeight()), m_resource_manager.m_progress_region, m_vbom,square_size * grid_size);
		m_progress_bar.setTotalTime(_level * 120);
		m_progress_bar.start();
	}
	private void createGame() {
		Point p;

		// Create random matrix
		m_game_state.createRandomMatrix();
		m_game_state.showMatrix2D();

		// Create NumItem & add to Hash table
		try {
			for (int i = 0;i<grid_size;i++)
				for (int j = 0;j< grid_size;j++){
					p = m_game_state.getPxPy(i,j);
					NumSprite item = new NumSprite(p.x, p.y, m_game_state.getMatrix()[i][j]);
					item.setMatrixPosition(i, j);
					Log.i(Constant.TAG, "MAP : " + String.valueOf(_map.size()));
					_map.put(p, item);
				}
			NumSprite.m_game_state = m_game_state;
		} catch (Exception e) {
			Log.i(Constant.TAG, e.toString());
		}

	}
	private void initialize() {
		_level = 1;
		grid_size = Constant.GRID_SIZE_DEFAULT;
		// Sprite class init
		NumSprite.m_scene = this;
		NumSprite.m_activity = this.m_activity;
		NumSprite.m_texture_region = m_resource_manager.m_number_region;
		NumSprite.m_vbom = m_resource_manager.m_vbom;

		// Dialog
		DialogExit.m_scene = this;
		DialogGameOver.m_scene = this;
		DialogPause.m_scene = this;
		DialogLevel.m_scene = this;
		// Map
		_map = new HashMap<Point, NumSprite>();
		removeList = new ArrayList<Integer>();
		ProgressBar._scene = this;

		m_game_state = new Matrix2D(grid_size);
	}
	private void initSize(){
		game_zone_height = (int) (0.9 * GameActivity.getCameraHeight());
		square_size = game_zone_height/grid_size;
		shift_col = (GameActivity.getCameraWidth() - grid_size * square_size)/2 + square_size/2;
		shift_row = square_size/2;
		// Matrix class init
		Matrix2D._item_size = square_size;

		Matrix2D._shift_row = shift_row;
		Matrix2D._shift_col = shift_col;
		NumSprite._size = square_size;
	}
	private void createBackground(){
		setBackground(new Background(Color.BLACK));
	}

	private void createButton(){
		// Button Exit
		btn_exit = new ButtonSprite(GameActivity.getCameraWidth() - shift_col/3 , GameActivity.getCameraHeight()/4, m_resource_manager.m_button_region[Constant.BTN_EXIT], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					pauseGame();				
					m_activity.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							new DialogExit(m_activity).show();
						}
					});

				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		registerTouchArea(btn_exit);
		btn_exit.setScale(0.75f);
		attachChild(btn_exit);

		// Button sound on

		btn_sound_on = new ButtonSprite(GameActivity.getCameraWidth() - shift_col/3 ,  GameActivity.getCameraHeight()/2, m_resource_manager.m_button_region[Constant.BTN_SOUND_ON], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if(pTouchEvent.isActionDown()) {
					this.setVisible(false);
					sound_on = false;
					btn_sound_off.setVisible(true);
				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		btn_sound_on.setScale(0.75f);
		registerTouchArea(btn_sound_on);
		attachChild(btn_sound_on);
		// Button sound off
		btn_sound_off = new ButtonSprite(GameActivity.getCameraWidth() - shift_col/3 ,  GameActivity.getCameraHeight()/2, m_resource_manager.m_button_region[Constant.BTN_SOUND_OFF], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					this.setVisible(false);
					sound_on = true;
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
		btn_settings = new ButtonSprite(GameActivity.getCameraWidth() - shift_col/3 , 3 * GameActivity.getCameraHeight()/4, m_resource_manager.m_button_region[Constant.BTN_SETTINGS], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				pauseGame();
				if(pTouchEvent.isActionDown()) {
					m_activity.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							new DialogLevel(m_activity).show();
						}
					});

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
				pauseGame();
				if(pTouchEvent.isActionDown()) {
					//					this.setVisible(false);
					//	btn_pause.setVisible(true);
					m_activity.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							new DialogPause(m_activity).show();
						}
					});
				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		btn_play.setScale(0.75f);
		registerTouchArea(btn_play);

		attachChild(btn_play);

		//		btn_pause = new ButtonSprite(shift_col/3 , 2*GameActivity.getCameraHeight()/5, m_resource_manager.m_button_region[Constant.BTN_PAUSE], m_vbom){
		//			@Override
		//			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		//				if(pTouchEvent.isActionDown()) {
		//					this.setVisible(false);
		//					btn_play.setVisible(true);
		//					resumeGame();
		//
		//				} 
		//				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		//			}
		//
		//		};
		//		btn_pause.setScale(0.75f);
		//		registerTouchArea(btn_pause);
		//		btn_pause.setVisible(false);
		//		attachChild(btn_pause);

		// Button restart
		btn_restart = new ButtonSprite(shift_col/3 , GameActivity.getCameraHeight()/5, m_resource_manager.m_button_region[Constant.BTN_RESTART], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					restartGame();
				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		btn_restart.setScale(0.75f);
		registerTouchArea(btn_restart);
		attachChild(btn_restart);
	}

	public void pauseGame(){
		m_progress_bar.pause();
		NumSprite.istouchable = false;
	}

	public void resumeGame(){
		m_progress_bar.resume();
		NumSprite.istouchable = true;
	}

	public void quitGame(){
		NumSprite.istouchable = false;
		removeMap();
		removeList.clear();
		m_progress_bar.stop();
		System.exit(0);
	}
	public void removeMap(){

		for (int i = 0;i<grid_size;i++)
			for (int j=0;j<grid_size;j++){
				Point p = m_game_state.getPxPy(i, j);
				if (_map.containsKey(p)){
					NumSprite sprite = _map.get(p);
					sprite.destroy();
					_map.remove(p);
				}
			}
	}
	public void restartGame(){
		removeMap();
		m_progress_bar.reset();
		m_progress_bar.start();
		removeList.clear();
		_score.reset();
		createGame();
	}

	public void levelChange() {

		initSize();
		m_progress_bar.setTotalTime(_level * 120);
		_score.setLevel(_level);
		_score.reset();
		removeList.clear();
		m_game_state.setSize(grid_size);
		createGame();
		NumSprite.istouchable = true;
		m_progress_bar.resume();
	}

	public void onTouchMovable(NumSprite sprite){
		NumSprite.istouchable = false;
		Point p0 = m_game_state.getZeroPxPy();
		Point p1 = new Point();
		p1.set(sprite.getPx(),sprite.getPy() );

		// toogle 2 sprite
		toogle(p0, p1);
		// update game_state
		updateGameState(sprite);
		// Check if archive target
		if (isArchive()){
			processArchivement();
			if (sound_on)
				m_resource_manager.sound.playArchive();
		} else {
			NumSprite.istouchable = true;
		}
	}

	private void processArchivement() {
		// TODO Auto-generated method stub
		if (!archiveRow.isEmpty()){
			for (int i = 0;i<archiveRow.size();i++)
			{
				// remove row from hash
				for (int j = 0;j< grid_size;j++){
					Point p = m_game_state.getPxPy(archiveRow.get(i),j);
					NumSprite sprite = _map.get(p);
					sprite.destroy();
					_map.remove(p);
					removeList.add(m_game_state.getMatrix()[archiveRow.get(i)][j]);
				}
				m_game_state.removeRow(archiveRow.get(i));
				_score.addScore(1);

			}
		}

		if (!archiveCol.isEmpty()){
			for (int i = 0;i<archiveCol.size();i++)
			{
				// remove col from hash
				for (int j = 0;j< grid_size;j++){
					Point p = m_game_state.getPxPy(j,archiveCol.get(i));
					NumSprite sprite = _map.get(p);
					sprite.destroy();
					_map.remove(p);
					removeList.add(m_game_state.getMatrix()[j][archiveCol.get(i)]);
				}
				m_game_state.removeCol(archiveCol.get(i));
				_score.addScore(1);
			}
		}

		if (cross_down)
		{
			// remove item

			for (int j = 0;j<grid_size;j++)
				for (int i = 0 ;i < grid_size;i++){
					if (i + j == grid_size - 1){
						Point p = m_game_state.getPxPy(i,j);
						NumSprite sprite = _map.get(p);
						sprite.destroy();
						_map.remove(p);
						removeList.add(m_game_state.getMatrix()[i][j]);
					}
				}


			m_game_state.removeCrossDown();
			_score.addScore(2);

		}

		if (cross_up)
		{
			// remove item
			for (int j = 0;j<grid_size;j++)
				for (int i = 0 ;i < grid_size;i++){
					if (i == j){
						Point p = m_game_state.getPxPy(i,j);
						NumSprite sprite = _map.get(p);
						sprite.destroy();
						_map.remove(p);
						removeList.add(m_game_state.getMatrix()[i][j]);
					}

				}
			m_game_state.removeCrossUp();
			_score.addScore(2);
		}
		fillGameState();
		NumSprite.istouchable = true;
	}

	public void fillGameState(){
		int value;
		ArrayList<Integer> list = new ArrayList<>(removeList.size());


		while (!removeList.isEmpty()){
			list.add(removeList.remove((int)(Math.random() * removeList.size())));
			//			Log.i(Constant.TAG, String.valueOf(list));

			if (removeList.isEmpty() && Util.checkContinuous(list)){
				removeList.addAll(list);
				list.clear();
			}
		}
		//		Log.i(Constant.TAG, String.valueOf(list));		
		int[][]  matrix = m_game_state.getMatrix();
		for (int i = 0;i< grid_size;i++)
			for (int j = 0;j<grid_size;j++){
				if (matrix[i][j] == -1) {
					value = list.remove(0);
					//					Log.i(Constant.TAG, String.valueOf(value));
					matrix[i][j] = value;
					Point p = m_game_state.getPxPy(i, j);
					NumSprite sprite = new NumSprite(p.x, p.y, value);
					sprite.setMatrixPosition(i,j);
					_map.put(p, sprite);
				}

			}
		m_game_state.setMatrix(matrix);
	}
	private boolean isArchive() {
		archiveRow = m_game_state.checkRow();
		archiveCol = m_game_state.checkCol();
		cross_down = m_game_state.checkCrossDown();
		cross_up = m_game_state.checkCrossUp();
		return ((!archiveRow.isEmpty()) || (!archiveCol.isEmpty()) || cross_down || cross_up);
	}
	public void updateGameState(NumSprite sprite){
		Point p3 = sprite.getMatrixPosition();
		Point p4 = m_game_state.getZeroXY();
		int[][] matrix = m_game_state.getMatrix();

		int i = matrix[p3.x][p3.y];
		matrix[p3.x][p3.y] = 0;
		matrix[p4.x][p4.y] = i;
		m_game_state.setMatrix(matrix);
	}

	public void toogle(Point p1,Point p2){
		// Save position
		int x1 = p1.x;
		int y1 = p1.y;
		int x2 = p2.x;
		int y2 = p2.y;

		NumSprite sp1 = _map.get(p1);
		NumSprite sp2 = _map.get(p2);
		int id1 = sp1.getResourceID();
		int id2 = sp2.getResourceID();
		Point p3 = sp1.getMatrixPosition();
		Point p4 = sp2.getMatrixPosition();
		// remove 2 sprite
		if (_map.containsKey(p1))
			_map.remove(p1);
		if (_map.containsKey(p2))
			_map.remove(p2);
		sp1.destroy();
		sp2.destroy();
		// Create 2 new sprite
		NumSprite sp1_new = new NumSprite(x1, y1, id2);
		NumSprite sp2_new = new NumSprite(x2, y2, id1);
		sp1_new.setMatrixPosition(p3.x, p3.y);
		sp2_new.setMatrixPosition(p4.x, p4.y);
		_map.put(p1, sp1_new);
		_map.put(p2, sp2_new);
		if (sound_on)
			m_resource_manager.sound.playSlide();
	}


	private void createHUD(){
		gameHUD = new HUD();
		t_score_text = new Text(shift_row/2 , 6 * GameActivity.getCameraHeight()/7, m_resource_manager.font, "Score: 0123456789", new TextOptions(org.andengine.util.adt.align.HorizontalAlign.CENTER), m_vbom);
		t_score_text.setSkewCenter(0, 0);    
		t_score_text.setText("Score: \n 0");
		t_score_text.setPosition(t_score_text.getWidth() , 6 * GameActivity.getCameraHeight()/7);
		gameHUD.attachChild(t_score_text);
		m_camera.setHUD(gameHUD);
		_score = new Score(_level);
		_score.setTextScore(t_score_text);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		//	m_list_num.clear();
		//	m_list_square.clear();
		//	m_resource_manager.unloadGameTextures();
		m_activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				new DialogExit(m_activity).show();
			}
		});
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
	public void timeOut() {
		// TODO Auto-generated method stub
		m_activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				new DialogGameOver(m_activity).show();
			}
		});
	}
	public void setLevel(int level) {
		// TODO Auto-generated method stub
		if (_level == level) {
			resumeGame();
			NumSprite.istouchable = true;
			return;
		}
		_level = level;
		removeMap();
		switch (_level) {
		case 1:
			grid_size = 4;			
			break;
		case 2:
			grid_size = 5;			
			break;
		case 3:
			grid_size = 6;			
			break;
		default:
			break;
		}

		levelChange();
	}


}
