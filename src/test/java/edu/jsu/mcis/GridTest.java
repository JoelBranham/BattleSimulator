package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;

public class GridTest{
	private Grid g;
	private Observable o;
	
	@Before
	public void setUp(){
		g = new Grid(5,5,10);
	}
	
	@Test
	public void testUnitsPlaced(){
		int count = 0;
		for (int row = 0; row < g.getHeight(); row++){
			for (int col = 0; col < g.getWidth(); col++){
				if (g.isOccupied(row,col)){
					count++;
				}
			}
		}
		assertEquals(10, count);
	}

}