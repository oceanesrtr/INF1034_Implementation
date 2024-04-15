package ca.delicivite.motDePasseOublieConfirmation;

/*Classe Controller : gère les controlleurs de l'interface d'envoi d'e-mail
 * de récupération de mot de passe*/


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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRecuperationMDP implements Initializable {

    @FXML
    public BorderPane root;
    @FXML
    public MenuBar barreMenu;
    @FXML
    public Menu titreMenuApplication;
    @FXML
    public MenuItem stRefaireAction;
    @FXML
    public Menu titreMenuApparence;
    @FXML
    public MenuItem stQuitterApp;
    @FXML
    public MenuItem modeClairMenuItem;
    @FXML
    public Menu titreMenuVue;
    @FXML
    public Menu taillePoliceMenu;
    @FXML
    public MenuItem petiteTailleMenuItem;
    @FXML
    public MenuItem stAnnulerAction;
    @FXML
    public MenuItem moyenneTailleMenuItem;
    @FXML
    public MenuItem grandeTailleMenuItem;
    @FXML
    public Label descriptionMDPOublie;
    @FXML
    public Menu menuAide;
    @FXML
    public MenuItem stGuideUtilisation;
    @FXML
    public MenuItem modeSombreMenuItem;
    @FXML
    public Pane filArianeBarreEtat;
    @FXML
    public Rectangle barreEtat;
    @FXML
    public Text copyrightMention;
    @FXML
    public MenuItem stAPropos;
    @FXML
    public Button filConnexion;
    @FXML
    public Button filMDPOublie;
    @FXML
    public Button boutonRetourEntreeMDPOublie;
    @FXML
    public Label titreMDPOUblie;
    @FXML
    public Label descriptionMDPOublie1;
    @FXML
    public Button boutonRetourConnexion;

    /*=========================================================================
    [1] Initialize au démarrage de la scène
    * ========================================================================*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //[a] Redirection vers les pages adéquates selon les boutons cliqués
        boutonRetourConnexion.setOnAction(actionEvent -> ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));
        boutonRetourEntreeMDPOublie.setOnAction(actionEvent -> ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/motDePasseOublie/VueMDPOublieMoyenne.fxml", "Mot de passe oublié", null));

        //[b] Associer fonctionnalités aux options de la barre de menu
        stQuitterApp.setOnAction(event -> Platform.exit());
        stGuideUtilisation.setOnAction(this::ouvrirGuideUtilisation);

        //[c] Fil d'Ariane : Retour à la page de connexion
        filConnexion.setOnAction(actionEvent -> ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));
        filMDPOublie.setOnAction(actionEvent -> ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/motDePasseOublie/VueMDPOublieMoyenne.fxml", "Mot de passe oublié", null));

        // [d] Raccourci mmémonique 2 : Ctrl Shift Q pour quitter l'application
        barreMenu.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
                Runnable runnable = Platform::exit;
                newScene.getAccelerators().put(keyCombination, runnable);
            }
        });

        //[e] Raccourci mmémonique 3 : CTRL =, CTRL - et CTRL 0 pour la taille de la police
        root.setOnKeyPressed(event -> {
            if (event.isControlDown()) {
                switch (event.getCode()) {
                    case UP:
                        grandePolice();
                        break;
                    case DOWN:
                        petitePolice();
                        break;
                    case DIGIT0:
                        moyennePolice();
                        break;
                }
            }
        });
    }


    /*=============================================================
     * [2] Méthodes pour les fonctionnalités du sous-menu Aide
     * [2] Afficher la fenêtre d'information A propos
     * ============================================================*/
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
     * Méthodes pour le sous-menu Aide
     *  [3] : Redirige vers le site JAVAFX
     * =======================================================*/
    @FXML
    private void ouvrirGuideUtilisation(ActionEvent event) {
        String url = "https://docs.oracle.com/javase/8/javase-clienttechnologies.htm";
        ClasseUtilitaire.redirectionSiteInternet(url);
    }


    /*===============================================
     * Méthodes pour changer la taille de la police
     * =============================================*/

    //[4] : Petite
    @FXML
    private void petitePolice() {
        Stage stage = (Stage) root.getScene().getWindow();
        ClasseUtilitaire.changerTaillePolice("/ca/delicivite/motDePasseOublieConfirmation/VueMDPOublieEmailValidePetite.fxml", stage);
    }

    //[5] : Grande
    @FXML
    private void grandePolice() {
        Stage stage = (Stage) root.getScene().getWindow();
        ClasseUtilitaire.changerTaillePolice("/ca/delicivite/motDePasseOublieConfirmation/VueMDPOublieEmailValideGrande.fxml", stage);
    }

    //[6] : Moyenne
    @FXML
    private void moyennePolice() {
        Stage stage = (Stage) root.getScene().getWindow();
        ClasseUtilitaire.changerTaillePolice("/ca/delicivite/motDePasseOublieConfirmation/VueMDPOublieEmailValideMoyenne.fxml", stage);
    }

}
