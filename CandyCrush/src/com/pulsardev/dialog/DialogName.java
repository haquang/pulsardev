package com.pulsardev.dialog;

import com.pulsardev.candycrush.GameScene;
import com.pulsardev.candycrush.R;
import com.pulsardev.util.UtilDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class DialogName extends Dialog
implements View.OnClickListener
{
	Activity m_activity;
	public static GameScene m_scene;
	ImageButton no;
	ImageButton yes;
	EditText txt_name;

	public DialogName(Context paramContext)
	{
		super(paramContext);
		UtilDialog.iniDialog(this);
		this.m_activity = ((Activity)paramContext);
		setContentView(R.layout.dialog_name);
		//  Util.resizeDialog(findViewById(R.id.dialogExitLayout));
		yes = ((ImageButton)findViewById(R.id.button_name_OK));
		no = ((ImageButton)findViewById(R.id.button_name_cancel));
		txt_name = (EditText)findViewById(R.id.txt_name);
		yes.setOnClickListener(this);
		no.setOnClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		try{
			switch (view.getId()) {
			case R.id.button_name_OK:
				// Update name to the high score
				m_scene.updateHighScore(txt_name.getText().toString());
				// Display gameover
				this.dismiss();
				break;
				
			case R.id.button_name_cancel:
				// Display game over
				m_scene.displayGameOver();
				this.dismiss();
				break;

			default:
				break;
			}       
		}catch(Exception e){}
	}
}
