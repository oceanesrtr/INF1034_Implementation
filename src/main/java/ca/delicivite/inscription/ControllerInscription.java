package ca.delicivite.inscription;

import ca.delicivite.inscription.historique.HistoriqueNavigation;
import ca.delicivite.modele.ModeleItemMenu.TypeUtilisateur;
import ca.delicivite.modele.ModeleUtilisateur;
import ca.delicivite.outils.ClasseUtilitaire;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import static ca.delicivite.outils.ClasseUtilitaire.changerScene;

public class ControllerInscription implements Initializable {
    public BorderPane root;
    public Menu titreMenuApplication;
    public MenuItem stAnnulerAction;
    public MenuItem stRefaireAction;
    public Menu titreMenuApparence;
    public MenuItem modeSombreMenuItem;
    public MenuItem modeClairMenuItem;
    public Menu titreMenuVue;
    public Menu taillePoliceMenu;
    public MenuItem petiteTailleMenuItem;
    public MenuItem moyenneTailleMenuItem;
    public MenuItem grandeTailleMenuItem;
    public Menu menuAide;
    public ScrollPane scrollPane;
    public VBox container;
    public Text sousTitreLogo;
    public AnchorPane anchorPane;
    public Text sousTitreLogo2;
    public Group groupeBarre;
    public ProgressBar barreProgression;
    public TextField entreePrenomInscrit;
    public TextField entreeNomInscrit;
    public DatePicker entreeDateNaissanceInscrit;
    public RadioButton boutonLivreur;
    public Button boutonReinitialiser;
    public Button boutonSuivant;
    public Button boutonFilArianeEmploye;
    public RadioButton boutonClient;
    public RadioButton boutonRestaurateur;
    public Text copyrightMention;
    public Rectangle barreEtat;
   @FXML
   public Button buttonFilConnexion;
    public Button boutonRetourConnexion;
    public Pane filArianeBarreEtat;
    public ImageView etape1;
    public Text titreInscription;
    @FXML
    public ToggleGroup groupeBouton;
    @FXML
    private MenuItem stGuideUtilisation;
    @FXML
    private MenuItem stAPropos;
    @FXML
    private MenuItem stQuitterApp;
    @FXML
    private MenuBar barreMenu;


    /*================================================
     * [1] Initialisation de la scène
     * ===============================================*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //[a] Associer fonctionnalités aux options de la barre de menu
        stQuitterApp.setOnAction(event -> Platform.exit());
        stGuideUtilisation.setOnAction(this::ouvrirGuideUtilisation);

        //[b] Fil d'Ariane : Retour à la page de connexion
        buttonFilConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));
        boutonRetourConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));


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
     *Méthodes pour les fonctionnalités du sous-menu Aide
     * [] Afficher la fenêtre d'information A propos
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
     * [] : Redirige vers le site JAVAFX
     * =======================================================*/
    @FXML
    private void ouvrirGuideUtilisation(ActionEvent event) {
        String url = "https://docs.oracle.com/javase/8/javase-clienttechnologies.htm";
        ClasseUtilitaire.redirectionSiteInternet(url);
    }


