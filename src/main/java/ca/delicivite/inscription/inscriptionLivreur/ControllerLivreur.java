package ca.delicivite.inscription.inscriptionLivreur;


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

public class ControllerLivreur implements Initializable {
   @FXML public BorderPane root;
    @FXML   public MenuBar barreMenu;
    @FXML  public MenuItem stAnnulerAction;
    @FXML   public Menu titreMenuApplication;
    @FXML   public MenuItem stRefaireAction;
    @FXML   public MenuItem stQuitterApp;
    @FXML  public Menu titreMenuApparence;
    @FXML  public MenuItem modeSombreMenuItem;
    @FXML  public MenuItem modeClairMenuItem;
    @FXML  public Menu titreMenuVue;
    @FXML  public Menu taillePoliceMenu;
    @FXML   public MenuItem petiteTailleMenuItem;
    @FXML  public MenuItem moyenneTailleMenuItem;
    @FXML    public MenuItem grandeTailleMenuItem;
    @FXML   public Menu menuAide;
    @FXML   public MenuItem stAPropos;
    @FXML   public MenuItem stGuideUtilisation;
    @FXML   public ScrollPane scrollPane;
    @FXML   public VBox container;
    @FXML   public AnchorPane anchorPane;
    @FXML   public Text sousTitreLogo;
    @FXML   public Text sousTitreLogo2;
    @FXML   public Group groupeBarre;
    @FXML  public ProgressBar barreProgression;
    @FXML   public Button boutonReinitialiser;
    @FXML   public Button boutonRetourPagePrecedente;
    @FXML    public Button boutonSuivant;
    @FXML    public Rectangle barreEtat;
    @FXML   public Pane filArianeBarreEtat;
    @FXML
    public Button buttonFilConnexion;
    @FXML   public Button boutonFilArianeEmploye;
    @FXML   public TextField entreeCellulaireInscritLivreur;
    @FXML   public Text copyrightMention;
    @FXML   public Button boutonRetourConnexion;
    @FXML
    public GridPane tableauDisponibilites;

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
        boutonRetourPagePrecedente.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/inscription/VueInscriptionGenerale1.fxml", "Connexion", null));

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

        if (entreeCellulaireInscritLivreur.getText().isBlank() && !(auMoinsUneCaseCochee())
        ) {
            entreeCellulaireInscritLivreur.setStyle("-fx-border-color: #FD2528");
            ClasseUtilitaire.afficherPopUp("Erreur", "Aucune donnée à réinitialiser", "Aucun champ n'est rempli.", Alert.AlertType.WARNING);
        } else {
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Confirmation d'annulation", "Êtes-vous sûr de vouloir annuler cette action ?")) {
                entreeCellulaireInscritLivreur.clear();


                // Parcours des enfants du GridPane
                for (Node node : tableauDisponibilites.getChildren()) {
                    if (node instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) node;
                        if (checkBox.isSelected()) {
                            checkBox.setSelected(false);
                        }
                    }
                }


                entreeCellulaireInscritLivreur.setStyle("-fx-border-color: #424242");
                tableauDisponibilites.setStyle("-fx-border-color: #424242");
            }

        }
    }


    private void validationChamp() {
        String cellulaireLivreur = entreeCellulaireInscritLivreur.getText().trim();

        if (!cellulaireLivreur.matches("[0-9\\-]+") || cellulaireLivreur.length() > 10) {
            afficherPopUp("Erreur", "Numéro de téléphone incorrect", "Veuillez entrer un numéro de téléphone valide (maximum 10 chiffres).", Alert.AlertType.ERROR);
            entreeCellulaireInscritLivreur.setStyle("-fx-border-color: #FD2528");
            return;
        }

        if (!(auMoinsUneCaseCochee())) {
            afficherPopUp("Champ incomplet", "Disponibilités non sélectionnées", "Veuillez sélectionner au moins une disponibilité.", Alert.AlertType.ERROR);
            tableauDisponibilites.setStyle("-fx-border-color: #FD2528");
        }

        //Si tout est valide :
        boutonSuivant.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/inscription/VueIdentifiantP3.fxml", "Connexion", null));
    }


    /*====================================
     * Méthode utilitaire pour vérifier si le livreur a bien coché au moins une case
     * ==================================*/

    private boolean auMoinsUneCaseCochee() {
        // Parcours des enfants du GridPane pour vérifier si au moins une case est cochée
        boolean auMoinsUneCaseCochee = false;
        for (Node node : tableauDisponibilites.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {
                    auMoinsUneCaseCochee = true;
                    break;
                }
            }
        }
        return auMoinsUneCaseCochee;
    }


}
