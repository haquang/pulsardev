package com.pulsardev.components;

import org.andengine.entity.text.Text;

import com.pulsardev.config.Constant;

import android.util.Log;

public class Score {
		private int _score = 0;
		private int _level;
		private Text t_score_text;
		public Score(int level){
			_level = level;
			_score = 0;
		}
		
		public void setLevel(int i){
			_level = i;
		}
		public void addScore(int bonus){
			
//			Log.i(Constant.TAG, "Add score: bonus: "  + String.valueOf(bonus) + " Level: " + String.valueOf(_level));
			switch (_level) {
			case 1:
				_score += 1 * bonus;
				break;
			case 2:
				_score += 5  * bonus;
				break;
			case 3:
				_score += 10  * bonus;
				break;
			default:
				break;
			}
//			Log.i(Constant.TAG, "Score: " + String.valueOf(_score));
			updateScore();
		}
		public int getScore(){
			return _score;
		}
		
		public void setTextScore(Text text){
			t_score_text = text;
		}
		
		private void updateScore(){
			t_score_text.setText("Score: \n" + _score);
//			Log.i(Constant.TAG, "Score: " + String.valueOf(t_score_text));
		}

		public void reset() {
			// TODO Auto-generated method stub
			_score = 0;
			updateScore();
		}
}
