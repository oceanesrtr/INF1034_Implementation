module ca.delicivite {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires java.sql;

    /*=======================================================
    * Ouvrir et exporter chaque sous-package (java et resources)
    * =======================================================*/
    opens ca.delicivite to javafx.fxml;
    exports ca.delicivite;

    exports ca.delicivite.connexion;
    opens ca.delicivite.connexion to javafx.fxml;

    exports ca.delicivite.motDePasseOublieConfirmation;
    opens ca.delicivite.motDePasseOublieConfirmation to javafx.fxml;

    exports ca.delicivite.motDePasseOublie;
    opens ca.delicivite.motDePasseOublie to javafx.fxml;

    exports ca.delicivite.modele;
    opens ca.delicivite.modele to javafx.fxml;

    exports ca.delicivite.modele.ModeleItemMenu;
    opens ca.delicivite.modele.ModeleItemMenu to javafx.fxml;

    exports ca.delicivite.outils;
    opens ca.delicivite.outils to javafx.fxml;

    exports ca.delicivite.patronObservateur;
    opens ca.delicivite.patronObservateur to javafx.fxml;

    exports ca.delicivite.clientConnecteAccueil;
    opens ca.delicivite.clientConnecteAccueil to javafx.fxml;

    exports ca.delicivite.proprietaire;
    opens ca.delicivite.proprietaire to javafx.fxml;

    exports ca.delicivite.inscription;
    opens ca.delicivite.inscription to javafx.fxml;

    exports ca.delicivite.inscription.inscriptionClient;
    opens ca.delicivite.inscription.inscriptionClient to javafx.fxml;

    exports ca.delicivite.inscription.inscriptionProprietaire;
    opens ca.delicivite.inscription.inscriptionProprietaire to javafx.fxml;

    exports ca.delicivite.inscription.inscriptionLivreur;
    opens ca.delicivite.inscription.inscriptionLivreur to javafx.fxml;

}
