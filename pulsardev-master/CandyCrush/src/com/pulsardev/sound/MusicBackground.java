package com.pulsardev.sound;

import com.pulsardev.candycrush.ResourcesManager;
import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;

public class MusicBackground
{
  Music mediaPlayer;

  public void loadMusic()
  {
    try
    {
      this.mediaPlayer = MusicFactory.createMusicFromAsset(ResourcesManager.getInstance().m_engine.getMusicManager(), ResourcesManager.getInstance().m_activity, "sfx/bg.mp3");
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      localIllegalStateException.printStackTrace();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public void pause()
  {
    if (this.mediaPlayer.isPlaying())
      this.mediaPlayer.pause();
  }

  public void play()
  {
    this.mediaPlayer.play();
    this.mediaPlayer.setLooping(true);
  }

  public void release()
  {
    this.mediaPlayer.stop();
  }

  public void resume()
  {
    if (!this.mediaPlayer.isPlaying())
      this.mediaPlayer.play();
  }
}
