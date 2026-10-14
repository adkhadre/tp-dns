package fr.uvsq.saclay.m1.info;

/**
 * Représente une commande de l'application DNS (patron Command).
 */
public interface Commande {

  /**
   * Exécute la commande.
   *
   * @return le résultat de l'exécution (par exemple une adresse IP, un nom de machine ou une liste
   *     d'associations), ou null si la commande ne produit aucun résultat
   */
  Object execute();
}