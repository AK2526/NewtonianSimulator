package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Mass 
{
	//CONSTANTS
	public double density = 2;
	
	//Store colour and size
	public Color color;
	public double size;
	public double mass;
	
	//Boolean to check if we're drawing velocityVector
	boolean isDrawingVector = false;;
	
	//Store camera
	Camera camera;
	
	//Vectors
	public double x, y, xVel, yVel, xAcc, yAcc;
	
	//Constructor
	public Mass(double xPos, double yPos, Camera cam)
	{
		camera = cam;
		
		color = Color.WHITE;
		mass = 100;
		size = Math.sqrt(mass) * 10/density;
		
		x = xPos;
		y = yPos;
	}
	
	//Constructor
	public Mass(Camera cam)
	{
		camera = cam;
		
		color = Color.rgb((int)(Math.random()*155 + 100), (int)(Math.random()*155 + 100), (int)(Math.random()*155 + 100));
		mass = 100;
		updateSize();
		
		x = Math.random()*(camera.sceneWidth*camera.scale) + camera.x;
		y = Math.random()*(camera.sceneHeight*camera.scale) + camera.y;
	}
	
	//Update size
	public void updateSize()
	{
		size = Math.sqrt(mass) * 10/density;
	}
	
	//Method to update mass's vectors
	public void update()
	{
		//Update velocities and positions
		xVel += xAcc;
		yVel += yAcc;
		x += xVel;
		y += yVel;
		xAcc = 0;
		yAcc = 0;
	}
	
	//Method to draw the mass
	public void draw(GraphicsContext pen)
	{
		//Change colour
		pen.setFill(color);
		
		//Determine size
		double s = size/camera.scale;
		
		//Store centre x and y
		double centreX = camera.getX(x), centreY = camera.getY(y);
		
		//Draw it
		pen.fillOval(centreX - s/2,centreY - s/2 , s, s);
		
		//Check if we're drawing velocity vector
		if (isDrawingVector)
		{
			//Change colour
			pen.setStroke(Color.rgb( (int)((1-color.getRed())*255), (int)((1-color.getBlue())*255), (int)((1-color.getGreen())*255)) );
			
			pen.setLineWidth(3);
			//Draw line
			pen.strokeLine(centreX, centreY, centreX + xVel/camera.scale*50, centreY+yVel/camera.scale*50);
		}
		
	}
	
	//Method to set camera
	public void setCamera(Camera c)
	{
		camera = c;
	}
}
