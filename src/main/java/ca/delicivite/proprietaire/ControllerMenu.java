package ca.delicivite.proprietaire;
/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe Controller de l'interface propriétaire : gère les menus d'un restaurant*/

import ca.delicivite.outils.ClasseUtilitaire;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerMenu implements Initializable {

    // StackPane des différentes interfaces de l'interface menu (apercu, menu et modifier)
    @FXML
    public StackPane contentArea;

    @FXML
    public Button boutonApercu;
    @FXML
    public Button buttonMenu;
    @FXML
    public Button buttonModifier;
    public Button boutonRedirectionMenu;

    // Indicateur d'initialisation
    private boolean initialized = false;

    @FXML
    public Button boutonDeconnexion;
    // Bouton pour retourner a l'interface connexion
    @FXML
    public Button boutonFilConnexion;
    // Bouton pour aller a l'interface propriétaire (Cela reset la page)
    @FXML
    public Button buttonProprio;

    @FXML
    public MenuItem stAPropos;

    @FXML
    public MenuItem stGuideUtilisation;

    @FXML
    public Pane filArianeBarreEtat;

    @FXML
    public Rectangle barreEtat;

    @FXML
    public Text copyrightMention;

    @FXML
    public BorderPane root;

    @FXML
    public Menu titreMenuApplication;

    @FXML
    public MenuBar barreMenu;

    @FXML
    public MenuItem stAnnulerAction;
    @FXML
    public MenuItem stRefaireAction;
    @FXML
    public Menu menuInformations;
    @FXML
    public MenuItem stQuitterApp;
    @FXML
    public MenuItem modeSombreMenuItem;
    @FXML
    public Menu titreMenuApparence;
    @FXML
    public MenuItem modeClairMenuItem;
    @FXML
    public MenuItem petiteTailleMenuItem;
    @FXML
    public Menu titreMenuVue;
    @FXML
    public Menu taillePoliceMenu;
    @FXML
    public MenuItem moyenneTailleMenuItem;
    @FXML
    public MenuItem grandeTailleMenuItem;

    /*=========================================================================
    [1] Initialize au démarrage de la scène
    * ========================================================================*/
    @Override
    public void initialize(URL location, ResourceBundle resource) {

        //[a] : Chargement de la scene voulue
        if (!initialized) {
            try {
                // Chargement de la vue par défaut
                Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VueApercuMenu.fxml"));
                contentArea.getChildren().removeAll();
                contentArea.getChildren().setAll(fxml);
                initialized = true;
                boutonApercu.getStyleClass().add("clicked-button");
            } catch (IOException ex) {
                Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        stQuitterApp.setOnAction(event -> Platform.exit());

        // [b] Raccourci mmémonique 2 : Ctrl Shift Q pour quitter l'application
        barreMenu.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
                Runnable runnable = Platform::exit;
                newScene.getAccelerators().put(keyCombination, runnable);
            }
        });
    }

    /*=========================================================================
    [2] Méthode pour afficher l'interface apercu
    * ========================================================================*/
    @FXML
    public void apercu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VueApercuMenu.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        boutonFonce(boutonApercu);
    }

    /*=========================================================================
    [3] Méthode pour afficher l'interface menu
    * ========================================================================*/
    @FXML
    public void menu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VueMenuMenu.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
        boutonFonce(buttonMenu);
    }

    /*=========================================================================
    [4] Méthode pour afficher l'interface modifier
    * ========================================================================*/
    @FXML
    public void modifier(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VueModifierMenu.fxml"));
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
        boutonApercu.getStyleClass().remove("clicked-button");
        buttonMenu.getStyleClass().remove("clicked-button");
        buttonModifier.getStyleClass().remove("clicked-button");
    }

    /*=========================================================================
   [7] Méthode pour afficher l'interface "Accueil"
   * ========================================================================*/
    public void accueil(javafx.event.ActionEvent actionEvent) throws IOException {
      /*  onButtonClicked(actionEvent);
        Parent fxml = FXMLLoader.load(getClass().getResource("ca/delicivite/proprietaire/VueAccueil.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);*/

        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VueAccueil.fxml", "Accueil Propriétaire", null);
    }

    /*=========================================================================
    [8] Méthode pour afficher l'interface "Commerce"
    * ========================================================================*/
    public void commerce(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VueCommerce.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [9] Méthode pour afficher l'interface "Commandes"
    * ========================================================================*/
    public void commandes(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VueCommande.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [10] Méthode pour afficher l'interface "Performances"
    * ========================================================================*/
    public void performances(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VuePerformance.fxml", "Accueil Propriétaire", null);

    }


    /*=========================================================================
    [11] Méthode pour afficher l'interface "Paiements"
    * ========================================================================*/
    public void paiements(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VuePaiement.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [12] Méthode pour afficher l'interface "Employés"
    * ========================================================================*/
    public void employes(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VueEmploye.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [13] Méthode pour afficher l'interface "Paramètres"
    * ========================================================================*/
    public void parametres(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VueParametre.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [14] Méthode pour gérer la demande de déconnexion dans le fil d'ariane
    * ========================================================================*/
    public void onConnexion() throws IOException {
        boutonDeconnexion.setStyle("-fx-background-color: #FFD324;-fx-text-fill: #424242;");
        if (ClasseUtilitaire.afficherPopUpConfirmation("Déconnexion", "Confirmation de déconnexion", "Êtes-vous sûr de vouloir vous déconnecter?")) {
            Parent root = FXMLLoader.load(getClass().getResource("/ca/delicivite/VueConnexionTailleMoyenne.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) boutonFilConnexion.getScene().getWindow();
            stage.setTitle("Connexion");
            stage.setScene(scene);
        } else {
            boutonDeconnexion.setStyle("-fx-background-color: #F44322;-fx-text-fill: #FFFFFF;");


        }
    }

    // Variables pour la gestion du déplacement de la fenêtre
    double x, y;


    // Bouton cliqué précédemment
    @FXML
    private Button dernierBoutonClique;

    /*=========================================================================
    [15] Méthode appelée lors du clic sur un bouton du menu pour appliquer une couleur plus sombre
    * ========================================================================*/
    @FXML
    private void onButtonClicked(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        // Retrait de l'element "clicked-button" au bouton
        if (dernierBoutonClique != null) {
            dernierBoutonClique.getStyleClass().remove("clicked-button");
        }

        // Ajout de l'element "clicked-button" au bouton
        clickedButton.getStyleClass().add("clicked-button");

        dernierBoutonClique = clickedButton;
    }

    /*=============================================================
     *Méthodes pour les fonctionnalités du sous-menu Informations
     * [16] Afficher la fenêtre d'information A propos
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
     * [17] : Redirige vers le site JAVAFX
     * =======================================================*/
    @FXML
    private void ouvrirGuideUtilisation(ActionEvent event) {
        String url = "https://docs.oracle.com/javase/8/javase-clienttechnologies.htm";
        ClasseUtilitaire.redirectionSiteInternet(url);
    }


}
