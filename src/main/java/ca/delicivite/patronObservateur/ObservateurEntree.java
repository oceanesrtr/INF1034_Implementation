package ca.delicivite.patronObservateur;

/* INF1034 - Devoir de fin de session hiver 2024
Implémentation du système Delicivite par
Océane RAKOTOARISOA
Julien Desrosiers
Lily Occhibelli
Ce : 23 avril 2024

Patron observateur : surveille les changements dans les champs
de texte de l'interface connexion (mot de passe et courriel) et
déclenche l'annulation et le rétablissement des champs quand ces
champs changent. */

public class ObservateurEntree implements Observateur {

    private ModeleEntree modele;
    private String textePrecedent = "";

    public ObservateurEntree(ModeleEntree modele) {
        this.modele = modele;
    }

    @Override
    public void mettreAJour() {
        // Réagir aux changements dans le champ de texte
        String nouveauTexte = modele.getTexte();

        // Annuler le changement si le nouveau texte est différent du texte précédent
        if (!nouveauTexte.equals(textePrecedent)) {
            textePrecedent = nouveauTexte;
            modele.setTexte(textePrecedent);
            System.out.println("Changement annulé : " + textePrecedent);
        } else {
            System.out.println("Pas de changement à annuler");
        }
    }
}