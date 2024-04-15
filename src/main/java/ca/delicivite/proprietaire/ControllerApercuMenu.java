package ca.delicivite.proprietaire;

import ca.delicivite.modele.ModeleItemMenu.*;

import javafx.collections.transformation.FilteredList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
    }

    /*public void initialize() {
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

    }*/


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
                ajoutFenetre.setAlwaysOnTop(true);
                ajoutFenetre.setOnCloseRequest(event -> ajoutFenetre = null); // Réinitialiser la référence lorsque la fenêtre est fermée
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
        } else {
            ajoutFenetre.toFront();
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
                fenetreSupprimer.setAlwaysOnTop(true);
                fenetreSupprimer.setOnCloseRequest(event -> fenetreSupprimer = null); // Réinitialiser la référence lorsque la fenêtre est fermée

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
        } else {
            fenetreSupprimer.toFront();
        }
    }


}

