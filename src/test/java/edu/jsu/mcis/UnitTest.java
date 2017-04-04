package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;

public class UnitTest{
	private Unit unit;
	
	@Before
	public void setUp(){
		unit = new Unit();
	}
	
	@Test
	public void testHit(){
		unit.hit(30);
		assertEquals(70, unit.getHealthRemaining());
		unit.hit(20);
		assertEquals(50, unit.getHealthRemaining());
		unit.hit(51);
		assertEquals(0, unit.getHealthRemaining());
	}
	
	@Test
	public void testIsAlive(){
		unit.hit(30);
		assertTrue(unit.isAlive());
		unit.hit(20);
		assertTrue(unit.isAlive());
		unit.hit(51);
		assertFalse(unit.isAlive());
	}

}