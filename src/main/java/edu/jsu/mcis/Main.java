package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
		JFrame frame = new JFrame("Battle Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new BattleSimulator(10,10));
		frame.pack();
		frame.setVisible(true);
	}
}

