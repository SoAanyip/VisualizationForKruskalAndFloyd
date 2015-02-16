package graphTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


class DrawPolygon extends JPanel{
	int count;
	int[][] relations;
	int[][] points;
	
	//public DrawPolygon(){}
	
	public DrawPolygon(int count,int[][] relations){
		this.count = count;
		this.relations = relations;
		points = new int[count][2];
	}
	
	public void paintComponent(Graphics g){
		   super.paintComponent(g);
		   int xCenter=getWidth()/2;
		   int yCenter=getHeight()/2;
		   int radius=(int)(Math.min(getWidth(),getHeight())*0.4);
		   Color c = g.getColor();
		   Polygon polygon=new Polygon();
		   polygon.addPoint(xCenter+radius, yCenter);
		   g.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		   for(int i = 0;i<count;i++){
			   points[i][0] = (int)(xCenter+radius*Math.cos((i+1)*2*Math.PI/count));
			   points[i][1] = (int)(yCenter-radius*Math.sin((i+1)*2*Math.PI/count));
			   g.setColor(Color.GREEN);
			   g.fillOval(points[i][0],  points[i][1], 10, 10);
			   polygon.addPoint( points[i][0],points[i][1]);
			   g.setColor(Color.BLUE);
			   g.drawString("" + (i+1), points[i][0], points[i][1]);
			  
			   for(int j = 0;j<count;j++){
				   g.setColor(Color.BLACK);
				   if(relations[i][j]!=0 && relations[i][j]!=Integer.MAX_VALUE){
					   g.drawLine(points[i][0], points[i][1], points[j][0], points[j][1]);
					   g.setColor(Color.orange);
					   g.drawString("" + relations[i][j], (points[i][0] + points[j][0])/2, (points[i][1] + points[j][1])/2);
				   }
			   }
		   }
		/* polygon.addPoint((int)(xCenter+radius*Math.cos(2*2*Math.PI/8)),(int)(yCenter-radius*Math.sin(2*2*Math.PI/8)));
		   polygon.addPoint((int)(xCenter+radius*Math.cos(3*2*Math.PI/8)),(int)(yCenter-radius*Math.sin(3*2*Math.PI/8)));
		   polygon.addPoint((int)(xCenter+radius*Math.cos(4*2*Math.PI/8)),(int)(yCenter-radius*Math.sin(4*2*Math.PI/8)));
		   polygon.addPoint((int)(xCenter+radius*Math.cos(5*2*Math.PI/8)),(int)(yCenter-radius*Math.sin(5*2*Math.PI/8)));
		   polygon.addPoint((int)(xCenter+radius*Math.cos(6*2*Math.PI/8)),(int)(yCenter-radius*Math.sin(6*2*Math.PI/8)));
		   polygon.addPoint((int)(xCenter+radius*Math.cos(7*2*Math.PI/8)),(int)(yCenter-radius*Math.sin(7*2*Math.PI/8)));*/
		   g.setColor(new Color(0f, 0f, 0f, 0f));
		   g.drawPolygon(polygon);
		   g.setColor(c);
	}
	
	public void launch(){
		JFrame frame = new JFrame();
		frame.setTitle("DrawPolygon");
	    frame.getContentPane().add(new DrawPolygon(count,relations));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,460);
		frame.setVisible(true);
	}
}