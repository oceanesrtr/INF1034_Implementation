package ca.delicivite.proprietaire;

/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe Controller de l'interface propriétaire : gère la suppresion d'item du menu
d'un restaurant*/


import ca.delicivite.modele.ModeleItemMenu.*;
import ca.delicivite.outils.ClasseUtilitaire;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSupprimerItem implements Initializable {


    public Button boutonSupprimer;
    @FXML
    private ChoiceBox<Item> item;

    private ObservableList<Item> items;

    @FXML
    private ListView<Item> listeItems;

    /*=========================================================================
    [1] Constructeur de la classe
    * ========================================================================*/
    public ControllerSupprimerItem() {
        items = DonneesItem.getItemsMenu();
    }

    /*=========================================================================
    [2] Méthode d'initialisation
    * ========================================================================*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        item.setItems(items);

    }


    /*=========================================================================
    [3] Méthode pour supprimer un item
    * ========================================================================*/
    @FXML
    private void supprimerItem(ActionEvent event) {
        Item itemSelectionne = item.getSelectionModel().getSelectedItem();

        // Si un item est sélectionné
        if (!item.getValue().equals("Choisissez un item à supprimer") || items.contains(itemSelectionne)) {
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Suppression de l'item dans le menu", "Voulez-vous vraiment supprimer cet item ?")) {
                items.remove(itemSelectionne);
                ClasseUtilitaire.afficherPopUp("Succès", "Opération réussie", "L'item' a été supprimé avec succès.", Alert.AlertType.INFORMATION);
                item.setItems(items);
                item.setStyle(" -fx-border-radius: 15px;-fx-background-radius: 15px; -fx-border-color: #424242");
                fermer((Node) event.getSource());
            }
        } else if (!items.contains(itemSelectionne)){
            ClasseUtilitaire.afficherPopUp("Erreur", "Aucun item sélectionné", "Veuillez choisir un item à supprimer", Alert.AlertType.ERROR);
            item.setStyle(" -fx-border-radius: 15px;-fx-background-radius: 15px; -fx-border-color: #F44322");
        }

    }

    /*=========================================================================
    [4] Méthode pour annuler l'opération
    * ========================================================================*/
    @FXML
    private void annuler(ActionEvent event) {
        fermer((Node) event.getSource());
    }

    /*=========================================================================
    [5] Méthode pour fermer la fenêtre
    * ========================================================================*/
    private void fermer(Node element) {
        Stage stage = (Stage) element.getScene().getWindow();
        stage.close();
    }
}
