package ca.delicivite.modele.ModeleItemMenu;
/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe Modele : modélise les données concernant les item (produits affichés au menu)*/

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DonneesItem {

    // Items du menu
    private static ObservableList<Item> items;

    //groupes du menu
    private static ObservableList<String> groupes;

    /*=========================================================================
    [1] Méthode permettant de créer des items de base lorsqu'on lance l'interface
    * ========================================================================*/
    private static void creerItems() {
        items = FXCollections.observableArrayList();
        groupes = FXCollections.observableArrayList();
        String groupe1 = "Souper";
        String groupe2 = "Déjeuner";
        groupes.add(groupe1);
        groupes.add(groupe2);
        items.add(new Item("Hamburger de boeuf", groupe1, "Boulette de boeuf, pain, Tomate"));
        items.add(new Item("Hamburger de poulet", groupe1, "Boulette de poulet, pain, Tomate"));

        items.add(new Item("Sandwich déjeuner", groupe2, "Bacon, fromage, pain campagnard, Tomate"));
        items.add(new Item("Classique déjeuner", groupe2, "Oeufs, bacon, Patate"));
    }


    /*=========================================================================
    [2] Méthode pour obtenir la liste des items du menu
    * ========================================================================*/
    public static ObservableList<Item> getItemsMenu() {
        if (items == null) {
            creerItems();
        }

        return items;
    }

    /*=========================================================================
    [3] Méthode pour obtenir la liste des groupes du menu
    * ========================================================================*/
    public static ObservableList<String> getGroupes() {
        if (groupes == null) {
            creerItems();
        }

        return groupes;
    }
}