package ca.delicivite.proprietaire;

import ca.delicivite.modele.ModeleItemMenu.Item;
import ca.delicivite.modele.ModeleItemMenu.DonneesItem;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAjoutItem implements Initializable {

    // Champ pour le nom de l'item
    @FXML
    private TextField nomItem;
    // Choix de groupe pour l'item
    @FXML
    private ChoiceBox<String> groupe;
    // Champ pour la description de l'item
    @FXML
    private TextField description;
    // Liste des items
    private ObservableList<Item> items;
    // Liste des groupes
    private ObservableList<String> listeGroupes;


    /*=========================================================================
   [1] Constructeur
   * ========================================================================*/
    public ControllerAjoutItem() {
        items = DonneesItem.getItemsMenu();
        listeGroupes = DonneesItem.getGroupes();
    }

    /*=========================================================================
   [2] Initialize au démarrage de la scène
   * ========================================================================*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupe.setItems(listeGroupes);
    }

   /* @FXML
    public void initialize() {
        groupe.setItems(listeGroupes);
    }*/

    /*=========================================================================
    [3] Méthode pour ajouter un nouvel item
    * ========================================================================*/
    @FXML
    private void ajouter(ActionEvent event) {

        // Récupération des valeurs des champs
        String nom = nomItem.getText().trim();
        String groupeSelectionne = groupe.getValue(); // Pas besoin de cast car ChoiceBox est désormais paramétré avec String
        String descriptionTexte = description.getText().trim();

        // Vérification que les champs soient belle et bien remplis
        if (!nom.isEmpty() && !groupeSelectionne.equals("Choisissez un groupe") && !descriptionTexte.isEmpty()) {
            // Ajout du nouvel item à la liste
            items.add(new Item(nom, groupeSelectionne, descriptionTexte));
            // Fermeture de la fenêtre
            fermer((Node) event.getSource());
        }
    }

    /*=========================================================================
   [4] Méthode pour annuler l'ajout d'un nouvel item
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
        Stage stage = (Stage) element.getScene().getWindow();
        stage.close();
    }
}


