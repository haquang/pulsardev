package com.pulsardev.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.pulsardev.fast15puzzle.R;
import com.pulsardev.fast15puzzle.GameScene;
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
   // Util.resizeDialog(findViewById(R.id.dialogLevelLayout));
    ((ImageButton)findViewById(R.id.button_level1)).setOnClickListener(this);
    ((ImageButton)findViewById(R.id.button_level2)).setOnClickListener(this);
    ((ImageButton)findViewById(R.id.button_level3)).setOnClickListener(this);
    ((ImageButton)findViewById(R.id.button_highscore)).setOnClickListener(this);
    ((ImageButton)findViewById(R.id.button_instruction)).setOnClickListener(this);
    
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
			case R.id.button_highscore:
				m_scene.highScore();
				this.dismiss();
				break;
			case R.id.button_instruction:
				m_scene.manualScreen();
				this.dismiss();
				break;

			default:
				break;
			}       
		}catch(Exception e){}
  }
}
