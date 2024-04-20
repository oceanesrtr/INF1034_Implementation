package ca.delicivite.motDePasseOublie;

/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe controller : gère les ControllerProprietaire de l'interface de récupération de mot de passe oublié*/

import ca.delicivite.modele.ModeleUtilisateur;
import ca.delicivite.outils.ClasseUtilitaire;
import ca.delicivite.patronObservateur.ModeleEntree;
import ca.delicivite.patronObservateur.ObservateurEntree;
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

import static ca.delicivite.outils.ClasseUtilitaire.*;


public class ControllerMDPOublie implements Initializable {

    @FXML
    public Menu titreMenuApplication;
    @FXML
    public MenuItem modeSombreMenuItem;
    @FXML
    public MenuItem modeClairMenuItem;
    @FXML
    public Menu taillePoliceMenu;
    @FXML
    public MenuItem petiteTailleMenuItem;
    @FXML
    public Button boutonAnnulerMDP;
    @FXML
    public MenuItem grandeTailleMenuItem;
    @FXML
    public Menu menuInformations;
    @FXML
    public Pane filArianeBarreEtat;
    @FXML
    public MenuItem moyenneTailleMenuItem;
    @FXML
    public Menu titreMenuApparence;
    @FXML
    public Text copyrightMention;
    @FXML
    public Button filConnexion;
    @FXML
    public Menu titreMenuVue;
    @FXML
    public Rectangle barreEtat;
    @FXML
    private Button filMDPOublie;
    @FXML
    private ModeleEntree modeleEntreeCourriel = new ModeleEntree();
    @FXML
    BorderPane root;
    @FXML
    Button boutonEnvoyerMailMDP;
    @FXML
    private Label descriptionMDPOublie;
    @FXML
    private MenuItem stGuideUtilisation;
    @FXML
    private MenuItem stAPropos;
    @FXML
    private MenuItem stAnnulerAction;
    @FXML
    private MenuItem stRefaireAction;
    @FXML
    private MenuItem stQuitterApp;
    @FXML
    private MenuBar barreMenu;
    @FXML
    private String entreeCourrielPrecedente = "";
    @FXML
    private TextField champMailMDPOublie;
    @FXML
    private Label titreMDPOUblie;
    @FXML
    private TextField champActif;

    /*=========================================================================
    [1] Initialize au démarrage de la scène
    * ========================================================================*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //[a] Redirection vers les pages adéquates selon les boutons cliqués
        boutonEnvoyerMailMDP.setOnAction(this::envoyerMail);

        //[b] Associer fonctionnalités aux options de la barre de menu
        stQuitterApp.setOnAction(event -> Platform.exit());
        stGuideUtilisation.setOnAction(this::ouvrirGuideUtilisation);
        stAnnulerAction.setOnAction(this::annulerAction);
        stRefaireAction.setOnAction(this::refaireAction);

        //[c] Fil d'Ariane : Retour à la page de connexion
        filConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));

        // [d] Création d'observateurs pour les champs de texte (courriel et mot de passe)
        champMailMDPOublie.setOnMouseClicked(event -> champActif = champMailMDPOublie);

        ObservateurEntree observateurEntreeCourriel = new ObservateurEntree(modeleEntreeCourriel);

        // Écouteurs
        champMailMDPOublie.textProperty().addListener((observable, oldValue, newValue) -> modeleEntreeCourriel.setTexte(newValue));
        modeleEntreeCourriel.ajouterObservateur(observateurEntreeCourriel);


        // [e] Raccourci mnémonique 1 : Entrée pour se connecter
        champMailMDPOublie.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                envoyerMail(new ActionEvent(event.getSource(), event.getTarget()));
            }
        });


        // [f] Raccourci mmémonique 2 : Ctrl Shift Q pour quitter l'application
        barreMenu.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
                Runnable runnable = Platform::exit;
                newScene.getAccelerators().put(keyCombination, runnable);
            }
        });

        //[g] Raccourci mmémonique 3 : CTRL =, CTRL - et CTRL 0 pour la taille de la police
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
     *Méthodes pour les fonctionnalités du sous-menu Informations
     * [2] Afficher la fenêtre d'information A propos
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
     * [3] : Redirige vers le site JAVAFX
     * =======================================================*/
    @FXML
    private void ouvrirGuideUtilisation(ActionEvent event) {
        String url = "https://docs.oracle.com/javase/8/javase-clienttechnologies.htm";
        ClasseUtilitaire.redirectionSiteInternet(url);
    }

