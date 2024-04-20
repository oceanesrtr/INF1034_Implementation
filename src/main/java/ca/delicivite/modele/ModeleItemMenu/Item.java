package ca.delicivite.modele.ModeleItemMenu;

/*INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Classe Modele qui regroupe les attributs d'un item en général (item de menu)  */

public class Item {


    private String nomItem;

    private String groupe;

    private String description;


    public Item(String nomItem, String groupe, String description) {
        this.nomItem = nomItem;
        this.groupe = groupe;
        this.description = description;

    }

    public String getNomItem() {
        return nomItem;
    }

    public void setNomItem(String nomItem) {
        this.nomItem = nomItem;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return nomItem + "   '" + groupe + "'";
    }


}