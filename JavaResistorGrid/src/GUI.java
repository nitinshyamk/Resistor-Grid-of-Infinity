import javax.swing.*;

import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

import javax.swing.*;

 public class GUI{
	 private JFrame DisplayGrid = new JFrame();
	 private static int WIDTH;
	 private static int HEIGHT = WIDTH;
	 private static int CELL_SIZE;
	 private static final Color INIT_COLOR = Color.black;
	 private Grid g;
	 
	 public static void main (String args[]) {
		 String info = "GUI Creates a visual simulation of a resistor grid (a square) with size s:"
		 		+ "Takes two arguments in the form: GUI <size (int) > <cell size> ";
		 int s = 0,c = 0;
		 if (args.length != 0){
		 try{
			 s = Integer.parseInt(args[0]);
			 c = Integer.parseInt(args[1]);
		 }
		 catch (Exception e){
			 e.printStackTrace();
			 System.out.println(info);
		 }
		 }
		 else{s = 20; c  = 4;}
		 WIDTH = s; HEIGHT = s;
		 CELL_SIZE = c;
		 System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		 	//line above to address bug in javax.swing package
		 GUI grid = new GUI(); 
	 }
	 
	 GUI(){
		g = new Grid(WIDTH,HEIGHT);
		g.setSource(WIDTH/2,HEIGHT/2);
		g.setDrain(WIDTH/2 + 1, HEIGHT/2 + 1);
		g.Equalize();
		System.out.println("Initializing Graphics");
		DisplayGrid.setSize(WIDTH*CELL_SIZE, HEIGHT*CELL_SIZE);
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
			percentage = percentage*0.8;
			this.setBackground(Color.getHSBColor((float) percentage, 1, 1));
			//this.setBackground(new Color(colorval, colorval, colorval));
			this.setPreferredSize(new Dimension (2,2));
		 }
	 }
	 

}