    /*=========================================================
     * [] : Réinitialiser les champas
     * ========================================================*/
    @FXML
    private void reinitialiserChamp() {
        if (entreeNomInscrit.getText().isBlank() &&
                entreePrenomInscrit.getText().isBlank() &&
                entreeDateNaissanceInscrit.getValue() == null &&
                groupeBouton.getSelectedToggle() == null) {
                entreePrenomInscrit.setStyle("-fx-border-color: #FD2528");
                entreeNomInscrit.setStyle("-fx-border-color: #FD2528");
                entreeDateNaissanceInscrit.setStyle("-fx-border-color:#FD2528");
                boutonRestaurateur.setStyle("-fx-border-color: #FD2528");
                boutonClient.setStyle("-fx-border-color: #FD2528");
                boutonLivreur.setStyle("-fx-border-color: #FD2528");
            ClasseUtilitaire.afficherPopUp("Erreur", "Aucune donnée à réinitialiser", "Aucun champ n'est rempli.", Alert.AlertType.WARNING);
        } else {
            // Sinon, réinitialiser les champs uniquement si l'utilisateur confirme son choix
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Confirmation d'annulation", "Êtes-vous sûr de vouloir annuler cette action ?")) {
                entreePrenomInscrit.clear();
                entreeNomInscrit.clear();
                entreeDateNaissanceInscrit.setValue(null);
                groupeBouton.selectToggle(null);
                entreePrenomInscrit.setStyle("-fx-border-color: #424242");
                entreeNomInscrit.setStyle("-fx-border-color: #424242");
                entreeDateNaissanceInscrit.setStyle("-fx-border-color: #424242");
                boutonRestaurateur.setStyle("-fx-border-color: transparent");
                boutonClient.setStyle("-fx-border-color: transparent");
                boutonLivreur.setStyle("-fx-border-color: transparent");
            }
        }
    }



    /*===================================================
     * [] : Valider les champs avant de passer à la page suivante
     *  et les sauvegarder pour la base de données
     * ==================================================*/

    @FXML
    private void validationChamp() {

        String prenom = entreePrenomInscrit.getText().trim();
        String nom = entreeNomInscrit.getText().trim();
        LocalDate dateNaissance = entreeDateNaissanceInscrit.getValue();

        // ---------------VALIDATION-------------------------

        if (!(prenom.matches("[a-zA-ZÀ-ÿ\\-' ]+"))) {
            ClasseUtilitaire.afficherPopUp("Erreur", "Prénom incorrect", "Le prénom doit être alphabétique.", Alert.AlertType.ERROR);
            entreePrenomInscrit.setStyle("-fx-border-color: #FD2528");
            return;
        }

        if (!(nom.matches("[a-zA-ZÀ-ÿ\\-' ]+"))) {
            ClasseUtilitaire.afficherPopUp("Erreur", "Nom incorrect", "Le nom doit être alphabétique.", Alert.AlertType.ERROR);
            entreeNomInscrit.setStyle("-fx-border-color: #FD2528");
            return;
        }

        // Date de naissance inférieure ou égale à aujourd'hui
        if (dateNaissance == null || dateNaissance.isAfter(LocalDate.now())) {
            ClasseUtilitaire.afficherPopUp("Erreur", "Date incorrecte", "Veuillez entrer une date valide.", Alert.AlertType.ERROR);
            entreeDateNaissanceInscrit.setStyle("-fx-border-color: #FD2528");
            return;
        }

        // Type d'utilisateur non nul
        if (!boutonClient.isSelected() && !boutonLivreur.isSelected() && !boutonRestaurateur.isSelected()) {
            ClasseUtilitaire.afficherPopUp("Erreur", "Champ incomplet", "Veuillez sélectionner votre catégorie d'utilisateur.", Alert.AlertType.ERROR);
            boutonClient.setStyle("-fx-border-color: #FD2528");
            boutonLivreur.setStyle("-fx-border-color: #FD2528");
            boutonRestaurateur.setStyle("-fx-border-color: #FD2528");
            return;
        }

        //--------------------SAUVEGARDE TEMPORAIRE DES 3 DONNÉES-----------------------------------------


        //Obtenez le type d'utilisateur en fonction de l'index du bouton sélectionné dans le ToggleGroup
        TypeUtilisateur typeUtilisateurSelectionne = associerBoutonTypeUtilisateur();

        // Sauvegarde des 3 données et redirection vers la page correspondante au type choisi
        sauvegarderDonnees(prenom, nom, typeUtilisateurSelectionne);


        // Redirection vers la page suivante
        switch (Objects.requireNonNull(typeUtilisateurSelectionne)) {
            case client:
                boutonSuivant.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/inscription/inscriptionClient/VueInscriptionClient2.fxml", "Connexion", null));
                break;
            case livreur:
                boutonSuivant.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/inscription/inscriptionLivreur/VueInscriptionLivreur2.fxml", "Connexion", null));
                break;
            case proprietaire:
                boutonSuivant.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/inscription/inscriptionProprietaire/VueInscriptionProprietaire2.fxml", "Connexion", null));
                break;
        }
    }

    /*===================================
     * [] Méthode utilitaire : associer un bouton à un type d'utilisateur
     * ===============================*/
    private TypeUtilisateur associerBoutonTypeUtilisateur() {
        RadioButton boutonSelectionne = (RadioButton) groupeBouton.getSelectedToggle();

        if (boutonSelectionne != null) {
            int indexDuBouton = groupeBouton.getToggles().indexOf(boutonSelectionne);
            switch (indexDuBouton) {
                case 0:
                    return TypeUtilisateur.proprietaire;
                case 1:
                    return TypeUtilisateur.client;
                case 2:
                    return TypeUtilisateur.livreur;
                default:
                    ClasseUtilitaire.afficherPopUp("Erreur", "Champ incomplet", "Veuillez sélectionner votre catégorie d'utilisateur.", Alert.AlertType.ERROR);
            }
        } else
            ClasseUtilitaire.afficherPopUp("Erreur", "Champ incomplet", "Veuillez sélectionner votre catégorie d'utilisateur.", Alert.AlertType.ERROR);
        return null;
    }

    /*======================================================================
     * [] Méthode utilitaire : Sauvegarde des données (nom, prénom et type d'utilisateur)
     * ===================================================================*/

    private void sauvegarderDonnees(String nom, String prenom, TypeUtilisateur typeUtilisateur) {

        //Récupérer le modèle d'objet utilisateur unique dans lequel on va sauvegarder les informations de l'utilisateur temporairement
        ModeleUtilisateur utilisateur = ModeleUtilisateur.getObjetUtilisateur();

        // Enregistrez les informations saisies dans l'objet utilisateur
        utilisateur.setPrenom(prenom);
        utilisateur.setNom(nom);
        utilisateur.setTypeUtilisateur(typeUtilisateur);
    }




}
