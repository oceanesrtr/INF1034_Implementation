/*Classe Modèle : gère les entrées utilisateurs via le modèle patron-observateur*/

package ca.delicivite.patronObservateur;

import java.util.ArrayList;
import java.util.List;

public class ModeleEntree implements Observable {

    private String texte = "";
    private List<Observateur> listeObservateurs = new ArrayList<>();

    //Ajouter des observateurs
    @Override
    public void ajouterObservateur(Observateur o) {
        listeObservateurs.add(o);
    }

    //Les supprimer
    @Override
    public void supprimerObservateur(Observateur o) {
        listeObservateurs.remove(o);
    }

    //Les notifiers des changements des suejts observés
    @Override
    public void notifierObservateurs() {
        for (Observateur o : listeObservateurs) {
            o.mettreAJour();
        }
    }

    //Getters et setters
    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}