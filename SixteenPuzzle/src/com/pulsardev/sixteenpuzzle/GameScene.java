package com.pulsardev.sixteenpuzzle;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import com.google.android.gms.ads.InterstitialAd;
import com.pulsardev.components.Highscore;
import com.pulsardev.components.Matrix2D;
import com.pulsardev.components.NumSprite;
import com.pulsardev.components.ProgressBar;
import com.pulsardev.components.Score;
import com.pulsardev.config.Constant;
import com.pulsardev.config.Constant.Swipe_type;
import com.pulsardev.dialog.DialogExit;
import com.pulsardev.dialog.DialogGameOver;
import com.pulsardev.dialog.DialogHighScore;
import com.pulsardev.dialog.DialogLevel;
import com.pulsardev.dialog.DialogName;
import com.pulsardev.dialog.DialogPause;
import com.pulsardev.sixteenpuzzle.GameScene;
import com.pulsardev.sixteenpuzzle.SceneManager.SceneType;
import android.graphics.Point;
import android.util.Log;


public class GameScene extends BaseScene {
	private HUD gameHUD;
	private Text t_score_text;
	private Text t_timer_text;
	private Score _score; 
	private ButtonSprite btn_exit;
	private ButtonSprite btn_play;
	private ButtonSprite btn_restart;
	private ButtonSprite btn_settings;
	private ButtonSprite btn_sound_on;
	private ButtonSprite btn_sound_off;
	//	private ButtonSprite btn_pause;
	private Sprite m_slogan;
	private Sprite m_timer_logo;
	private Highscore m_high_score;
	InterstitialAd m_ads;
	HashMap<Point, NumSprite> _map;
	int shift_row = 0;
	int shift_col = 0;
	int game_zone_width;
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

