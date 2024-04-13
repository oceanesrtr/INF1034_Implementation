package ca.delicivite;

/*Classe principale : lance l'application Delicivite*/


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationPrincipale extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationPrincipale.class.getResource("VueConnexionTailleMoyenne.fxml"));
        Scene sceneConnexion = new Scene(fxmlLoader.load());
        stage.setTitle("Connexion");
        stage.getIcons().add(new Image("/images/logo_fond_grise.png"));
        stage.setScene(sceneConnexion);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}