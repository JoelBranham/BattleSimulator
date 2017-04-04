package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;

public class Grid extends Observable{
	
	private Location[][] grid;
	private int width, height, numUnits;
	private Timer timer;
	private boolean gameover;
	private double totalSeconds;
	
	public Grid(int height, int width, int numUnits){
		this.height = height;
		this.width = width;
		this.numUnits = numUnits;
		totalSeconds = 0.0;
		gameover = false;
		grid = new Location[height][width];
		resetGrid();
		placeUnits(numUnits);
	}
	
	public void launchGame(){  
		int maxSeconds = 1000, interval = 1000;
		timer = new Timer();
		TimerTask task = new TimerTask(){
			@Override
			public void run(){
				if (totalSeconds < maxSeconds && numUnits > 1){
					totalSeconds += (interval / 1000);
					if (totalSeconds % 2 == 0.0){
						moveUnits();
					}
					else{
						engageInBattle();
					}
				}
				else{
					gameover = true;
					timer.cancel();
					setChanged();
					notifyObservers("Gameover");
					clearChanged();
				}
			}
		};
		timer.schedule(task,0,interval);
	}
	
	public void resetGrid(){
		for (int row = 0; row < grid.length; row++){
			for (int col = 0; col < grid[0].length; col++){
				grid[row][col] = new Location();
			}
		}
	}
	
	public void placeUnits(int numUnits){
		Random r = new Random();
		while (numUnits > 0){
			boolean placed = false;
			while (!placed){
				int row = r.nextInt(grid.length);
				int col = r.nextInt(grid[0].length);
				if (!isOccupied(row, col)){
					grid[row][col] = new Location(new Unit());
					placed = true;
				}
			}
			numUnits--;
		}
	}
	
	public void moveUnits(){
		for (int row = 0; row < grid.length; row++){
			for (int col = 0; col < grid[0].length; col++){
				if (grid[row][col].hasUnit()){
					Unit.Direction d = grid[row][col].moveUnit();
					if (!grid[row][col].getUnit().wasMoved()){
						boolean placed = false;
						if (d == Unit.Direction.UP){
							if (isLegalIndex(row-1, col)){
								if (!isOccupied(row-1, col)){
									grid[row-1][col].placeUnit(grid[row][col].getUnit());
									placed = true;
								}
							}
						}
						else if (d == Unit.Direction.DOWN){
							if (isLegalIndex(row+1, col)){
								if (!isOccupied(row+1, col)){
									grid[row+1][col].placeUnit(grid[row][col].getUnit());
									placed = true;
								}
							}
						}
						else if (d == Unit.Direction.LEFT){
							if (isLegalIndex(row, col-1)){
								if (!isOccupied(row, col-1)){
									grid[row][col-1].placeUnit(grid[row][col].getUnit());
									placed = true;
								}
							}
						}
						else {
							if (isLegalIndex(row, col+1)){
								if (!isOccupied(row, col+1)){
									grid[row][col+1].placeUnit(grid[row][col].getUnit());
									placed = true;
								}
							}
						}
						if (placed){
							grid[row][col].removeUnit();
						}
					}
				}
			}
		}
		for (int row = 0; row < grid.length; row++){
			for (int col = 0; col < grid[0].length; col++){
				if (grid[row][col].hasUnit()){
					grid[row][col].getUnit().resetMoved();
				}
			}
		}
		setChanged();
		notifyObservers("");
		clearChanged();
	}
	
	private void engageInBattle(){
		for (int row = 0; row < grid.length; row++){
			for (int col = 0; col < grid[0].length; col++){
				if (grid[row][col].hasUnit()){
					checkForBattleBetweenTwoUnits(row, col, row, col+1);
					checkForBattleBetweenTwoUnits(row, col, row, col-1);
					checkForBattleBetweenTwoUnits(row, col, row-1, col);
					checkForBattleBetweenTwoUnits(row, col, row+1, col);
				}
			}
		}
		for (int row = 0; row < grid.length; row++){
			for (int col = 0; col < grid[0].length; col++){
				if (grid[row][col].hasUnit()){
					grid[row][col].getUnit().resetHit();
				}
			}
		}
		setChanged();
		notifyObservers("");
		clearChanged();
	}
	
	private void checkForBattleBetweenTwoUnits(int row1, int col1, int row2, int col2){
		if (isLegalIndex(row2, col2) && isLegalIndex(row1,col1)){
			if(grid[row2][col2].hasUnit() && grid[row1][col1].hasUnit()){
				if (!grid[row2][col2].getUnit().wasHit() && !grid[row1][col1].getUnit().wasHit()){
					grid[row2][col2].getUnit().hit(grid[row1][col1].getUnit().getAttack());
					grid[row2][col2].getUnit().setHit();
					grid[row1][col1].getUnit().hit(grid[row2][col2].getUnit().getAttack());
					grid[row1][col1].getUnit().setHit();
				}
				if (!grid[row2][col2].getUnit().isAlive()){
					grid[row2][col2].removeUnit();
					numUnits--;
				}
				if (!grid[row1][col1].getUnit().isAlive()){
					grid[row1][col1].removeUnit();
					numUnits--;
				}
			}
		}
	}
	
	public boolean isOccupied(int row, int col){
		return grid[row][col].hasUnit();
	}
	
	public Unit getUnit(int row, int col){
		if (isOccupied(row,col)){
			return grid[row][col].getUnit();
		}
		throw new UnitException();
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	private boolean isLegalIndex(int row, int col){
		return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
	}
	
	public boolean isGameover(){
		return gameover;
	}
	
	public double getTotalSeconds(){
		return totalSeconds;
	}
	
	public int getUnitsRemaining(){
		return numUnits;
	}

}