package com.pulsardev.sound;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;

import com.pulsardev.sixteenpuzzle.ResourcesManager;

public class GameSound
{
  Sound sndSlide;
  Sound sndArchive;
  public void loadSound()
  {
    try
    {
      sndSlide = SoundFactory.createSoundFromAsset(ResourcesManager.getInstance().m_engine.getSoundManager(), ResourcesManager.getInstance().m_activity, "sfx/slide.mp3");
      sndArchive = SoundFactory.createSoundFromAsset(ResourcesManager.getInstance().m_engine.getSoundManager(), ResourcesManager.getInstance().m_activity, "sfx/archive.mp3");
      sndSlide.setVolume(0.25f);
      sndArchive.setVolume(1.0f);
      return;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("Error while loading sounds", localException);
    }
    
  }

  public void playSlide()
  {
    sndSlide.play();
  }
  
  public void playArchive()
  {
    sndArchive.play();
  }

}

