package com.pulsardev.components;

import com.pulsardev.config.Constant;

import android.content.Context;
import android.content.SharedPreferences;

public class Highscore {
	private SharedPreferences preferences; 
	private String names[]; 
	private long score[]; 

	public Highscore(Context context) 
	{ 
		preferences = context.getSharedPreferences("Highscore", 0); 
		names = new String[Constant.HIGHT_SCORE_SIZE]; 
		score = new long[Constant.HIGHT_SCORE_SIZE]; 

		for (int x=0; x<Constant.HIGHT_SCORE_SIZE; x++) 
		{ 
			names[x] = preferences.getString("name"+x, "-"); 
			score[x] = preferences.getLong("score"+x, 0); 
		} 

	} 

	public String getName(int x) 
	{ 
		//get the name of the x-th position in the Highscore-List 
		return names[x]; 
	} 

	public long getScore(int x) 
	{ 
		//get the score of the x-th position in the Highscore-List 
		return score[x]; 
	} 

	public boolean inHighscore(long score) 
	{ 
		//test, if the score is in the Highscore-List 
		int position; 
		for (position=0; position<Constant.HIGHT_SCORE_SIZE&&this.score[position]>score; position++); 

		if (position==Constant.HIGHT_SCORE_SIZE) return false; 
		return true; 
	} 

	public boolean addScore(String name, long score) 
	{ 
		//add the score with the name to the Highscore-List 
		int position; 
		for (position=0; position<Constant.HIGHT_SCORE_SIZE&&this.score[position]>score; position++); 

		if (position==Constant.HIGHT_SCORE_SIZE) return false; 

		for (int x=Constant.HIGHT_SCORE_SIZE - 1; x>position; x--) 
		{ 
			names[x]=names[x-1]; 
			this.score[x]=this.score[x-1]; 
		} 

		this.names[position] = new String(name); 
		this.score[position] = score; 

		SharedPreferences.Editor editor = preferences.edit(); 
		for (int x=0; x< Constant.HIGHT_SCORE_SIZE; x++) 
		{ 
			editor.putString("name"+x, this.names[x]); 
			editor.putLong("score"+x, this.score[x]); 
		} 
		editor.commit(); 
		return true; 
	}
	
	public void clear(){
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
		
	}
}
