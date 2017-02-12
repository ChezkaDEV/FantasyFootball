package code;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TeamBuilder extends Application {
	final int maxPlayers = 20;
	Stage primaryStage;
	Scene scene;
	AnchorPane pane;
	Player[] players = new Player[maxPlayers];	
	Team[] teams = new Team[3];
	Point2D previousPoint = null;
	int counter = 0;
	Button printBtn = new Button("Print");
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		pane = new AnchorPane();
		scene = new Scene(pane,1400,800);
		this.primaryStage = primaryStage;
		
		primaryStage.setTitle("Fantasy Football - Team Setup");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		scene.setOnMouseClicked(mouseHandler);
		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMouseReleased(mouseHandler);
		
		createTeamContainers();
		createPlayers();
		
		printBtn.setLayoutX(440);
		printBtn.setLayoutY(580);
		pane.getChildren().add(printBtn);
		printBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for(Team team: teams){
					team.displayInformation();
				}
			}   	
        });  
		
	}
	
	public void setImage(ImageView imgView){
		pane.getChildren().add(imgView);
	}
	
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {	 
        @Override
        public void handle(MouseEvent mouseEvent) {      	
        	Point2D clickPoint = new Point2D(mouseEvent.getX(),mouseEvent.getY());  
        	String eventName = mouseEvent.getEventType().getName(); 
        	Player currentPlayer = null;
        	for(Player player: players)
        	{	
        		if(player.getContainer(clickPoint) != null){
        			currentPlayer = player;
        			break;
        		}  			
        	}
        	
        	switch(eventName){
        	case "MOUSE_RELEASED":
        		Entity currentTeam = null;
        		for(Team team:teams){
        			currentTeam = team.getContainer(clickPoint);
        			if(currentTeam!=null && currentPlayer != null){
        				team.addPlayer(currentPlayer);
        				break;
        			}
        		}		

        		currentPlayer = null;
        	break;
        	case "MOUSE_CLICKED":
        		if (mouseEvent.getClickCount() == 2 && currentPlayer !=null)
        			currentPlayer.loadImage();
        		previousPoint = null;
        	break;
        	case "MOUSE_DRAGGED":
        		//System.out.println("Dragging");
        		if(previousPoint == null)
        			previousPoint = clickPoint;
        		if(currentPlayer!=null){
        			currentPlayer.moveRelative(clickPoint.getX()-previousPoint.getX(), clickPoint.getY()-previousPoint.getY());     				
        		}	
        		previousPoint = clickPoint;
        	break;
        	}
        	
        }               
     };
	
    public void createTeamContainers(){ 
       teams[0] = new Team(this, 1);
       teams[1] = new Team(this,2);
       teams[2] = new Team(this,3);
       for(int j = 0; j < 3; j++){
    	   pane.getChildren().add(teams[j].getTeamContainer());
    	   pane.getChildren().add(teams[j].getText());
    	   pane.getChildren().add(teams[j].getTeamName());
       }
    }
     
	public void createPlayers(){	
		Label playerLabel = new Label("Add Players Here");
		playerLabel.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
		playerLabel.setLayoutX(10);
		playerLabel.setLayoutY(15);
		pane.getChildren().add(playerLabel);
		
		int bX = 10, bY =70;
		int rowCounter = 0;
		for(int j = 0; j < maxPlayers; j++){
			if (j > 0){
				if(rowCounter++ < 4){
					bY = bY+140;
				} else {
					bY = 70;
					bX = bX + 100;
					rowCounter = 0;
				}
			}
				
			players[j] = new Player(this);
			players[j].buildRectangle(new Point2D(bX,bY),100,80);
			
			pane.getChildren().add(players[j].getRectangle());
			pane.getChildren().add(players[j].getTextField());
			
		}
	}
	
	
	
}
