package ca.delicivite.inscription.historique;

import java.util.Stack;

/*Classe pour garder un historique des pages naviguées */
public class HistoriqueNavigation {

    //String : chemin fxml
    private Stack<String> historiquePages = new Stack<>();

    public void ajouterPage(String page) {
        historiquePages.push(page);
    }

    public String getPagePrecedente() {
        if (!historiquePages.isEmpty()) {
            return historiquePages.pop();
        } else {
            return null;
        }
    }
}
