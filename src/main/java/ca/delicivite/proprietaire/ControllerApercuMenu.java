package ca.delicivite.proprietaire;

/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe Controller de l'interface propriétaire : gère l'apercu du menu d'un restaurant */

import ca.delicivite.modele.ModeleItemMenu.*;

import javafx.collections.transformation.FilteredList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerApercuMenu implements Initializable {

    // Champ pour la recherche d'items
    @FXML
    private TextField recherche;

    // Liste filtrée d'items
    private FilteredList<Item> listeFiltrer;

    // Liste des items du menu
    private ObservableList<Item> items;

    // Liste des items affichés
    @FXML
    private ListView<Item> listeItems;

    // Fenêtre pour l'ajout d'un nouvel item
    private Stage ajoutFenetre;

    /*=========================================================================
    [1] Constructeur
    * ========================================================================*/
    public ControllerApercuMenu() {
        items = DonneesItem.getItemsMenu();
    }

    /*=========================================================================
    [2] Initialize au démarrage de la scène
    * ========================================================================*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Ajout des items à la liste view
        listeItems.setItems(items);
        // Création de la liste filtrée basée sur la liste vies
        listeFiltrer = new FilteredList<>(listeItems.getItems());

        // Mise en place d'un écouteur sur le champ de recherche
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            listeFiltrer.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return item.getNomItem().toLowerCase().contains(newValue.toLowerCase());
            });
            listeItems.setItems(listeFiltrer);
        });


        //Écouteur pour voir si l'utilisateur a cliqué sur un item
        listeItems.setOnMouseClicked(event -> {
            Item selectedItem = listeItems.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Alert alerte = new Alert(Alert.AlertType.CONFIRMATION);
                alerte.setTitle("Action sur l'item");
                alerte.setHeaderText("Que souhaitez-vous faire avec l'item sélectionné?");
                alerte.setContentText("Choisissez votre action :");
                Stage stage = (Stage) listeItems.getScene().getWindow();
                stage.getIcons().add(new Image("/images/logo_fond_grise.png"));
                alerte.initOwner(stage);

                stage.setResizable(false);

                DialogPane dialogPane = alerte.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/ca/delicivite/proprietaire/proprietaire.css").toExternalForm());
                dialogPane.getStyleClass().add("fondPopUp");


                ButtonType boutonAjout = new ButtonType("Ajouter un item");
                ButtonType boutonSuppression = new ButtonType("Supprimer un item");
                ButtonType boutonAnnuler = new ButtonType("Annuler");

                alerte.getButtonTypes().setAll(boutonAjout, boutonSuppression, boutonAnnuler);

                alerte.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == boutonAjout) {
                        ajouterItem();
                    } else if (buttonType == boutonSuppression) {
                        supprimerItem();
                    } else {
                        alerte.close();
                    }
                });
            }
        });


    }


    /*=========================================================================
    [3] Méthode pour ajouter un item
    * ========================================================================*/
    @FXML
    private void ajouterItem() {
        // Vérification de la présence de la fenêtre d'ajout
        if (ajoutFenetre == null) {
            try {
                // Création de la fenêtre d'ajout
                ajoutFenetre = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ca/delicivite/proprietaire/VueAjoutItem.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(getClass().getResource("/ca/delicivite/proprietaire/proprietaire.css").toExternalForm());
                ajoutFenetre.setTitle("Ajouter un item");
                ajoutFenetre.getIcons().add(new Image("/images/logo_fond_grise.png"));
                ajoutFenetre.setScene(scene);
                ajoutFenetre.setOnHidden(event -> ajoutFenetre = null); // Réinitialiser la référence lorsque la fenêtre est fermée
                ajoutFenetre.show();

                // Fermeture de la fenêtre de suppression si ouverte
                if (fenetreSupprimer != null) {
                    fenetreSupprimer.close();
                    fenetreSupprimer = null;
                }

            } catch (IOException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Impossible de charger l'interface demandée. Contactez un administrateur.");
                alert.getButtonTypes().add(ButtonType.OK);
                alert.show();
            }
        }
    }

    // Fenêtre pour la suppression d'un item
    private Stage fenetreSupprimer;

    /*=========================================================================
    [4] Méthode pour supprimer un item
    * ========================================================================*/
    @FXML
    private void supprimerItem() {
        // Vérification de la présence de la fenêtre de suppression
        if (fenetreSupprimer == null) {
            try {
                // Création de la fenêtre de suppression
                fenetreSupprimer = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ca/delicivite/proprietaire/VueSupprimerItem.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(getClass().getResource("/ca/delicivite/proprietaire/proprietaire.css").toExternalForm());
                fenetreSupprimer.setTitle("Supprimer un item");
                fenetreSupprimer.getIcons().add(new Image("/images/logo_fond_grise.png"));
                fenetreSupprimer.setScene(scene);
                fenetreSupprimer.setOnHidden(event -> fenetreSupprimer = null); // Réinitialiser la référence lorsque la fenêtre est fermée
                fenetreSupprimer.show();

                // Fermeture de la fenêtre d'ajout si elle est ouverte
                if (ajoutFenetre != null) {
                    ajoutFenetre.close();
                    ajoutFenetre = null;
                }

            } catch (IOException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Impossible de charger l'interface demandée. Contactez un administrateur.");
                alert.getButtonTypes().add(ButtonType.OK);
                alert.show();
            }
        }
    }


}

