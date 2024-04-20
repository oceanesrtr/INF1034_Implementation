package ca.delicivite.proprietaire;

/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe Controller de l'interface propriétaire : gère l'ajout d'item dans un menu d'un restaurant*/

import ca.delicivite.modele.ModeleItemMenu.Item;
import ca.delicivite.modele.ModeleItemMenu.DonneesItem;


import ca.delicivite.outils.ClasseUtilitaire;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
            if(ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Ajout de l'item dans le menu", "Voulez-vous vraiment ajouter cet item ?")){
                nomItem.setStyle(" -fx-border-radius: 15px;-fx-background-radius: 15px; -fx-border-color: #424242");
                description.setStyle(" -fx-border-radius: 15px;-fx-background-radius: 15px; -fx-border-color: #424242");
                groupe.setStyle(" -fx-border-radius: 15px;-fx-background-radius: 15px; -fx-border-color: #424242");
                // Ajout du nouvel item à la liste
                items.add(new Item(nom, groupeSelectionne, descriptionTexte));
                // Affichage de la confirmation
                ClasseUtilitaire.afficherPopUp("Succès", "Opération réussie", "L'item' a été ajouté avec succès.", Alert.AlertType.INFORMATION);
                // Fermeture de la fenêtre
                fermer((Node) event.getSource());
            }

        } else {
            // Affichage du message d'erreur
            ClasseUtilitaire.afficherPopUp("Erreur", "Champs incomplets", "Veuillez remplir tous les champs", Alert.AlertType.ERROR);
            if(nomItem.getText().isBlank()) nomItem.setStyle(" -fx-border-radius: 15px;-fx-background-radius: 15px; -fx-border-color: #F44322");
            else if(description.getText().isBlank())  description.setStyle(" -fx-border-radius: 15px;-fx-background-radius: 15px; -fx-border-color: #F44322");
            else if (groupeSelectionne.isBlank() || groupeSelectionne.equals("Choisissez un groupe")) groupe.setStyle(" -fx-border-radius: 15px;-fx-background-radius: 15px; -fx-border-color: #F44322");
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


