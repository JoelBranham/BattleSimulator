package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Timer;

public class BattleSimulator extends JPanel implements Observer, ChangeListener, ActionListener{

	private int min, max, init;
	private JSlider slider;
	private JButton startButton;
	
	private int width, height, numUnits;
	private Grid grid;
	private JLabel[][] labels;
	private JLabel message;
	private JPanel buttonPanel, field, messagePanel;
	
	public BattleSimulator(int height, int width){
		
		this.height = height;
		this.width = width;
		
		min = 0;
		max = height * width;
		init = numUnits = height * width / 2;
		slider = new JSlider(JSlider.HORIZONTAL, min, max, init);
		slider.addChangeListener(this);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		add(slider, SwingConstants.CENTER);
		
		buttonPanel = new JPanel();
		startButton = new JButton("Start Simulation");
		startButton.addActionListener(this);
		startButton.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(startButton);
		add(buttonPanel);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		field = new JPanel();
		field.setLayout(new GridLayout(height, width));
		labels = new JLabel[height][width];
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				labels[i][j] = new JLabel();
				labels[i][j].setPreferredSize(new Dimension(50,50));
				labels[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				labels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				labels[i][j].setOpaque(true);
				field.add(labels[i][j]);
			}
		}
		add(field);
		
		messagePanel = new JPanel();
		message = new JLabel("");
		messagePanel.add(message);
		
		add(messagePanel, BorderLayout.CENTER);
	}
	
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			numUnits = (int)source.getValue();
		}
	}
	
	public void actionPerformed(ActionEvent e){
		if (startButton == e.getSource()){
			grid = new Grid(height, width, numUnits);
			grid.addObserver(this);
			message.setText("Units remaining: " + grid.getUnitsRemaining());
			grid.launchGame();
			updateUnits();
		}
	}
	
    public void update(Observable o, Object arg) {	
		Scanner s = new Scanner((String) arg).useDelimiter(":");
		updateUnits();
	}
	
	private void updateUnits(){
		for (int row = 0; row < labels.length; row++){
			for (int col = 0; col < labels[0].length; col++){
				if (grid.isOccupied(row,col)){
					double fullHealth = grid.getUnit(row,col).getFullHealth();
					double currentHealth = grid.getUnit(row,col).getHealthRemaining();
					if (fullHealth == currentHealth){
						labels[row][col].setBackground(Color.green);
					}
					else if (currentHealth / fullHealth >= 0.75){
						labels[row][col].setBackground(Color.yellow);
					}
					else if (currentHealth / fullHealth >= 0.50){
						labels[row][col].setBackground(Color.orange);
					}
					else{
						labels[row][col].setBackground(Color.red);
					}
					labels[row][col].setText(currentHealth + "");
				}
				else{
					labels[row][col].setBackground(Color.white);
					labels[row][col].setText("");
				}
			}
		}
		if(grid.isGameover()){
			message.setText("Simulation completed in " + grid.getTotalSeconds() + " seconds.");
		}
		else{
			message.setText("Units remaining: " + grid.getUnitsRemaining());
		}
	}
	
}