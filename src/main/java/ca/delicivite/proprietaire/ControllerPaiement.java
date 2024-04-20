package ca.delicivite.proprietaire;

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

/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe Controller de l'interface propriétaire : gère les paiements d'un restaurant*/
public class ControllerPaiement implements Initializable {


    // StackPane des différentes interfaces de l'interface proprietaire
    @FXML
    public StackPane conteneurPrincipal;
    // Bouton de déconnexion
    @FXML
    public Button boutonDeconnexion;
    // Bouton pour retourner a l'interface connexion
    @FXML
    public Button boutonFilConnexion;
    // Bouton pour aller a l'interface propriétaire (Cela reset la page)
    @FXML
    public Button boutonProprietaire;
    public MenuItem stAPropos;
    public MenuItem stGuideUtilisation;
    public Pane filArianeBarreEtat;
    public Rectangle barreEtat;
    public Text copyrightMention;
    public BorderPane root;
    public Menu titreMenuApplication;
    public MenuBar barreMenu;
    public MenuItem stAnnulerAction;
    public MenuItem stRefaireAction;
    public Menu menuInformations;
    public MenuItem stQuitterApp;
    public MenuItem modeSombreMenuItem;
    public Menu titreMenuApparence;
    public MenuItem modeClairMenuItem;
    public MenuItem petiteTailleMenuItem;
    public Menu titreMenuVue;
    public Menu taillePoliceMenu;
    public MenuItem moyenneTailleMenuItem;
    public MenuItem grandeTailleMenuItem;
    public Button boutonPaiement;

    /*=========================================================================
    [1] Initialize au démarrage de la scène
    * ========================================================================*/
    @Override
    public void initialize(URL location, ResourceBundle resource) {

        //[a] : redirection vers des pages
        stQuitterApp.setOnAction(event -> Platform.exit());

        // [b] Raccourci mmémonique : Ctrl Shift Q pour quitter l'application
        barreMenu.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
                Runnable runnable = Platform::exit;
                newScene.getAccelerators().put(keyCombination, runnable);
            }
        });
    }

    /*=========================================================================
    [2] Méthode pour afficher l'interface "Accueil"
    * ========================================================================*/
    public void accueil(javafx.event.ActionEvent actionEvent) throws IOException {

        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VueAccueil.fxml", "Accueil Propriétaire", null);
    }

    /*=========================================================================
    [3] Méthode pour afficher l'interface "Commerce"
    * ========================================================================*/
    public void commerce(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VueCommerce.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [4] Méthode pour afficher l'interface "Commandes"
    * ========================================================================*/
    public void commandes(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VueCommande.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [5] Méthode pour afficher l'interface "Performances"
    * ========================================================================*/
    public void performances(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VuePerformance.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [6] Méthode pour afficher l'interface "Menu"
    * ========================================================================*/
    public void menu(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VueMenu.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [7] Méthode pour afficher l'interface "Paiements"
    * ========================================================================*/
    public void paiements(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VuePaiement.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [8] Méthode pour afficher l'interface "Employés"
    * ========================================================================*/
    public void employes(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VueEmploye.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [9] Méthode pour afficher l'interface "Paramètres"
    * ========================================================================*/
    public void parametres(javafx.event.ActionEvent actionEvent) throws IOException {
        ClasseUtilitaire.changerScene(actionEvent, "/ca/delicivite/proprietaire/VueParametre.fxml", "Accueil Propriétaire", null);

    }

    /*=========================================================================
    [10] Méthode pour gérer la demande de déconnexion dans le fil d'ariane
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
    [11] Méthode appelée lors du clic sur un bouton du menu pour appliquer une couleur plus sombre
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
     * [12] Afficher la fenêtre d'information A propos
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
     * [13] : Redirige vers le site JAVAFX
     * =======================================================*/
    @FXML
    private void ouvrirGuideUtilisation(ActionEvent event) {
        String url = "https://docs.oracle.com/javase/8/javase-clienttechnologies.htm";
        ClasseUtilitaire.redirectionSiteInternet(url);
    }

}