	// Swipe processing
	public Point touchDown = new Point(0,0);
	public Point touchUp = new Point(0,0);
	Swipe_type swipeMotion;
	public static NumSprite touchedSprite;
	int touchedPosition;
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		initialize();
		initSize();
		createSlogan();
		createButton();
//		createHUD();
		createGame();
	}
	private void createSlogan() {
		// Application slogan
		
		Point slogan_position = new Point();
		slogan_position.x = (int) ((0.9*game_zone_width)/2);
		slogan_position.y = (int) (0.95*GameActivity.getCameraHeight());
		m_slogan = new Sprite(slogan_position.x, slogan_position.y, m_resource_manager.m_slogan_region, m_vbom);
		m_slogan.setScale((float) (0.8*game_zone_width/m_slogan.getWidth()));
		attachChild(m_slogan);
		
		// Timer logo
		

		Point timer_position = new Point();
		timer_position.x = (int) ((0.6*game_zone_width)/2);
		timer_position.y = (int) (0.5*(GameActivity.getCameraHeight() + game_zone_width + shift_row/2));
		m_timer_logo = new Sprite(timer_position.x, timer_position.y, m_resource_manager.m_timer_region, m_vbom);
		m_timer_logo.setScale((float) (0.12*game_zone_width/m_timer_logo.getWidth()));
		attachChild(m_timer_logo);
		
		t_timer_text = new Text(timer_position.x + square_size/2 + m_timer_logo.getWidth()/2 , timer_position.y, m_resource_manager.font, "0123456789", new TextOptions(org.andengine.util.adt.align.HorizontalAlign.CENTER), m_vbom);
		t_timer_text.setText("00:00");
		t_timer_text.setColor(80,51,56);
		t_timer_text.setPosition(timer_position.x + square_size/2 + t_timer_text.getWidth()/2, timer_position.y);
		attachChild(t_timer_text);
	}

	private void createGame() {
		Point p;

		// Create random matrix
		m_game_state.createRandomMatrix();
//		m_game_state.createTestMatrix();
		m_game_state.showMatrix2D();

		// Create NumItem & add to Hash table
		try {
			for (int i = 0;i<grid_size;i++)
				for (int j = 0;j< grid_size;j++){
					p = m_game_state.getPxPy(i,j);
					NumSprite item = new NumSprite(p.x, p.y, m_game_state.getMatrix()[i][j]);
					item.setMatrixPosition(i, j);
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
		NumSprite.istouchable =  true;
		// Dialog
		DialogExit.m_scene = this;
		DialogGameOver.m_scene = this;
		DialogPause.m_scene = this;
		DialogLevel.m_scene = this;
		DialogName.m_scene = this;
		DialogHighScore.m_scene = this;
		// Map
		_map = new HashMap<Point, NumSprite>();
		removeList = new ArrayList<Integer>();
		ProgressBar._scene = this;
		m_game_state = new Matrix2D(grid_size);

		// High score
		m_high_score = new Highscore(this.m_activity.getBaseContext());
		m_high_score.clear();
		//				m_high_score.addScore("Player 1", 5);
		//				m_high_score.addScore("Player 2",10);
		//				m_high_score.addScore("Player 3",15);
		//				m_high_score.addScore("Player 4",20);
		//				m_high_score.addScore("Player 5",25);
		//
		for (int i = 0;i< Constant.HIGHT_SCORE_SIZE;i++){
			Log.i(Constant.TAG, String.valueOf(m_high_score.getName(i)) + String.valueOf(m_high_score.getScore(i)));
		}

	}
	private void initSize(){
		game_zone_width = (int) (0.98*GameActivity.getCameraWidth());
		square_size = game_zone_width/grid_size;
		shift_col = (int) (0.01*GameActivity.getCameraWidth()) + square_size/2;
		shift_row = 0;
		// Matrix class init
		Matrix2D._item_size = square_size;

		Matrix2D._shift_row = shift_row;
		Matrix2D._shift_col = shift_col;
		NumSprite._size = square_size;
	}
	private void createBackground(){
		setBackground(new Background(79,200,201));
	}

	private void createButton(){
		// Button Exit
		btn_exit = new ButtonSprite(game_zone_width-square_size/2,game_zone_width + square_size,  m_resource_manager.m_button_region[Constant.BTN_EXIT], m_vbom){
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
		btn_exit.setScale(0.5f);
		attachChild(btn_exit);

//		// Button sound on
//
//		btn_sound_on = new ButtonSprite(GameActivity.getCameraWidth() - shift_col/3 ,  GameActivity.getCameraHeight()/2, m_resource_manager.m_button_region[Constant.BTN_SOUND_ON], m_vbom){
//			@Override
//			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
//
//				if(pTouchEvent.isActionDown()) {
//					this.setVisible(false);
//					sound_on = false;
//					btn_sound_off.setVisible(true);
//				}
//				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
//			}
//
//		};
//		btn_sound_on.setScale(0.75f);
//		registerTouchArea(btn_sound_on);
//		attachChild(btn_sound_on);
//		// Button sound off
//		btn_sound_off = new ButtonSprite(GameActivity.getCameraWidth() - shift_col/3 ,  GameActivity.getCameraHeight()/2, m_resource_manager.m_button_region[Constant.BTN_SOUND_OFF], m_vbom){
//			@Override
//			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
//				if(pTouchEvent.isActionDown()) {
//					this.setVisible(false);
//					sound_on = true;
//					btn_sound_on.setVisible(true);
//				}
//				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
//			}
//
//		};
//		btn_sound_off.setScale(0.75f);
//		btn_sound_off.setVisible(false);
//		registerTouchArea(btn_sound_off);
//		attachChild(btn_sound_off);
		// Button Settings
		btn_settings = new ButtonSprite((float) (0.9*GameActivity.getCameraWidth()) , (float) (0.95 * GameActivity.getCameraHeight()), m_resource_manager.m_button_region[Constant.BTN_SETTINGS], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				pauseGame();
				if(pTouchEvent.isActionDown()) {
					//			
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
		btn_settings.setScale(0.5f);
		registerTouchArea(btn_settings);
		attachChild(btn_settings);
		// Button Play
//		btn_play = new ButtonSprite(shift_col/3 , 2*GameActivity.getCameraHeight()/5, m_resource_manager.m_button_region[Constant.BTN_PLAY], m_vbom){
//			@Override
//			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
//				pauseGame();
//				if(pTouchEvent.isActionDown()) {
//					//					this.setVisible(false);
//					//	btn_pause.setVisible(true);
//					m_activity.runOnUiThread(new Runnable()
//					{
//						@Override
//						public void run()
//						{
//							new DialogPause(m_activity).show();
//						}
//					});
//				}
//				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
//			}
//
//		};
//		btn_play.setScale(0.75f);
//		registerTouchArea(btn_play);
//
//		attachChild(btn_play);

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
		btn_restart = new ButtonSprite(square_size/2,game_zone_width + square_size,m_resource_manager.m_button_region[Constant.BTN_RESTART], m_vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pTouchEvent.isActionDown()) {
					restartGame();


				}
				return super.onAreaTouched(pTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		btn_restart.setScale(0.5f);
		registerTouchArea(btn_restart);
		attachChild(btn_restart);
	}

	public void manualScreen(){
		SceneManager.getInstance().loadManualScene(m_engine);
	}

	public void highScore(){
		m_activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				DialogHighScore highScore = new DialogHighScore(m_activity);
				highScore.setHighScore(m_high_score);
				highScore.show();
			}
		});
	}
	public void pauseGame(){
		NumSprite.istouchable = false;
	}

	public void resumeGame(){
		NumSprite.istouchable = true;
	}

	public void quitGame(){
		NumSprite.istouchable = false;
		removeMap();
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
		createGame();
	}

	public void levelChange() {
		initSize();
		m_game_state.setSize(grid_size);
		createGame();
		NumSprite.istouchable = true;
	}

	public void onTouchMovable(NumSprite sprite){
		NumSprite.istouchable = false;

		if ((Math.abs(touchDown.x - touchUp.x) < Constant.SWIPE_THRESHOLD) && (Math.abs(touchDown.y - touchUp.y) < Constant.SWIPE_THRESHOLD))
		{
			NumSprite.istouchable = true;
			return;
		}
		else  if (((Math.abs(touchDown.x - touchUp.x) > Constant.SWIPE_THRESHOLD) && (Math.abs(touchDown.y - touchUp.y) < Constant.SWIPE_THRESHOLD)) ||( (Math.abs(touchDown.x - touchUp.x) > Math.abs(touchDown.y - touchUp.y)) && (Math.abs(touchDown.y - touchUp.y) > Constant.SWIPE_THRESHOLD)))
		{
			if (sound_on)
				m_resource_manager.sound.playSlide();
			
			if (touchDown.x - touchUp.x < 0){
				Log.i(Constant.TAG, "Swipe right at " + String.valueOf(touchedSprite.getRow()));
				swipeMotion = Swipe_type.RIGHT;
			} else {
				Log.i(Constant.TAG, "Swipe left at " + String.valueOf(touchedSprite.getRow()));
				swipeMotion = Swipe_type.LEFT;
			}
		}
		else  if (((Math.abs(touchDown.x - touchUp.x) < Constant.SWIPE_THRESHOLD) && (Math.abs(touchDown.y - touchUp.y) > Constant.SWIPE_THRESHOLD)) ||( (Math.abs(touchDown.x - touchUp.x) < Math.abs(touchDown.y - touchUp.y)) && (Math.abs(touchDown.x - touchUp.x) > Constant.SWIPE_THRESHOLD)))
		{
			if (sound_on)
				m_resource_manager.sound.playSlide();
			
			if (touchDown.y - touchUp.y < 0){
				Log.i(Constant.TAG, "Swipe down at " + String.valueOf(touchedSprite.getCol()));
				swipeMotion = Swipe_type.DOWN;
			} else {
				Log.i(Constant.TAG, "Swipe up at " + String.valueOf(touchedSprite.getCol()));
				swipeMotion = Swipe_type.UP;
			}
		}


		// update game_state
		updateGameState();
		//	m_game_state.showMatrix2D();
		NumSprite.istouchable = true;
		return;

	}

	public void fillGameState(){
		Log.i(Constant.TAG, "Touch pos : " + String.valueOf(touchedPosition));
		if ((swipeMotion == Swipe_type.DOWN) || (swipeMotion == Swipe_type.UP)){
			for (int i = 0;i < grid_size;i++){
				Point p = m_game_state.getPxPy(i, touchedPosition);
				NumSprite sprite = new NumSprite(p.x,p.y, m_game_state.getMatrix()[i][touchedPosition]);
				sprite.setMatrixPosition(i, touchedPosition);
				_map.put(p, sprite);
			}
		}
		else 
		{
			for (int i = 0;i < grid_size;i++){
				Point p = m_game_state.getPxPy(touchedPosition,i);
				NumSprite sprite = new NumSprite(p.x,p.y, m_game_state.getMatrix()[touchedPosition][i]);
				sprite.setMatrixPosition(touchedPosition,i);
				_map.put(p, sprite);
			}
		}
	}
	
	public void updateGameState(){
		//		m_game_state.showMatrix2D();
		if ((swipeMotion == Swipe_type.DOWN) || (swipeMotion == Swipe_type.UP)){
			touchedPosition  = touchedSprite.getCol();
			for (int i = 0;i < grid_size;i++){
				Point p = m_game_state.getPxPy(i, touchedPosition);
				NumSprite sprite = _map.get(p);
				sprite.destroy();
				_map.remove(p);
			}
		}
		else 
		{
			touchedPosition  = touchedSprite.getRow();
			for (int i = 0;i < grid_size;i++){
				Point p = m_game_state.getPxPy(touchedPosition, i);
				NumSprite sprite = _map.get(p);
				sprite.destroy();
				_map.remove(p);
			}
		}
		m_game_state.shift(touchedPosition, swipeMotion);

		m_game_state.showMatrix2D();
		fillGameState();
		
		// check state
		if (m_game_state.isSixteen() || (m_game_state.isMagicSquare()))
			highScore();
		else 
			Log.i(Constant.TAG, "NOT DONE");
			
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
		removeMap();
		removeList.clear();
		this.detachSelf();
		this.dispose();
	}
	public void timeOut() {
		// TODO Auto-generated method stub
		// Check if high score & display high score
		if (m_high_score.inHighscore(_score.getScore())){
			//			Log.i(Constant.TAG, "High Score");
			// 			Display InputName dialog
			m_activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new DialogName(m_activity).show();
				}
			});
		} else {
			displayGameOver();
		}


	}

	public void updateHighScore(String name){
		Log.i(Constant.TAG, name);
		m_high_score.addScore(name, _score.getScore());
	}

	public void displayGameOver() {
		// ADS
		//		m_activity.runOnUiThread(new Runnable() {
		//			
		//			@Override
		//			public void run() {
		//				// TODO Auto-generated method stub
		//				m_ads = new InterstitialAd(m_activity);
		//				m_ads.setAdUnitId(Constant.ADS_ID);
		//				
		//				AdRequest adRequest = new AdRequest.Builder().build();
		//				m_ads.loadAd(adRequest);
		//				
		//				m_ads.setAdListener(new AdListener(){
		//			          public void onAdLoaded(){
		//			        	  m_ads.show();
		//			          }
		//				});
		//	        
		//			}
		//		});
		//		

		// Display Game Over
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
