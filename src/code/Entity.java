package code;

import javafx.geometry.Point2D;

public interface Entity {
	public Entity getContainer(Point2D point);
	public void displayInformation();
}
