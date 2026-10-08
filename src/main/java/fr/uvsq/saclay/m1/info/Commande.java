package fr.uvsq.saclay.m1.info;


public interface Commande {

    /**
     * Exécute la commande.
     *
     * @return le résultat de l'exécution
     */
    Object execute();
}