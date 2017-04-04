package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Location {
	
	private Unit unit;
	
	public Location(){
		this(null);
	}
	
	public Location(Unit unit){
		this.unit = unit;
	}
	
	public boolean hasUnit(){
		return unit != null;
	}
	
	public void removeUnit(){
		unit = null;
	}
	
	public void placeUnit(Unit u){
		this.unit = u;
		unit.setMoved();
	}
	
	public Unit getUnit(){
		return unit;
	}
	
	public Unit.Direction moveUnit(){
		if (hasUnit()){
			return unit.move();
		}
		throw new UnitException();
	}

}