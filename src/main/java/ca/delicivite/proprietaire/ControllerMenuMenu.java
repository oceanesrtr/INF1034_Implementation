package ca.delicivite.proprietaire;

/*Classe controller : affiche les items des menus d'un restaurant dans une section de la page Menu*/
import ca.delicivite.modele.ModeleItemMenu.*;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ControllerMenuMenu implements Initializable {
    // Liste des items
    private ObservableList<Item> items;

    // Vue de la liste des items
    @FXML
    private ListView<Item> listeItems;

    /*=========================================================================
    [1] Constructeur
    * ========================================================================*/
    public ControllerMenuMenu() {
        // Récupération de la liste des items
        items = DonneesItem.getItemsMenu();
        // Tri des items par ordre alphabétique du premier caractère du nom du groupe
        items.sort(Comparator.comparing(item -> item.getGroupe().substring(0, 1).toUpperCase()));
    }

    /*=========================================================================
    [2] Initialize au démarrage de la scène
    * ========================================================================*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configuration de la liste des items
        listeItems.setItems(items);
        // Ajout de la feuille de style pour la liste des items
        listeItems.getStylesheets().add("/ca/delicivite/proprietaire/proprietaire.css");
        // Définition de la cellule personnalisée pour la liste des items
        listeItems.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> itemListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Item item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // Affichage du nom et de la description de l'item
                            setText("Nom de l'item: " + item.getNomItem() + ": \n" +
                                    "Description:" + "\n" + item.getDescription());
                        }
                    }
                };
            }
        });
    }

   /* public void initialize() {
        // Configuration de la liste des items
        listeItems.setItems(items);
        // Ajout de la feuille de style pour la liste des items
        listeItems.getStylesheets().add("/del/delicivite/Style/proprietaire.css");
        // Définition de la cellule personnalisée pour la liste des items
        listeItems.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> itemListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Item item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // Affichage du nom et de la description de l'item
                            setText("Nom de l'item: " + item.getNomItem() + ": \n" +
                                    "Description:" + "\n" + item.getDescription());
                        }
                    }
                };
            }
        });
    }*/


}
