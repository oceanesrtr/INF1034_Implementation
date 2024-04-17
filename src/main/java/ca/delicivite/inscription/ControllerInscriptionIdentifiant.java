package ca.delicivite.inscription;


import ca.delicivite.modele.ModeleItemMenu.TypeUtilisateur;
import ca.delicivite.modele.ModeleUtilisateur;
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

public class ControllerInscriptionIdentifiant implements Initializable {
    @FXML public BorderPane root;
    @FXML  public MenuBar barreMenu;
    @FXML  public MenuItem stAnnulerAction;
    @FXML   public Menu titreMenuApplication;
    @FXML  public MenuItem stRefaireAction;
    @FXML  public MenuItem stQuitterApp;
    @FXML   public Menu titreMenuApparence;
    @FXML   public MenuItem modeSombreMenuItem;
    @FXML  public MenuItem modeClairMenuItem;
    @FXML  public Menu titreMenuVue;
    @FXML  public Menu taillePoliceMenu;
    @FXML  public MenuItem petiteTailleMenuItem;
    @FXML   public MenuItem moyenneTailleMenuItem;
    @FXML  public MenuItem grandeTailleMenuItem;
    @FXML   public Menu menuAide;
    @FXML   public MenuItem stAPropos;
    @FXML  public MenuItem stGuideUtilisation;
    @FXML   public ScrollPane scrollPane;
    @FXML  public VBox container;
    @FXML  public AnchorPane anchorPane;
    @FXML  public Text sousTitreLogo;
    @FXML   public Text sousTitreLogo2;
    @FXML   public Group groupeBarre;
    @FXML   public ProgressBar barreProgression;
    @FXML   public Button boutonReinitialiser;
    @FXML
    public Button boutonSuivant;
    @FXML  public Text copyrightMention;
    @FXML  public Rectangle barreEtat;
    @FXML  public Pane filArianeBarreEtat;
    @FXML
    public Button buttonFilConnexion;
    @FXML  public Button boutonFilArianeEmploye;


    public Button boutonPagePrecedente;
    public TextField confirmerMotDePasse;
    public TextField entreeCourrielIdentifiant;
    public TextField entreeMotDePasse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //[a] Associer fonctionnalités aux options de la barre de menu
        stQuitterApp.setOnAction(event -> Platform.exit());
        stGuideUtilisation.setOnAction(this::ouvrirGuideUtilisation);
        boutonSuivant.setOnAction(event -> {
            validationChamp();
        });

        //[b] Fil d'Ariane : Retour à la page de connexion
        buttonFilConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));
        boutonPagePrecedente.setOnAction(actionEvent -> {
            retourPage(actionEvent);
        });
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

        if (entreeCourrielIdentifiant.getText().isBlank() &&
                entreeMotDePasse.getText().isBlank() &&
                confirmerMotDePasse.getText().isBlank()
        ) {
            entreeMotDePasse.setStyle("-fx-border-color: #FD2528");
            entreeCourrielIdentifiant.setStyle("-fx-border-color: #FD2528");
            confirmerMotDePasse.setStyle("-fx-border-color: #FD2528");

            ClasseUtilitaire.afficherPopUp("Erreur", "Aucune donnée à réinitialiser", "Aucun champ n'est rempli.", Alert.AlertType.WARNING);
        } else {
            // Sinon, réinitialiser les champs uniquement si l'utilisateur confirme son choix
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Confirmation d'annulation", "Êtes-vous sûr de vouloir annuler cette action ?")) {
                entreeCourrielIdentifiant.clear();
                entreeMotDePasse.clear();
                confirmerMotDePasse.clear();

                entreeCourrielIdentifiant.setStyle("-fx-border-color: #424242");
                entreeMotDePasse.setStyle("-fx-border-color: #424242");
                confirmerMotDePasse.setStyle("-fx-border-color: #424242");
            }
        }
    }

    /*===================================================
     * [] : Valider les champs avant de passer à la page suivante
     *  et les sauvegarder pour la base de données
     * ==================================================*/

    @FXML
    private void validationChamp() {

        String identifiant = entreeCourrielIdentifiant.getText().trim();
        String motDePasse = entreeMotDePasse.getText();
        String confirmerMotDePasseText = confirmerMotDePasse.getText();


        // Validation de l'identifiant (adresse e-mail)
        if (!(identifiant.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))) {
            afficherPopUp("Erreur", "Identifiant incorrect", "Veuillez entrer une adresse e-mail valide.", Alert.AlertType.ERROR);
            entreeCourrielIdentifiant.setStyle("-fx-border-color: #FD2528");
            return;
        } else  // Vérification si l'e-mail existe déjà
            if (ModeleUtilisateur.verifierEmail(identifiant)) {
                afficherPopUp("Erreur", "Adresse e-mail déjà utilisée", "L'adresse e-mail est déjà associée à un compte.", Alert.AlertType.ERROR);
                entreeCourrielIdentifiant.setStyle("-fx-border-color: #FD2528");
                return;
            }

        // Validation du mot de passe
        if (motDePasse.isEmpty()) {
            afficherPopUp("Erreur", "Mot de passe incorrect", "Veuillez entrer un mot de passe.", Alert.AlertType.ERROR);
            entreeMotDePasse.setStyle("-fx-border-color: #FD2528");
            return;
        }

        // Validation de la confirmation du mot de passe
        if (!motDePasse.equals(confirmerMotDePasseText)) {
            afficherPopUp("Erreur", "Confirmation de mot de passe incorrecte", "La confirmation du mot de passe ne correspond pas.", Alert.AlertType.ERROR);
            confirmerMotDePasse.setStyle("-fx-border-color: #FD2528");
            return;
        }

        // Si tout est valide, continuer avec l'enregistrement
        ModeleUtilisateur modeleUtilisateur = ModeleUtilisateur.getObjetUtilisateur();
        modeleUtilisateur.setAdresseCourriel(identifiant);
        modeleUtilisateur.setMotDePasse(motDePasse);

        // Enregistrer les données dans la base de données via la méthode inscription
        ModeleUtilisateur.inscription(modeleUtilisateur.getNom(),modeleUtilisateur.getPrenom(), identifiant, motDePasse, modeleUtilisateur.getTypeUtilisateur().toString() );
        modeleUtilisateur.reinitialiserModeleUtilisateur();

        boutonSuivant.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/inscription/VueInscriptionFinP4.fxml", "Connexion", null));
    }


    /*==========================================
     * Retour à la page précédente selon le type d'utilisateur
     * =======================================*/
    @FXML
    private void retourPage(ActionEvent actionEvent) {

        // Récupérer le type d'utilisateur à partir du modèle utilisateur
        ModeleUtilisateur modeleUtilisateur = ModeleUtilisateur.getObjetUtilisateur();
        TypeUtilisateur typeUtilisateur = modeleUtilisateur.getTypeUtilisateur();

        // Redirection en fonction du type d'utilisateur
        switch (typeUtilisateur) {
            case client:
                changerScene(actionEvent, "/ca/delicivite/inscription/inscriptionClient/VueInscriptionClient2.fxml", "Connexion", null);
                break;
            case livreur:
                changerScene(actionEvent, "/ca/delicivite/inscription/inscriptionLivreur/VueInscriptionLivreur2.fxml", "Connexion", null);
                break;
            case proprietaire:
                changerScene(actionEvent, "/ca/delicivite/inscription/inscriptionProprietaire/VueInscriptionProprietaire2.fxml", "Connexion", null);
                break;
            default:
                changerScene(actionEvent, "/ca/delicivite/inscription/VueInscriptionGenerale1.fxml", "Connexion", null);
                break;
        }
    }

}