    /*=======================================================
     * [4] : Annule une entrée
     * =======================================================*/

    @FXML
    protected void annulerEntree() {
        if (champMailMDPOublie.getText().isBlank()) {
            afficherPopUp("Erreur", "Erreur lors de l'annulation", "Veuillez remplir au moins un champ avant d'annuler la connexion.", Alert.AlertType.WARNING);
            champMailMDPOublie.setStyle("-fx-border-color: #FD2528;-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #FD2528;");
        } else {
            champMailMDPOublie.setStyle("-fx-border-color: #424242;-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #424242;");
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Confirmation d'annulation", "Êtes-vous sûrs de vouloir annuler cette action ?"))
                champMailMDPOublie.clear();
        }
    }

    /*===============================================
     * Méthodes pour changer la taille de la police
     * =============================================*/

    //[5] : Petite
    @FXML
    private void petitePolice() {
        Stage stage = (Stage) root.getScene().getWindow();
        ClasseUtilitaire.changerTaillePolice("/ca/delicivite/motDePasseOublie/VueMDPOubliePetite.fxml", stage);
    }

    //[6] : Grande
    @FXML
    private void grandePolice() {
        Stage stage = (Stage) root.getScene().getWindow();
        ClasseUtilitaire.changerTaillePolice("/ca/delicivite/motDePasseOublie/VueMDPOublieGrande.fxml", stage);
    }

    //[7] : Moyenne
    @FXML
    private void moyennePolice() {
        Stage stage = (Stage) root.getScene().getWindow();
        ClasseUtilitaire.changerTaillePolice("/ca/delicivite/motDePasseOublie/VueMDPOublieMoyenne.fxml", stage);
    }


    /*=======================================================
     * Méthodes pour le sous-menu Application
     * [8] : Annule l'entrée active
     * =======================================================*/
    @FXML
    private void annulerAction(ActionEvent event) {
        // Avant d'annuler, sauvegarder l'entrée et effacer le champ après
        if (champActif == champMailMDPOublie) {
            entreeCourrielPrecedente = champMailMDPOublie.getText();
            champMailMDPOublie.clear();
        }
    }

    /*=========================================================
     * [9] : Refait l'Entrée active
     * =========================================================*/
    @FXML
    private void refaireAction(ActionEvent event) {
        // Remettre le texte précédent dans le champ
        if (champActif == champMailMDPOublie) champMailMDPOublie.setText(entreeCourrielPrecedente);
    }

    /*===============================================
     * [10] : Valide l'entrée (e-mail de récupération)
     * =============================================*/

    @FXML
    private void envoyerMail(ActionEvent actionEvent) {
        if (champMailMDPOublie.getText().isBlank()) {
            afficherPopUp("Erreur", "Erreur d'entrée", "Veuillez fournir votre courriel identifiant.", Alert.AlertType.WARNING);
            champMailMDPOublie.setStyle("-fx-border-color: #FD2528;-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #FD2528;");
        } else {
            champMailMDPOublie.setStyle("-fx-border-color: #424242;-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #424242;");
            if (afficherPopUpConfirmation("Confirmation", "Confirmation d'adresse courriel", "Confirmez-vous votre adresse-courriel ?")) {
                ModeleUtilisateur.verifierEmailMotDePasseOublie(actionEvent, champMailMDPOublie.getText());
                champMailMDPOublie.setStyle("-fx-border-color: #424242;-fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-color: #424242;");

            }
        }
    }
}