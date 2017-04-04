package edu.jsu.mcis;

import java.util.*;

public class Unit{
	
	private int attack;
	private int health, fullHealth;
	private Random random;
	public enum Direction{UP, DOWN, LEFT, RIGHT};
	private boolean movedAlready;
	private boolean hitAlready;
	
	public Unit(){
		this(new Random());
	}
	
	public Unit(Random random){
		attack = 25;
		health = fullHealth = 100;
		this.random = random;
		movedAlready = hitAlready = false;
	}
	
	public Direction move(){
		int move = random.nextInt(4);
		if (move == 0){
			return Direction.UP;
		}
		else if (move == 1){
			return Direction.DOWN;
		}
		else if (move == 2){
			return Direction.LEFT;
		}
		return Direction.RIGHT;
	}
	
	public void setMoved(){
		movedAlready = true;
	}
	
	public void resetMoved(){
		movedAlready = false;
	}
	
	public boolean wasMoved(){
		return movedAlready;
	}
	
	public int getAttack(){
		return attack;
	}
	
	public void setHit(){
		hitAlready = true;
	}
	
	public boolean wasHit(){
		return hitAlready;
	}
	
	public void resetHit(){
		hitAlready = false;
	}
	
	public void hit(int healthRemove){
		health -= healthRemove;
	}
	
	public int getFullHealth(){
		return fullHealth;
	}
	
	public int getHealthRemaining(){
		return (health <= 0) ? 0: health;
	}
	
	public boolean isAlive(){
		return health > 0;
	}
	
}