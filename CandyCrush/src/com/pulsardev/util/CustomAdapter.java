package com.pulsardev.util;

import java.util.ArrayList;

import com.pulsardev.candycrush.R;
import com.pulsardev.components.Highscore;
import com.pulsardev.config.Constant;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	private static LayoutInflater inflater=null;
	ArrayList<String> _name;
	ArrayList<String> _score;
	 Context mContext;
	
	public CustomAdapter(Context c, ArrayList<String> _name2,ArrayList<String> _score2) {
		// TODO Auto-generated constructor stub
		mContext = c;
		_name = _name2;
		_score = _score2;
		 inflater = (LayoutInflater) mContext
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _name.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class ViewHolder{      
        public TextView name;
        public TextView score;
     }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
        ViewHolder holder;
		 if(convertView==null){
             
             /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
             vi = inflater.inflate(R.layout.list_row, parent, false);
              
             /****** View Holder Object to contain tabitem.xml file elements ******/
          //   Log.i(Constant.TAG, _name.get(position) + String.valueOf(_name.get(position)));
             holder = new ViewHolder();
             holder.name = (TextView) vi.findViewById(R.id.txt_highscore_name);
             holder.name.setText( _name.get(position));
             holder.score=(TextView)vi.findViewById(R.id.txt_highscore_score);         
             holder.score.setText( String.valueOf(_score.get(position)));
//             
            /************  Set holder with LayoutInflater ************/
             vi.setTag( holder );
         }
         else 
             holder=(ViewHolder)vi.getTag();

             /************  Set Model values in Holder elements ***********/

            
              
		 return vi;
	}

}
