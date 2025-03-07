module ChomeursQuest {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	requires org.json;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.controllers to javafx.fxml;
}