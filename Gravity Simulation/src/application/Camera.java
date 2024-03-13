package application;

public class Camera 
{
	//Boolean to check mode
	private boolean isFollowingObject = false;
	
	//Boolean to check if object is centered
	private boolean isCentered = true;
	
	//Boolean to check if we're transitioning
	private boolean isTransitioning = false;
	
	//Double to store x and y coords to focus on (left corner, not center)
	private double focusX, focusY;
	
	//Store focus object
	public Mass focusObject;
	
	//Variables to store x, y and scale
	public double x, y, scale; //Scale: units/pixel
	
	//Variables to store scene width and length
	public final double sceneWidth, sceneHeight;
	
	//Variables to store endpoints
	public double minX, maxX, minY, maxY;
	
	//Constructor
	public Camera(double sw, double sh)
	{
		//Set dimensions
		sceneWidth = sw;
		sceneHeight = sh;
	}
	
	//Method to update camera x and y
	public void updateCamera()
	{
		//Check if not transitioning
		if (!isTransitioning)
		{
			//Check if following object
			if (isFollowingObject)
			{
				//If centered, set camera coords to center minus distance
				if (isCentered)
				{
					x = focusObject.x - scale*sceneWidth/2;
					y = focusObject.y - scale*sceneHeight/2;
				}
				
			}
			
			//If following coords, do that
			else
			{
				x = focusX;
				y = focusY;
				
			}
			
		}
	}
	
	//Method to return virtual x given x
	public double getX(double objX)
	{
		return (objX - x)/scale;
	}
	
	//Method to return virtual y given y
	public double getY(double objY)
	{
		return (objY - y)/scale;
	}
	
	//Method to move camera
	public void moveCamera(double moveX, double moveY)
	{
		//If was following object, set that to false
		if (isFollowingObject)
		{
			isFollowingObject = false;
			
			//Reset focus x and y
			focusX = focusObject.x - sceneWidth * scale/2;
			focusY = focusObject.y - sceneHeight * scale/2;
		}
		focusX += moveX*scale;
		focusY += moveY *scale;
		
	}
	
	//Method to initialize dimensions
	public void setPlayground(double minimumX, double minimumY, double maximumX, double maximumY)
	{
		minX = minimumX;
		minY = minimumY;
		maxX = maximumX;
		maxY = maximumY;
		
		//Set scale
		scale = Math.max((maximumX-minimumX)/sceneWidth, (maximumY-minimumY)/sceneHeight);
		
		//Update maximums as required since we want to maintain scale
		maxX = minX + sceneWidth*scale;
		maxY = minY + sceneHeight*scale;
		x = minX;
		y = minY;
	}
	
	//Method to zoom in/out
	public void zoom(double newScale)
	{
		/*
		if (newScale < scale)
		{
			double halfX = sceneWidth * scale/2;
			double halfY = sceneHeight*scale/2;
			
			scale = newScale;
			
			halfX -= sceneWidth * scale/2;
			halfY -= sceneHeight*scale/2;
			
			minX += halfX;
			maxX = minX + scale*sceneWidth;
			maxY -= halfY;
			minY = maxY - scale*sceneHeight;
		}
		else
		{
			double halfX = -sceneWidth * scale/2;
			double halfY = -sceneHeight*scale/2;
			
			scale = newScale;
			
			halfX += sceneWidth * scale/2;
			halfY += sceneHeight*scale/2;
			
			minX += halfX;
			maxX = minX + scale*sceneWidth;
			maxY -= halfY;
			minY = maxY - scale*sceneHeight;
		}*/
		
		//If following object, set x and y values depending on it
		if (isFollowingObject)
		{
			x = focusObject.x - sceneWidth/2*newScale;
			y = focusObject.y - sceneHeight/2*newScale;
		}
		else
		{
			focusX = (focusX + sceneWidth/2*scale) - sceneWidth/2*newScale;
			focusY = (focusY + sceneHeight/2*scale) - sceneHeight/2*newScale;
		}
		
		//Update scale
		scale = newScale;
		
		//Update camera
		updateCamera();
	}
	
	//Method to set target object
	public void setTargetObject(Mass m)
	{
		isFollowingObject = true;
		focusObject = m;
	}
}
