package code;

import java.io.File;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Player implements Entity {
	int X; 
	int Y; 
	Image image;
	ImageView imageView;
	String name = null;
	int value;
	Rectangle rectangle = new Rectangle();
	TextField text = new TextField();
	Stage primaryStage;
	TeamBuilder teamBuilder;
	Team team = null;
		
	public Player(TeamBuilder teamBuilder){
		this.teamBuilder = teamBuilder;
		image = null;
		imageView = null;
		name = "Add Player";
		value = 0;
	}	  
	
	public void moveRelative(double X, double Y) {
		rectangle.setX(rectangle.getX()+X);
		rectangle.setY(rectangle.getY()+Y);	
		if(imageView !=null){
			imageView.setX(imageView.getX()+X);
			imageView.setY(imageView.getY()+Y);
		}
		text.setLayoutX(rectangle.getX());
		text.setLayoutY(rectangle.getY()+100);
	}
	
	private void buildTextField(){
		text.setMaxSize(80,20);
		text.setLayoutX(rectangle.getX());
		text.setLayoutY(rectangle.getY()+100);
		text.setAlignment(Pos.CENTER);
	}
	public void buildRectangle(Point2D topLeft, int height, int width){
		rectangle.setHeight(height);
		rectangle.setWidth(width);
		rectangle.setX(topLeft.getX());
		rectangle.setY(topLeft.getY());
		rectangle.setFill(null);
		rectangle.setStroke(Color.BLACK);		
		rectangle.setStrokeWidth(1);
		buildTextField();	
	}
	
	public Rectangle getRectangle(){
		return rectangle;
	}
	
	public TextField getTextField(){
		return text;
	}
	
	public void loadImage(){
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setInitialDirectory(new File("C:\\soccerPlayers"));
		 fileChooser.setTitle("Open Resource File");
		 fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		 File selectedFile = fileChooser.showOpenDialog(teamBuilder.primaryStage);
		 image = new Image("File:" + selectedFile.toString(),80,100,false,false);
		 imageView = new ImageView(image);	
		 imageView.setX(rectangle.getX());
		 imageView.setY(rectangle.getY());
		 teamBuilder.setImage(imageView);
	}
	
	public void joinTeam(Team team){
		if (hasTeam())
			leaveTeam();
		this.team = team;
	}
	
	public boolean hasTeam(){
		if (team != null)
			return true;
		else
			return false;
	}
	
	public void leaveTeam(){
		team.removePlayer(this);
		team = null;
	}

	@Override
	public Entity getContainer(Point2D point) {
		if ( (point.getX() >= rectangle.getX() && point.getX() <= rectangle.getX()+rectangle.getWidth()
				&& point.getY()>= rectangle.getY() && point.getY() <= rectangle.getY()+rectangle.getHeight()))
			return this;
		else
			return null;
	}

	@Override
	public void displayInformation() {
		System.out.println("  " + text.getText());
		
	}
	
	

}
