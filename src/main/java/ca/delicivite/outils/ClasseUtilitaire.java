package ca.delicivite.outils;

/*
INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe utilitaire : regroupe des méthodes utilitaires réutilisables */


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ClasseUtilitaire {

    /*===============================================================
    Méthode 1 : permet de changer de scène suite à une certaine action
    ================================================================== */
    public static void changerScene(ActionEvent event, String fxmlFile, String titreFenêtre, String courrielClientConnecte) {
        Parent root = null;

        // Passage du courriel en paramètres car permet d'identifier l'utilisateur
        if (courrielClientConnecte != null) {
            try {
                FXMLLoader loaderFichierFXML = new FXMLLoader(ClasseUtilitaire.class.getResource(fxmlFile));
                root = loaderFichierFXML.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(ClasseUtilitaire.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // Créer une nouvelle scène
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(titreFenêtre);
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/images/logo_court_blanc.png"));
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    /*==========================================================================================
    Méthode 2 : change la taille de la police en chargeant une nouvelle scène FXML
    * ==========================================================================================*/
    public static void changerTaillePolice(String nomFichierFXML, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(ClasseUtilitaire.class.getResource(nomFichierFXML));
            Parent nouvelleRacine = loader.load();
            Scene nouvelleScene = new Scene(nouvelleRacine);
            stage.setScene(nouvelleScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*==============================================================================
     * Méthode 3 : affiche une fenêtre pop up personnalisée
     * =============================================================================*/
    public static void afficherPopUp(String titre, String sousTitre, String message, Alert.AlertType typeAlerte) {
        Alert alerte = new Alert(typeAlerte);
        alerte.setTitle(titre);
        alerte.setHeaderText(sousTitre);
        alerte.setContentText(message);
        Stage stage = (Stage) alerte.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/images/logo_fond_grise.png"));
        stage.centerOnScreen();
        alerte.showAndWait();
    }


    /*===================================================================================
     * Méthode 4 : affiche une fenêtre pop up de confirmation avec des boutons oui et non,
     * retourne un booléen
     * ===================================================================================*/

    public static boolean afficherPopUpConfirmation(String titre, String sousTitre, String message) {
        Alert popUpConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        popUpConfirmation.setTitle(titre);
        popUpConfirmation.setHeaderText(sousTitre);
        popUpConfirmation.setContentText(message);
        Stage stage = (Stage) popUpConfirmation.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/images/logo_fond_grise.png"));
        stage.centerOnScreen();

        // Boutons oui et non
        ButtonType boutonOui = new ButtonType("Oui");
        ButtonType boutonAnnuler = new ButtonType("Annuler");

        popUpConfirmation.getButtonTypes().setAll(boutonOui, boutonAnnuler);

        // Retourner la réponse de l'utilisateur
        Optional<ButtonType> reponse = popUpConfirmation.showAndWait();
        return reponse.isPresent() && reponse.get() == boutonOui;
    }

    /*===================================================
     * Méthode 5 : redirection vers un site Internet
     * ================================================
     */
    public static void redirectionSiteInternet(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            afficherPopUp("Erreur", "Impossible d'ouvrir la page web.", "Veuillez vérifier votre connexion Internet et réessayer.", Alert.AlertType.ERROR);
        }
    }
}
