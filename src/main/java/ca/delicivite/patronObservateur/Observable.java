package ca.delicivite.patronObservateur;

/*Patron observateur : interface observable implémentées pour tout objet observables*/

public interface Observable {
    void ajouterObservateur(Observateur o);
    void supprimerObservateur(Observateur o);
    void notifierObservateurs();
}