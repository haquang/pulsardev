package com.pulsardev.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.pulsardev.sixteenpuzzle.GameScene;
import com.pulsardev.sixteenpuzzle.R;
import com.pulsardev.util.Util;
import com.pulsardev.util.UtilDialog;

public class DialogGameOver extends Dialog
implements View.OnClickListener
{
	TextView textView;
	Button btn_try_again;
	public static GameScene m_scene;

	public DialogGameOver(Context paramContext)
	{
		super(paramContext);
		UtilDialog.iniDialog(this);
		setContentView(R.layout.dialog_gameover);
	//	Util.resizeDialog(findViewById(R.id.dialogGameOverLayout));

		((Button)findViewById(R.id.button_tryagain)).setOnClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		try{
			switch (view.getId()) {
			case R.id.button_tryagain:
				m_scene.restartGame();
				this.dismiss();
				break;

			default:
				break;
			}       
		}catch(Exception e){}
	}

}