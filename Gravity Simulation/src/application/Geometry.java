package application;

/*
 * WHY DOES THIS CLASS EXIST???
 * It exists because I'm too lazy to figure out the 
 * trigonometry required to calculate stuff in 2 dimensions.
 * It makes programming in 2d way easier.
 * Did all my math on a sticky note, and now I don't have to 
 * worry about it when programming the game.
 * Using trigonometry in java is the same as using it in math
 * except since the origin is at a different position, we have
 * to switch the y-axis in math for the x-axis in java and 
 * vice versa
 * Radians are used since the java trig functions only accept angles in radians
 */
public class Geometry 
{
	//Useful constants
	public static double FULL = 2*Math.PI, DOWN = 0, DOWN_RIGHT = FULL*(1.0/8), RIGHT = FULL*(1.0/4),
			UP_RIGHT = FULL*(3.0/8), UP = FULL*(1.0/2), UP_LEFT = FULL*(5.0/8), LEFT = FULL*(3.0/4), 
			DOWN_LEFT = FULL*(7.0/8);
	
	//Angles are basically the same, except 0 degrees is at the bottom, and we 
	//go counter clockwise like usual
	
	//Method that returns the x component, when given an angle and a magnitude
	public static double getXComponent(double angle, double magnitude)
	{
		return magnitude * Math.sin(angle);
	}
	
	//Method that returns the y component, when given an angle and a magnitude
	public static double getYComponent(double angle, double magnitude)
	{
		return magnitude * Math.cos(angle);
	}
	
	//Method that returns the angle given an x component and a y component
	public static double getAngle(double x, double y)
	{
		if (y > 0)
		{
			return Math.atan(x/y);
		}
		
		else if (y < 0)
		{
			return Math.atan(x/y) + FULL*(0.5);
		}
		
		else
		{
			if (x > 0)
			{
				return RIGHT;
			}
			else
			{
				return LEFT;
			}
		}
			
	}
	
	//Method that returns an angle from an object to an object
	public static double angleFromAtoB(double Ax, double Ay, double Bx, double By)
	{
		return getAngle(Bx-Ax, By-Ay);
	}
	
	//Method that returns the distance between two points
	public static double getDistance(double x1, double y1, double x2, double y2)
	{
		return Math.pow(getDistanceSquared(x1, y1, x2, y2), 0.5);
	}
	
	//Method that returns the distance squared between two points (Useful for physics simulations where the distance squared is often required)
	public static double getDistanceSquared(double x1, double y1, double x2, double y2)
	{
		return Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2);
	}
		
	//Method that converts an angle in radians to an angle in degrees
	public static double getDegrees(double angle)
	{
		return angle*(180/Math.PI);
	}
	
	//Return an angle that Java's methods can work with
	public static double getJavaAngle(double angle)
	{
		return -getDegrees(angle); //Most useless method ever ngl
	}
	
	//Method that tells you whether something is pointing left or right
	public static boolean isPointingLeft(double angle)
	{
		if (getXComponent(angle, 1) < 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
