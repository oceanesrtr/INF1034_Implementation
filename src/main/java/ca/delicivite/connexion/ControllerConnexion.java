package ca.delicivite.connexion;

/*Classe controller : s'occupe des controller de la page Connexion */

import ca.delicivite.modele.ModeleUtilisateur;
import ca.delicivite.outils.ClasseUtilitaire;
import ca.delicivite.patronObservateur.ModeleEntree;
import ca.delicivite.patronObservateur.ObservateurEntree;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.net.URL;
import java.util.ResourceBundle;

import static ca.delicivite.outils.ClasseUtilitaire.*;

public class ControllerConnexion implements Initializable {

    @FXML
    public BorderPane root;
    @FXML
    public Menu titreMenuApplication;
    @FXML
    public Button boutonAnnulerConnexion;
    @FXML
    public Menu titreMenuApparence;
    @FXML
    public MenuItem modeSombreMenuItem;
    @FXML
    public MenuItem modeClairMenuItem;
    @FXML
    public Menu titreMenuVue;
    @FXML
    public Menu titreMenuZoom;
    @FXML
    public MenuItem stRetrecir;
    @FXML
    public MenuItem stResetZoom;
    @FXML
    public Pane filArianeBarreEtat;
    @FXML
    public Rectangle barreEtat;
    @FXML
    public Text copyrightMention;
    @FXML
    public Menu menuAide;
    @FXML
    public VBox container;
    @FXML
    public Text sousTitreLogo;
    @FXML
    public Text titreConnexion;
    @FXML
    public Text texteGeneral2;
    @FXML
    public Text texteGeneral1;
    @FXML
    private Menu taillePoliceMenu;
    @FXML
    private MenuItem petiteTailleMenuItem;
    @FXML
    private MenuItem moyenneTailleMenuItem;
    @FXML
    private MenuItem grandeTailleMenuItem;
    @FXML
    public MenuItem stAgrandir;
    @FXML
    private Button filConnexion;
    @FXML
    public Text sousTitreLogo2;
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
    private Button boutonInscription;
    @FXML
    private Button boutonMotDePasseOublie;
    @FXML
    private Button boutonAccueil;

    @FXML
    private TextField entreeCourriel;
    @FXML
    private PasswordField motDePasseCache;
    @FXML
    private TextField motDePasseRevele;
    @FXML
    private Button boutonSeConnecter;
    @FXML
    private CheckBox boutonRevelerMdp;
    @FXML
    private ImageView oeilOuvert;
    @FXML
    private ImageView oeilFerme;
    @FXML
    private TextField champActif;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private String entreeCourrielPrecedente = "";
    @FXML
    private String entreeMotDePassePrecedente = "";

    private ModeleEntree modeleEntreeCourriel = new ModeleEntree();

    private ModeleEntree modeleEntreeMotDePasse = new ModeleEntree();




