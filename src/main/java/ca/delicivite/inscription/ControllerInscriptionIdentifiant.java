package ca.delicivite.inscription;

import ca.delicivite.inscription.historique.HistoriqueNavigation;
import ca.delicivite.outils.ClasseUtilitaire;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerInscriptionIdentifiant implements Initializable {


    /*==================================
     * Injection d'un historique de navigation
     * ==================================*/

    private HistoriqueNavigation historique;

    // Constructeur prenant le gestionnaire de navigation en paramètre
    public ControllerInscriptionIdentifiant(HistoriqueNavigation historique) {
        this.historique = historique;
    }

    // Méthode pour passer à une nouvelle page
    private void passerNouvellePage(String cheminPage) {
        historique.ajouterPage(cheminPage);
        ClasseUtilitaire.changerScene(new ActionEvent(), cheminPage, "Inscription", null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
