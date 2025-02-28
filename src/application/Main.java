package application;

<<<<<<< HEAD
=======
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

>>>>>>> origin/interface
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
        // System.out.println(getClass().getResource("/views/Home.fxml"));
        
        // Charger la racine en tant que VBox, car c'est ce qui est défini dans le fichier FXML
        VBox root = loader.load();
        System.out.println("Controller: " + loader.getController());
        
        // Créer une scène avec le VBox comme racine
        Scene scene = new Scene(root, 600, 600);
        
        // Définir la scène sur le stage et afficher
        primaryStage.setScene(scene);
        primaryStage.setTitle("Exemple avec VBox");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