    /*=========================================================================
    [1] Initialize au démarrage de la scène
    * ========================================================================*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //[a] Redirection vers les pages adéquates selon les boutons cliqués
        boutonSeConnecter.setOnAction(actionEvent -> ModeleUtilisateur.clientSeConnecte(actionEvent, entreeCourriel.getText().trim(), motDePasseCache.getText().trim()));

        //TODO : Créer les pages non faîtes inscription et accueil pour non-connecté
        boutonInscription.setOnAction(actionEvent -> changerScene(actionEvent, "VueInscriptionP1.fxml", "Inscription", null));
        boutonAccueil.setOnAction(actionEvent -> changerScene(actionEvent, "VueClientNonConnecteAccueil.fxml", "ControllerAccueil", null));

        boutonMotDePasseOublie.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/motDePasseOublie/VueMDPOublieMoyenne.fxml", "Mot de passe oublié", null));

        //[b] Associer fonctionnalités aux options de la barre de menu
        stQuitterApp.setOnAction(event -> Platform.exit());
        stGuideUtilisation.setOnAction(this::ouvrirGuideUtilisation);
        stAnnulerAction.setOnAction(this::annulerAction);
        stRefaireAction.setOnAction(this::refaireAction);

        //[c] Fil d'Ariane : Retour à la page de connexion
        filConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));

        // [d] Création d'observateurs pour les champs de texte (courriel et mot de passe)
        entreeCourriel.setOnMouseClicked(event -> champActif = entreeCourriel);
        motDePasseCache.setOnMouseClicked(event -> champActif = motDePasseCache);

        ObservateurEntree observateurEntreeCourriel = new ObservateurEntree(modeleEntreeCourriel);
        ObservateurEntree observateurEntreeMotDePasse = new ObservateurEntree(modeleEntreeMotDePasse);

        // Écouteurs
        entreeCourriel.textProperty().addListener((observable, oldValue, newValue) -> modeleEntreeCourriel.setTexte(newValue));
        motDePasseCache.textProperty().addListener((observable, oldValue, newValue) -> modeleEntreeMotDePasse.setTexte(newValue));

        modeleEntreeCourriel.ajouterObservateur(observateurEntreeCourriel);
        modeleEntreeMotDePasse.ajouterObservateur(observateurEntreeMotDePasse);

        // [e] Raccourci mnémonique 1 : Entrée pour se connecter
        motDePasseCache.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                ModeleUtilisateur.clientSeConnecte(new ActionEvent(event.getSource(), event.getTarget()), entreeCourriel.getText().trim(), motDePasseCache.getText().trim());
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


    /*=========================================================================
    [2] Annuler la connexion et valider si chaque champ de texte est rempli
    * ========================================================================*/
    @FXML
    protected void annulerConnexion() {
        if (entreeCourriel.getText().isBlank() || motDePasseCache.getText().isBlank()) {
            afficherPopUp("Erreur", "Erreur lors de l'annulation de la connexion", "Veuillez remplir au moins un champ avant d'annuler la connexion.", Alert.AlertType.WARNING);
            // Mettre en rouge la bordure du champ vide
            if (entreeCourriel.getText().isBlank()) entreeCourriel.setStyle("-fx-border-color: #FD2528;");
            if (motDePasseCache.getText().isBlank()) motDePasseCache.setStyle("-fx-border-color: #FD2528;");

        } else {
            entreeCourriel.setStyle("-fx-border-color: #424242;");
            motDePasseCache.setStyle("-fx-border-color: #424242;");
            //Si l'utilisateur confirme son choix :
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Confirmation d'annulation", "Êtes-vous sûrs de vouloir annuler cette action ?")) {
                entreeCourriel.clear();
                motDePasseCache.clear();
            }
        }
    }

    /*=========================================================
     * [3] Cache et révèle le mot de passe
     * =======================================================*/
    @FXML
    private void revelerMotDePasse() {
        if (oeilOuvert.getOpacity() == 1 && oeilFerme.getOpacity() == 0) {
            if (boutonRevelerMdp.isSelected()) {
                motDePasseRevele.setText(motDePasseCache.getText());
                motDePasseRevele.setOpacity(1);
                motDePasseCache.setOpacity(0);
                oeilOuvert.setOpacity(0);
                oeilFerme.setOpacity(1);
            }
        } else if (oeilOuvert.getOpacity() == 0 && oeilFerme.getOpacity() == 1) {
            motDePasseRevele.setOpacity(0);
            motDePasseCache.setOpacity(1);
            oeilOuvert.setOpacity(1);
            oeilFerme.setOpacity(0);
        }
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

    /*=======================================================
     * Méthodes pour le sous-menu Application
     * [6] : Annule une entrée
     * =======================================================*/
    @FXML
    private void annulerAction(ActionEvent event) {
        // Avant d'annuler, sauvegarder l'entrée et effacer le champ après
        if (champActif == entreeCourriel) {
            entreeCourrielPrecedente = entreeCourriel.getText();
            entreeCourriel.clear();
        } else if (champActif == motDePasseCache) {
            entreeMotDePassePrecedente = motDePasseCache.getText();
            motDePasseCache.clear();
        }
    }



    /*==============================================
     * [7] : Refait une entrée
     * ============================================*/
    @FXML
    private void refaireAction(ActionEvent event) {
        // Remettre le texte précédent dans le champ
        if (champActif == entreeCourriel) entreeCourriel.setText(entreeCourrielPrecedente);
        else if (champActif == motDePasseCache) motDePasseCache.setText(entreeMotDePassePrecedente);
    }

    /*===============================================
     * Méthodes pour changer la taille de la police
     * =============================================*/

    //[8] : Petite
    @FXML
    private void petitePolice() {
        Stage stage = (Stage) root.getScene().getWindow();
        ClasseUtilitaire.changerTaillePolice("/ca/delicivite/connexion/VueConnexionTaillePetite.fxml", stage);
    }

    //[9] : Grande
    @FXML
    private void grandePolice() {
        Stage stage = (Stage) root.getScene().getWindow();
        ClasseUtilitaire.changerTaillePolice("/ca/delicivite/connexion/VueConnexionTailleGrande.fxml", stage);
    }

    //[10] : Moyenne
    @FXML
    private void moyennePolice() {
        Stage stage = (Stage) root.getScene().getWindow();
        ClasseUtilitaire.changerTaillePolice("/ca/delicivite/VueConnexionTailleMoyenne.fxml", stage);
    }
}