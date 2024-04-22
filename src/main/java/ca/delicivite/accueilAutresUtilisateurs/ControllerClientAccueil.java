package ca.delicivite.accueilAutresUtilisateurs;

/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe Controller : Page d'accueil des autres utilisateurs */

import ca.delicivite.outils.ClasseUtilitaire;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static ca.delicivite.outils.ClasseUtilitaire.changerScene;

public class ControllerClientAccueil implements Initializable {

    public BorderPane root;
    public MenuBar barreMenu;
    public Menu titreMenuApplication;
    public MenuItem stAnnulerAction;
    public MenuItem stRefaireAction;
    public Button filMDPOublie;
    public Button filConnexion;
    public MenuItem stQuitterApp;
    @FXML
    private MenuItem stAPropos;

    /*=============================================================
     *Méthodes pour les fonctionnalités du sous-menu Informations
     * [1] Afficher la fenêtre d'information A propos
     *============================================================*/
    @FXML
    private void afficherPopupAPropos() {
        // Récupérer la fenêtre principale
        Stage stage = (Stage) stAPropos.getParentPopup().getOwnerWindow();

        Stage stagePopup = new Stage();
        stagePopup.getIcons().add(new Image("/images/logo_fond_grise.png"));
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(10);
        content.getChildren().addAll(
                new Label("DELICIVITE"),
                new Label("Version: 1.0"),
                new Label("Pour Windows 64 bit"),
                new Label("Copyright © INF1034")
        );
        Scene scene = new Scene(content, 300, 200);

        // Effet de fond flou
        BoxBlur blurEffect = new BoxBlur();
        blurEffect.setWidth(5);
        blurEffect.setHeight(5);
        stage.getScene().getRoot().setEffect(blurEffect);

        stagePopup.setScene(scene);
        stagePopup.initOwner(stage);
        stagePopup.initModality(Modality.WINDOW_MODAL);
        stagePopup.setTitle("À propos");
        stagePopup.showAndWait();

        stage.getScene().getRoot().setEffect(null);
    }

    /*=======================================================
     * [2] : Redirige vers le site JAVAFX
     * =======================================================*/
    @FXML
    private void ouvrirGuideUtilisation(ActionEvent event) {
        String url = "https://docs.oracle.com/javase/8/javase-clienttechnologies.htm";
        ClasseUtilitaire.redirectionSiteInternet(url);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //[1] : Redirection vers page adéquate
        filConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));
        filMDPOublie.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/accueilAutresUtilisateurs/VueClientConnecteAccueil.fxml", "Connexion", null));
        stQuitterApp.setOnAction(event -> Platform.exit());

        // [2] Raccourci mmémonique 2 : Ctrl Shift Q pour quitter l'application
barreMenu.sceneProperty().addListener((observable, oldScene, newScene) -> {
    if (newScene != null) {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.ALT_DOWN);
        Runnable runnable = Platform::exit;
        newScene.getAccelerators().put(keyCombination, runnable);
    }
});

    }
}
