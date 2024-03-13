package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class UI extends Stage
{
	//Different labels and stuff
	Label lblTime, lblPrecision, lblSpeed, lblPrecisionVal, lblVelocity, lblAcceleration, lblCoordinates, lblMass;
	Button incSpeed, decSpeed, incPrecision, decPrecision, btnEdit, btnView;
	Circle mass, editMass;
	TextField txtMass, txtRed, txtGreen, txtBlue;

	//Store camera
	Camera cam;

	//Boolean to check if we're in creator mode
	boolean isEditing = false;

	//Vbox to store focus object info
	VBox massVB;

	//Vbox to store creation object info
	VBox editVB;
	Label lblMassText, lblVelocityText;
	TextField txtVelocity;
	
	//Set temp color vars
	public int R, G, B;
	

	//Actually responsible for storing information
	public int time = 1, precision = 1;

	//Root
	VBox UIroot;

	//Constructor
	public UI(Camera c)
	{
		//Store camera
		cam = c;

		//Create UI stage
		UIroot = new VBox();
		Scene UIScene = new Scene(UIroot, 200, 500, Color.BLACK);
		this.setX(0);
		this.setScene(UIScene);
		this.show();
		UIroot.setStyle("-fx-background-color: black");


		UIroot.setAlignment(Pos.CENTER);

		{
			//Create Time label
			lblTime = new Label("Speed");
			lblTime.setTextFill(Color.WHITE);
			UIroot.getChildren().add(lblTime);

			lblSpeed = new Label(time + "x");
			lblSpeed.setTextFill(Color.WHITE);
			lblSpeed.setPrefWidth(100);
			lblSpeed.setAlignment(Pos.CENTER);

			incSpeed = new Button(">");

			incSpeed.setOnAction(e ->
			{

				UIroot.requestFocus();
				
				if (time == 0)
				{
					time += 1;
				}
				else
				{
					time += Math.pow(10,(int)Math.log10(time));
				}
				
				lblSpeed.setText(time + "x");
			});
			decSpeed = new Button("<");
			decSpeed.setOnAction(e ->
			{
				UIroot.requestFocus();
				if (time > 1)
				{
					time -= Math.pow(10,(int)Math.log10(time-1) );
					lblSpeed.setText(time + "x");
				}
				else if (time == 1)
				{
					time = 0;
					lblSpeed.setText( time + "x");
				}
			});

		}

		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(decSpeed, lblSpeed, incSpeed);
		UIroot.getChildren().add(hb);

		//Create Precision label
		lblPrecision = new Label("Precision");
		lblPrecision.setTextFill(Color.WHITE);
		UIroot.getChildren().add(lblPrecision);

		lblPrecisionVal = new Label(precision + "");
		lblPrecisionVal.setTextFill(Color.WHITE);
		lblPrecisionVal.setPrefWidth(100);
		lblPrecisionVal.setAlignment(Pos.CENTER);

		incPrecision = new Button(">");

		incPrecision.setOnAction(e ->
		{
			UIroot.requestFocus();
			precision ++;
			lblPrecisionVal.setText(precision + "");
		});
		decPrecision = new Button("<");
		decPrecision.setOnAction(e ->
		{
			UIroot.requestFocus();
			if (precision > 1)
			{
				precision --;
				lblPrecisionVal.setText(precision + "");

			}
		});

		//New hbox to store edit and create
		HBox hb1 = new HBox();
		hb1.setAlignment(Pos.CENTER);
		hb1.setSpacing(10);
		VBox.setMargin(hb1, new Insets(20, 0, 0, 0));

		//Create and edit buttons
		btnView = new Button("View");
		btnView.setOnAction(e -> 
		{
			removeMassInfo();
			isEditing = false;
			UIroot.requestFocus();
			btnEdit.setDisable(false);
			btnView.setDisable(true);

			addMassInfo();

		});

		btnEdit = new Button("Edit");
		btnEdit.setOnAction(e -> 
		{
			removeMassInfo();
			isEditing = true;
			UIroot.requestFocus();
			btnEdit.setDisable(true);
			btnView.setDisable(false);

			addMassInfo();
		});

		hb1.getChildren().addAll(btnView, btnEdit);

		HBox hb2 = new HBox();
		hb2.setAlignment(Pos.CENTER);
		hb2.getChildren().addAll(decPrecision, lblPrecisionVal, incPrecision);
		UIroot.getChildren().addAll(hb2, hb1);

		//Create vb for masses
		massVB = new VBox();

		//Create the circle to show planet
		mass = new Circle(0, 0, 45);
		mass.setFill(Color.WHITE);

		//Create labels to show information
		lblVelocity = new Label("Velocity: ");
		lblVelocity.setTextFill(Color.WHITE);
		lblAcceleration = new Label("Acceleration: ");
		lblAcceleration.setTextFill(Color.WHITE);
		lblCoordinates = new Label("( , )");
		lblCoordinates.setTextFill(Color.WHITE);
		lblMass = new Label("Mass: ");
		lblMass.setTextFill(Color.WHITE);

		massVB.getChildren().addAll(mass, lblMass, lblVelocity, lblAcceleration, lblCoordinates);
		massVB.setAlignment(Pos.CENTER);
		massVB.setSpacing(10);

		VBox.setMargin(massVB, new Insets(20, 10, 10, 10));
		
		
		//Create the VBOX for editing
		editVB = new VBox();
		editVB.setAlignment(Pos.CENTER);
		
		//Create the circle
		editMass = new Circle(0, 0, 45);
		updateColor();
		
		//Mass label
		lblMassText = new Label("Mass: ");
		lblMassText.setTextFill(Color.WHITE);
		
		//Mass textfield
		txtMass = new TextField();
		txtMass.setPromptText("Enter mass: ");
		txtMass.setPrefWidth(100);
		
		//Mass hbox
		HBox hbMass = new HBox();
		hbMass.setSpacing(10);
		hbMass.getChildren().addAll(lblMassText, txtMass);
		hbMass.setAlignment(Pos.CENTER);
		
		//Create RGB textfields
		txtRed = new TextField();
		txtRed.setPromptText("R:");
		txtRed.setPrefWidth(45);
		txtRed.setOnAction(e -> {updateColor(); UIroot.requestFocus();});
		
		txtGreen = new TextField();
		txtGreen.setPromptText("G:");
		txtGreen.setPrefWidth(45);
		txtGreen.setOnAction(e -> {updateColor(); UIroot.requestFocus();});
		
		txtBlue = new TextField();
		txtBlue.setPromptText("B:");
		txtBlue.setPrefWidth(50);
		txtBlue.setOnAction(e -> {updateColor(); UIroot.requestFocus();});
		
		//RGB Hbox
		HBox hbRGB = new HBox();
		hbRGB.setAlignment(Pos.CENTER);
		hbRGB.setSpacing(10);
		hbRGB.getChildren().addAll(txtRed, txtGreen, txtBlue);
		
		//Label and textfield for velocity
		lblVelocityText = new Label("Velocity:");
		lblVelocityText.setTextFill(Color.WHITE);
		txtVelocity = new TextField();
		txtVelocity.setPromptText("Enter velocity");
		
		
		//Velcoity HBox 
		HBox hbVel = new HBox();
		
		editVB.getChildren().addAll(editMass, hbMass, hbRGB, hbVel);
		editVB.setSpacing(10);
		VBox.setMargin(editVB, new Insets(20, 0, 0, 0));
		
		addMassInfo();

	}

	//Method to add mass vbox
	public void addMassInfo()
	{

		if (!isEditing && !UIroot.getChildren().contains(massVB))
		{
			UIroot.getChildren().add(massVB);
		}
		else if (isEditing && !UIroot.getChildren().contains(editVB))
		{
			UIroot.getChildren().add(editVB);
		}

	}


	//Method to remove mass vbox
	public void removeMassInfo()
	{
		if (!isEditing && UIroot.getChildren().contains(massVB))
		{
			UIroot.getChildren().remove(massVB);
		}
		if (isEditing && UIroot.getChildren().contains(editVB))
		{
			UIroot.getChildren().remove(editVB);
		}

	}
	//Method to update UI info
	public void updateUI()
	{
		//Check if in viewing mode
		if (!isEditing)
		{
			//Check if camera has focused object stored
			if (cam.focusObject != null)
			{
				//Set color
				mass.setFill(cam.focusObject.color);

				//Set velocity and acceleration
				lblVelocity.setText("Velocity: " + (int)Geometry.getDistance(0, 0, cam.focusObject.xVel, cam.focusObject.yVel));
				lblAcceleration.setText("Acceleration: " + (int)Geometry.getDistance(0, 0, cam.focusObject.xAcc, cam.focusObject.yAcc));

				//Set coordinates
				lblCoordinates.setText("(" + (int)cam.focusObject.x + ", " + (int)cam.focusObject.y + ")");

				//Set mass
				lblMass.setText("Mass: " + (int)cam.focusObject.mass);
			}
		}

	}
	
	//Update edit mass
	public void setEditMass(Mass m)
	{
		txtRed.setText((int)(m.color.getRed()*256) + "");
		txtGreen.setText((int)(m.color.getGreen()*256) + "");
		txtBlue.setText((int)(m.color.getBlue()*256) + "");
		
		editMass.setFill(Color.rgb((int)(m.color.getRed()*255), (int)(m.color.getGreen()*255), (int)(m.color.getBlue()*255)));
		
		txtMass.setText(m.mass + "");
		
	}
	
	//Method to update colour
	public void updateColor()
	{
		try
		{
			R = (int)(Double.parseDouble(txtRed.getText())) % 256;
		}
		catch(Exception e)
		{
			R = (int)(Math.random()*256);
		}
		try
		{
			G = (int)(Double.parseDouble(txtGreen.getText())) % 256;
		}
		catch(Exception e)
		{
			G = (int)(Math.random()*256);
		}
		
		try
		{
			B = (int)(Double.parseDouble(txtBlue.getText())) % 256;
		}
		catch(Exception e)
		{
			B = (int)(Math.random()*256);
		}
		
		editMass.setFill(Color.rgb(R, G, B));
	}
	
	//Create a new mass
	public Mass newMass(double x, double y)
	{
		Mass temp = new Mass(cam);
		
		//Try to set mass
		try
		{
			temp.mass = Double.parseDouble(txtMass.getText());
			temp.updateSize();
		}
		catch(Exception e)
		{
			
		}
		
		//Update color
		updateColor();
		
		//Set temporary mass's colour
		temp.color = Color.rgb(R, G, B);
		
		temp.x = x;
		temp.y = y;
		
		//Set as focus object
		cam.moveCamera(0, 0);
		cam.focusObject = temp;
		
		return temp;
	}
}
