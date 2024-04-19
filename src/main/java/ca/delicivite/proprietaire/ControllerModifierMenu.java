package ca.delicivite.proprietaire;


import ca.delicivite.outils.ClasseUtilitaire;
import javafx.fxml.Initializable;

import ca.delicivite.modele.ModeleItemMenu.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControllerModifierMenu implements Initializable {


    // Champ pour le nom du nouveau groupe
    @FXML
    private TextField nouveauGroupe;

    // Choix du groupe
    @FXML
    private ChoiceBox<String> groupe;

    // Liste des groupes
    private ObservableList<String> listeGroupes;

    /*=========================================================================
    [1] Constructeur
    * ========================================================================*/
    public ControllerModifierMenu() {
        listeGroupes = DonneesItem.getGroupes();
    }

    /*=========================================================================
    [2] Initialize au démarrage de la scène
    * ========================================================================*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupe.setItems(listeGroupes);
    }

    /*@FXML
    public void initialize() {
        groupe.setItems(listeGroupes);
    }*/

    /*=========================================================================
    [3] Méthode pour ajouter un nouveau groupe
    * ========================================================================*/
    @FXML
    public void onAjoutGroupe() {
        // Récupération du nom du nouveau groupe
        String nomGroupe = nouveauGroupe.getText();

        // Vérification si le nom du groupe existe déjà
        if (listeGroupes.contains(nomGroupe)) {
            ClasseUtilitaire.afficherPopUp("Erreur", "Groupe déjà existant", "Veuillez choisir un autre nom de groupe.", Alert.AlertType.ERROR);
        }
        // Vérification si le nom du groupe est vide
        else if (nomGroupe.equals("")) {
            // Affichage d'une alerte si le nom du groupe est vide
            ClasseUtilitaire.afficherPopUp("Erreur", "Champs incomplets", "Veuillez entrer un nom valide.", Alert.AlertType.ERROR);

        } else {
            // Confirmation de l'ajout
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Ajout du groupe", "Voulez-vous vraiment ajouter ce groupe ?")) {
                // Ajout du nouveau groupe à la liste des groupes
                listeGroupes.add(nomGroupe);
                nouveauGroupe.setText("");
                // Affichage de la confirmation
                ClasseUtilitaire.afficherPopUp("Succès", "Opération réussie", "Le groupe a été ajouté avec succès.", Alert.AlertType.INFORMATION);

            }


        }
    }

    /*=========================================================================
    [4] Méthode pour supprimer un groupe
    * ========================================================================*/
    @FXML
    private void onSupprimerGroupe(ActionEvent event) {

        //Validation 2 : si l'utilisateur a choisi un groupe
        if (groupe.getValue() == null) {
            ClasseUtilitaire.afficherPopUp("Erreur", "Champs incomplets", "Veuillez choisir un groupe.", Alert.AlertType.ERROR);
        } else {
            // Récupération du groupe sélectionné
            String selectedItem = groupe.getSelectionModel().getSelectedItem();
            // Confirmation de la suppression
            if (ClasseUtilitaire.afficherPopUpConfirmation("Confirmation", "Suppression du groupe", "Voulez-vous vraiment supprimer ce groupe ?")) {
                // Suppression du groupe de la liste des groupes
                if (selectedItem != null) {
                    listeGroupes.remove(selectedItem);
                    // Affichage de la confirmation
                    ClasseUtilitaire.afficherPopUp("Succès", "Opération réussie", "Le groupe a été supprimé avec succès.", Alert.AlertType.INFORMATION);
                }
            }
        }
    }
}
