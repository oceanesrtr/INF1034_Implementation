package ca.delicivite.inscription;

/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe Controller : de la paremière page d'inscription générale*/

import ca.delicivite.modele.ModeleItemMenu.TypeUtilisateur;
import ca.delicivite.modele.ModeleUtilisateur;
import ca.delicivite.outils.ClasseUtilitaire;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import static ca.delicivite.outils.ClasseUtilitaire.changerScene;

public class ControllerInscription implements Initializable {
    @FXML
    public BorderPane root;
    @FXML
    public Menu titreMenuApplication;
    @FXML
    public MenuItem stAnnulerAction;
    @FXML
    public MenuItem stRefaireAction;
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
    public ScrollPane scrollPane;
    @FXML
    public VBox container;
    @FXML
    public Text sousTitreLogo;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Text sousTitreLogo2;
    @FXML
    public Group groupeBarre;
    @FXML
    public ProgressBar barreProgression;
    @FXML
    public TextField entreePrenomInscrit;
    @FXML
    public TextField entreeNomInscrit;
    @FXML
    public DatePicker entreeDateNaissanceInscrit;
    @FXML
    public RadioButton boutonLivreur;
    @FXML
    public Button boutonReinitialiser;
    @FXML
    public Button boutonSuivant;
    @FXML
    public Button boutonFilArianeEmploye;
    @FXML
    public RadioButton boutonClient;
    @FXML
    public RadioButton boutonRestaurateur;
    @FXML
    public Text copyrightMention;
    @FXML
    public Rectangle barreEtat;
    @FXML
    public Button boutonFilConnexion;
    @FXML
    public Button boutonRetourConnexion;
    @FXML
    public Pane filArianeBarreEtat;
    @FXML
    public ImageView etape1;
    @FXML
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
        boutonSuivant.setOnAction(event -> {
            validationChamp(event);
        });


        boutonRetourConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));


        //[b] Fil d'Ariane : Retour à la page de connexion
        boutonFilConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));


        // [c] Raccourci mmémonique 2 : Ctrl Shift Q pour quitter l'application
