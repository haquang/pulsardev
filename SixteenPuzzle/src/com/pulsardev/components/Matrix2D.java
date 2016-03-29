package com.pulsardev.components;

import java.util.ArrayList;
import java.util.List;

import com.pulsardev.config.Constant;
import com.pulsardev.config.Constant.Swipe_type;
import com.pulsardev.util.Util;

import android.graphics.Point;
import android.util.Log;

public class Matrix2D {
	private int[][] _matrix;
	public int _matrix_size;
	public static int _item_size; 
	public static int _shift_col;
	public static int _shift_row;
	/**
	 *  Create random matrix
	 */
	public void createRandomMatrix() {
		try {
			int N = _matrix_size*_matrix_size;
			ArrayList<Integer> list = new ArrayList<Integer>(N); // row * col - 1
			for (int i = 1; i <= N; i++){
				list.add(i);
			}
			for (int i = 0;i<_matrix_size;i++)
				for (int j = 0;j<_matrix_size;j++){
					_matrix[i][j] = list.remove((int)(Math.random() * list.size()));
				}
		} catch (Exception e) {
			Log.i(Constant.TAG, "EXEPTION");
		}
		
	}
	
	public void createTestMatrix(){
	// 16
//		_matrix[0][0] = 5;
//		_matrix[0][1] = 2;
//		_matrix[0][2] = 3;
//		_matrix[0][3] = 4;
//		_matrix[1][0] = 9;
//		_matrix[1][1] = 6;
//		_matrix[1][2] = 7;
//		_matrix[1][3] = 8;
//		_matrix[2][0] = 13;
//		_matrix[2][1] = 10;
//		_matrix[2][2] = 11;
//		_matrix[2][3] = 12;
//		_matrix[3][0] = 1;
//		_matrix[3][1] = 14;
//		_matrix[3][2] = 15;
//		_matrix[3][3] = 16;
//				[5,2,3,4,
//		           9,6,7,8,
//		           13,10,11,12,
//		           1,14,15,16];
		
		
		// magic square
		
		_matrix[0][0] = 9;
		_matrix[0][1] = 14;
		_matrix[0][2] = 15;
		_matrix[0][3] = 1;
		_matrix[1][0] = 5;
		_matrix[1][1] = 7;
		_matrix[1][2] = 6;
		_matrix[1][3] = 12;
		_matrix[2][0] = 16;
		_matrix[2][1] = 11;
		_matrix[2][2] = 10;
		_matrix[2][3] = 8;
		_matrix[3][0] = 4;
		_matrix[3][1] = 2;
		_matrix[3][2] = 3;
		_matrix[3][3] = 13;
		
	}

	/**
	 * Return position of -1 (empty) in matrix
	 * 
	 * @return
	 */
	public ArrayList<Point> getEmptyPoint() {
		ArrayList<Point> list_point = new ArrayList<Point>();
		for (int i = 0; i < _matrix_size; i++) {
			for (int j = 0; j < _matrix_size; j++) {
				if (_matrix[i][j] == -1) {
					list_point.add(new Point(i, j)); // Point keep the row,col of empty position
				}
			}
		}
		return list_point;
	}
	public void setSize(int size){
		_matrix_size = size;
	}
	/**
	 * Construction
	 */
	public Matrix2D(int size) {
		_matrix_size = size;
		_matrix = new int[Constant.GRID_MAX][Constant.GRID_MAX];
	}

	/**
	 * Display matrix for debugging
	 */
	public void showMatrix2D() {
		ArrayList<Integer> subArray =  new ArrayList<Integer>();

		for (int i = 0;i<_matrix_size;i++){
			for (int j = 0;j<_matrix_size;j++){
				subArray.add(_matrix[i][j]);
			}
			Log.i(Constant.TAG, String.valueOf(subArray));
			subArray.clear();
		}
	}

	/*
	 *  Get the display position from row/col
	 */
	public Point getPxPy(int row,int col){
		// Matrix of display is rotated
		if ((row > _matrix_size) || (col >= _matrix_size)){
			return null;
		}
		return (new Point(_shift_col + col * _item_size,_shift_row + (_matrix_size -row) * _item_size));
	}

//	public Point getZeroPxPy(){
//		for (int i = 0;i<_matrix_size;i++)
//			for (int j = 0;j<_matrix_size;j++)
//			{
//				if (_matrix[i][j] ==0)
//					return (new Point(_shift_col + j * _item_size,_shift_row + i * _item_size));
//			}
//		return (new Point(0,0));
//	}
	
	/*
	 * Shift operation
	 */
	
	public void shift(int pos,Swipe_type type){
		switch (type){
		case LEFT:
			shiftLeft(pos);
			break;
		case RIGHT:
			shiftRight(pos);
			break;
		case UP:
			shiftUp(pos);
			break;
		case DOWN:
			shiftDown(pos);
			break;
	
		}
	}
	
	public void shiftLeft(int pos){
		
		int temp = _matrix[pos][0];
		for (int i = 0;i < _matrix_size;i++){
			_matrix[pos][i] = _matrix[pos][i+1]; 
		}
		_matrix[pos][_matrix_size-1] = temp;
	}
	
	public void shiftRight(int pos){
		int temp = _matrix[pos][_matrix_size-1];
		for (int i = _matrix_size - 1;i > 0;i--){
			_matrix[pos][i] = _matrix[pos][i-1]; 
		}
		_matrix[pos][0] = temp;
	}
	
