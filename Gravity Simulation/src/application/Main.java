package application;

import java.util.ArrayList;
import java.util.Collections;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Main extends Application 
{
	//CONSTANT
	public double G = 1;

	//Store camera
	public Camera camera;

	//Store pen
	GraphicsContext pen;

	//AnimationTimer
	public AnimationTimer loop;

	//Objects
	public Scene scene;
	public Mass m1, m2;
	ArrayList<Mass> masses;

	//UI
	UI UIStage;

	//Time
	public int time = 1;
	public int precision = 1;

	//Booleans to keep track of inputs
	public boolean up, down, left, right, in, out;

	public void start(Stage primaryStage) 
	{
		try {
			Pane root = new Pane();
			scene = new Scene(root,1080,720, Color.BLACK);
			primaryStage.setScene(scene);
			primaryStage.show();

			//Create camera
			camera = new Camera(scene.getWidth(), scene.getHeight());
			camera.setPlayground(0, 0, 10000 , 400);
			System.out.println(camera.x + " " + camera.y);

			//Create pen
			Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
			pen = canvas.getGraphicsContext2D();
			root.getChildren().add(canvas);
			root.setStyle("-fx-background-color: black");

			//Create masses
			masses = new ArrayList<Mass>();
			for (int i = 0; i < 1000; i ++)
			{
				masses.add(new Mass(camera));
//				masses.get(i).xVel = (Math.random()-0.2)*10;
//				masses.get(i).yVel = (Math.random()-0.2)*10;
				if (i == 1)
				{
					masses.get(i).x += 10000;
					masses.get(i).xVel = 0;
					masses.get(i).yVel = 0;
					masses.get(i).mass = 1000000;
					masses.get(i).updateSize();
				}
				if (i == 2)
				{
					masses.get(i).x = 100000;
					masses.get(i).xVel = 0;
					masses.get(i).yVel = 0.953;
					masses.get(i).mass = 100000;
					masses.get(i).updateSize();
				}
				if (i == 3)
				{
					masses.get(i).x -= 10000;
					masses.get(i).xVel = 0;
					masses.get(i).yVel = -7.07;
					masses.get(i).mass = 1000000;
					masses.get(i).updateSize();
				}
				if (i > 5)
				{
					masses.get(i).y += 100000;
					masses.get(i).xVel = 0;
					masses.get(i).yVel = -2;
				}
								if (i%2 == 0)
								{
									masses.get(i).x += 10000;
									masses.get(i).xVel -= 40;
								}



			}

			//Create UI
			UIStage = new UI(camera);
			UIStage.setAlwaysOnTop(true);

			//Close UI when main closed
			primaryStage.setOnCloseRequest(e ->
			{
				UIStage.close();
			});

			UIStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>()
			{
				public void handle(KeyEvent e)
				{
					if (e.getCode() == KeyCode.UP)
					{
						up = true;
					}
					else if (e.getCode() == KeyCode.DOWN)
					{
						down = true;
					}
					else if (e.getCode() == KeyCode.RIGHT)
					{
						right = true;
					}
					else if (e.getCode() == KeyCode.LEFT)
					{
						left = true;
					}
					else if (e.getCode() == KeyCode.EQUALS)
					{
						in = true;
					}
					else if (e.getCode() == KeyCode.MINUS)
					{
						out = true;
					}
					else if (e.getCode() == KeyCode.SPACE)
					{
						time = (time + 1)%50;
						System.out.println(time + "x");
					}
				}
			});

			//Get keyboard input
			scene.setOnKeyPressed(new EventHandler<KeyEvent>()
			{
				public void handle(KeyEvent e)
				{
					if (e.getCode() == KeyCode.UP)
					{
						up = true;
					}
					else if (e.getCode() == KeyCode.DOWN)
					{
						down = true;
					}
					else if (e.getCode() == KeyCode.RIGHT)
					{
						right = true;
					}
					else if (e.getCode() == KeyCode.LEFT)
					{
						left = true;
					}
					else if (e.getCode() == KeyCode.EQUALS)
					{
						in = true;
					}
					else if (e.getCode() == KeyCode.MINUS)
					{
						out = true;
					}
					else if (e.getCode() == KeyCode.SPACE)
					{
						time = (time + 1)%50;
						System.out.println(time + "x");
					}
				}
			});

			UIStage.getScene().setOnKeyReleased(new EventHandler<KeyEvent>()
			{
				public void handle(KeyEvent e)
				{
					if (e.getCode() == KeyCode.UP)
					{
						up = false;
					}
					else if (e.getCode() == KeyCode.DOWN)
					{
						down = false;
					}
					else if (e.getCode() == KeyCode.RIGHT)
					{
						right = false;
					}
					else if (e.getCode() == KeyCode.LEFT)
					{
						left = false;
					}
					else if (e.getCode() == KeyCode.EQUALS)
					{
						in = false;
					}
					else if (e.getCode() == KeyCode.MINUS)
					{
						out = false;
					}
				}
			});

			scene.setOnKeyReleased(new EventHandler<KeyEvent>()
			{
				public void handle(KeyEvent e)
				{
					if (e.getCode() == KeyCode.UP)
					{
						up = false;
					}
					else if (e.getCode() == KeyCode.DOWN)
					{
						down = false;
					}
					else if (e.getCode() == KeyCode.RIGHT)
					{
						right = false;
					}
					else if (e.getCode() == KeyCode.LEFT)
					{
						left = false;
					}
					else if (e.getCode() == KeyCode.EQUALS)
					{
						in = false;
					}
					else if (e.getCode() == KeyCode.MINUS)
					{
						out = false;
					}
				}
			});

			//Create Animation Timer
			loop = new AnimationTimer()
			{
				public void handle(long val)
				{
					//If buttons pressed, move
					if (up || down || left || right || in || out)
					{
						if (right)
						{
							camera.moveCamera(10, 0);
						}
						else if (left)
						{
							camera.moveCamera(-10, 0);
						}
						if (up)
						{
							camera.moveCamera(0, -10);
						}
						else if (down)
						{
							camera.moveCamera(0, 10);
						}

						if (in)
						{
							camera.zoom(camera.scale/1.05);
						}
						else if (out)
						{
							camera.zoom(camera.scale*1.05);
						}


					}
					
					
					//Update UI
					UIStage.updateUI();

					//Run loop
					updateStatus();

					//Update camera
					camera.updateCamera();


					//Redraw
					redraw();
				}

			};

			//Check if mouse pressed
			scene.setOnMousePressed(new EventHandler<MouseEvent>()
			{
				public void handle(MouseEvent e)
				{
					//Temporarily store x and y
					double mouseX = e.getX()*camera.scale + camera.x;
					double mouseY = e.getY()*camera.scale + camera.y;
					
					//Bring the UI to front
					UIStage.UIroot.requestFocus();
					
					//Arraylist to store all distances from 
					ArrayList<Double> distancesFromMouse = new ArrayList<Double>();
					
					
					//Check if in viewing mode
					if (!UIStage.isEditing)
					{
						//Go through all the masses
						for (int i = 0; i < masses.size(); i ++)
						{
							//Calculate distance
							double dist = Geometry.getDistance(masses.get(i).x, masses.get(i).y, mouseX, mouseY);
							
							distancesFromMouse.add(dist);
							
							//If distance is less than size of circle, then break loop, and set focus object as the mass
							if (dist <= masses.get(i).size/2)
							{
								camera.setTargetObject(masses.get(i));
								return;
							}
						}
						
						//If we haven't clicked anywhere, set object to closest object
						camera.setTargetObject(masses.get(distancesFromMouse.indexOf(Collections.min(distancesFromMouse))));
					}
					
					//Else if in editing mode
					else
					{
						//Go through all the masses
						for (int i = 0; i < masses.size(); i ++)
						{
							//Calculate distance
							double dist = Geometry.getDistance(masses.get(i).x, masses.get(i).y, mouseX, mouseY);
							
							//If distance is less than size of circle, then break loop, and set focus object as the mass
							if (dist <= masses.get(i).size/2)
							{
								camera.setTargetObject(masses.get(i));
								UIStage.setEditMass(masses.get(i));
								return;
							}
						}
						
						//If we haven't selected object, then create new mass
						masses.add(UIStage.newMass(mouseX, mouseY));
					}
					
				}
			});

			//Request UI focus
			UIStage.UIroot.requestFocus();

			loop.start();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	//Method to redraw screen
	public void redraw()
	{
		pen.clearRect(0, 0, scene.getWidth(), scene.getHeight());

		//Draw all the masses
		for (int i = 0; i < masses.size(); i ++)
		{
			masses.get(i).draw(pen);
		}
	}

	//Method to add to the accelerations of all the boys
	public void handleGravity(Mass m1, Mass m2)
	{
		//Find gravitational force between them
		double force = (G*m1.mass*m2.mass)/Geometry.getDistanceSquared(m1.x, m1.y, m2.x, m2.y);

		//Find angle from m1 to m2
		double angle = Geometry.angleFromAtoB(m1.x, m1.y, m2.x, m2.y);

		//Add to accelerations
		m1.xAcc += Geometry.getXComponent(angle, force/m1.mass);
		m1.yAcc += Geometry.getYComponent(angle, force/m1.mass);

		m2.xAcc -= Geometry.getXComponent(angle, force/m2.mass);
		m2.yAcc -= Geometry.getYComponent(angle, force/m2.mass);
	}

	//Method to check for collision
	public boolean isColliding(Mass m1, Mass m2)
	{
		//Distance
		double distance = Geometry.getDistance(m1.x, m1.y, m2.x, m2.y);

		//If distance less than sizes, return true
		if (distance < (m1.size/2 + m2.size/2)*0.9)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//Do loop tiem number of times
	public void updateStatus()
	{
		for (int t = 0; t < UIStage.time; t ++)
		{
			//Reset accelerations and update masses;
			for (int i = 0; i < masses.size(); i ++)
			{
				masses.get(i).update();
			}

			if (t%UIStage.precision == 0)
			{
				//Update gravities for all
				for (int i = 0; i < masses.size(); i ++)
				{
					for (int j = i+1; j < masses.size(); j ++)
					{
						//Check if colliding
						if (isColliding(masses.get(i), masses.get(j)))
						{
							//Replace lower index with new mass object and set its properties
							Mass temp = new Mass(camera);
							temp.mass = masses.get(i).mass + masses.get(j).mass;
							temp.updateSize();
							temp.x = weightedAverage(masses.get(i), masses.get(j), masses.get(i).x, masses.get(j).x);
							temp.y = weightedAverage(masses.get(i), masses.get(j), masses.get(i).y, masses.get(j).y);
							temp.color = Color.rgb((int)(255*weightedAverage(masses.get(i), masses.get(j), masses.get(i).color.getRed(), masses.get(j).color.getRed())),(int)(255*weightedAverage(masses.get(i), masses.get(j), masses.get(i).color.getBlue(), masses.get(j).color.getBlue())),(int)(255*weightedAverage(masses.get(i), masses.get(j), masses.get(i).color.getGreen(), masses.get(j).color.getGreen())) );

							//Set velocities using momentum
							temp.xVel = (masses.get(i).mass*masses.get(i).xVel + masses.get(j).mass*masses.get(j).xVel)/temp.mass;
							temp.yVel = (masses.get(i).mass*masses.get(i).yVel + masses.get(j).mass*masses.get(j).yVel)/temp.mass;

							//Check if camera focus object is either, and if so, replace camera focus object
							if (camera.focusObject == masses.get(i) || camera.focusObject == masses.get(j))
							{
								camera.focusObject = temp;
							}

							masses.set(i, temp);
							masses.remove(j);

							//Set i high and break
							j = masses.size();
							//i = masses.size()+1;
							//break;
						}
						else
						{
							//Calculate gravity for masses
							handleGravity(masses.get(i), masses.get(j));
						}

					}

				}

			}

		}

	}

	//Method to return weighted average
	public double weightedAverage(Mass m1, Mass m2, double val1, double val2)
	{
		//Find weight of m1
		double weight1 = m1.mass/(m1.mass+m2.mass);

		return (weight1*val1) + ((1-weight1)*val2);
	}

	//Method to add to the UIScene
	public void addUI(VBox vb)
	{

	}
}
