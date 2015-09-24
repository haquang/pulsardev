package com.pulsardev.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.pulsardev.candycrush.GameScene;
import com.pulsardev.candycrush.R;
import com.pulsardev.config.Constant;
import com.pulsardev.util.Util;
import com.pulsardev.util.UtilDialog;

public class DialogExit extends Dialog
  implements View.OnClickListener
{
  Activity m_activity;
  public static GameScene m_scene;
  ImageButton no;
  ImageButton yes;

  public DialogExit(Context paramContext)
  {
    super(paramContext);
    UtilDialog.iniDialog(this);
    this.m_activity = ((Activity)paramContext);
    setContentView(R.layout.dialog_exit);
  //  Util.resizeDialog(findViewById(R.id.dialogExitLayout));
    yes = ((ImageButton)findViewById(R.id.button_yes));
    no = ((ImageButton)findViewById(R.id.button_no));
    yes.setOnClickListener(this);
    no.setOnClickListener(this);
  }

  @Override
public void onClick(View view)
  {
	  try{
          switch (view.getId()) {
          case R.id.button_yes:
              this.dismiss();
        	  m_scene.quitGame();
              break;
 
          case R.id.button_no:
        	  m_scene.resumeGame();
              this.dismiss();
              break;
 
          default:
              break;
          }       
      }catch(Exception e){}
  }
}
