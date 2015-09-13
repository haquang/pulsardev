package com.pulsardev.candycrush;

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

	private BuildableBitmapTextureAtlas m_menu_texture_atlas;

	public ITextureRegion m_game_background_region;
	private BuildableBitmapTextureAtlas m_game_texture_atlas;

	public BitmapTextureAtlas[] m_number_atlas;
	public TextureRegion[] m_number_region;

	public BitmapTextureAtlas[] m_button_atlas;
	public TextureRegion[] m_button_region;

	
	public BitmapTextureAtlas m_square_atlas;
	public TextureRegion m_square_region;
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
		m_game_texture_atlas.unload();

	}

	private void loadMenuAudio()
	{

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
		 * Load square to make to grid
		 * 
		 */
		m_square_atlas = new BitmapTextureAtlas(m_engine.getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		m_square_region = BitmapTextureAtlasTextureRegionFactory .createFromAsset(m_square_atlas, m_activity, "square_grid.png", 0, 0);
		m_engine.getTextureManager().loadTexture(m_square_atlas);
		
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

	}

	public void loadSplashScreen()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		m_splash_texture_atlas = new BitmapTextureAtlas(m_activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		m_splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(m_splash_texture_atlas, m_activity, "splash.jpg", 0, 0);
		m_splash_texture_atlas.load();
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
