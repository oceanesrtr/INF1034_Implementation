package ca.delicivite.proprietaire;


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
            // Affichage d'une alerte si le nom du groupe existe déjà
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Ce nom de groupe existe déjà.");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/images/logo_fond_grise.png"));
            alert.showAndWait();
        }
        // Vérification si le nom du groupe est vide
        else if (nomGroupe.equals("")) {
            // Affichage d'une alerte si le nom du groupe est vide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un nom valide.");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/images/logo_fond_grise.png"));
            alert.showAndWait();
        } else {
            // Ajout du nouveau groupe à la liste des groupes
            listeGroupes.add(nomGroupe);
            nouveauGroupe.setText("");
        }
    }

    /*=========================================================================
    [4] Méthode pour supprimer un groupe
    * ========================================================================*/
    @FXML
    private void onSupprimerGroupe(ActionEvent event) {
        // Récupération du groupe sélectionné
        String selectedItem = groupe.getSelectionModel().getSelectedItem();
        // Suppression du groupe de la liste des groupes
        if (selectedItem != null) {
            listeGroupes.remove(selectedItem);
        }
    }
}
