package com.pulsardev.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import com.pulsardev.candycrush.GameActivity;;

public class Util
{
	public static int getRandomIndex(int paramInt1, int paramInt2)
	{
		return paramInt1 + (int)(Math.random() * (1 + (paramInt2 - paramInt1)));
	}

	public static boolean checkContinuous(ArrayList<Integer> matrix){
		if (2 == matrix.size())
			return (1 == matrix.get(1) - matrix.get(0));
		else {
			int val = matrix.remove(0);
			return (1 == matrix.get(0) -  val) && checkContinuous(matrix);
		}
	}
	public static ArrayList<Integer> checkRow(ArrayList<Integer> matrix,int size){
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> subArray = new ArrayList<Integer>();
		for (int i = 0;i<size;i++){
			for (int j = 0;j< size;j++){
				subArray.add(matrix.get(i*size+j));
			}
			if (checkContinuous(subArray)){
				result.add(i);
			}
			subArray.clear();
		}
		return result;
	}

	public  static ArrayList<Integer> checkCol(ArrayList<Integer> matrix,int size){
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> subArray = new ArrayList<Integer>();
		for (int j = 0;j<size;j++){
			for (int i = size - 1 ;i >=0;i--){
				subArray.add(matrix.get(j+i*size));
			}
			if (checkContinuous(subArray)){
				result.add(j);
			}
			subArray.clear();
		}
	
		return result;
	}

	public static boolean checkCrossDown(ArrayList<Integer> matrix,int size){
		ArrayList<Integer> subArray = new ArrayList<Integer>();
		for (int i=0,j = size - 1;(j >= 0) && (i<size);j--,i++)
			subArray.add(matrix.get(j+i*size));
		return checkContinuous(subArray);
	}


	public static boolean checkCrossUp(ArrayList<Integer> matrix,int size){
		ArrayList<Integer> subArray = new ArrayList<Integer>();
		for (int i=0,j = 0;(j<size) && (i<size);j++,i++)
			subArray.add(matrix.get(j+i*size));			
		return checkContinuous(subArray);
	}
}

