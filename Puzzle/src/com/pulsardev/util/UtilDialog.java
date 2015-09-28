package com.pulsardev.util;
import com.pulsardev.fast15puzzle.R;

import android.app.Dialog;
import android.view.Window;

public class UtilDialog
{
  public static void iniDialog(Dialog paramDialog)
  {
    paramDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    paramDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_null);
    paramDialog.setCancelable(false);
    paramDialog.getWindow().getAttributes().windowAnimations = R.style.Animations_Dialog;
  }
}