barreMenu.sceneProperty().addListener((observable, oldScene, newScene) -> {
    if (newScene != null) {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.ALT_DOWN);
        Runnable runnable = Platform::exit;
        newScene.getAccelerators().put(keyCombination, runnable);
    }
});


        //[d] Initialiser les informations de l'utilisateur inscrit
        ModeleUtilisateur modeleUtilisateur = ModeleUtilisateur.getObjetUtilisateur();
        modeleUtilisateur.reinitialiserModeleUtilisateur();
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
        content.getChildren().addAll(new Label("DELICIVITE"), new Label("Version: 1.0"), new Label("Pour Windows 64 bit"), new Label("Copyright © INF1034"));
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


    /*=========================================================
     * [4] : Réinitialiser les champas
     * ========================================================*/
    @FXML
    private void reinitialiserChamp() {
        if (entreeNomInscrit.getText().isBlank() && entreePrenomInscrit.getText().isBlank() && entreeDateNaissanceInscrit.getValue() == null && groupeBouton.getSelectedToggle() == null) {
            entreePrenomInscrit.setStyle("-fx-border-color: #FD2528; -fx-border-radius: 5px; -fx-background-radius: 5px");
            entreeNomInscrit.setStyle("-fx-border-color: #FD2528; -fx-border-radius: 5px; -fx-background-radius: 5px");
            entreeDateNaissanceInscrit.setStyle("-fx-border-color:#FD2528; -fx-border-radius: 5px; -fx-background-radius: 5px");
            boutonRestaurateur.setStyle("-fx-border-color: #FD2528; -fx-border-radius: 5px; -fx-background-radius: 5px");
            boutonClient.setStyle("-fx-border-color: #FD2528; -fx-border-radius: 5px; -fx-background-radius: 5px");
            boutonLivreur.setStyle("-fx-border-color: #FD2528; -fx-border-radius: 5px; -fx-background-radius: 5px");
            ClasseUtilitaire.afficherPopUp("Erreur", "Aucune donnée à réinitialiser", "Aucun champ n'est rempli.", Alert.AlertType.WARNING);
        } else {
            // Sinon, réinitialiser les champs uniquement si l'utilisateur confirme son choix
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Confirmation d'annulation", "Êtes-vous sûr de vouloir annuler cette action ?")) {
                entreePrenomInscrit.clear();
                entreeNomInscrit.clear();
                entreeDateNaissanceInscrit.setValue(null);
                groupeBouton.selectToggle(null);
                entreePrenomInscrit.setStyle("-fx-border-color: #424242; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: #424242;");
                entreeNomInscrit.setStyle("-fx-border-color: #424242; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: #424242;");
                entreeDateNaissanceInscrit.setStyle("-fx-border-color: #424242; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: #424242;");
                boutonRestaurateur.setStyle("-fx-border-color: transparent; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                boutonClient.setStyle("-fx-border-color: transparent; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                boutonLivreur.setStyle("-fx-border-color: transparent; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            }
        }
    }



    /*===================================================
     * [5] : Valider les champs avant de passer à la page suivante
     *  et les sauvegarder pour la base de données
     * ==================================================*/

    @FXML
    private void validationChamp(ActionEvent event) {

        String prenom = entreePrenomInscrit.getText().trim();
        String nom = entreeNomInscrit.getText().trim();
        LocalDate dateNaissance = entreeDateNaissanceInscrit.getValue();

        // ---------------VALIDATION-------------------------

        if (!(prenom.matches("[a-zA-ZÀ-ÿ\\-' ]+"))) {
            ClasseUtilitaire.afficherPopUp("Erreur", "Prénom incorrect", "Le prénom doit être alphabétique.", Alert.AlertType.ERROR);
            entreePrenomInscrit.setStyle("-fx-border-color: #FD2528");
            return;
        } else if (!(nom.matches("[a-zA-ZÀ-ÿ\\-' ]+"))) {
            ClasseUtilitaire.afficherPopUp("Erreur", "Nom incorrect", "Le nom doit être alphabétique.", Alert.AlertType.ERROR);
            entreeNomInscrit.setStyle("-fx-border-color: #FD2528");
            return;
        }

        // Date de naissance inférieure ou égale à aujourd'hui
        else if (dateNaissance == null || dateNaissance.isAfter(LocalDate.now())) {
            ClasseUtilitaire.afficherPopUp("Erreur", "Date incorrecte", "Veuillez entrer une date valide.", Alert.AlertType.ERROR);
            entreeDateNaissanceInscrit.setStyle("-fx-border-color: #FD2528");
            return;
        }

        // Type d'utilisateur non nul
        else if (!boutonClient.isSelected() && !boutonLivreur.isSelected() && !boutonRestaurateur.isSelected()) {
            ClasseUtilitaire.afficherPopUp("Erreur", "Champ incomplet", "Veuillez sélectionner votre catégorie d'utilisateur.", Alert.AlertType.ERROR);
            boutonClient.setStyle("-fx-border-color: #FD2528");
            boutonLivreur.setStyle("-fx-border-color: #FD2528");
            boutonRestaurateur.setStyle("-fx-border-color: #FD2528");
            return;
        }

        //--------------------SAUVEGARDE TEMPORAIRE DES 3 DONNÉES-----------------------------------------

        else {
            //Obtenez le type d'utilisateur en fonction de l'index du bouton sélectionné dans le ToggleGroup
            TypeUtilisateur typeUtilisateurSelectionne = associerBoutonTypeUtilisateur();

            // Sauvegarde des 3 données et redirection vers la page correspondante au type choisi
            sauvegarderDonnees(prenom, nom, typeUtilisateurSelectionne, null, null);


            // Redirection vers la page suivante
            switch (Objects.requireNonNull(typeUtilisateurSelectionne)) {


                case client:
                    changerScene(event, "/ca/delicivite/inscription/inscriptionClient/VueInscriptionClient2.fxml", "Connexion", null);
                    break;
                case livreur:
                    changerScene(event, "/ca/delicivite/inscription/inscriptionLivreur/VueInscriptionLivreur2.fxml", "Connexion", null);
                    break;
                case proprietaire:
                    changerScene(event, "/ca/delicivite/inscription/inscriptionProprietaire/VueInscriptionProprietaire2.fxml", "Connexion", null);
                    break;
            }
        }
    }


    /*===================================
     * [6] Méthode utilitaire : associer un bouton à un type d'utilisateur
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
     * [7] Méthode utilitaire : Sauvegarde des données (nom, prénom et type d'utilisateur)
     * ===================================================================*/

    private void sauvegarderDonnees(String nom, String prenom, TypeUtilisateur typeUtilisateur, String motDePasse, String adresseCourriel) {

        //Récupérer le modèle d'objet utilisateur unique dans lequel on va sauvegarder les informations de l'utilisateur temporairement
        ModeleUtilisateur utilisateur = ModeleUtilisateur.getObjetUtilisateur();

        // Enregistrez les informations saisies dans l'objet utilisateur
        utilisateur.setPrenom(prenom);
        utilisateur.setNom(nom);
        utilisateur.setTypeUtilisateur(typeUtilisateur);
        utilisateur.setAdresseCourriel(null);
        utilisateur.setMotDePasse(null);
    }


}
