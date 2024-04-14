package ca.delicivite.proprietaire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerMenu implements Initializable{

    // StacPane des différentes interfaces de l'interface menu (apercu, menu et modifier)
    @FXML
    public StackPane contentArea;

    @FXML
    public Button buttonApercu;
    @FXML
    public Button buttonMenu;
    @FXML
    public Button buttonModifier;

    // Indicateur d'initialisation
    private boolean initialized = false;

    /*=========================================================================
    [1] Initialize au démarrage de la scène
    * ========================================================================*/
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        if (!initialized) {
            try {
                // Chargement de la vue par défaut
                Parent fxml = FXMLLoader.load(getClass().getResource("menu-apercu.fxml"));
                contentArea.getChildren().removeAll();
                contentArea.getChildren().setAll(fxml);
                initialized = true;
                buttonApercu.getStyleClass().add("clicked-button");
            } catch (IOException ex) {
                Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*=========================================================================
    [2] Méthode pour afficher l'interface apercu
    * ========================================================================*/
    public void apercu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("menu-apercu.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        boutonFonce(buttonApercu);
    }

    /*=========================================================================
    [3] Méthode pour afficher l'interface menu
    * ========================================================================*/
    public void menu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("menu-menu.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        boutonFonce(buttonMenu);
    }

    /*=========================================================================
    [4] Méthode pour afficher l'interface modifier
    * ========================================================================*/
    public void modifier(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("menu-Modifier.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        boutonFonce(buttonModifier);
    }

    /*=========================================================================
    [5] Méthode pour mettre le bouton foncé (Celui de l'interface affichée)
    * ========================================================================*/
    @FXML
    private void boutonFonce(Button button) {
        resetBouton();
        button.getStyleClass().add("clicked-button");

    }

    /*=========================================================================
    [6] Méthode pour mettre les boutons non foncé
    * ========================================================================*/
    @FXML
    private void resetBouton() {
        buttonApercu.getStyleClass().remove("clicked-button");
        buttonMenu.getStyleClass().remove("clicked-button");
        buttonModifier.getStyleClass().remove("clicked-button");
    }


}
