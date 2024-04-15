package ca.delicivite.proprietaire;

import ca.delicivite.outils.ClasseUtilitaire;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerMonCommerce implements Initializable {


    // StacPane des différentes interfaces de l'interface proprietaire
    @FXML
    public StackPane contentArea;
    // Bouton de déconnexion
    @FXML
    public Button buttonDeconnexion;
    // Bouton pour retourner a l'interface connexion
    @FXML
    public Button buttonfilConnexion;
    // Bouton pour aller a l'interface propriétaire (Cela reset la page)
    @FXML
    public Button buttonProprio;
    public MenuItem stAPropos;
    public MenuItem stGuideUtilisation;
    public Rectangle barreEtat;

    /*=========================================================================
    [1] Initialize au démarrage de la scène
    * ========================================================================*/
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        try {
            // Chargement de la vue "Accueil" par défaut
            Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VueAccueil.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException ex){
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /*=========================================================================
    [2] Méthode pour afficher l'interface "Accueil"
    * ========================================================================*/
    public void accueil(javafx.event.ActionEvent actionEvent) throws IOException {
        onButtonClicked(actionEvent);
        Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VueAccueil.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    /*=========================================================================
    [3] Méthode pour afficher l'interface "Commerce"
    * ========================================================================*/
    public void commerce(javafx.event.ActionEvent actionEvent) throws IOException {
        onButtonClicked(actionEvent);
        Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VueCommerce.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    /*=========================================================================
    [4] Méthode pour afficher l'interface "Commandes"
    * ========================================================================*/
    public void commandes(javafx.event.ActionEvent actionEvent) throws IOException {
        onButtonClicked(actionEvent);
        Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VueCommande.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    /*=========================================================================
    [5] Méthode pour afficher l'interface "Performances"
    * ========================================================================*/
    public void performances(javafx.event.ActionEvent actionEvent) throws IOException {
        onButtonClicked(actionEvent);
        Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VuePerformance.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    /*=========================================================================
    [6] Méthode pour afficher l'interface "Menu"
    * ========================================================================*/
    public void menu(javafx.event.ActionEvent actionEvent) throws IOException {
        onButtonClicked(actionEvent);
        Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VueMenu.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    /*=========================================================================
    [7] Méthode pour afficher l'interface "Paiements"
    * ========================================================================*/
    public void paiements(javafx.event.ActionEvent actionEvent) throws IOException {
        onButtonClicked(actionEvent);
        Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VuePaiement.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    /*=========================================================================
    [8] Méthode pour afficher l'interface "Employés"
    * ========================================================================*/
    public void employes(javafx.event.ActionEvent actionEvent) throws IOException {
        onButtonClicked(actionEvent);
        Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VueEmploye.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    /*=========================================================================
    [9] Méthode pour afficher l'interface "Paramètres"
    * ========================================================================*/
    public void parametres(javafx.event.ActionEvent actionEvent) throws IOException {
        onButtonClicked(actionEvent);
        Parent fxml = FXMLLoader.load(getClass().getResource("/ca/delicivite/proprietaire/VueParametre.fxml.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    /*=========================================================================
    [10] Méthode pour se déconnecté
    * ========================================================================*/
    public void onDeconnexion() throws IOException {
        // Redirection vers la vue de connexion
        Parent root = FXMLLoader.load(getClass().getResource("/ca/delicivite/VueConnexionTailleMoyenne.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) buttonDeconnexion.getScene().getWindow();
        stage.setScene(scene);
    }

    /*=========================================================================
    [11] Méthode pour gérer la demande de déconnexion dans le fil d'ariane
    * ========================================================================*/
    public void onConnexion() throws IOException {
        buttonDeconnexion.setStyle("-fx-background-color: #3DBDD2;");

        // Affichage d'une boîte de dialogue de confirmation
        Alert verification = new Alert(Alert.AlertType.CONFIRMATION);
        verification.setTitle("Confirmation");
        verification.setHeaderText(null);
        verification.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");
        Stage stage1 = (Stage) verification.getDialogPane().getScene().getWindow();
        stage1.getIcons().add(new Image("/images/logo_fond_grise.png"));

        Optional<ButtonType> resultat = verification.showAndWait();

        if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
            // Redirection vers la vue de connexion
            Parent root = FXMLLoader.load(getClass().getResource("/ca/delicivite/VueConnexionTailleMoyenne.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) buttonfilConnexion.getScene().getWindow();
            stage.setScene(scene);
        }
    }

    // Variables pour la gestion du déplacement de la fenêtre
    double x, y;

    /*=========================================================================
    [12] Méthode pour reouvrir l'interface propriétaire
    * ========================================================================*/
    public void onProprio() throws Exception {
        // Chargement de la vue propriétaire
        Parent root = FXMLLoader.load(getClass().getResource("ca/delicivite/VueConnexionTailleMoyenne.fxml"));
        Stage stage = (Stage) buttonProprio.getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("Interface Proprietaire");
        stage.getIcons().add(new Image("/images/logo_fond_grise.png"));
        Scene scene = new Scene(root, 1300, 800);
        scene.getStylesheets().add(getClass().getResource("/ca/delicivite/proprietaire/proprietaire.css").toExternalForm());
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);

        // Déplacement de la fenêtre ----------- a regarder
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.show();
    }


    // Bouton cliqué précédemment
    @FXML
    private Button lastClickedButton;

    /*=========================================================================
    [13] Méthode appelée lors du clic sur un bouton du menu pour appliquer une couleur plus sombre
    * ========================================================================*/
    @FXML
    private void onButtonClicked(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        // Retrait de l'element "clicked-button" au bouton
        if (lastClickedButton != null) {
            lastClickedButton.getStyleClass().remove("clicked-button");
        }

        // Ajout de l'element "clicked-button" au bouton
        clickedButton.getStyleClass().add("clicked-button");

        lastClickedButton = clickedButton;
    }
    /*=============================================================
     *Méthodes pour les fonctionnalités du sous-menu Aide
     * [4] Afficher la fenêtre d'information A propos
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
     * [5] : Redirige vers le site JAVAFX
     * =======================================================*/
    @FXML
    private void ouvrirGuideUtilisation(ActionEvent event) {
        String url = "https://docs.oracle.com/javase/8/javase-clienttechnologies.htm";
        ClasseUtilitaire.redirectionSiteInternet(url);
    }

}