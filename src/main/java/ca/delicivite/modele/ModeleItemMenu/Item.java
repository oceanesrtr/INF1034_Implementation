package ca.delicivite.modele.ModeleItemMenu;

// Classe représentant les items du menu

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