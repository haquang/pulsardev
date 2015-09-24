package com.pulsardev.components;

import java.util.ArrayList;

import com.pulsardev.config.Constant;
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
			int N = _matrix_size*_matrix_size - 1;
			ArrayList<Integer> list = new ArrayList<Integer>(N); // row * col - 1
			for (int i = 0; i <= N; i++){
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
		return (new Point(_shift_col + col * _item_size,_shift_row + row * _item_size));
	}

	public Point getZeroPxPy(){
		for (int i = 0;i<_matrix_size;i++)
			for (int j = 0;j<_matrix_size;j++)
			{
				if (_matrix[i][j] ==0)
					return (new Point(_shift_col + j * _item_size,_shift_row + i * _item_size));
			}
		return (new Point(0,0));
	}

	public Point getZeroXY(){
		for (int i = 0;i<_matrix_size;i++)
			for (int j = 0;j<_matrix_size;j++)
			{
				if (_matrix[i][j] ==0)
					return (new Point(i,j));
			}
		return (new Point(0,0));
	}

	public ArrayList<Integer> checkRow(){
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> subArray = new ArrayList<Integer>();
		for (int i = 0;i<_matrix_size;i++){
			for (int j = 0;j< _matrix_size;j++){
				subArray.add(_matrix[i][j]);
			}
			if (Util.checkContinuous(subArray)){
				result.add(i);
			}
			subArray.clear();
		}
		return result;
	}

	public ArrayList<Integer> checkCol(){
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> subArray = new ArrayList<Integer>();
		for (int j = 0;j<_matrix_size;j++){
			for (int i = _matrix_size - 1 ;i >=  0;i--){
				subArray.add(_matrix[i][j]);
			}
			if (Util.checkContinuous(subArray)){
				result.add(j);
			}
			subArray.clear();
		}

		return result;
	}

	public boolean checkCrossDown(){
		ArrayList<Integer> subArray = new ArrayList<Integer>();
		for (int j = 0;j<_matrix_size;j++)
			for (int i = 0 ;i < _matrix_size;i++){
				if (i + j == _matrix_size - 1)
					subArray.add(_matrix[i][j]);
			}
		return Util.checkContinuous(subArray);
	}

	public boolean checkCrossUp(){
		ArrayList<Integer> subArray = new ArrayList<Integer>();
		for (int j = 0;j<_matrix_size;j++)
			for (int i = 0 ;i < _matrix_size;i++){
				if (i == j)
					subArray.add(_matrix[i][j]);
			}
		return Util.checkContinuous(subArray);
	}

	public void removeRow(int i){
		for (int j = 0;j< _matrix_size;j++){
			_matrix[i][j] = -1;
		}
	}

	public void removeCol(int j){
		for (int i = 0;i< _matrix_size;i++){
			_matrix[i][j] = -1;
		}
	}
	
	public void removeCrossDown(){
		for (int j = 0;j<_matrix_size;j++)
			for (int i = 0 ;i < _matrix_size;i++){
				if (i + j == _matrix_size - 1)
					_matrix[i][j] = -1;
			}
	}
	
	public void removeCrossUp(){
		for (int j = 0;j<_matrix_size;j++)
			for (int i = 0 ;i < _matrix_size;i++){
				if (i == j)
					_matrix[i][j] = -1;
			}
	}


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
