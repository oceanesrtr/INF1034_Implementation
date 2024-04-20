package ca.delicivite.inscription.inscriptionProprietaire;
/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe Controller : de la page d'inscription en tant que propriétaire */

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

public class ControllerProprietaire implements Initializable {
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
    public TextField entreeNomRestaurant;
    @FXML
    public TextField entreeAdresseRestaurant;
    @FXML
    public Text copyrightMention;
    @FXML
    public TextField entreeCellulaireRestaurant;
    @FXML
    public TextField entreeCodePostalRestaurant;
    @FXML
    public GridPane tableauChoixSpecialiteRestaurant;
    @FXML
    public ToggleGroup Cuisine;
    @FXML
    public Rectangle barreEtat;
    @FXML
    public Pane filArianeBarreEtat;
    @FXML
    public Button boutonFilConnexion;
    @FXML
    public Button boutonFilArianeEmploye;


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
        boutonRetourPagePrecedente.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));

        // [c] Raccourci mmémonique 2 : Ctrl Shift Q pour quitter l'application
        barreMenu.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
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
        if (entreeCodePostalRestaurant.getText().isBlank() && entreeAdresseRestaurant.getText().isBlank() && entreeCellulaireRestaurant.getText().isBlank() && entreeNomRestaurant.getText().isBlank() && !(auMoinsUneCaseCochee())
        ) {
            entreeCodePostalRestaurant.setStyle("-fx-border-color: #FD2528");
            entreeAdresseRestaurant.setStyle("-fx-border-color: #FD2528");
            entreeNomRestaurant.setStyle("-fx-border-color: #FD2528");
            entreeCellulaireRestaurant.setStyle("-fx-border-color: #FD2528");
            tableauChoixSpecialiteRestaurant.setStyle("-fx-border-color: #FD2528");
            ClasseUtilitaire.afficherPopUp("Erreur", "Aucune donnée à réinitialiser", "Aucun champ n'est rempli.", Alert.AlertType.WARNING);
        } else {
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Confirmation d'annulation", "Êtes-vous sûr de vouloir annuler cette action ?")) {
                entreeCodePostalRestaurant.clear();
                entreeAdresseRestaurant.clear();
                entreeNomRestaurant.clear();
                entreeCellulaireRestaurant.clear();

                // Parcours des enfants du GridPane
                for (Node node : tableauChoixSpecialiteRestaurant.getChildren()) {
                    if (node instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) node;
                        if (checkBox.isSelected()) {
                            checkBox.setSelected(false);
                        }
                    }
                }

                entreeCodePostalRestaurant.setStyle("-fx-border-color: #424242");
                entreeAdresseRestaurant.setStyle("-fx-border-color: #424242");
                entreeNomRestaurant.setStyle("-fx-border-color: #424242");
                entreeCellulaireRestaurant.setStyle("-fx-border-color: #424242");
                tableauChoixSpecialiteRestaurant.setStyle("-fx-border-color: #424242");

            }

        }
    }


    /*=========================================
    * [4] Validation des champs
    * ======================================*/
    @FXML
    private void validationChamp(ActionEvent event) {
        String adresse = entreeAdresseRestaurant.getText().trim();
        String codePostal = entreeCodePostalRestaurant.getText().trim();
        String cellulaire = entreeCellulaireRestaurant.getText().trim();
        String nom = entreeNomRestaurant.getText().trim();

        // Valider l'adresse
        if (!adresse.matches("[a-zA-Z0-9À-ÿ\\-' ]+")) {
            afficherPopUp("Erreur", "Adresse incorrecte", "L'adresse doit contenir uniquement des lettres, des chiffres et des caractères spéciaux.", Alert.AlertType.ERROR);
            entreeAdresseRestaurant.setStyle("-fx-border-color: #FD2528");
            return;
        }

        // Valider le nom du restaurant
        if (!adresse.matches("[a-zA-Z0-9À-ÿ\\-' ]+")) {
            afficherPopUp("Erreur", "Adresse incorrecte", "L'adresse doit contenir uniquement des lettres, des chiffres et des caractères spéciaux.", Alert.AlertType.ERROR);
            entreeNomRestaurant.setStyle("-fx-border-color: #FD2528");
            return;
        }


        // Valider le code postal
        if (!codePostal.matches("[A-Za-z]\\d[A-Za-z]\\s?\\d[A-Za-z]\\d")) {
            afficherPopUp("Erreur", "Code postal incorrect", "Veuillez entrer un code postal valide.", Alert.AlertType.ERROR);
            entreeCodePostalRestaurant.setStyle("-fx-border-color: #FD2528");
            return;
        }

        // Valider le numéro de téléphone
        if (!cellulaire.matches("[0-9\\-]+") || cellulaire.length() > 10) {
            afficherPopUp("Erreur", "Numéro de téléphone incorrect", "Veuillez entrer un numéro de téléphone valide (maximum 10 chiffres).", Alert.AlertType.ERROR);
            entreeCellulaireRestaurant.setStyle("-fx-border-color: #FD2528");
            return;
        }

        if (!auMoinsUneCaseCochee()) {
            afficherPopUp("Champ incomplet", "Spécialités non sélectionnées", "Veuillez sélectionner au moins une spécialité de cuisine.", Alert.AlertType.ERROR);
            tableauChoixSpecialiteRestaurant.setStyle("-fx-border-color: #FD2528");
        }

        //Si tout est valide :
        changerScene(event, "/ca/delicivite/inscription/VueIdentifiantP3.fxml", "Connexion", null);
    }

    /*====================================
     * Méthode utilitaire pour vérifier si
     *  le livreur a bien coché au moins une case
     * ==================================*/
    private boolean auMoinsUneCaseCochee() {
        return Cuisine.getSelectedToggle() != null;
    }
}
