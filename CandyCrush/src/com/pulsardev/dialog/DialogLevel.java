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

public class DialogLevel extends Dialog
  implements View.OnClickListener
{
  Activity activity;
  public static GameScene m_scene;
	
  public DialogLevel(Context context)
  {
    super(context);
    UtilDialog.iniDialog(this);
    this.activity = ((Activity)context);
    setContentView(R.layout.dialog_level);
    Util.resizeDialog(findViewById(R.id.dialogLevelLayout));
    ((Button)findViewById(R.id.button_level1)).setOnClickListener(this);
    ((Button)findViewById(R.id.button_level2)).setOnClickListener(this);
    ((Button)findViewById(R.id.button_level3)).setOnClickListener(this);
    
  }

  @Override
public void onClick(View view)
  {
	  try{
			switch (view.getId()) {
			case R.id.button_level1:
				m_scene.setLevel(1);
				this.dismiss();
				break;
				
			case R.id.button_level2:
				m_scene.setLevel(2);
				this.dismiss();
				break;
			case R.id.button_level3:
				m_scene.setLevel(3);
				this.dismiss();
				break;

			default:
				break;
			}       
		}catch(Exception e){}
  }
}
