package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void handlePlay() {
        welcomeLabel.setText("Vous avez cliqué sur Play !");
    }

    @FXML
    private void handleOptions() {
        welcomeLabel.setText("Vous avez cliqué sur Options !");
    }
}
