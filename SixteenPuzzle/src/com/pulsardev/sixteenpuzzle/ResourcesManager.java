package com.pulsardev.sixteenpuzzle;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import org.andengine.util.debug.Debug;

import com.pulsardev.config.Constant;
import com.pulsardev.sound.GameSound;

public class ResourcesManager {
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------

	private static final ResourcesManager INSTANCE = new ResourcesManager();

	public Engine m_engine;
	public GameActivity m_activity;
	public Camera m_camera;
	public VertexBufferObjectManager m_vbom;
	Font font;

	public ITextureRegion m_splash_region;
	private BitmapTextureAtlas m_splash_texture_atlas;

	public ITextureRegion m_menu_background_region;
	public ITextureRegion m_play_region;
	public ITextureRegion m_options_region;
	public ITextureRegion m_exit_region;

	public BitmapTextureAtlas[] m_number_atlas;
	public TextureRegion[] m_number_region;

	public BitmapTextureAtlas[] m_button_atlas;
	public TextureRegion[] m_button_region;

	public ITextureRegion[] m_manual_region;
	private BitmapTextureAtlas[] m_manual_texture_atlas;
	
	public BitmapTextureAtlas m_progress_atlas;
	public TextureRegion m_progress_region;
	
	public BitmapTextureAtlas m_slogan_atlas;
	public TextureRegion m_slogan_region;
	
	public BitmapTextureAtlas m_timer_atlas;
	public TextureRegion m_timer_region;
	
	public BitmapTextureAtlas m_return_button_atlas;
	public TextureRegion m_return_button_region;

	
	GameSound sound;
	//---------------------------------------------
	// TEXTURES & TEXTURE REGIONS
	//---------------------------------------------

	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------

	

	public void loadGameResources()
	{
		loadGameGraphics();
		loadGameFonts();
		loadGameAudio();
	}

	public void unloadGameTextures()
	{
		/*
		 * Unload number sprite
		 */

		for (int i = 0; i < Constant.MAX_ITEM_NUM; i++) {
			m_number_atlas[i].unload();
			m_number_region[i] = null;
		}
		
		/*
		 * Unload button sprite
		 * 
		 */
		for (int i = 0; i < Constant.MAX_BUTTON_NUM; i++) {
			m_button_atlas[i].unload();
			m_button_region[i] = null;
		}
		/*
		 * Load progress bar
		 */
		
		m_progress_atlas.unload();
		m_progress_region = null;
	}


	private void loadGameGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		/*
		 * Load number sprite
		 */

