package com.pulsardev.sound;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;

import com.pulsardev.candycrush.ResourcesManager;

public class GameSound
{
  Sound sndCatch;
  Sound sndFail;

  public void loadSound()
  {
    try
    {
      this.sndCatch = SoundFactory.createSoundFromAsset(ResourcesManager.getInstance().m_engine.getSoundManager(), ResourcesManager.getInstance().m_activity, "sfx/catch.mp3");
      this.sndFail = SoundFactory.createSoundFromAsset(ResourcesManager.getInstance().m_engine.getSoundManager(), ResourcesManager.getInstance().m_activity, "sfx/fail.mp3");
      return;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("Error while loading sounds", localException);
    }
  }

  public void playBad()
  {
    this.sndFail.play();
  }

  public void playGood()
  {
    this.sndCatch.play();
  }
}

