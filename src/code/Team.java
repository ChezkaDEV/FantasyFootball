package code;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Team implements Entity {
	Rectangle rectangle = new Rectangle();
	TextField teamName;
	Label text = new Label();
	
	TeamBuilder tb;
	List<Player> players = new ArrayList<Player>();
	int teamCount = 0;
	
	public Team(TeamBuilder tb, int TeamNo){
		this.tb = tb;
		this.teamName = new TextField();
		this.teamName.setFont(Font.font ("Verdana", FontWeight.BOLD, 16));
		this.teamName.setLayoutX((TeamNo*320)+120);
		this.teamName.setLayoutY(70);
		this.teamName.setMaxSize(140,20);
		text.setText(" " + teamCount);
		text.setFont(Font.font ("Verdana", 16));
		text.setLayoutX((TeamNo*320) + 268);
		text.setLayoutY(75);
		rectangle.setX((TeamNo*320) +120);
		rectangle.setY(70);
		rectangle.setWidth(300);
		rectangle.setHeight(500);
		rectangle.setFill(Color.CORNSILK);
		rectangle.setStroke(Color.BLACK);		
		rectangle.setStrokeWidth(1);
	}
	
	public Rectangle getTeamContainer(){
		return rectangle;
	}
	
	public Label getText(){
		return text;
	}
	
	public TextField getTeamName(){
		return teamName;
	}
	
	public void addPlayer(Player player){
		player.joinTeam(this);  // Assign team name to player
		players.add(player);  // Add player to current team
		teamCount++;
		text.setText(" " +teamCount);
		
	}
	
	public void removePlayer(Player player){
		if(players.contains(player)){
			players.remove(player);
			teamCount--;
			text.setText(" " +teamCount);
		}
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
		System.out.println(teamName.getText());
		for(Player player: players){
			player.displayInformation();
		}
	}
}
