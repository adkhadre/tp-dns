package fr.uvsq.saclay.m1.info;

/**
 * Commande permettant de quitter l'application DNS.
 */
public class Quitter implements Commande {

    /**
     * Exécute la commande de sortie.
     *
     * @return true pour indiquer qu'il faut quitter
     */
    @Override
    public Object execute() {
        return true;
    }
}