package ca.delicivite.inscription.inscriptionLivreur;

import ca.delicivite.inscription.historique.HistoriqueNavigation;
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

import static ca.delicivite.outils.ClasseUtilitaire.changerScene;

public class ControllerLivreur implements Initializable {
    public BorderPane root;
    public MenuBar barreMenu;
    public MenuItem stAnnulerAction;
    public Menu titreMenuApplication;
    public MenuItem stRefaireAction;
    public MenuItem stQuitterApp;
    public Menu titreMenuApparence;
    public MenuItem modeSombreMenuItem;
    public MenuItem modeClairMenuItem;
    public Menu titreMenuVue;
    public Menu taillePoliceMenu;
    public MenuItem petiteTailleMenuItem;
    public MenuItem moyenneTailleMenuItem;
    public MenuItem grandeTailleMenuItem;
    public Menu menuAide;
    public MenuItem stAPropos;
    public MenuItem stGuideUtilisation;
    public ScrollPane scrollPane;
    public VBox container;
    public AnchorPane anchorPane;
    public Text sousTitreLogo;
    public Text sousTitreLogo2;
    public Group groupeBarre;
    public ProgressBar barreProgression;
    public Button boutonReinitialiser;
    public Button boutonRetourPagePrecedente;
    public Button boutonSuivant;
    public Rectangle barreEtat;
    public Pane filArianeBarreEtat;
    @FXML
    public Button buttonFilConnexion;
    public Button boutonFilArianeEmploye;
    public TextField entreeCellulaireInscritLivreur;
    public Text copyrightMention;
    public Button boutonRetourConnexion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //[a] Associer fonctionnalités aux options de la barre de menu
        stQuitterApp.setOnAction(event -> Platform.exit());
        stGuideUtilisation.setOnAction(this::ouvrirGuideUtilisation);


        //[b] Fil d'Ariane : Retour à la page de connexion
        buttonFilConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));
        boutonRetourPagePrecedente.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));

        //TODO : utiliser une pile pour garder les informations de la dernière page ??

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
        if (entreeCellulaireInscritLivreur.getText().isBlank()
        ) {
            entreeCellulaireInscritLivreur.setStyle("-fx-border-color: #FD2528");
            ClasseUtilitaire.afficherPopUp("Erreur", "Aucune donnée à réinitialiser", "Aucun champ n'est rempli.", Alert.AlertType.WARNING);
        } else {
            // Sinon, réinitialiser les champs uniquement si l'utilisateur confirme son choix
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Confirmation d'annulation", "Êtes-vous sûr de vouloir annuler cette action ?")) {
                entreeCellulaireInscritLivreur.clear();
                entreeCellulaireInscritLivreur.setStyle("-fx-border-color: #424242");
            }
        }
    }
}