	public void shiftUp(int pos){
		int temp = _matrix[0][pos];
		for (int i = 0;i < _matrix_size;i++){
			_matrix[i][pos] = _matrix[i+1][pos]; 
		}
		_matrix[_matrix_size-1][pos] = temp;
	}
	
	public void shiftDown(int pos){
		int temp = _matrix[_matrix_size-1][pos];
		for (int i = _matrix_size - 1;i > 0;i--){
			_matrix[i][pos] = _matrix[i-1][pos]; 
		}
		_matrix[0][pos] = temp;		
	}
	
	public boolean isSixteen(){
		int value = _matrix[0][0];
		for (int i = 0; i < _matrix_size; i++)
			for (int j = 0; j < _matrix_size; j++){
				if ((i!=0) || (j!=0)){
					if (((_matrix[i][j] - value) == 1))
						value = _matrix[i][j];
					else 
						return false;
				}
			}
		return true;
	}
	
	public boolean isMagicSquare(){
		int magicConst = (int) (0.5*_matrix_size * (_matrix_size * _matrix_size + 1));
		// check row
		int sum;
		for (int i = 0;i < _matrix_size;i++){
			sum = 0;
			for (int j = 0; j < _matrix_size;j++)
				sum = sum + _matrix[i][j];
			if (sum != magicConst)
				return false;
		}
		
		// check col
		for (int i = 0;i < _matrix_size;i++){
			sum = 0;
			for (int j = 0; j < _matrix_size;j++)
				sum = sum + _matrix[j][i];
			if (sum != magicConst)
				return false;
		}
		
		// check diagonal
		sum = 0;
		for (int i = 0;i < _matrix_size;i++){
			for (int j = 0; j < _matrix_size;j++)
				if (i == j)
				sum = sum + _matrix[i][j];
		}
		if (sum != magicConst)
			return false;
		
		sum = 0;
		for (int i = 0;i < _matrix_size;i++){
			for (int j = 0; j < _matrix_size;j++)
				if ((i + j) == _matrix_size -1)
				sum = sum + _matrix[i][j];
		}
		if (sum != magicConst)
			return false;
		return true;
		
	}

//	public Point getZeroXY(){
//		for (int i = 0;i<_matrix_size;i++)
//			for (int j = 0;j<_matrix_size;j++)
//			{
//				if (_matrix[i][j] ==0)
//					return (new Point(i,j));
//			}
//		return (new Point(0,0));
//	}
//
//	public ArrayList<Integer> checkRow(){
//		ArrayList<Integer> result = new ArrayList<Integer>();
//		ArrayList<Integer> subArray = new ArrayList<Integer>();
//		for (int i = 0;i<_matrix_size;i++){
//			for (int j = 0;j< _matrix_size;j++){
//				subArray.add(_matrix[i][j]);
//			}
//			if (Util.checkContinuous(subArray)){
//				result.add(i);
//			}
//			subArray.clear();
//		}
//		return result;
//	}
//
//	public ArrayList<Integer> checkCol(){
//		ArrayList<Integer> result = new ArrayList<Integer>();
//		ArrayList<Integer> subArray = new ArrayList<Integer>();
//		for (int j = 0;j<_matrix_size;j++){
//			for (int i = _matrix_size - 1 ;i >=  0;i--){
//				subArray.add(_matrix[i][j]);
//			}
//			if (Util.checkContinuous(subArray)){
//				result.add(j);
//			}
//			subArray.clear();
//		}
//
//		return result;
//	}
//
//	public boolean checkCrossDown(){
//		ArrayList<Integer> subArray = new ArrayList<Integer>();
//		for (int j = 0;j<_matrix_size;j++)
//			for (int i = 0 ;i < _matrix_size;i++){
//				if (i + j == _matrix_size - 1)
//					subArray.add(_matrix[i][j]);
//			}
//		return Util.checkContinuous(subArray);
//	}
//
//	public boolean checkCrossUp(){
//		ArrayList<Integer> subArray = new ArrayList<Integer>();
//		for (int j = 0;j<_matrix_size;j++)
//			for (int i = 0 ;i < _matrix_size;i++){
//				if (i == j)
//					subArray.add(_matrix[i][j]);
//			}
//		return Util.checkContinuous(subArray);
//	}
//
//	public void removeRow(int i){
//		for (int j = 0;j< _matrix_size;j++){
//			_matrix[i][j] = -1;
//		}
//	}
//
//	public void removeCol(int j){
//		for (int i = 0;i< _matrix_size;i++){
//			_matrix[i][j] = -1;
//		}
//	}
//	
//	public void removeCrossDown(){
//		for (int j = 0;j<_matrix_size;j++)
//			for (int i = 0 ;i < _matrix_size;i++){
//				if (i + j == _matrix_size - 1)
//					_matrix[i][j] = -1;
//			}
//	}
//	
//	public void removeCrossUp(){
//		for (int j = 0;j<_matrix_size;j++)
//			for (int i = 0 ;i < _matrix_size;i++){
//				if (i == j)
//					_matrix[i][j] = -1;
//			}
//	}


	/**
	 * SET - GET
	 *
	 * @return
	 */
	public int[][] getMatrix() {
		return _matrix;
	}
	public void setMatrix(int[][] matrix) {
		_matrix = matrix;
	}

	public void setShift(int row,int col){
		_shift_row = row;
		_shift_col = col;
	}
}
