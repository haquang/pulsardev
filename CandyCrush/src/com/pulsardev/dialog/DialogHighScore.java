package com.pulsardev.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import org.andengine.entity.text.Text;

import com.pulsardev.candycrush.GameScene;
import com.pulsardev.candycrush.R;
import com.pulsardev.components.Highscore;
import com.pulsardev.config.Constant;
import com.pulsardev.util.Util;
import com.pulsardev.util.UtilDialog;

public class DialogHighScore extends Dialog
  implements View.OnClickListener
{
  Activity m_activity;
  public static GameScene m_scene;
  Button finish;
  
  ArrayList<TextView> lst_name;
  ArrayList<TextView> lst_score;
  
  TextView txt_name1;
  TextView txt_name2;
  TextView txt_name3;
  TextView txt_name4;
  TextView txt_name5;
  
  TextView txt_high_score1;
  TextView txt_high_score2;
  TextView txt_high_score3;
  TextView txt_high_score4;
  TextView txt_high_score5;

  public DialogHighScore(Context paramContext)
  {
    super(paramContext);
    UtilDialog.iniDialog(this);
    this.m_activity = ((Activity)paramContext);
    setContentView(R.layout.dialog_highscore);
  //  Util.resizeDialog(findViewById(R.id.dialogExitLayout));
    finish = ((Button)findViewById(R.id.button_finish));
    finish.setOnClickListener(this);
    
    lst_name = new ArrayList<TextView>();
    lst_score = new ArrayList<TextView>();
    
    txt_name1 = (TextView)findViewById(R.id.txt_name1);
    txt_name2 = (TextView)findViewById(R.id.txt_name2);
    txt_name3 = (TextView)findViewById(R.id.txt_name3);
    txt_name4 = (TextView)findViewById(R.id.txt_name4);
    txt_name5 = (TextView)findViewById(R.id.txt_name5);
    lst_name.add(txt_name1);
    lst_name.add(txt_name2);
    lst_name.add(txt_name3);
    lst_name.add(txt_name4);
    lst_name.add(txt_name5);
    
    txt_high_score1 = (TextView)findViewById(R.id.txt_score1);
    txt_high_score2 = (TextView)findViewById(R.id.txt_score2);
    txt_high_score3 = (TextView)findViewById(R.id.txt_score3);
    txt_high_score4 = (TextView)findViewById(R.id.txt_score4);
    txt_high_score5 = (TextView)findViewById(R.id.txt_score5);
    
    lst_score.add(txt_high_score1);
    lst_score.add(txt_high_score2);
    lst_score.add(txt_high_score3);
    lst_score.add(txt_high_score4);
    lst_score.add(txt_high_score5);
    
  }

  public void setHighScore(Highscore m_high_score){
	  for (int i = 0;i<Constant.HIGHT_SCORE_SIZE;i++){
		  if (m_high_score.getName(i).isEmpty())
			return;
		  else 
		  {
			  Log.i(Constant.TAG, String.valueOf(m_high_score.getName(i)) + String.valueOf(m_high_score.getScore(i)));
			  lst_name.get(i).setText(m_high_score.getName(i));
			  lst_score.get(i).setText(String.valueOf(m_high_score.getScore(i)));
		  }
	  }
  }
  @Override
public void onClick(View view)
  {
	  try{
          switch (view.getId()) {
          case R.id.button_finish:
              this.dismiss();
              break;
 
          default:
              break;
          }       
      }catch(Exception e){}
  }
}
