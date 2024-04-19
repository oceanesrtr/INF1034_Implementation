package ca.delicivite.inscription;


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
import java.util.ResourceBundle;

import static ca.delicivite.outils.ClasseUtilitaire.changerScene;

public class ControllerConfirmationInscription implements Initializable {
    @FXML  public BorderPane root;
    @FXML  public Menu titreMenuApplication;
    @FXML   public MenuItem stAnnulerAction;
    @FXML   public MenuItem stRefaireAction;
    @FXML  public Menu titreMenuApparence;
    @FXML public MenuItem modeSombreMenuItem;
    @FXML   public MenuItem modeClairMenuItem;
    @FXML   public Menu titreMenuVue;
    @FXML  public Menu taillePoliceMenu;
    @FXML    public MenuItem petiteTailleMenuItem;
    @FXML  public MenuItem moyenneTailleMenuItem;
    @FXML  public MenuItem grandeTailleMenuItem;
    @FXML   public Menu menuInformations;
    @FXML  public ScrollPane scrollPane;
    @FXML  public VBox container;
    @FXML  public Text sousTitreLogo;
    @FXML    public AnchorPane anchorPane;
    @FXML  public Text sousTitreLogo2;
    @FXML  public Group groupeBarre;
    @FXML  public ProgressBar barreProgression;
    @FXML  public TextField entreePrenomInscrit;
    @FXML   public TextField entreeNomInscrit;
    @FXML   public DatePicker entreeDateNaissanceInscrit;
    @FXML   public RadioButton boutonLivreur;
    @FXML   public Button boutonReinitialiser;
    @FXML  public Button boutonSuivant;
    @FXML  public Button boutonFilArianeEmploye;
    @FXML  public RadioButton boutonClient;
    @FXML   public RadioButton boutonRestaurateur;
    @FXML   public Text copyrightMention;
    @FXML   public Rectangle barreEtat;
    @FXML
    public Button buttonFilConnexion;
    @FXML   public Button boutonRetourConnexion;
    @FXML   public Pane filArianeBarreEtat;
    @FXML   public ImageView etape1;
    @FXML   public Text titreInscription;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//[a] Associer fonctionnalités aux options de la barre de menu
        stQuitterApp.setOnAction(event -> Platform.exit());
        stGuideUtilisation.setOnAction(this::ouvrirGuideUtilisation);

        boutonRetourConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));


        //[b] Fil d'Ariane : Retour à la page de connexion
        buttonFilConnexion.setOnAction(actionEvent -> changerScene(actionEvent, "/ca/delicivite/VueConnexionTailleMoyenne.fxml", "Connexion", null));


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


}
