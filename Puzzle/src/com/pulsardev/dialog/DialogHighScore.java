package com.pulsardev.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import org.andengine.entity.text.Text;

import com.pulsardev.fast15puzzle.R;
import com.pulsardev.components.Highscore;
import com.pulsardev.components.NumSprite;
import com.pulsardev.config.Constant;
import com.pulsardev.fast15puzzle.GameScene;
import com.pulsardev.util.CustomAdapter;
import com.pulsardev.util.Util;
import com.pulsardev.util.UtilDialog;

public class DialogHighScore extends Dialog
  implements View.OnClickListener
{
  Activity m_activity;
  public static GameScene m_scene;
  Button finish;
  ListView listView;
  ArrayList<String> _name;
  ArrayList<String> _score;
  
  
  public DialogHighScore(Context paramContext)
  {
    super(paramContext);
    UtilDialog.iniDialog(this);
    this.m_activity = ((Activity)paramContext);
    setContentView(R.layout.dialog_highscore);
  //  Util.resizeDialog(findViewById(R.id.dialogExitLayout));
    finish = ((Button)findViewById(R.id.button_finish));
    finish.setOnClickListener(this);
    listView = (ListView)findViewById(R.id.list_highscore);
   
    _name = new ArrayList<String>();
    _score = new ArrayList<String>();
          
  }

  public void setHighScore(Highscore m_high_score){
	  for (int i = 0;i<Constant.HIGHT_SCORE_SIZE;i++){
		  if (m_high_score.getName(i).isEmpty())
			return;
		  else 
		  {		 
			  _name.add(m_high_score.getName(i));
			  _score.add(String.valueOf(m_high_score.getScore(i)));
		  }
	  }
	  
	    CustomAdapter adapter = new CustomAdapter(m_activity, _name,_score);
	    // Assign adapter to ListView
	    listView.setAdapter(adapter); 
  }
  @Override
public void onClick(View view)
  {
	  try{
          switch (view.getId()) {
          case R.id.button_finish:
        	  NumSprite.istouchable = true;
              this.dismiss();
              break;
 
          default:
              break;
          }       
      }catch(Exception e){}
  }
}
