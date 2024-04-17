/*Classe ModeleUtilisateur : classe modèe qui gère les données
des utilisateurs en général */
package ca.delicivite.modele;

import ca.delicivite.connexion.ControllerConnexion;
import ca.delicivite.modele.ModeleItemMenu.TypeUtilisateur;
import ca.delicivite.outils.ClasseUtilitaire;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;

public class ModeleUtilisateur {


    //Données essentielles d'un utilisateur
    private String prenom;
    private String nom;
    private TypeUtilisateur typeUtilisateur;
    private String adresseCourriel;
    private String motDePasse;


    //Objet utilisateur unique qui changera en fonction de l'inscription
    private static ModeleUtilisateur objetUtilisateur;

    public ModeleUtilisateur() {
    }

    // Méthode statique pour obtenir l'instance unique de la classe
    public static ModeleUtilisateur getObjetUtilisateur() {
        if (objetUtilisateur == null) {
            objetUtilisateur = new ModeleUtilisateur();
        }
        return objetUtilisateur;
    }

    // Méthode pour réinitialiser les informations d'inscription une fois l'inscription terminée
    public void reinitialiserModeleUtilisateur() {
        this.prenom = null;
        this.nom = null;
        this.typeUtilisateur = null;
        this.adresseCourriel = null;
        this.motDePasse = null;
    }

    //[1] : Inscrit l'utilisateur et l'insère dans la base de données
    public static void inscription(String nom, String prenom, String adresseCourriel, String motDePasse, String typeUtilisateur) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/delicivite", "root", "OceSQL2005.");
            preparedStatement = connection.prepareStatement("INSERT INTO inscription (nom_inscrit, prenom_inscrit, courriel_inscrit, mot_de_passe_inscrit, type_utilisateur) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, adresseCourriel);
            preparedStatement.setString(4, motDePasse);
            preparedStatement.setString(5, typeUtilisateur);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // ORDRE 1 : FERMER LES PREPARED STATEMENT
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // ORDRE 3 : CONNECTION
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //[2] Authentifie et redirige l'utilisateur vers la page d'accueil correspondant à son type
    public static void clientSeConnecte(ActionEvent event, String courriel, String motDePasse) {

        //Validation
        if (courriel.isEmpty() || motDePasse.isEmpty()) {
            ClasseUtilitaire.afficherPopUp("Erreur", "Erreur lors de la tentative de connexion", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Chargement explicite de la classe du pilote JDBC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/delicivite", "root", "OceSQL2005.");

            //Sélectionner le type de l'utilisateur
            preparedStatement = connection.prepareStatement("SELECT type_utilisateur FROM inscription WHERE courriel_inscrit = ? AND mot_de_passe_inscrit = ?");
            /*preparedStatement = connection.prepareStatement("SELECT mot_de_passe_inscrit FROM inscription WHERE courriel_inscrit =?");*/
            preparedStatement.setString(1, courriel);
            preparedStatement.setString(2, motDePasse);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("L'utilisateur n'a pas été retrouvé dans la base de données");
                ClasseUtilitaire.afficherPopUp("Erreur", "Erreur lors la tentative de connexion", "Le courriel ou le mot de passe est incorrect.", Alert.AlertType.ERROR);

            } else {
                while (resultSet.next()) {
                    String typeUtilisateur = resultSet.getString("type_utilisateur");
                    switch (typeUtilisateur) {
                        case "proprietaire":
                            // Redirection vers la page d'accueil du propriétaire
                            ClasseUtilitaire.changerScene(event, "accueilProprietaire.fxml", "ControllerAccueil Propriétaire", courriel);
                            break;

                        case "client":
                            // Redirection vers la page d'accueil du client
                            ClasseUtilitaire.changerScene(event, "/ca/delicivite/clientConnecteAccueil/VueClientConnecteAccueil.fxml", "ControllerAccueil", courriel);
                            break;
                        case "livreur":
                            // Redirection vers la page d'accueil du livreur
                            ClasseUtilitaire.changerScene(event, "accueilLivreur.fxml", "ControllerAccueil Livreur", courriel);
                            break;

                        case "employe":
                            // Redirection vers la page d'accueil du proprietare
                            ClasseUtilitaire.changerScene(event, "/ca/delicivite/proprietaire/VueAccueil.fxml", "Accueil Propriétaire", courriel);
                            break;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // ORDRE 1 : FERMER LES RESULTSET
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // ORDRE 2 : FERMER LES PREPARED STATEMENT
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // ORDRE 3 : CONNECTION
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*===============================================
    * [3] Vérifier si l'email de l'inscrit existe deja dans la base de données
    * Pour les interfaces inscriptions
    * Retourne un booléen
    =================================================
     */

    public static boolean verifierEmail(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        boolean emailExiste = false;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/delicivite", "root", "OceSQL2005.");
            preparedStatement = connection.prepareStatement("SELECT courriel_inscrit FROM inscription WHERE courriel_inscrit = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            emailExiste = resultSet.next(); // Si resultSet.next() retourne true, l'e-mail existe

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return emailExiste;
    }

    /*==============================================
    4] : Vérifie si l'e-mail existe dans la base de données pour l'interface mot de passe oublié
    Retourne : void
    =================================================*/
    public static void verifierEmailMotDePasseOublie(ActionEvent event, String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/delicivite", "root", "OceSQL2005.");
            preparedStatement = connection.prepareStatement("SELECT courriel_inscrit FROM inscription WHERE courriel_inscrit = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // L'e-mail existe dans la base de données, redirigez l'utilisateur vers une autre scène
                ClasseUtilitaire.changerScene(event, "/ca/delicivite/motDePasseOublieConfirmation/VueMDPOublieEmailValideMoyenne.fxml", "Confirmation", email);
            } else
                ClasseUtilitaire.afficherPopUp("Erreur", "E-mail incorrect", "L'e-mail entré n'est associé à aucun compte", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*=============================
     * Getter et setter
     * ============================*/
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    public String getAdresseCourriel() {
        return adresseCourriel;
    }

    public void setAdresseCourriel(String adresseCourriel) {
        this.adresseCourriel = adresseCourriel;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public TypeUtilisateur getTypeUtilisateur() {
        return typeUtilisateur;
    }

    public void setTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }
}

