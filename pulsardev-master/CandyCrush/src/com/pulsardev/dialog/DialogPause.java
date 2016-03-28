package com.pulsardev.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.pulsardev.candycrush.GameScene;
import com.pulsardev.candycrush.R;
import com.pulsardev.util.Util;
import com.pulsardev.util.UtilDialog;

public class DialogPause extends Dialog
  implements View.OnClickListener
{
  Activity activity;
  public static GameScene m_scene;
	
  public DialogPause(Context context)
  {
    super(context);
    UtilDialog.iniDialog(this);
    this.activity = ((Activity)context);
    setContentView(R.layout.dialog_pause);
  //  Util.resizeDialog(findViewById(R.id.dialogPauseLayout));
    ((Button)findViewById(R.id.button_resume)).setOnClickListener(this);
    
  }

  @Override
public void onClick(View view)
  {
	  try{
			switch (view.getId()) {
			case R.id.button_resume:
				this.dismiss();
				break;

			default:
				break;
			}       
		}catch(Exception e){}
  }
}