		m_number_atlas = new BitmapTextureAtlas[Constant.MAX_ITEM_NUM];
		m_number_region = new TextureRegion[Constant.MAX_ITEM_NUM];
		for (int i = 0; i < Constant.MAX_ITEM_NUM; i++) {
			m_number_atlas[i] = new BitmapTextureAtlas(m_engine.getTextureManager(), 128, 128,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			m_number_region[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(m_number_atlas[i], m_activity,Constant.ITEM_NAME_NUMBER[i], 0, 0);
			m_engine.getTextureManager().loadTexture(m_number_atlas[i]);
		}
		
		/*
		 * Load button sprite
		 * 
		 */
		m_button_atlas = new BitmapTextureAtlas[Constant.MAX_BUTTON_NUM];
		m_button_region = new TextureRegion[Constant.MAX_BUTTON_NUM];
		for (int i = 0; i < Constant.MAX_BUTTON_NUM; i++) {
			m_button_atlas[i] = new BitmapTextureAtlas(m_engine.getTextureManager(), 256, 256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			m_button_region[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(m_button_atlas[i], m_activity,Constant.ITEM_NAME_BUTTON[i], 0, 0);
			m_engine.getTextureManager().loadTexture(m_button_atlas[i]);
		}
		/*
		 * Load progress bar
		 */
		
		m_progress_atlas = new BitmapTextureAtlas(m_engine.getTextureManager(), 512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		m_progress_region = BitmapTextureAtlasTextureRegionFactory .createFromAsset(m_progress_atlas, m_activity, "progressbar.png", 0, 0);
		m_engine.getTextureManager().loadTexture(m_progress_atlas);
		
		/*
		 *  Load slogan region
		 */
		
		m_slogan_atlas = new BitmapTextureAtlas(m_engine.getTextureManager(), 512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		m_slogan_region = BitmapTextureAtlasTextureRegionFactory .createFromAsset(m_slogan_atlas, m_activity, "app_slogan.png", 0, 0);
		m_engine.getTextureManager().loadTexture(m_slogan_atlas);
		
		/*
		 *  Load timer region
		 */
		
		m_timer_atlas = new BitmapTextureAtlas(m_engine.getTextureManager(), 64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		m_timer_region = BitmapTextureAtlasTextureRegionFactory .createFromAsset(m_timer_atlas, m_activity, "icon_time.png", 0, 0);
		m_engine.getTextureManager().loadTexture(m_timer_atlas);
		
		
	}

	private void loadGameFonts()
	{
		FontFactory.setAssetBasePath("font/");
		new BitmapTextureAtlas(this.m_activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.font = FontFactory.createFromAsset(this.m_activity.getFontManager(), this.m_activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR, this.m_activity.getAssets(), "BrushScriptStd.otf", 64.0F, true, -65281);
		this.font.load();
	}

	private void loadGameAudio()
	{
		sound = new GameSound();
		sound.loadSound();
	}

	public void loadSplashScreen()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		m_splash_texture_atlas = new BitmapTextureAtlas(m_activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		m_splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(m_splash_texture_atlas, m_activity, "splash.jpg", 0, 0);
		m_splash_texture_atlas.load();
	}
	
	public void loadManualScreenResources(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		m_manual_texture_atlas = new BitmapTextureAtlas[Constant.MANUAL_MAX];
		m_manual_region = new TextureRegion[Constant.MANUAL_MAX];
		
		for (int i = 0; i < Constant.MANUAL_MAX; i++) {
			m_manual_texture_atlas[i] = new BitmapTextureAtlas(m_engine.getTextureManager(), 1024, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			m_manual_region[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(m_manual_texture_atlas[i], m_activity,Constant.MANUAL_NAME[i], 0, 0);
			m_engine.getTextureManager().loadTexture(m_manual_texture_atlas[i]);
		}
			
		m_return_button_atlas = new BitmapTextureAtlas(m_engine.getTextureManager(), 256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		m_return_button_region = BitmapTextureAtlasTextureRegionFactory .createFromAsset(m_return_button_atlas, m_activity, "btn_return.png", 0, 0);
		m_engine.getTextureManager().loadTexture(m_return_button_atlas);
		
	}
	
	public void unloadManualScreenResources() {
		// TODO Auto-generated method stub
		for (int i = 0; i < Constant.MANUAL_MAX; i++)
		{
			m_manual_texture_atlas[i].unload();
			m_manual_region[i] = null;
		}
				
		m_return_button_atlas.unload();
		m_return_button_region = null;
		
		
		
	}

	public void unloadSplashScreen()
	{
		m_splash_texture_atlas.unload();
		m_splash_region = null;
	}


	/**
	 * @param engine
	 * @param activity
	 * @param camera
	 * @param vbom
	 * <br><br>
	 * We use this method at beginning of game loading, to prepare Resources Manager properly,
	 * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
	 */
	public static void prepareManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom)
	{
		getInstance().m_engine = engine;
		getInstance().m_activity = activity;
		getInstance().m_camera = camera;
		getInstance().m_vbom = vbom;
	}

	//---------------------------------------------
	// GETTERS AND SETTERS
	//---------------------------------------------

	public static ResourcesManager getInstance()
	{
		return INSTANCE;
	}


}
