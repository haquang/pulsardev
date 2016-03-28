package com.pulsardev.log;

import android.util.Log;

public class GameLog
{
  static String TAG_ERROR = "TAG_ERROR";
  static String TAG_INFO;
  public static boolean islog = true;

  static
  {
    TAG_INFO = "TAG_INFO";
  }

  public static void LogError(String paramString)
  {
    if (islog)
      Log.e(TAG_ERROR, paramString);
  }

  public static void LogInfo(String paramString)
  {
    if (islog)
      Log.i(TAG_INFO, paramString);
  }
}

/* Location:           /home/haquang/workspace/Android2Java/CandyCatcherV2.0_dex2jar.jar
 * Qualified Name:     com.pulsardev.log.GameLog
 * JD-Core Version:    0.6.2
 */