package ca.delicivite.proprietaire;

import ca.delicivite.modele.ModeleItemMenu.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSupprimerItem implements Initializable {

    // Choix de l'élément à supprimer
    @FXML
    private ChoiceBox<Item> item;

    // Liste des items du menu
    private ObservableList<Item> items;

    // Liste des items affichée
    @FXML
    private ListView<Item> listeItems;

    /*=========================================================================
    [1] Constructeur de la classe
    * ========================================================================*/
    public ControllerSupprimerItem() {
        // Récupération de la liste des items du menu
        items = DonneesItem.getItemsMenu();
    }

    /*=========================================================================
    [2] Méthode d'initialisation
    * ========================================================================*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        item.setItems(items);
    }

    /*@FXML
    public void initialize() {
        // Attribution de la liste des items au choix
        item.setItems(items);
    }*/

    /*=========================================================================
    [3] Méthode pour supprimer un item
    * ========================================================================*/
    @FXML
    private void supprimerItem(ActionEvent event) {
        // Récupération de l'item sélectionné
        Item selectedItem = item.getSelectionModel().getSelectedItem();
        // Vérification si un item est sélectionné
        if (selectedItem != null) {
            // Suppression de l'item de la liste
            items.remove(selectedItem);
            // Mise à jour de la liste affichée
            item.setItems(items);
            // Fermeture de la fenêtre
            fermer((Node) event.getSource());
        }
    }
    /*=========================================================================
    [4] Méthode pour annuler l'opération
    * ========================================================================*/
    @FXML
    private void annuler(ActionEvent event) {
        // Fermeture de la fenêtre
        fermer((Node) event.getSource());
    }

    /*=========================================================================
    [5] Méthode pour fermer la fenêtre
    * ========================================================================*/
    private void fermer(Node element) {
        // Récupération de la fenêtre parente et fermeture
        Stage stage = (Stage) element.getScene().getWindow();
        stage.close();
    }
}
