package ca.delicivite.inscription.inscriptionClient;

/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe Controller : de la page d'inscription en tant que client */

import ca.delicivite.outils.ClasseUtilitaire;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static ca.delicivite.outils.ClasseUtilitaire.afficherPopUp;
import static ca.delicivite.outils.ClasseUtilitaire.changerScene;

public class ControllerClient implements Initializable {
    @FXML
    public BorderPane root;
    @FXML
    public MenuBar barreMenu;
    @FXML
    public MenuItem stAnnulerAction;
    @FXML
    public Menu titreMenuApplication;
    @FXML
    public MenuItem stRefaireAction;
    @FXML
    public MenuItem stQuitterApp;
    @FXML
    public Menu titreMenuApparence;
    @FXML
    public MenuItem modeSombreMenuItem;
    @FXML
    public MenuItem modeClairMenuItem;
    @FXML
    public Menu titreMenuVue;
    @FXML
    public Menu taillePoliceMenu;
    @FXML
    public MenuItem petiteTailleMenuItem;
    @FXML
    public MenuItem moyenneTailleMenuItem;
    @FXML
    public MenuItem grandeTailleMenuItem;
    @FXML
    public Menu menuInformations;
    @FXML
    public MenuItem stAPropos;
    @FXML
    public MenuItem stGuideUtilisation;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox container;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Text sousTitreLogo;
    @FXML
    public Text sousTitreLogo2;
    @FXML
    public Group groupeBarre;
    @FXML
    public ProgressBar barreProgression;
    @FXML
    public Button boutonReinitialiser;
    @FXML
    public Button boutonRetourPagePrecedente;
    @FXML
    public Button boutonSuivant;
    @FXML
    public Text copyrightMention;
    @FXML
    public GridPane tableauCuisineFavorite;
    @FXML
    public Rectangle barreEtat;
    @FXML
    public Pane filArianeBarreEtat;
    @FXML
    public Button boutonFilConnexion;
    @FXML
    public Button boutonFilArianeEmploye;
    @FXML
    public Button boutonRetourConnexion;
    @FXML
    public TextField entree1;
    @FXML
    public TextField entree2;
    @FXML
    public TextField entree3;
    @FXML
    public Text titreInscription;
    @FXML
    public Text sousTitre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //[a] Associer fonctionnalités aux options de la barre de menu
        stQuitterApp.setOnAction(event -> Platform.exit());
        stGuideUtilisation.setOnAction(this::ouvrirGuideUtilisation);
        boutonSuivant.setOnAction(event -> {
            validationChamp(event);
        });

        //[b] Fil d'Ariane : Retour à la page de connexion
        boutonFilConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));
        boutonRetourPagePrecedente.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/inscription/VueInscriptionGenerale1.fxml", "Connexion", null));

        // [c] Raccourci mmémonique 2 : Ctrl Shift Q pour quitter l'application
barreMenu.sceneProperty().addListener((observable, oldScene, newScene) -> {
    if (newScene != null) {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.ALT_DOWN);
        Runnable runnable = Platform::exit;
        newScene.getAccelerators().put(keyCombination, runnable);
    }
});

    }

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


    /*=========================================================
     * [3] : Réinitialiser les champas
     * ========================================================*/
    @FXML
    private void reinitialiserChamp() {


        if (entree1.getText().isBlank() &&
                entree3.getText().isBlank() &&
                entree2.getText().isBlank() && !(auMoinsUneSpecialiteSelectionnee())
        ) {
            entree1.setStyle("-fx-border-color: #FD2528");
            entree3.setStyle("-fx-border-color: #FD2528");
            entree2.setStyle("-fx-border-color: #FD2528");
            tableauCuisineFavorite.setStyle("-fx-border-color: #FD2528");

            ClasseUtilitaire.afficherPopUp("Erreur", "Aucune donnée à réinitialiser", "Aucun champ n'est rempli.", Alert.AlertType.WARNING);
        } else {
            // Sinon, réinitialiser les champs uniquement si l'utilisateur confirme son choix
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Confirmation d'annulation", "Êtes-vous sûr de vouloir annuler cette action ?")) {
                entree1.clear();
                entree2.clear();
                entree3.clear();
                for (Node node : tableauCuisineFavorite.getChildren()) {
                    if (node instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) node;
                        checkBox.setSelected(false);
                    }
                }

                entree1.setStyle("-fx-border-color: #424242");
                entree2.setStyle("-fx-border-color: #424242");
                entree3.setStyle("-fx-border-color: #424242");
                tableauCuisineFavorite.setStyle("-fx-border-color: transparent");
            }
        }
    }

    /*===================================================
     * [4] : Valider les champs avant de passer à la page suivante
     *  et les sauvegarder pour la base de données
     * ==================================================*/

    @FXML
    private void validationChamp(ActionEvent event) {


        String adresse = entree1.getText().trim();
        String codePostal = entree2.getText().trim();
        String cellulaire = entree3.getText().trim();

        // ---------------VALIDATION-------------------------

        // Valider l'adresse
        if (!adresse.matches("[a-zA-Z0-9À-ÿ\\-' ]+")) {
            afficherPopUp("Erreur", "Adresse incorrecte", "L'adresse doit contenir uniquement des lettres, des chiffres et des caractères spéciaux.", Alert.AlertType.ERROR);
            entree1.setStyle("-fx-border-color: #FD2528");
            return;
        }

        // Valider le code postal
        else if (!codePostal.matches("[A-Za-z]\\d[A-Za-z]\\s?\\d[A-Za-z]\\d")) {
            afficherPopUp("Erreur", "Code postal incorrect", "Veuillez entrer un code postal valide.", Alert.AlertType.ERROR);
            entree2.setStyle("-fx-border-color: #FD2528");
            return;
        }

        // Valider le numéro de téléphone
        else if (!cellulaire.matches("[0-9\\-]+") || cellulaire.length() > 10) {
            afficherPopUp("Erreur", "Numéro de téléphone incorrect", "Veuillez entrer un numéro de téléphone valide (maximum 10 chiffres).", Alert.AlertType.ERROR);
            entree3.setStyle("-fx-border-color: #FD2528");
            return;
        }


        else if (!auMoinsUneSpecialiteSelectionnee()) {
            afficherPopUp("Champ incomplet", "Spécialités non sélectionnées", "Veuillez sélectionner au moins une spécialité de cuisine.", Alert.AlertType.ERROR);
            tableauCuisineFavorite.setStyle("-fx-border-color: #FD2528");
        }

        //Si tout est valide :
        else changerScene(event, "/ca/delicivite/inscription/VueIdentifiantP3.fxml", "Connexion", null);
    }

    /*===========================================================
     * [5] Méthode utilitaire : vérifier si au moins une spécialité
     *  a été cochée par le client
     * ================================================================*/

    private boolean auMoinsUneSpecialiteSelectionnee() {
        boolean auMoinsUneSpecialiteSelectionnee = false;
        for (Node node : tableauCuisineFavorite.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {
                    auMoinsUneSpecialiteSelectionnee = true;
                    break;
                }
            }
        }
        return auMoinsUneSpecialiteSelectionnee;
    }
}
