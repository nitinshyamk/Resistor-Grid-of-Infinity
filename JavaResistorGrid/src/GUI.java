import javax.swing.*;

import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

import javax.swing.*;

 public class GUI{
	 private JFrame DisplayGrid = new JFrame();
	 private static final int WIDTH = 20;
	 private static final int HEIGHT = WIDTH;
	 private static final Color INIT_COLOR = Color.black;
	 private Grid g;
	 
	 GUI(){
		g = new Grid(WIDTH,HEIGHT);
		g.setSource(8, 8);
		g.setDrain(7, 7);
		g.Equalize();
		System.out.println("Initializing Graphics");
		DisplayGrid.setSize(WIDTH*10, HEIGHT*10);
		DisplayGrid.setTitle("Resistor Grid: Potential MAP");
		DisplayGrid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container displaypane = DisplayGrid.getContentPane();
		displaypane.setLayout(new GridLayout(WIDTH, HEIGHT));
		 
		for (int x = 0; x <(WIDTH*HEIGHT); x++){
			if (x == WIDTH*HEIGHT/4){System.out.println("25% finished");}
			if (x == WIDTH*HEIGHT/2){System.out.println("50% finished");}
			if (x == 3*WIDTH*HEIGHT/4){System.out.println("75% finished");}
			Position pos = new Position(x/WIDTH, x%WIDTH);
			Cell c = new Cell(pos);
	        displaypane.add(c);
	    }
		System.out.println("100% finished");
		DisplayGrid.setVisible(true);
		 
	 }
	 
	 public class Cell extends JPanel{
		 Cell(Position pos){
			double percentage = g.getNode(pos.r, pos.c).getVoltage()/10.0;
			percentage = percentage*255.0;
			int colorval = (int)(Math.round(percentage));
			this.setBackground(new Color(colorval, colorval, colorval));
			this.setPreferredSize(new Dimension (2,2));
		 }
		 
	 }
	 public static void main (String args[]) {
		 System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		 	//line above to address bug in javax.swing package
		 GUI grid = new GUI(); 
	 }
}